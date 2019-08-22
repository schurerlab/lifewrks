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

public class HMSHeatMapDataFetcher extends HttpServlet {
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
		String type = request.getParameter("type");
		String measure = request.getParameter("measure");
		String input = request.getParameter("input");
		sessions = connect();
		ses = sessions.openSession();

		String json = "";

		String QueryForData = "select distinct p2.name as ParticipantName,e.value as value,e.timepoint as Time,e.concentration as Concentration "
				+ " from endpoint e"
				+ " 	 inner join epname ep on (ep.epid = e.epname_epid) "
				+ " 	   inner join perturbation_measure pm on (pm.measureid = e.measureid) "
				+ " 	      inner join endpoint_has_participant ehp on  (ehp.endpointid = e.id) "
				+ " 	        INNER JOIN endpoint_has_participant ehp2 on (ehp2.endpointid = e.id) "
				+ "        inner join participant p on (p.id = ehp.participantid)"
				+" inner join participant_to_sourceid ps on (ps.id = p.id)"
				+ "        inner join participant p2 on (p2.id = ehp2.participantid)"
				+" inner join participant_to_sourceid p2s on (p2s.id = p2.id) "
				+ "      where ps.sourceid ='" + input.trim()+"'"
				+ "    and p2s.sourceid !=' "
				+ input.trim()+"'"
				+ "       and ep.name ='"
				+ type.trim()
				+ "' and pm.name ='"+ measure.trim() +"'  order by e.value desc";
		// + input.trim();

		System.out.println(QueryForData);
		SQLQuery query = ses.createSQLQuery(QueryForData)
				.addScalar("ParticipantName", Hibernate.STRING)
				.addScalar("value", Hibernate.FLOAT)
				.addScalar("Time", Hibernate.STRING)
				.addScalar("Concentration", Hibernate.FLOAT);
		Iterator objects = null;
		try {
			objects = query.list().iterator();
		} catch (HibernateException he) {
			log.info("Possibly a communications link failure, check database connectivity");
		}

		if (objects.hasNext()) {
			int i = 0;
			String prevType = "";
			json += "{ \"results\": [";
			while (objects.hasNext()) {
				Object[] tuple = (Object[]) objects.next();

				json += "{\"participant\":\"" + tuple[0].toString() + "\","
						+ "\"value\":\"" + tuple[1].toString() + "\","
						+ "\"time\":\"" + tuple[2].toString() + "\","
						+ "\"concentration\":\"" + tuple[3].toString() + "\"}";

				if (objects.hasNext())
					json += ",";
			}

		}

		json += "] }";
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
