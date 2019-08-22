package edu.miami.ccs.life;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;



/**
@author ndatar

*/

//@WebServlet(description = "Allows user to search across LINCS Centers by participants and get information about them", urlPatterns = { "/participantinfo" })

public class ParticipantInfo extends HttpServlet {
	

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
						  throws ServletException, IOException {
		this.doGet(req, resp);
	}
	
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
	
		PrintWriter out = response.getWriter();
		try{
		
			String jsonStr;
			
			jsonStr = makeParticipantResults(request);
			System.out.println(jsonStr);
			int spacesToIndentEachLevel = 2;
			
			out.print(new JSONObject(jsonStr).toString(spacesToIndentEachLevel).replaceAll("null,", ""));
		   
		}
		catch(Exception e)
		{
			out.print("{\"error\":\"no data found\"}");
		}
		
		
	}

	
	private String makeParticipantResults(HttpServletRequest request)
	{
		//Initializing almost all values to be null so that constructor can handle correctly
		JSONObject jsonObj = null;
		StringBuffer jsonStr = new StringBuffer("");
		String url = "http://life.ccs.miami.edu/participant-solr";
		String query = request.getParameter("searchTerm");
		query="{!lucene q.op=AND df=text}"+query;
		String fields = "";
		String rows = null;
		String start = null;
		if(request.getParameter("limit") != null)
			rows = request.getParameter("limit");
		if(request.getParameter("skip") != null)
			start = request.getParameter("skip");
		String[] facets = {"AssayTypeName"};
		String[] groups = null;
		
		SolrShield sm = null;
		String fileName = null;
		
		if(request.getParameter("constituentType") == null)
			return "{ \"status\": \"Error. Missing Parameter\" }";
		
		if(request.getParameter("constituentType").equalsIgnoreCase("kinaseProtein"))
		{
			groups = new String[]{"ProteinName"};
			fields = "KinaseFamily,HMSProteinId,KinaseDescription,KinaseHingei3,KinaseSymbol,KnaseHingei1,MainCategory,ProteinName,ProteinOrganism,ProteinId,RefSeqId,UniprotId";
			fileName = "/kinase-protein.properties";
		}
		else if(request.getParameter("constituentType").equalsIgnoreCase("phosphoProtein"))
		{
			groups = new String[]{"PhosphoProteinName"};
			fields = "PhosphoProteinBasePeptide,PhosphoProteinCluster,PhosphoProteinClusterCode,PhosphoProteinGeneId,PhosphoProteinGeneSymbol,PhosphoProteinId,PhosphoProteinModifiedPeptide,PhosphoProteinName,PhosphoProteinPhosphosite,PhosphoProteinProbeId,PhosphoProteinUniProtId";
			fileName = "/phospho-protein.properties";
		}
		else if(request.getParameter("constituentType").equalsIgnoreCase("geneTarget"))
		{
			groups = new String[]{"GeneName"};
			fields = "GeneId,GeneDescription,GeneName,GeneOrganism,EntrezId";
			fileName = "/gene.properties";
		}			
		else if(request.getParameter("constituentType").equalsIgnoreCase("cellLine"))
		{
			groups = new String[]{"CellLineName"};
			fields = "CellLineCatelogId,CellLineGender,CellLineGeneticMutation,LincsCellId,CellLineLincsSourceId,CellLineName,CellLineOrgan,CellLineOrganism,CellLineProvider,CellLineProviderId,CellLineTissue,CellLineType,Disease,DoId";
			fileName = "/cell.properties";
		}		
		else if(request.getParameter("constituentType").equalsIgnoreCase("Shrna"))
		{
			groups = new String[]{"ShRnaName"};
			fields = "ShRna6MerSequence,ShRna7MerSequence,ShRnaID,ShRnaName,ShRnaPerturbagenId,ShRnaTargetSequence";
			fileName = "/shrna.properties";
		}		
		else if(request.getParameter("constituentType").equalsIgnoreCase("Cdna"))
		{
			groups = new String[]{"CdnaName"};
			fields = "CdnaID,CdnaName";
			fileName = "/cdna.properties";
		}	
		else if(request.getParameter("constituentType").equalsIgnoreCase("SmallMolecule"))
		{
			groups = new String[]{"SmallMoleculeId"};
			fields = "SmallMoleculeId,SmallMoleculeName,MolecularFormulae,MolecularWeight,PubchemId,PerturbagenId,LincsSMId";
			fileName = "/small-molecule.properties";
		}		
		else
			return "{ \"status\": \"Error. Incorrect Participant Type\" }";
		
		
		try {
			sm = new SolrShield(url, query, fields, rows, start, facets, groups);
			jsonObj = sm.getGroupFacetResults();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		jsonObj.remove("valueFacets");
		return readPropertyFile(jsonObj, fileName);
	}
	
	 public static String readPropertyFile(JSONObject jsonObj, String fileName) {
		 Properties prop = new Properties();
		 InputStream input;
		 Set<String> propertyNames;
		 String jsonStr = jsonObj.toString();
		 try 
		 {		 
			 input = AssayInfo.class.getResourceAsStream(fileName);
			 prop.load(input);			 
			 propertyNames = prop.stringPropertyNames();
			 for (String Property : propertyNames) {
				 jsonStr = jsonStr.replaceAll("\""+Property+"\"", "\""+prop.getProperty(Property)+"\"");
			 }
		 } catch (FileNotFoundException e) {
			 e.printStackTrace();
		 } catch (IOException e) {
			 e.printStackTrace();
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		 return jsonStr;
	 }
	
	private StringBuffer makePolygonLayer(String layer, HttpServletRequest request)
	{
		StringBuffer jsonStr = new StringBuffer("[");

		
		
		return jsonStr;
	}
}