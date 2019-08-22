package edu.miami.ccs.life;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.FacetField.Count;

public class StatsLoader extends HttpServlet {
	/**
	 * Constant serialized ID used for compatibility.
	 */
	private static final long serialVersionUID = 1289131677014606851L;
	Logger log = Logger.getLogger(this.getClass().getName());
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String input = request.getParameter("searchTerm");
		if (input.equals("")){
			input="*:*";
		}
		out.print ("{ \"stats\"");
		  
			
	
		String url = "http://baoquery.ccs.miami.edu/docentapp/collection1/";
		SolrServer server = new CommonsHttpSolrServer(url);
		SolrQuery query = new SolrQuery(input);
		  query.set("facet",true );
		  query.set("facet.limit",-1 );
//		  query.set("facet.mincount",1 );
		  query.addFacetField("category");
		  query.set("rows", 0);
		  query.set("wt", "json");
		  query.set("indent", true);
		  query.set("json.nl", "map");
		  query.set("q.op","AND");
		  QueryResponse res = null;
		try {
			res = server.query(query);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		  
		System.out.println(res.getFacetFields().toString());
		List<FacetField> l = res.getFacetFields();
		
		 

		  for (int j = 0; j < l.size(); j++) {
			  FacetField doc = l.get(j);
			  out.print(doc.toString().replaceAll("\\(", ":").replaceAll("\\)", "").replaceAll("category", "").replaceAll("Antibodies ", "\"antibodyCount\"").replaceAll("Cell Line ", "\"celllineCount\"").replaceAll("Detected proteins ", "\"detectedproteinCount\"").replaceAll("Genes", "\"geneCount\"").replaceAll("Differentiated Cells ", "\"differentiatedcellCount\"").replaceAll("IPSCs ", "\"ipscCount\"").replaceAll("Phosphoproteins", "\"phosphoproteinCount\"").replaceAll("Primary Cells ", "\"primarycellCount\"").replaceAll("Kinases ", "\"kinaseCount\"").replaceAll("Proteins ", "\"proteinCount\"").replaceAll("RNAi ", "\"rnaiCount\"").replaceAll("Small Molecule ", "\"smallmoleculeCount\"").replaceAll("cDNAs ", "\"cdnaCount\"").replaceAll("shRNAs ", "\"shrnaCount\"").replaceAll("assay ", "\"assayCount\"").replaceAll("\\[", "{").replaceAll("\\]", "}"));
			  
		  }

		out.print("}");
	}
	
}
