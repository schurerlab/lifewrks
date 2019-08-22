package edu.miami.ccs.life;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.solr.client.solrj.SolrServerException;

public class FetchViewDataJson extends HttpServlet {
	String url = "";
	
	JSONObject docListObj = new JSONObject();
	JSONObject docList = new JSONObject();
	JSONArray docs ;
	
	String parCoordStr = "";
	String searchTerm = "";
	
	int noOfRows = -1;
	
	public void init(ServletConfig config) throws ServletException {
	}

	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");

		JSONObject cellLineNameJsonObj = new JSONObject();
		JSONObject cellLineFacetObject = new JSONObject();
		
		JSONArray  cellLineNameArr = new JSONArray();
		String cellLinestr = "";
		
		JSONArray  lsmIdArr = new JSONArray();
		String lsmStr = "";
		
		JSONObject signatureIdJsonObj = new JSONObject();
		JSONObject signatureIds = new JSONObject();
		JSONArray  signatureIdGrps = new JSONArray();
		
		String finalOutput = "";

		searchTerm = URLEncoder.encode(request.getParameter("q"));
		
		parCoordStr += "jsonCallback ({ " + '"' + "data" + '"' + ": [" ;
		
		try {
			
			// get celline headers and put it in cellLineArr array
			cellLineNameJsonObj = getCellLineHeaders(searchTerm);

			cellLineFacetObject = cellLineNameJsonObj.getJSONObject("facet_counts").getJSONObject("facet_fields");
			cellLineNameArr = cellLineFacetObject.getJSONArray("CellLineName");
			lsmIdArr = cellLineFacetObject.getJSONArray("LincsSMId");
			
			for(int i = 0 ; i < cellLineNameArr.size() ; i += 2){
				cellLinestr += cellLineNameArr.getString(i);
				
				if(i < cellLineNameArr.size()-2){
					cellLinestr += ",";
				}
			}
			for(int j = 0 ; j < lsmIdArr.size() ; j += 2){
				lsmStr += '"' + lsmIdArr.getString(j) + '"';
				
				if(j < lsmIdArr.size()-2){
					lsmStr += "||";
				}
			}
			
			// get signature ids 
			signatureIdJsonObj = getSignatureIdData(searchTerm,cellLinestr,lsmStr);
			
			signatureIds = signatureIdJsonObj.getJSONObject("grouped").getJSONObject("PerturbationId");
			signatureIdGrps = signatureIds.getJSONArray("groups");

			//get data for parallel coordinates chart
			finalOutput = getDataForParCoord(signatureIdGrps,cellLineNameArr);
			
			response.getWriter().write(finalOutput);
			response.flushBuffer();
			
			parCoordStr = "";
			
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private JSONObject getCellLineHeaders(String q) throws SolrServerException,
	MalformedURLException {

		URLConnection urlConn = null;
		JSONObject obj = null;
		
		url =  "http://lincs.ccs.miami.edu:8080/solr-example/select?q=" + q;
		url += "&wt=json&indent=true&rows=-1";
		url += "&facet=true&facet.field=LincsSMId&facet.field=CellLineName&facet.mincount=1&facet.limit=10000";
		
		try {
			urlConn = new URL(url).openConnection();
			urlConn.setRequestProperty("Accept-Charset", "UTF-8");
			urlConn.setUseCaches(false);
		
			BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
		
			String line = null;
			String jsonResponse = "";
			while ((line = br.readLine()) != null) {
				jsonResponse += line;
			}
			obj = JSONObject.fromObject(jsonResponse);
		
		} catch (MalformedURLException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return obj;
		
	}
	
	private JSONObject getSignatureIdData(String q, String cellLines , String lsmIds) throws SolrServerException,
			MalformedURLException {

		/*URLConnection urlConn = null;*/
		JSONObject jsonObj = null;

		url = "http://lincs.ccs.miami.edu:8080/data-solr/select?";
		url += "&wt=json" + "&rows=-1";
		url += "&group=true&group.field=PerturbationId";

		try {
			
			URL obj = new URL(url);
			HttpURLConnection urlConn = (HttpURLConnection) obj.openConnection();
	 
			String urlParameters = "q=" + lsmIds;
			urlParameters += "&fl=SmallMoleculeName,TimePoint,SMCategories," + cellLines ;
			
			//add reuqest header
			urlConn.setRequestMethod("POST");
			
			urlConn =  (HttpURLConnection) new URL(url).openConnection();
			urlConn.setRequestProperty("Accept-Charset", "UTF-8");

			// Send post request
			urlConn.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(urlConn.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

			String line = null;
			String jsonResponse = "";
			while ((line = br.readLine()) != null) {
				jsonResponse += line;
			}
			jsonObj = JSONObject.fromObject(jsonResponse);

		} catch (MalformedURLException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("End of method");
		return jsonObj;
	}

	private String getDataForParCoord(JSONArray signatureIdData,JSONArray cellLineName) throws IOException{
		JSONObject currentDoc = new JSONObject();
		
		for(int j = 0 ; j <signatureIdData.size(); j++){
		
			//get the currents document
			docListObj = signatureIdData.getJSONObject(j);
			docList = docListObj.getJSONObject("doclist");
			docs = docList.getJSONArray("docs");
			
			currentDoc = docs.getJSONObject(0);
			parCoordStr += currentDoc;
			
			if(j < signatureIdData.size()-1){
				parCoordStr += ","; 
			}
		}	
		String strWithoutQuotes = searchTerm.replace("\"", "");
		
		parCoordStr += "]," + '"'+ "q" + '"' + ":" + '"' + strWithoutQuotes + '"' + "," + '"' + "status" + '"' + ":" + '"' + "Success!" + '"' + "});" ;
		return parCoordStr;
	}
}
