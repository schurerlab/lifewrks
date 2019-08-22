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

/**
 * 
 * @author saradhye
 *
 */
public class FetchViewDataKinomeScan extends HttpServlet {
	String url = "";

	JSONObject docListObj = new JSONObject();
	JSONObject docList = new JSONObject();
	JSONArray docs;

	String parCoordStr = "";
	String searchTerm = "";
	String tempTest = "";
	String min = "";
	String max = "";

	String Status = "Success!";

	public void init(ServletConfig config) throws ServletException {
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");

		JSONObject mainCategoryJsonObj = new JSONObject();
		JSONObject mainCategoryFacetObj = new JSONObject();

		JSONArray mainCategoryArr = new JSONArray();
		String mainCategoryStr = "";

		JSONArray lsmIdArr = new JSONArray();
		String lsmStr = "";
		
		JSONObject perturbationIdJsonObj = new JSONObject();
		JSONObject perturbationIds = new JSONObject();
		JSONArray perturbationIdGrps = new JSONArray();

		String finalOutput = "";

		searchTerm = URLEncoder.encode(request.getParameter("q"),"UTF-8");

		parCoordStr += "jsonCallback ({ " + '"' + "data" + '"' + ": [";

		try {

			// get celline headers and put it in cellLineArr array
			mainCategoryJsonObj = getMainCategories(searchTerm);
			mainCategoryFacetObj = mainCategoryJsonObj.getJSONObject("facet_counts").getJSONObject("facet_fields");
			 

			mainCategoryArr = mainCategoryFacetObj.getJSONArray("MainCategory");
			lsmIdArr = mainCategoryFacetObj.getJSONArray("LincsSMId");

			for (int i = 0; i < mainCategoryArr.size(); i += 2) {
				mainCategoryStr += mainCategoryArr.getString(i).replaceAll(" ", "_");
				
				if (i < mainCategoryArr.size() - 2) {
					mainCategoryStr += ",";
				}
			}
			for (int j = 0; j < lsmIdArr.size(); j += 2) {
				lsmStr += '"' + lsmIdArr.getString(j) + '"';

				if (j < lsmIdArr.size() - 2) {
					lsmStr += "||";
				}
			}
			tempTest = mainCategoryStr;
			
			 min  = mainCategoryJsonObj.getJSONObject("stats").getJSONObject("stats_fields").getJSONObject("PerturbationMeasure").getString("min");
			 max  = mainCategoryJsonObj.getJSONObject("stats").getJSONObject("stats_fields").getJSONObject("PerturbationMeasure").getString("max");
			
			
          
			// get perturbation ids
			perturbationIdJsonObj = getPerturbationIdData(searchTerm, mainCategoryStr,lsmStr);
			perturbationIds = perturbationIdJsonObj.getJSONObject("grouped").getJSONObject("PerturbationId");
			perturbationIdGrps = perturbationIds.getJSONArray("groups");

			// get data for parallel coordinates chart
			finalOutput = getDataForParCoord(perturbationIdGrps);

			response.getWriter().write(finalOutput);
			response.flushBuffer();

			parCoordStr = "";

		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


    
    
    
	private JSONObject getMainCategories(String q) throws SolrServerException,
			MalformedURLException {

		URLConnection urlConn = null;
		JSONObject obj = null;

		url = "http://baoquery.ccs.miami.edu/solr-example/select?q=" + q;
		url += "&wt=json&rows=0&stats=true&stats.field=PerturbationMeasure";
		url += "&facet=true&facet.field=LincsSMId&facet.field=MainCategory&facet.mincount=1&facet.limit=-1";
		
		try {
			urlConn = new URL(url).openConnection();
			urlConn.setRequestProperty("Accept-Charset", "UTF-8");

			BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

			String line = null;
			String jsonResponse = "";
			while ((line = br.readLine()) != null) {
				jsonResponse += line;
			}
			obj = JSONObject.fromObject(jsonResponse);

		} catch (MalformedURLException e) {
			Status = "Failure!";
			e.printStackTrace();
		} catch (IOException e) {
			Status = "Failure!";
			e.printStackTrace();
		}
		return obj;
	}

	private JSONObject getPerturbationIdData(String q, String mainCategory,
			String lsmIds) throws SolrServerException, MalformedURLException {

		JSONObject jsonObj = null;

		url = "http://baoquery.ccs.miami.edu/kinomescan-solr/select?";
		url += "&wt=json&rows=-1";
		url += "&group=true&group.field=PerturbationId";

		try {

			URL obj = new URL(url);
			HttpURLConnection urlConn = (HttpURLConnection) obj.openConnection();

			String urlParameters = "q=" + lsmIds;
			urlParameters += "&fl=LincsSMId,SmallMoleculeName,TimePoint,SMCategories,"+ mainCategory;
			
			urlConn.setRequestMethod("POST");
			urlConn = (HttpURLConnection) new URL(url).openConnection();
			
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
			Status = "Failure!";
			e.printStackTrace();
		} catch (IOException e) {
			Status = "Failure!";
			e.printStackTrace();
		}
		
		return jsonObj;
	}

	private String getDataForParCoord(JSONArray perturbationIdData) throws IOException {
		
		JSONObject currentDoc = new JSONObject();

		for (int j = 0; j < perturbationIdData.size(); j++) {

			// get the currents document
			docListObj = perturbationIdData.getJSONObject(j);
			docList = docListObj.getJSONObject("doclist");
			docs = docList.getJSONArray("docs");

			currentDoc = docs.getJSONObject(0);
			parCoordStr += currentDoc;

			if (j < perturbationIdData.size() - 1) {
				parCoordStr += ",";
			}
		}
		

		String[] categoryArray = tempTest.split(","); 
	  	  parCoordStr  += ",{\"LincsSMId\":\"Zmin\",\"SMCategories\":\"\",\"TimePoint\":\"1\",\"SmallMoleculeName\":\"\",";
			for (int i = 0; i<categoryArray.length;i++ ){
			parCoordStr  += "\""+categoryArray[i]+"\":\""+min+"\"";
				if (i<categoryArray.length-1){
					parCoordStr  +=",";
				}else{
					parCoordStr  +="},";
				}
			}
			parCoordStr  += "{\"LincsSMId\":\"Zmax\",\"SMCategories\":\"\",\"TimePoint\":\"1\",\"SmallMoleculeName\":\"\",";
			for (int i = 0; i<categoryArray.length;i++ ){
			parCoordStr  += "\""+categoryArray[i]+"\":\""+max+"\"";
				if (i<categoryArray.length-1){
					parCoordStr  +=",";
				}else{
					parCoordStr  +="}";
				}
			}
			String strWithoutQuotes = searchTerm.replace("\"", "");
		parCoordStr += "]," + '"' + "q" + '"' + ":" + '"' + strWithoutQuotes
				+ '"' + "," + '"' + "status" + '"' + ":" + '"' + Status + '"'
				+ "});";
		
		return parCoordStr;
	}
}
