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

public class CompareBarChart extends HttpServlet {
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

		String epid = null;
		String timepoint = null;
		String concentration = null;
		String targetArray = request.getParameter("listArr");
		String mode = request.getParameter("elementType");
		if (request.getParameter("tp") != null)
			timepoint = request.getParameter("tp");
		if (request.getParameter("conc") != null)
			concentration = request.getParameter("conc");
		if (request.getParameter("epid") != null)
			epid = request.getParameter("epid");

		sessions = connect();
		ses = sessions.openSession();
		String json = "";
		String[] compstr;

		if (!targetArray.equals(null)) {

			json += "{  \"results\": { \"experiments\" : [";

			String EndpointQuery = "select distinct(a.assay_type_name) as AssayName  "
					+ "  from assay a, assay_has_participant a2p "
					+ "    where a.assayid = a2p.assay_assayid "
					+ " and a2p.participantid in (" + targetArray + ")";

			SQLQuery epquery = ses.createSQLQuery(EndpointQuery).addScalar(
					"AssayName", Hibernate.STRING);
			Iterator epobjects = null;
			try {
				epobjects = epquery.list().iterator();
			} catch (HibernateException he) {
				log.info("Possibly a communications link failure, check database connectivity");
			}
			if (epobjects.hasNext()) {

				while (epobjects.hasNext()) {
					String list = (String) epobjects.next();
					json += "\"" + list + "\"";

					if (epobjects.hasNext()) {
						json += ",";
					} else {
						json += "], \"cols\": [ { \"id\": \"task\", \"label\": \"CompoundName\",    \"type\": \"string\" }, {\"id\": \"protein\", \"label\": \"ActiveCount\",\"type\": \"number\" }], \"rows\": [";
					}

				}

			}
		}
		if (!mode.equals("CellLine")) {
			if (epid == null)
				epid = "25106";
			if (timepoint == null)
				timepoint = "1";
			if (concentration == null)
				concentration = "10";

			String BarQuery = "select count(distinct e2p.participantid) as ActiveCompoundCount,p.name as Pname,p.id as Pid,p.paticipanttype as Ptype"
					+ " from endpoint e "
					+ " inner join endpoint_has_participant e2p on (e2p.endpointid = e.id) "
					+ " inner join endpoint_has_participant e2p2 on (e2p2.endpointid = e.id) "
					+ "  inner join participant p on (p.id = e2p2.participantid) "
					+ "   inner join perturbation_measure pm on (pm.measureid = e.measureid) "
					+ " where pm.name = 'active' "
					+ " and e.epname_epid = "
					+ epid
					+ " and e2p.participantid in ( "
					+ targetArray
					+ " )"
					+ " and e2p.participantid != e2p2.participantid"
					+ " and e.timepoint ="
					+ timepoint
					+ " and e.concentration = "
					+ concentration
					+ " group by p.name,p.id,p.paticipanttype "
					+ " order by count(distinct e2p.participantid) desc ";
			SQLQuery barchartquery = ses.createSQLQuery(BarQuery)
					.addScalar("ActiveCompoundCount", Hibernate.INTEGER)
					.addScalar("Pname", Hibernate.STRING)
					.addScalar("Pid", Hibernate.INTEGER)
					.addScalar("Ptype", Hibernate.STRING);
			Iterator barobjects = null;
			try {
				barobjects = barchartquery.list().iterator();
			} catch (HibernateException he) {
				log.info("Possibly a communications link failure, check database connectivity");
			}
			if (barobjects.hasNext()) {

				while (barobjects.hasNext()) {
					Object[] barlist = (Object[]) barobjects.next();
					json += "{ \"c\":[{\"v\":\"" + barlist[1].toString()
							+ "\"}" + ",";
					json += " {\"v\":" + "\"" + barlist[0].toString() + "\"}]}";
					if (barobjects.hasNext()) {
						json += ",";
					}
				}
			}

		} else {
			if (epid == null)
				epid = "141128";
			if (timepoint == null)
				timepoint = "72";
			if (concentration == null)
				concentration = "5";

			String BarQuery = "select count(distinct e2p.participantid) as ActiveCompoundCount,p.name as Pname,p.id as Pid ,p.paticipanttype as Ptype"
					+ " from endpoint e "
					+ " inner join endpoint_has_participant e2p on (e2p.endpointid = e.id) "
					+ " inner join endpoint_has_participant e2p2 on (e2p2.endpointid = e.id) "
					+ "  inner join participant p on (p.id = e2p2.participantid) "
					+ "   inner join perturbation_measure pm on (pm.measureid = e.measureid) "
					+ " where pm.name = 'active' "
					+ " and e.epname_epid = "
					+ epid
					+ " and e2p.participantid in ( "
					+ targetArray
					+ " )"
					+ " and e2p.participantid != e2p2.participantid"
					+ " and e.timepoint ="
					+ timepoint
					+ " and e.concentration = "
					+ concentration
					+ " group by p.name,p.id,p.paticipanttype "
					+ " order by count(distinct e2p.participantid) desc ";
			SQLQuery barchartquery = ses.createSQLQuery(BarQuery)
					.addScalar("ActiveCompoundCount", Hibernate.INTEGER)
					.addScalar("Pname", Hibernate.STRING)
					.addScalar("Pid", Hibernate.INTEGER)
					.addScalar("Ptype", Hibernate.STRING);
			Iterator barobjects = null;
			try {
				barobjects = barchartquery.list().iterator();
			} catch (HibernateException he) {
				log.info("Possibly a communications link failure, check database connectivity");
			}
			if (barobjects.hasNext()) {

				while (barobjects.hasNext()) {
					Object[] barlist = (Object[]) barobjects.next();
					json += "{ \"c\":[{\"v\":\"" + barlist[1].toString()
							+ "\"}" + ",";
					json += " {\"v\":" + "\"" + barlist[0].toString() + "\"}]}";
					if (barobjects.hasNext()) {
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
