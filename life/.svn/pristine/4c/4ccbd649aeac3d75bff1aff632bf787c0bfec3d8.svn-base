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

public class StaticsData extends HttpServlet {
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
		String input = request.getParameter("input");
		String mode = request.getParameter("mode");
		String url = "http://life.ccs.miami.edu/solr-example/";
		SolrServer server = new CommonsHttpSolrServer(url);
		ArrayList participants = new ArrayList();
		ArrayList counts = new ArrayList();

		SolrQuery assayQuery = new SolrQuery(input);
		assayQuery.set("facet", true);
		assayQuery.set("facet.limit", -1);
		assayQuery.addFacetField("AssayTypeName");
		assayQuery.set("rows", 0);
		QueryResponse res2 = null;
		try {
			res2 = server.query(assayQuery);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<FacetField> al = res2.getFacetFields();
		for (int i = 0; i < al.size(); i++) {

			int counter = 0;
			FacetField doc = al.get(i);
			out.println(al.get(i));
		}
		SolrQuery query = new SolrQuery(input);
		query.set("facet", true);
		query.set("facet.limit", -1);
		query.addFacetField("ProteinId");
		query.addFacetField("PhosphoProteinId");
		query.addFacetField("SmallMoleculeId");
		query.addFacetField("CdnaID");
		query.addFacetField("ShRnaID");
		query.addFacetField("CellLineId");
		query.addFacetField("AntibodyId");
		query.addFacetField("NonKinaseProteinId");
		query.addFacetField("GeneId");

		query.set("rows", 0);

		QueryResponse res = null;
		try {
			res = server.query(query);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(res.getFacetFields().toString());
		List<FacetField> l = res.getFacetFields();

		String json = "";
		// json +=
		// "{  \"results\": { \"cols\": [ { \"id\": \"participant\", \"label\": \"participant\", \"type\": \"string\" }, {\"id\": \"count\", \"label\": \"count\",\"type\": \"number\" }],\"rows\": [";

		for (int i = 0; i < l.size(); i++) {

			int counter = 0;
			FacetField doc = l.get(i);
			//

			String participant = doc.getName().toString();

			List<Count> s = doc.getValues();
//			json += "{\"c\":[{\"v\":";

			for (int j = 0; j < s.size(); j++) {

				Count c = s.get(j);
				long k = c.getCount();

				if (k != 0) {
					counter++;

				}

			}

			if (participant.equals("ProteinId")) {
				json += "\"Kinase Protein\":";

			} else if (participant.equals("PhosphoProteinId")) {
				json += "\"PhosphoProtein\":";

			}else if (participant.equals("SmallMoleculeId")) {
				json += "\"SmallMolecule\":";

			}else if (participant.equals("CdnaID")) {
				json += "\"Cdna\":";

			}else if (participant.equals("AntibodyId")) {
				json += "\"Antibody\":";

			}else if (participant.equals("NonKinaseProteinId")) {
				json += "\"Protein\":";

			}else if (participant.equals("ShRnaID")) {
				json += "\"ShRna\":";

			} else if (participant.equals("CellLineId")) {
				json += "\"CellLine\":";

			} else if (participant.equals("GeneId")) {
				json += "\"Gene\":";

			}

//			json += "}, {\"v\":";

			json += counter;

//			json += "}]}";
			if (i < l.size() - 1) {
				json += ",";
			}
		}
//		json += "] }}";

		out.print(json.toString());
	}

}
