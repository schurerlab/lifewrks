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

public class ConditionsFetcher extends HttpServlet {
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

		String targetArray = request.getParameter("listArr");
		String epid = request.getParameter("epid");
		sessions = connect();
		ses = sessions.openSession();
		String json = "";

		if (!targetArray.equals(null)) {
			String EndpointQuery = "select distinct  e.concentration as conc "
					+ " from endpoint_has_participant e2p "
					+ " inner join endpoint e on (e.id = e2p.endpointid) "
					+ " where e.epname_epid = " + epid
					+ " and e2p.participantid in (" + targetArray + ")";
			json += "{\"results\": { \"concentration\": [";
			SQLQuery epquery = ses.createSQLQuery(EndpointQuery).addScalar(
					"conc", Hibernate.DOUBLE);
			Iterator epobjects = null;
			try {
				epobjects = epquery.list().iterator();
			} catch (HibernateException he) {
				log.info("Possibly a communications link failure, check database connectivity");
			}
			if (epobjects.hasNext()) {

				while (epobjects.hasNext()) {
					Double list = (Double) epobjects.next();

					json += "\"" + list + "\"";

					if (epobjects.hasNext()) {
						json += ",";
					} else {
						json += "],\"timepoint\":[";
					}

				}

			}
		}
		if (!targetArray.equals(null)) {
			String EndpointQuery = "select distinct  e.timepoint as timePoint "
					+ " from endpoint_has_participant e2p "
					+ " inner join endpoint e on (e.id = e2p.endpointid) "
					+ " where e.epname_epid = " + epid
					+ " and e2p.participantid in (" + targetArray + ")";
			SQLQuery epquery = ses.createSQLQuery(EndpointQuery).addScalar(
					"timePoint", Hibernate.INTEGER);
			Iterator epobjects = null;
			try {
				epobjects = epquery.list().iterator();
			} catch (HibernateException he) {
				log.info("Possibly a communications link failure, check database connectivity");
			}
			if (epobjects.hasNext()) {

				while (epobjects.hasNext()) {
					Integer list = (Integer) epobjects.next();

					json += "\"" + list + "\"";

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
