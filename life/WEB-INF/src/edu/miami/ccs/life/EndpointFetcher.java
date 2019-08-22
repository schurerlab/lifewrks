package edu.miami.ccs.life;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.json.JSONException;
import org.json.JSONObject;

public class EndpointFetcher extends HttpServlet {
	/**
	 * Constant serialized ID used for compatibility.
	 */
	private static final long serialVersionUID = 128913189700996851L;
	Logger log = Logger.getLogger(this.getClass().getName());
	SessionFactory sessions;
	Session ses;

	/**
	 * Overrides the method in GenericServlet class to create an active
	 * hibernate session
	 */
	// end of method

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
		JSONObject jsonObj = new JSONObject();

		String assayName = request.getParameter("Ename");
		sessions = connect();
		ses = sessions.openSession();
		String json = "";
		String[] compstr;

		if (!assayName.equals(null)) {
			
			  json+= "{  \"results\": { \"endpoints\" : [";
			   
			             
			String EndpointQuery = " select distinct ep.epid as epId,ep.name as epName  from assay a "
				    + " inner join endpoint e on (e.assay_assayid = a.assayid) "
				    + "    inner join epname ep on (ep.epid = e.epname_epid) "
				+ " where a.assay_type_name = '"+assayName +"\'";

			SQLQuery epquery = ses.createSQLQuery(EndpointQuery)
					.addScalar("epId", Hibernate.INTEGER)
					.addScalar("epName", Hibernate.STRING);
			Iterator epobjects = null;
			try {
				epobjects = epquery.list().iterator();
			} catch (HibernateException he) {
				log.info("Possibly a communications link failure, check database connectivity");
			}
			if (epobjects.hasNext()) {

				while (epobjects.hasNext()) {
					Object[] eplist = (Object[]) epobjects.next();
					json += "{\"Endpointname\":"+"\""+eplist[1].toString()+"\",";
					json += "\"EpId\":"+"\""+eplist[0].toString()+"\"}";
							
					if (epobjects.hasNext()) {
						json += ",";
					} 

				}

			}
		}
		
		json += "] }}";
		out.print(json);
		sessions.close();

	}

	/**
	 * Called by the init(...) method to generate hibernate Session object
	 * 
	 * @return Hibernate SessionFactory object
	 * 
	 */
	public static SessionFactory connect() {

		Configuration cfg = new Configuration();
		SessionFactory sessions = cfg.configure().buildSessionFactory();

		return sessions;
	}

}

