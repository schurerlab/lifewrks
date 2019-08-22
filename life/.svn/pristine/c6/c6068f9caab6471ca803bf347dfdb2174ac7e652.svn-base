package edu.miami.ccs.life;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

public class FetchViewData extends HttpServlet {
	String url = "";
	
	JSONObject docListObj = new JSONObject();
	JSONObject docList = new JSONObject();
	JSONArray docs ;
	JSONArray  CellLineInCurrObj = new JSONArray();
	String parCoordStr = "";
	String searchTerm = "";
	int noOfRows = 100;
	boolean cellLineMatchFound = false ;
	
	public void init(ServletConfig config) throws ServletException {
	}

	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");

		JSONObject cellLineNameJsonObj = new JSONObject();
		JSONObject cellLineNames = new JSONObject();
		JSONArray  cellLineNameGrps = new JSONArray();
		JSONArray  cellLineNameArr = new JSONArray();
		JSONObject currentCellLineName = new JSONObject();
		
		JSONObject signatureIdJsonObj = new JSONObject();
		JSONObject signatureIds = new JSONObject();
		JSONArray  signatureIdGrps = new JSONArray();
		
		String finalOutput = "";

		searchTerm = URLEncoder.encode(request.getParameter("q"));
		
		parCoordStr += "jsonCallback ({ " + '"' + "data" + '"' + ": [" ;
		
		try {
			
			// get celline headers and put it in cellLineArr array
			cellLineNameJsonObj = getCellLineHeaders(searchTerm);

			cellLineNames = cellLineNameJsonObj.getJSONObject("grouped").getJSONObject("CellLineName");
			cellLineNameGrps = cellLineNames.getJSONArray("groups");
			
			for(int j = 0 ; j <cellLineNameGrps.size(); j++){
				docListObj = cellLineNameGrps.getJSONObject(j);
				docList = docListObj.getJSONObject("doclist");
				docs = docList.getJSONArray("docs");
				currentCellLineName = docs.getJSONObject(0);
				cellLineNameArr.add(currentCellLineName.getString("CellLineName"));
			}
			
			// get signature ids 
			signatureIdJsonObj = getSignatureIdData(searchTerm);
			
			signatureIds = signatureIdJsonObj.getJSONObject("grouped").getJSONObject("SignatureId");
			signatureIdGrps = signatureIds.getJSONArray("groups");

			//get data for parallel coordinates chart
			finalOutput = getDataForParCoord(cellLineNameArr,signatureIdGrps);
			
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
		
		url =  "http://baoquery.ccs.miami.edu/solr-example/select?q=" + q;
		url += "&rows="+noOfRows+"&wt=json&indent=true";
		url += "&group=true&group.ngroups=true&group.field=CellLineName";
		url += "&sort=LincsSMId+desc,TimePoint+desc&fl=CellLineName";
		
		System.out.println("getCellLineHeaders URL = " + url);
		try {
			urlConn = new URL(url).openConnection();
			urlConn.setRequestProperty("Accept-Charset", "UTF-8");
		
			BufferedReader br = new BufferedReader(new InputStreamReader(
					urlConn.getInputStream()));
		
			String line = null;
			String jsonResponse = "";
			while ((line = br.readLine()) != null) {
				jsonResponse += line;
			}
			obj = JSONObject.fromObject(jsonResponse);
		
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
		
		return obj;
	}
	
	private JSONObject getSignatureIdData(String q) throws SolrServerException,
			MalformedURLException {

		URLConnection urlConn = null;
		JSONObject obj = null;

		url = "http://baoquery.ccs.miami.edu/solr-example/select?q=" + q;
		url += "&wt=json&indent=true" + "&rows=" + noOfRows;
		url += "&group=true&group.field=SignatureId&group.ngroups=true";
		url += "&sort=LincsSMId+desc,TimePoint+desc";
		url += "&fl=TimePoint,CellLineName,SmallMoleculeName,LincsSMId,SMCategories,MolecularWeight";

		System.out.println("getParCoordsData URL = "+url);
		try {
			urlConn = new URL(url).openConnection();
			urlConn.setRequestProperty("Accept-Charset", "UTF-8");

			BufferedReader br = new BufferedReader(new InputStreamReader(
					urlConn.getInputStream()));

			String line = null;
			String jsonResponse = "";
			while ((line = br.readLine()) != null) {
				jsonResponse += line;
			}
			obj = JSONObject.fromObject(jsonResponse);

		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}

		return obj;
	}

	private String getDataForParCoord(JSONArray cellLineHeaders, JSONArray signatureIdData) throws IOException{
		String currentLSMId = "" ,prevLSMId = "",currentTP = "",prevTP = "";
		int prevLSMCnt = 0;
		
		JSONObject currentDoc = new JSONObject();
		JSONObject prevDoc = new JSONObject();
		boolean newLine = false;
		
		for(int j = 0 ; j <signatureIdData.size(); j++){
			
			//get the currents document
			docListObj = signatureIdData.getJSONObject(j);
			docList = docListObj.getJSONObject("doclist");
			docs = docList.getJSONArray("docs");
			
			currentDoc = docs.getJSONObject(0);
			currentLSMId = currentDoc.getString("LincsSMId");
			currentTP = currentDoc.getString("TimePoint");
			
			//get the prev document
			if( j != 0 ){
				prevLSMCnt = j - 1;
				docListObj = signatureIdData.getJSONObject(prevLSMCnt);
				docList = docListObj.getJSONObject("doclist");
				docs = docList.getJSONArray("docs"); 
				
				prevDoc = docs.getJSONObject(0);
				prevLSMId = prevDoc.getString("LincsSMId");
				prevTP = prevDoc.getString("TimePoint");
			}
			
			if( (currentLSMId.equals(prevLSMId) && !currentTP.equals(prevTP)) || !currentLSMId.equals(prevLSMId)){
				newLine =  true;
			}else{
				newLine =  false;
			}
			
			if(newLine || j == 0){
				if(j !=0){
					//get celllines with no value and put it in json with "" value
					getMissingCellLines(cellLineHeaders,CellLineInCurrObj);
					
					CellLineInCurrObj = new JSONArray();
					
					//end the json object
					parCoordStr += "},";
				}
				parCoordStr += "{";
				
				parCoordStr += '"' + "CompoundName" + '"' + ":" + '"' +currentDoc.getString("SmallMoleculeName") + '"' + ","; 
				parCoordStr += '"' + "TimePoint" + '"' + ":" + '"'+ currentDoc.getString("TimePoint") + '"' + ","; 
				parCoordStr += '"' + "branch" + '"' + ":" + '"'+ currentDoc.getString("SMCategories") + '"' + ",";
			}
			if(!newLine){
				parCoordStr += ",";
			}
			parCoordStr += '"' + currentDoc.getString("CellLineName") +'"' + ":" + '"' + currentDoc.getString("MolecularWeight") + '"';
			CellLineInCurrObj.add(currentDoc.getString("CellLineName"));
		}
		//call this method here for adding missing cellines in json for the last record
		getMissingCellLines(cellLineHeaders,CellLineInCurrObj);
		
		String strWithoutQuotes = searchTerm.replace("\"", "");
		
		parCoordStr += "}]," + '"'+ "q" + '"' + ":" + '"' + strWithoutQuotes + '"' + "," + '"' + "status" + '"' + ":" + '"' + "Success!" + '"' + "});" ;
		return parCoordStr;
	}
	
	//find out the celllines that are not present in each json object, so that "" string can be put for that cellline key in json
	private void getMissingCellLines(JSONArray cellLineHeaders,JSONArray CellLineInCurrObj){
		for(int i = 0 ; i < cellLineHeaders.size() ; i++ ){
			for(int k = 0 ; k < CellLineInCurrObj.size() ; k++ ){
				if(cellLineHeaders.get(i).equals(CellLineInCurrObj.get(k))){
					cellLineMatchFound = true;
					break;
			    }
		    }
			if(!cellLineMatchFound){
				parCoordStr += ","+ '"' + cellLineHeaders.get(i).toString() +'"' + ":" + '"' + "" + '"';
			}
			cellLineMatchFound = false;
	    }   
	}
}
