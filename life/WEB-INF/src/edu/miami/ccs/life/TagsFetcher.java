package edu.miami.ccs.life;

import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.SessionFactory;

import net.sf.json.JSONObject;



import edu.miami.ccs.sweng.commons.SolrMask;

public class TagsFetcher extends HttpServlet {
	private static final long serialVersionUID = 128913189700996851L;
	Logger log = Logger.getLogger(this.getClass().getName());
	

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		this.doGet(req, resp);
	}
		protected void doGet(HttpServletRequest request,
				HttpServletResponse response) throws ServletException, IOException {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			String query = request.getParameter("q");
			String [] facetQuery = request.getParameterValues("fq");
			String limit = request.getParameter("limit");
			String category = request.getParameter("category");
			if(query.contains("{!lucene q.op=AND df=text}")){
				query = query;
			}else{
			query="{!lucene q.op=AND df=text}"+query;
			}
			SolrMask solMask = new SolrMask("http://lincs.ccs.miami.edu:8080/participant-solr",query,facetQuery, category, limit);
			JSONObject obj = null;
			try {
				obj = solMask.getTags();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.print(obj.toString());
		}

			
		
				
}
