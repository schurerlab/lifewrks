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

public class ListDownload extends HttpServlet 
{
	private static final int FILE_LENGTH = 512*1024*4; //limit file length to 2MB 
		private static PrintWriter out;
	private static HttpServletResponse response;
	private static HttpServletRequest request;
		private ServletConfig config;
		String input;
		String mode;
	public void init(ServletConfig config) throws ServletException
	{
		this.config = config;
		
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		this.request = req;
		this.response = resp;
		 input = request.getParameter("input");
		 mode = request.getParameter("mode");
		
		SolrDocumentList docs = null;
		
		try{
			 docs = Search(input);
			 //System.out.println(docs.toString());
		   
		}
		catch(Exception e)
		{
			
		}
	
		downloadData(docs);   

		
	}

	/**
	 * Generates Solr Documents
	 * 
	 * @return SolrDocumentList objects
	 */
	private SolrDocumentList Search(String q) throws SolrServerException, MalformedURLException
	{
		String url = "http://life.ccs.miami.edu/participant-solr";
		SolrServer server = new CommonsHttpSolrServer(url);
		SolrQuery query = new SolrQuery(q);
		query.set("group", true);
		query.set("group.main", true);
		if (mode.equalsIgnoreCase("SmallMolecule")){
			query.set("group.field", "SmallMoleculeId");
		}else if(mode.equalsIgnoreCase("Protein")){
			query.set("group.field", "ProteinId");
		}else if(mode.equalsIgnoreCase("CellLine")){
			query.set("group.field", "CellLineId");
		}else if(mode.equalsIgnoreCase("Gene")){
			query.set("group.field", "GeneId");
		}
				
        
	    

		
		System.out.println(q);
		  
		query.set("rows", 1000000);
   		
		  QueryResponse  response = server.query(query);
		   SolrDocumentList docs = response.getResults();
		 
		return docs ;
	}
	
	/**
	 * Creates the output stream for download
	 * 
	 * @return void	 */
	
	private void downloadData(SolrDocumentList docs) throws IOException
	{
		HttpSession hs = request.getSession(true);
		
		//Setting up file name to download results into
		String extension = ".csv";
		String delim = ",";
		String filename = "searchResults";
		Calendar cal = Calendar.getInstance();
		cal.getTimeInMillis();
		filename = filename + cal.getTimeInMillis() + extension;
		
		//Set up ServletOutputStream to write the file to
		ServletOutputStream sos = response.getOutputStream();
		ServletContext context = config.getServletContext();
		String mimetype = context.getMimeType(filename);

		//Set up response to write to a file
		response.setContentType((mimetype != null) ? mimetype : "application/octet-stream");
		response.setContentLength(FILE_LENGTH);
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
		int p=0;
		
		if (mode.equalsIgnoreCase("SmallMolecule")){
			 
			sos.print("\"Name\",\"Lincs Id\",\"Molecular Weight\",\"Molecular Formulae\",\"Pubchem Cid\",\"SMILES\",\"Category\",\"Inhibitor Type\"");
			sos.println();
			for (int i = 0; i < docs.size(); i++) {
				SolrDocument doc = docs.get(i);
				
				
				String[] data = new String[8];
				String[] label = {"SmallMoleculeName","LincsSMId","MolecularWeight","MolecularFormulae","PubchemId","SMSmiles","SMCategories","KinaseInhibitorType"};
				
				for(int k=0;k<label.length;k++)
				{
					switch(k)
					{
					
					default: { 
						if(doc.getFieldValue(label[k])==null)
							data[k] = "\""+""+"\"";

						else
							data[k] = "\""+doc.getFieldValue(label[k]).toString()+"\"";
						}
						
					break;
					}
								
				}	
				
						
				for(int j=0;j<data.length;j++)
				{
					if(data[j]=="")
					{
						sos.print("");
					
					}
					else 
					{
						sos.print(data[j]);
					
					
					}
					sos.print(delim);
				}
					sos.println();
				}
			
		}else if (mode.equalsIgnoreCase("Protein")){
							
			sos.print("\"Protein Name\",\"Mutation\",\"Modification\",\"UniProt ID\",\"Gene Symbol\",\"Hierarchy\",\"Kinase Family\",\"Kinase Category\",\"Kinase GateKeeper\",\"Kinase Group\",\"Kinase Hingei3\",\"Kinase Hingei1\"");
			sos.println();
			for (int i = 0; i < docs.size(); i++) {
				SolrDocument doc = docs.get(i);
				
				
				String[] data = new String[12];
				String[] label = {"ProteinName","Mutation","Modification","UniprotId","KinaseSymbol","KinaseDomain","KinaseFamily","MainCategory","KinaseGateKeeper","KinaseGroup","KinaseHingei3","KnaseHingei1"};
				
				for(int k=0;k<label.length;k++)
				{
					switch(k)
					{
					
					default: { 
						if(doc.getFieldValue(label[k])==null)
							data[k] = "\""+""+"\"";

						else
							data[k] = "\""+doc.getFieldValue(label[k]).toString()+"\"";	}
						
					break;
					}
								
				}	
					
				for(int j=0;j<data.length;j++)
				{
					if(data[j]=="")
					{
						sos.print("");
					
					}
					else 
					{
						sos.print(data[j]);
					
					
					}
					sos.print(delim);
				}
					sos.println();
				}
			
		}else if (mode.equalsIgnoreCase("Gene")){
			
			
			
			sos.print("\"Gene Name\",\"Gene Description\",\"Organism\",\"Entrez Id\"");
			sos.println();
			for (int i = 0; i < docs.size(); i++) {
				SolrDocument doc = docs.get(i);
				
				
				String[] data = new String[4];
				String[] label = {"GeneName","GeneDescription","GeneOrganism","EntrezId"};
				
				for(int k=0;k<label.length;k++)
				{
					switch(k)
					{
					
					default: { 
						if(doc.getFieldValue(label[k])==null)
							data[k] = "\""+""+"\"";

						else
							data[k] = "\""+doc.getFieldValue(label[k]).toString()+"\"";	}
						
					break;
					}
								
				}	
				
						
				for(int j=0;j<data.length;j++)
				{
					if(data[j]=="")
					{
						sos.print("");
					
					}
					else 
					{
						sos.print(data[j]);
					
					
					}
					sos.print(delim);
				}
					sos.println();
				}
			
		}else if (mode.equalsIgnoreCase("CellLine")){

			sos.print("\"CellLine Name\",\"CellLine Provider\",\"CellLine Catelog Id\",\"CellLine Organism\",\"CellLine Gender\",\"CellLine Organ\",\"CellLine Tissue\",\"CellLine Type\",\"Disease\",\"DoId\"");
			sos.println();
			for (int i = 0; i < docs.size(); i++) {
				SolrDocument doc = docs.get(i);
				
				
				String[] data = new String[10];
				String[] label = {"CellLineName","CellLineProvider","CellLineCatelogId","CellLineOrganism","CellLineGender","CellLineOrgan","CellLineTissue","CellLineType","Disease","DoId"};
				
				for(int k=0;k<label.length;k++)
				{
					switch(k)
					{
					
					default: { 
						if(doc.getFieldValue(label[k])==null)
							data[k] = "\""+""+"\"";

						else
							data[k] = "\""+doc.getFieldValue(label[k]).toString()+"\"";	}
						
					break;
					}
								
				}	
				
						
				for(int j=0;j<data.length;j++)
				{
					if(data[j]=="")
					{
						sos.print("");
					
					}
					else 
					{
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
	
	
	
	
	
	
	
	
	


