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

public class FilterCompareTableFeeder extends HttpServlet {
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
		String compoundArray = request.getParameter("compArray");
		String targetArray = request.getParameter("trgArr");
		String mode = request.getParameter("elementType");
		String num = request.getParameter("i");
		if (request.getParameter("tp") != null)
			timepoint = request.getParameter("tp");
		if (request.getParameter("conc") != null)
			concentration = request.getParameter("conc");
		if (request.getParameter("epid") != null)
			epid = request.getParameter("epid");
		int i = 0;
		int next = 0;

		sessions = connect();
		ses = sessions.openSession();
		String json = "";
		String target = "";
		String targetName = "";
		String participant = "";
		String targetidlist = "";
		String compareType = "";

		if (!mode.equals("CellLine")) {
			if (epid == null)
				epid = "25106";
			if (timepoint == null)
				timepoint = "1";
			if (concentration == null)
				concentration = "10";
			json += "{";
			json += "\"" + "XList" + "\":{ \"elem\":[";
			String TargetQuery = "select count(e2p.participantid) as ActiveCompoundCount,p.name as Pname,p.id as Pid,p.paticipanttype as Ptype "
					+ " from endpoint e "
					+ " inner join endpoint_has_participant e2p on (e2p.endpointid = e.id) "
					+ " inner join endpoint_has_participant e2p2 on (e2p2.endpointid = e.id) "
					+ "  inner join participant p on (p.id = e2p2.participantid) "
					+ "   inner join perturbation_measure pm on (pm.measureid = e.measureid) "
					+ " where pm.name = 'active' "
					+ " and e.epname_epid = "
					+ epid
					+ " and e2p2.participantid in ( "
					+ targetArray
					+ " )"
					+ " and e2p.participantid in ( "
					+ compoundArray
					+ " )"
					+ " and e.timepoint ="
					+ timepoint
					+ " and e.concentration = "
					+ concentration
					+ " group by p.name,p.id,p.paticipanttype "
					+ " order by count(e2p.participantid) desc ";
			SQLQuery targettablequery = ses.createSQLQuery(TargetQuery)
					.addScalar("ActiveCompoundCount", Hibernate.INTEGER)
					.addScalar("Pname", Hibernate.STRING).addScalar("Pid",
							Hibernate.INTEGER).addScalar("Ptype",
							Hibernate.STRING);
			Iterator targettableobjects = null;
			try {
				targettableobjects = targettablequery.list().iterator();
			} catch (HibernateException he) {
				log
						.info("Possibly a communications link failure, check database connectivity");
			}
			if (targettableobjects.hasNext()) {

				while (targettableobjects.hasNext()) {
					Object[] targetlist = (Object[]) targettableobjects.next();

					target += targetlist[2].toString();
					targetName += targetlist[1].toString();
					if (targettableobjects.hasNext()) {
						target += ",";
						targetName += ";";
					} else {
						compareType += targetlist[3].toString();
						;
					}

				}
			}
			String[] plist;
			plist = target.split(",");

			String[] tnamelist;
			tnamelist = targetName.split(";");

			if (num.equals("1")) {
				i = 0;

			} else {
				i = (Integer.parseInt(num) - 1) * 10;
			}
			if (i + 10 < plist.length) {
				next = i + 10;
			} else {
				next = plist.length;
			}
			for (int j = i; j < next; j++) {
				json += "{ \"name\":\"" + tnamelist[j] + "\",";
				json += "  \"id\":\"" + plist[j] + "\"}";
				targetidlist += plist[j];
				if (j < next - 1) {
					json += ",";
					targetidlist += ",";
				} else {
					json += "],";
					json += "\"compareType\":\"" + compareType + "\"},";
				}
			}
			String PartipantQuery = "select distinct e2p.participantid as cid,p.name as Cname,p.paticipanttype as Ptype "
					+ "from endpoint e "
					+ " inner join endpoint_has_participant e2p on (e2p.endpointid = e.id) "
					+ " inner join endpoint_has_participant e2p2 on (e2p2.endpointid = e.id) "
					+ "  inner join participant p on (p.id = e2p.participantid)   "
					+ "   inner join perturbation_measure pm on (pm.measureid = e.measureid) "
					+ " where pm.name = 'active' "
					+ " and e.epname_epid = "
					+ epid
					+ " and e2p.participantid in ( "
					+ compoundArray
					+ " )"
					+ " and e2p.participantid != e2p2.participantid"
					+ " and e.timepoint ="
					+ timepoint
					+ " and e.concentration = " + concentration;
			SQLQuery participantquery = ses.createSQLQuery(PartipantQuery)
					.addScalar("cid", Hibernate.INTEGER).addScalar("Cname",
							Hibernate.STRING).addScalar("Ptype",
							Hibernate.STRING);
			Iterator participantobjects = null;
			try {
				participantobjects = participantquery.list().iterator();
			} catch (HibernateException he) {
				log
						.info("Possibly a communications link failure, check database connectivity");
			}
			if (participantobjects.hasNext()) {
				String tempeptype = new String("");
				while (participantobjects.hasNext()) {
					Object[] participantlist = (Object[]) participantobjects
							.next();
					if (!((String) participantlist[2]).equals(tempeptype)) {
						json += "\"" + "YList" + "\":[";

					}
					json += "\"" + participantlist[1].toString() + "\"";
					participant += participantlist[0].toString();
					if (participantobjects.hasNext()) {
						json += ",";
						participant += ",";
					} else {
						json += "],\"data\": [";
					}
					tempeptype = participantlist[2].toString();
				}
			}
			String[] clist;

			clist = participant.split(",");

			if (!participant.equals("")) {
				for (i = 0; i < clist.length; i++) {
					json += "{\"combi\": [";
					System.out.println(clist[i]);
					String DataQuery = "select distinct p.name as Pname,p2.name as CName,e.value as Value "
							+ " from endpoint e "
							+ " inner join endpoint_has_participant e2p on (e2p.endpointid = e.id) "
							+ " inner join participant p on (p.id = e2p.participantid) "
							+ "  inner join endpoint_has_participant e2p2 on (e2p2.endpointid = e.id) "
							+ "    inner join participant p2 on (p2.id = e2p2.participantid) "
							+ " where e2p.participantid ="
							+ clist[i]
							+ " and e2p2.participantid in ("
							+ targetidlist
							+ " ) "
							+ " and e.epname_epid ="
							+ epid
							+ " and e.timepoint ="
							+ timepoint
							+ "and e.concentration =" + concentration;
					SQLQuery dataquery = ses.createSQLQuery(DataQuery)
							.addScalar("Pname", Hibernate.STRING).addScalar(
									"CName", Hibernate.STRING).addScalar(
									"Value", Hibernate.FLOAT);
					Iterator dataobjects = null;
					try {
						dataobjects = dataquery.list().iterator();
					} catch (HibernateException he) {
						log
								.info("Possibly a communications link failure, check database connectivity");
					}
					if (dataobjects.hasNext()) {

						while (dataobjects.hasNext()) {
							Object[] datalist = (Object[]) dataobjects.next();
							json += "{ \"xEle\":" + "\""
									+ datalist[1].toString() + "\","
									+ "\"yEle\":" + "\""
									+ datalist[0].toString() + "\","
									+ "\"value\":" + "\""
									+ datalist[2].toString() + "\"" + "}";

							if (dataobjects.hasNext()) {
								json += ",";
							}
						}
						if (dataobjects.hasNext()) {
							json += ",";
						}

					}

					if (i < clist.length - 1) {
						json += "]},";
					} else {
						json += "]}";
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
			json += "{";
			json += "\"" + "XList" + "\":{ \"elem\":[";
			String TargetQuery = "select count(distinct e2p.participantid) as ActiveCompoundCount,p.name as Pname,p.id as Pid,p.paticipanttype as Ptype "
					+ " from endpoint e "
					+ " inner join endpoint_has_participant e2p on (e2p.endpointid = e.id) "
					+ " inner join endpoint_has_participant e2p2 on (e2p2.endpointid = e.id) "
					+ "  inner join participant p on (p.id = e2p2.participantid) "
					+ "   inner join perturbation_measure pm on (pm.measureid = e.measureid) "
					+ " where pm.name = 'active' "
					+ " and e.epname_epid = "
					+ epid
					+ " and e2p2.participantid in ( "
					+ targetArray
					+ " )"
					+ " and e2p.participantid "
					+ " and e2p.participantid in ( "
					+ compoundArray
					+ " )"
					+ " and e.timepoint ="
					+ timepoint
					+ " and e.concentration = "
					+ concentration
					+ " group by p.name,p.id,p.paticipanttype "
					+ " order by count(distinct e2p.participantid) desc ";
			SQLQuery targettablequery = ses.createSQLQuery(TargetQuery)
					.addScalar("ActiveCompoundCount", Hibernate.INTEGER)
					.addScalar("Pname", Hibernate.STRING).addScalar("Pid",
							Hibernate.INTEGER).addScalar("Ptype",
							Hibernate.STRING);
			Iterator targettableobjects = null;
			try {
				targettableobjects = targettablequery.list().iterator();
			} catch (HibernateException he) {
				log
						.info("Possibly a communications link failure, check database connectivity");
			}
			if (targettableobjects.hasNext()) {

				while (targettableobjects.hasNext()) {
					Object[] targetlist = (Object[]) targettableobjects.next();

					target += targetlist[2].toString();
					targetName += targetlist[1].toString();
					if (targettableobjects.hasNext()) {
						target += ",";
						targetName += ";";
					} else {
						compareType += targetlist[3].toString();
						;
					}

				}
			}
			String[] plist;
			plist = target.split(",");

			String[] tnamelist;
			tnamelist = targetName.split(";");

			if (num.equals("1")) {
				i = 0;

			} else {
				i = (Integer.parseInt(num) - 1) * 10;
			}
			if (i + 10 < plist.length) {
				next = i + 10;
			} else {
				next = plist.length;
			}
			for (int j = i; j < next; j++) {
				json += "{ \"name\":\"" + tnamelist[j] + "\",";
				json += "  \"id\":\"" + plist[j] + "\"}";
				targetidlist += plist[j];
				if (j < next - 1) {
					json += ",";
					targetidlist += ",";
				} else {
					json += "],";
					json += "\"compareType\":\"" + compareType + "\"},";
				}
			}
			String PartipantQuery = "select distinct e2p.participantid as cid,p.name as Cname,p.paticipanttype as Ptype "
					+ "from endpoint e "
					+ " inner join endpoint_has_participant e2p on (e2p.endpointid = e.id) "
					+ " inner join endpoint_has_participant e2p2 on (e2p2.endpointid = e.id) "
					+ "  inner join participant p on (p.id = e2p.participantid)   "
					+ "   inner join perturbation_measure pm on (pm.measureid = e.measureid) "
					+ " where pm.name = 'active' "
					+ " and e.epname_epid = "
					+ epid
					+ " and e2p.participantid in ( "
					+ compoundArray
					+ " )"
					+ " and e2p.participantid != e2p2.participantid"
					+ " and e.timepoint ="
					+ timepoint
					+ " and e.concentration = " + concentration;
			SQLQuery participantquery = ses.createSQLQuery(PartipantQuery)
					.addScalar("cid", Hibernate.INTEGER).addScalar("Cname",
							Hibernate.STRING).addScalar("Ptype",
							Hibernate.STRING);
			Iterator participantobjects = null;
			try {
				participantobjects = participantquery.list().iterator();
			} catch (HibernateException he) {
				log
						.info("Possibly a communications link failure, check database connectivity");
			}
			if (participantobjects.hasNext()) {
				String tempeptype = new String("");
				while (participantobjects.hasNext()) {
					Object[] participantlist = (Object[]) participantobjects
							.next();
					if (!((String) participantlist[2]).equals(tempeptype)) {
						json += "\"" + "YList" + "\":[";

					}
					json += "\"" + participantlist[1].toString() + "\"";
					participant += participantlist[0].toString();
					if (participantobjects.hasNext()) {
						json += ",";
						participant += ",";
					} else {
						json += "],\"data\": [";
					}
					tempeptype = participantlist[2].toString();
				}
			}
			String[] clist;

			clist = participant.split(",");

			if (!participant.equals("")) {
				for (i = 0; i < clist.length; i++) {
					json += "{\"combi\": [";
					System.out.println(clist[i]);
					String DataQuery = "select distinct p.name as Pname,p2.name as CName,e.value as Value "
							+ " from endpoint e "
							+ " inner join endpoint_has_participant e2p on (e2p.endpointid = e.id) "
							+ " inner join participant p on (p.id = e2p.participantid) "
							+ "  inner join endpoint_has_participant e2p2 on (e2p2.endpointid = e.id) "
							+ "    inner join participant p2 on (p2.id = e2p2.participantid) "
							+ " where e2p.participantid ="
							+ clist[i]
							+ " and e2p2.participantid in ("
							+ targetidlist
							+ " ) "
							+ " and e.epname_epid ="
							+ epid
							+ " and e.timepoint ="
							+ timepoint
							+ "and e.concentration =" + concentration;
					SQLQuery dataquery = ses.createSQLQuery(DataQuery)
							.addScalar("Pname", Hibernate.STRING).addScalar(
									"CName", Hibernate.STRING).addScalar(
									"Value", Hibernate.FLOAT);
					Iterator dataobjects = null;
					try {
						dataobjects = dataquery.list().iterator();
					} catch (HibernateException he) {
						log
								.info("Possibly a communications link failure, check database connectivity");
					}
					if (dataobjects.hasNext()) {

						while (dataobjects.hasNext()) {
							Object[] datalist = (Object[]) dataobjects.next();
							json += "{ \"xEle\":" + "\""
									+ datalist[1].toString() + "\","
									+ "\"yEle\":" + "\""
									+ datalist[0].toString() + "\","
									+ "\"value\":" + "\""
									+ datalist[2].toString() + "\"" + "}";

							if (dataobjects.hasNext()) {
								json += ",";
							}
						}
						if (dataobjects.hasNext()) {
							json += ",";
						}

					}

					if (i < clist.length - 1) {
						json += "]},";
					} else {
						json += "]}";
					}

				}
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
