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
import org.apache.solr.util.NamedList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;



public class GetData extends HttpServlet 
{
	private static final int FILE_LENGTH = 20*1024*1024; //limit file length to 20MB 
		private static PrintWriter out;
	private static HttpServletResponse response;
	private static HttpServletRequest request;
		private ServletConfig config;

	public void init(ServletConfig config) throws ServletException
	{
		this.config = config;
		
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		this.request = req;
		this.response = resp;
		String input = request.getParameter("searchTerm");
//		String limit = request.getParameter("limit");
//		String sk= request.getParameter("skip");
		System.out.println("test:");
		SolrDocumentList docs = null;
		
		try{
			 docs = Search(input);
			 System.out.println(docs.toString());
		   
		}
		catch(Exception e)
		{
			e.printStackTrace();
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
		String url = "http://life.ccs.miami.edu/solr-example";
		SolrServer server = new CommonsHttpSolrServer(url);
		
		SolrQuery query = new SolrQuery(q);
		String limit = "1000";
		String sk = "0";
		if(request.getParameter("limit") != null)
			limit = request.getParameter("limit");
		query.set("rows",limit);
		
		if(request.getParameter("skip") != null)
			sk = request.getParameter("skip");
		query.set("start",sk);
		System.out.println(q);
	
		
   		
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
		
		sos.print("datarecordId,AssayName,bioAssay,smCenterCompoundID,smLincsID,smName,clName,clLincsID,clCenterSpecificID,ppName,ppLincsID,datapointName,concentration(uM),datapointValue");
		sos.println();
		for (int i = 0; i < docs.size(); i++) {
			SolrDocument doc = docs.get(i);
			
			
			String[] data = new String[14];
			String[] label = {"PerturbationId","AssayType","AssayTypeName","PerturbagenId","LincsSMId","SmallMoleculeName","CellLineName","LincsCellId","CellLineLincsSourceId","ProteinName","HMSProteinId","PerturbationType","Concentration","PerturbationMeasure"};
			
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
		
		sos.close();
	}
	
}
	
	
	
	
	
	
	
	
	


