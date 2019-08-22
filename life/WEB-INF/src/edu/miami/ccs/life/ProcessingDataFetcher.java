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

public class ProcessingDataFetcher extends HttpServlet {
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

		String input = request.getParameter("input");
		String	timepoint = request.getParameter("tp");	
		String	concentration = request.getParameter("conc");
	    String epid = request.getParameter("epid");
		
		sessions = connect();
		ses = sessions.openSession();
  if (!input.equals(null)){
		
		String json = "";
		json += "{ \"results\": [";
		String Query = "select distinct ep.name as Type,count(distinct pm.name) as countOf,e.epname_epid as epid " +
				"from endpoint e"
				+ " inner join epname ep on (ep.epid = e.epname_epid)"
				+ "   inner join perturbation_measure pm on (pm.measureid = e.measureid)"
				+ "      inner join endpoint_has_participant ehp on  (ehp.endpointid = e.id)"
				+ "        INNER JOIN endpoint_has_participant ehp2 on (ehp2.endpointid = e.id)"
				+ "        inner join participant p on (p.id = ehp.participantid)"
				+ "        inner join participant p2 on (p2.id = ehp2.participantid)"
				+ "     where p.id ="
				+ input.trim()
				+ "     and p2.id != "
				+ input.trim() + " and e.epname_epid="+epid;
		if (timepoint!=null){
			
		Query +=" and e.timepoint ="+timepoint +" and e.concentration =" +concentration;
		Query+= " group by ep.name,e.epname_epid";
			
		}
		else{
			Query+= " group by ep.name,e.epname_epid";
		}

		SQLQuery querycount = ses.createSQLQuery(Query)
				.addScalar("Type", Hibernate.STRING)
				.addScalar("countOf", Hibernate.INTEGER)
				.addScalar("epid", Hibernate.INTEGER);
		Iterator countobjects = null;
		try {
			countobjects = querycount.list().iterator();
		} catch (HibernateException he) {
			log.info("Possibly a communications link failure, check database connectivity");
		}
		if (countobjects.hasNext()) {
			while (countobjects.hasNext()) {
				Object[] type = (Object[]) countobjects.next();
			
		String QueryForData = "select distinct ep.name as Perturbation_Type,pm.name  as Perturbation_Measure, count(pm.name) as count,e.epname_epid as epid " +
				" from endpoint e"
				+ " inner join epname ep on (ep.epid = e.epname_epid)"
				+ "   inner join perturbation_measure pm on (pm.measureid = e.measureid)"
				+ "      inner join endpoint_has_participant ehp on  (ehp.endpointid = e.id)"
				+ "        INNER JOIN endpoint_has_participant ehp2 on (ehp2.endpointid = e.id)"
				+ "        inner join participant p on (p.id = ehp.participantid)"
				+ "        inner join participant p2 on (p2.id = ehp2.participantid)"
				+ "     where p.id ="
				+ input.trim()
				+ "     and p2.id != " 
				+ input.trim();
		
		if (epid!=null){
		if (epid.equals(type[2].toString())){
			QueryForData +=  " and ep.epid  =" +epid +" and e.timepoint ="+timepoint +" and e.concentration =" +concentration;
			QueryForData +="   group by pm.name,ep.name,e.epname_epid";
		}else{
			QueryForData += " and ep.name ='" + type[0]+"'" ;
			QueryForData +="   group by pm.name,ep.name,e.epname_epid";
		}
		}
		else
		{
			QueryForData += " and ep.name ='" + type[0]+"'" ;
			QueryForData +="   group by pm.name,ep.name,e.epname_epid";
		}
		json += "{" + "\"type\":\"" + type[0].toString()
				+ "\"," + "\"epid\":\"" +type[2].toString() + "\","
				+" \"statistics\": [";
		
		SQLQuery query = ses.createSQLQuery(QueryForData)
				.addScalar("Perturbation_Type", Hibernate.STRING)
				.addScalar("Perturbation_Measure", Hibernate.STRING)
				.addScalar("count", Hibernate.INTEGER)
				.addScalar("epid", Hibernate.INTEGER);
		Iterator objects = null;
		try {
			objects = query.list().iterator();
		} catch (HibernateException he) {
			log.info("Possibly a communications link failure, check database connectivity");
		}
		int count = (Integer) type[1];
		if (objects.hasNext()) {
			
			String prevType = "";
		
			while (objects.hasNext()) {
				Object[] tuple = (Object[]) objects.next();
				if (count == 2){
				json += "{\"measure\":\"" + tuple[1].toString() + "\","
						+ "\"count\":\"" + tuple[2].toString() + "\"}";
				}else{
					if (tuple[1].toString().equals("active")){
						json += "{\"measure\":\"" + tuple[1].toString() + "\","
							 + "\"count\":\"" + tuple[2].toString() + "\"},";
						json += "{\"measure\":\"inactive\","
						     + "\"count\":\"0\" }";
					}else{
						json += "{\"measure\":\"active\","
							     + "\"count\":\"0\" },";
						json += "{\"measure\":\"" + tuple[1].toString() + "\","
							 + "\"count\":\"" + tuple[2].toString() + "\"}";
					}
					
				}
				
				if (objects.hasNext())
					json += ",";
				
			}
			
			json += "]}"; 
			if (countobjects.hasNext()){
				json += ",";
				}

		}
		
		}
		

		json += "] }";
		out.print(json);
		
     }
  }
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
