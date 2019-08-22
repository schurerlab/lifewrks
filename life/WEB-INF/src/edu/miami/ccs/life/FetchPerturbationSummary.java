package edu.miami.ccs.life;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
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
//import org.hibernate.type.StandardBasicTypes;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class to fetch perturbation summary
 * 
 * @author D Puram, UM Center for Computational Science
 * @version 1.0
 */

public class FetchPerturbationSummary extends HttpServlet {
	
	/**
	 * Constant serialized ID used for compatibility.
	 */
	private static final long serialVersionUID = 1289131677014606851L;
	Logger log = Logger.getLogger(this.getClass().getName());
	SessionFactory sessions;
	Session ses;

	
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
		//String type = request.getParameter("type");
		//String measure = request.getParameter("measure");
		String input = request.getParameter("input");
		sessions = connect();
		ses = sessions.openSession();

		String json = "";
		json += "{ \"results\": [";

		String QueryForData = "select distinct e.id as id,e.value as evalue, e.timepoint as time, e.timeunit as timeunit, e.concentration as concentration,e.unit as concenunit, a.assayname as experimentname,a.assayformat as assayformat,a.assaytype as assaytype,a.assay_type_name as assayname from endpoint e " 
				+"inner join assay a on (e.assay_assayid = a.assayid)"
				 +"where e.id ="+ input.trim(); 
		
		
		System.out.println(QueryForData);
		
		SQLQuery query = ses.createSQLQuery(QueryForData)
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("evalue", Hibernate.FLOAT)
				.addScalar("time", Hibernate.STRING)
				.addScalar("timeunit", Hibernate.STRING)
				.addScalar("concentration", Hibernate.FLOAT)
				.addScalar("concenunit", Hibernate.STRING)
				.addScalar("experimentname", Hibernate.STRING)
				.addScalar("assayformat", Hibernate.STRING)
				.addScalar("assaytype", Hibernate.STRING)
				.addScalar("assayname", Hibernate.STRING);
		
		
		Iterator objects = null;
		try {
			objects = query.list().iterator();
		} catch (HibernateException he) {
			log.info("Possibly a communications link failure, check database connectivity");
		}

		if (objects.hasNext()) {
			int i = 0;
			String prevType = "";
			
			while (objects.hasNext()) {
				Object[] tuple = (Object[]) objects.next();
				

				json += "{\"id\":\"" + tuple[0].toString() + "\","
						+ "\"value\":\"" + tuple[1].toString() + "\","
						+ "\"time\":\"" + tuple[2].toString() + "\","
						+ "\"timeunit\":\"" + tuple[3].toString() + "\","
						+ "\"concentration\":\"" + tuple[4].toString()+ "\","
						+ "\"concentrationunit\":\"" + tuple[5].toString() + "\","
						+ "\"experimentname\":\"" + tuple[6].toString() + "\","
						+ "\"assayformat\":\"" + tuple[7].toString() + "\","
						+ "\"assaytype\":\"" + tuple[8].toString() + "\","
						+ "\"assayname\":\"" + tuple[9].toString() + "\",";
						
				
				String QueryForParticipantData = "select distinct p.id as id,p.name as name,p.paticipanttype as type from endpoint e " 
						+ " inner join endpoint_has_participant ep on (ep.endpointid = e.id) "
				        + " inner join participant p on (p.id = ep.participantid)"
				        + "  where e.id = "+ input.trim(); 

				
				System.out.println(QueryForParticipantData);
				
				SQLQuery query2 = ses.createSQLQuery(QueryForParticipantData)
						.addScalar("id", Hibernate.INTEGER)
						.addScalar("name", Hibernate.STRING)
						.addScalar("type", Hibernate.STRING);
				
				Iterator participants = null;
				try {
					participants = query2.list().iterator();
				} catch (HibernateException he) {
					log.info("Possibly a communications link failure, check database connectivity");
				}

				if (participants.hasNext()) {
				json +="\"participant\":[";
					while (participants.hasNext()) {
						Object[] tuples = (Object[]) participants.next();
						String type = tuples[2].toString();
						if(!type.equals(null))
						{
							if(type.equals("Cell"))
								type = "CellLine";
							else if(type.equals("Small molecule"))
								type = "SmallMolecule";
						}

						json += "{\"id\":\"" + tuples[0].toString() + "\","
								+ "\"name\":\"" + tuples[1].toString() + "\","
								+ "\"type\":\"" + type						
								+ "\"}";
						
						if (participants.hasNext())
							json += ",";
					}

				}

				if (objects.hasNext())
					json += ",";
			}
			json+="]";

		}
		
		json += "}] }";
		System.out.println("json"+json);
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



