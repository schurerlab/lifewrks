package edu.miami.ccs.life;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.GroupParams;

public class SummaryDownload extends HttpServlet {
	private static final int FILE_LENGTH = 10 * 1024 * 1024; // limit file
																// length to
																// .5MB
	private static PrintWriter out;
	private static HttpServletResponse response;
	private static HttpServletRequest request;
	private ServletConfig config;
	String input;
	String mode;

	public void init(ServletConfig config) throws ServletException {
		this.config = config;

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.request = req;
		this.response = resp;
		input = request.getParameter("input");
		mode = request.getParameter("mode");

		SolrDocumentList docs = null;

		try {
			docs = Search(input);
			// System.out.println(docs.toString());

		} catch (Exception e) {

		}

		downloadData(docs);

	}

	/**
	 * Generates Solr Documents
	 * 
	 * @return SolrDocumentList objects
	 */
	private SolrDocumentList Search(String q) throws SolrServerException,
			MalformedURLException {
		String url = "http://lincs.ccs.miami.edu:8080/solr-example";
		SolrServer server = new CommonsHttpSolrServer(url);
		if (mode.equalsIgnoreCase("SmallMolecule")) {
			q = "SmallMoleculeId:" + q;
		} else if (mode.equalsIgnoreCase("Protein")) {
			q = "ProteinId:" + q;
		} else if (mode.equalsIgnoreCase("Gene")) {
			q = "GeneId:" + q;
		} else if (mode.equalsIgnoreCase("CellLine")) {
			q = "CellLineId:" + q;
		}

		SolrQuery query = new SolrQuery("" + q);
		System.out.println(q);

		query.set("rows", 1000000);

		QueryResponse response = server.query(query);
		SolrDocumentList docs = response.getResults();

		return docs;
	}

	/**
	 * Creates the output stream for download
	 * 
	 * @return void
	 */

	private void downloadData(SolrDocumentList docs) throws IOException {
		HttpSession hs = request.getSession(true);

		// Setting up file name to download results into
		String extension = ".csv";
		String delim = ",";
		String filename = "searchResults";
		Calendar cal = Calendar.getInstance();
		cal.getTimeInMillis();
		filename = filename + cal.getTimeInMillis() + extension;

		// Set up ServletOutputStream to write the file to
		ServletOutputStream sos = response.getOutputStream();
		ServletContext context = config.getServletContext();
		String mimetype = context.getMimeType(filename);

		// Set up response to write to a file
		response.setContentType((mimetype != null) ? mimetype
				: "application/octet-stream");
		response.setContentLength(FILE_LENGTH);
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ filename + "\"");
		int p = 0;

		if (mode.equalsIgnoreCase("SmallMolecule")) {

			sos.print("\"Assay\",\"Compound Name\",\"LINCS Small Molecule ID\",\"Protein Name\",\"UniProt ID\",\"Cell Line\",\"Gene\",\"Endpoint\",\"Endpoint Value\",\"Concentration\",\"Concentration Unit\",\"Timepoint(hrs)\"");
			sos.println();
			for (int i = 0; i < docs.size(); i++) {
				SolrDocument doc = docs.get(i);

				String[] data = new String[12];
				String[] label = { "AssayTypeName", "SmallMoleculeName",
						"LincsSMId", "ProteinName", "UniprotId",
						"CellLineName", "GeneName", "PerturbationType",
						"PerturbationMeasure", "Concentration",
						"ConcentrationUnit", "TimePoint" };

				for (int k = 0; k < label.length; k++) {
					switch (k) {

					default: {
						if (doc.getFieldValue(label[k]) == null)
							data[k] = "\""+""+"\"";

						else
							data[k] = "\""+doc.getFieldValue(label[k]).toString()+"\"";
					}

						break;
					}

				}

				for (int j = 0; j < data.length; j++) {
					if (data[j] == "") {
						sos.print("");

					} else {
						sos.print(data[j]);

					}
					sos.print(delim);
				}
				sos.println();
			}

		} else if (mode.equalsIgnoreCase("Protein")) {

			sos.print("\"Assay\",\"Protein Name\",\"UniProt ID\",\"Compound Name\",\"LINCS Small Molecule ID\",\"Endpoint\",\"Endpoint Value\",\"Concentration\",\"Concentration Unit\",\"Timepoint(hrs)\"");
			sos.println();
			for (int i = 0; i < docs.size(); i++) {
				SolrDocument doc = docs.get(i);

				String[] data = new String[10];
				String[] label = { "AssayTypeName", "ProteinName", "UniprotId",
						"SmallMoleculeName", "LincsSMId", "PerturbationType",
						"PerturbationMeasure", "Concentration",
						"ConcentrationUnit", "TimePoint" };

				for (int k = 0; k < label.length; k++) {
					switch (k) {

					default: {
						if (doc.getFieldValue(label[k]) == null)
							data[k] = "\""+""+"\"";

						else
							data[k] = "\""+doc.getFieldValue(label[k]).toString()+"\"";
					}

						break;
					}

				}

				for (int j = 0; j < data.length; j++) {
					if (data[j] == "") {
						sos.print("");

					} else {
						sos.print(data[j]);

					}
					sos.print(delim);
				}
				sos.println();
			}

		} else if (mode.equalsIgnoreCase("Gene")) {

			sos.print("\"Assay\",\"Gene\",\"Compound Name\",\"LINCS Small Molecule ID\",\"Cell Line\",\"Endpoint\",\"Endpoint Value\",\"Concentration\",\"Concentration Unit\",\"Timepoint(hrs)\"");

			sos.println();
			for (int i = 0; i < docs.size(); i++) {
				SolrDocument doc = docs.get(i);

				String[] data = new String[10];
				String[] label = { "AssayTypeName", "GeneName",
						"SmallMoleculeName", "LincsSMId", "CellLineName",
						"PerturbationType", "PerturbationMeasure",
						"Concentration", "ConcentrationUnit", "TimePoint" };

				for (int k = 0; k < label.length; k++) {
					switch (k) {

					default: {
						if (doc.getFieldValue(label[k]) == null)
							data[k] = "\""+""+"\"";

						else
							data[k] = "\""+doc.getFieldValue(label[k]).toString()+"\"";
					}

						break;
					}

				}

				for (int j = 0; j < data.length; j++) {
					if (data[j] == "") {
						sos.print("");

					} else {
						sos.print(data[j]);

					}
					sos.print(delim);
				}
				sos.println();
			}

		} else if (mode.equalsIgnoreCase("CellLine")) {

			sos.print("\"Assay\",\"Cell Line\",\"Compound Name\",\"LINCS Small Molecule ID\",\"Gene\",\"Endpoint\",\"Endpoint Value\",\"Concentration\",\"Concentration Unit\",\"Timepoint(hrs)\"");
			sos.println();
			for (int i = 0; i < docs.size(); i++) {
				SolrDocument doc = docs.get(i);

				String[] data = new String[10];
				String[] label = { "AssayTypeName", "CellLineName",
						"SmallMoleculeName", "LincsSMId", "GeneName",
						"PerturbationType", "PerturbationMeasure",
						"Concentration", "ConcentrationUnit", "TimePoint" };

				for (int k = 0; k < label.length; k++) {
					switch (k) {

					default: {
						if (doc.getFieldValue(label[k]) == null)
							data[k] = "\""+""+"\"";

						else
							data[k] = "\""+doc.getFieldValue(label[k]).toString()+"\"";
					}

						break;
					}

				}

				for (int j = 0; j < data.length; j++) {
					if (data[j] == "") {
						sos.print("");

					} else {
						sos.print(data[j]);

					}
					sos.print(delim);
				}
				sos.println();
			}

		}

		sos.close();
	}

}
