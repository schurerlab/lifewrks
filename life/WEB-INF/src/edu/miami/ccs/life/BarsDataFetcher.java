package edu.miami.ccs.life;

/**
 Copyright (c) 2011, The University of Miami
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright
 notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 notice, this list of conditions and the following disclaimer in the
 documentation and/or other materials provided with the distribution.
 * Neither the name of the University of Miami nor the
 names of its contributors may be used to endorse or promote products
 derived from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL THE UNIVERSITY OF MIAMI BE LIABLE FOR ANY
 DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */

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

/**
 * @author akoleti - UM Center for Computational Science
 * 
 *         Servlet implementation to return the Participant details given the Id
 */
public class BarsDataFetcher extends HttpServlet {

	private static final long serialVersionUID = -1769618792883731258L;
	Logger log = Logger.getLogger(this.getClass().getName());
	SessionFactory sessions;
	Session ses;

	public void init(ServletConfig config) throws ServletException {

		sessions = connect();
		ses = sessions.openSession();

	}

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

		String input = request.getParameter("input");
		String json = "";

		json += "{";

		 json += "\"participantID\" : "+request.getParameter("input")+", \"results\": [";
		

		String QueryStr = "select count(pm.name) as count,pm.Name as Name,pt.name as type,et.epid from perturbation p"
				+ " inner join perturbation_has_participant pp on (pp.perturbationid = p.perturbationid)"
				+ "  inner join perturbation_type pt on (pt.perturbationtypeid = p.typeid)"
				+ "     inner join perturbation_measure pm on (pm.measureid = p.measureid)"
				+" inner join epid_perturbation_type et on (et.typeid = p.typeid)"
				+ "  where pp.participantid ="
				+ input.trim()
				+ " group by pm.Name,pt.name,et.epid "+  " order by pt.name,pm.name,et.epid";

		SQLQuery query = ses.createSQLQuery(QueryStr)
				.addScalar("count", Hibernate.INTEGER)
				.addScalar("Name", Hibernate.STRING)
				.addScalar("type", Hibernate.STRING)
				.addScalar("epid", Hibernate.INTEGER);

		Iterator objects = null;
		try {
			if (!input.equals(null))
				objects = query.list().iterator();
		} catch (HibernateException he) {
			log.info("Possibly a communications link failure, check database connectivity");
		}

		int activeCount = 0;
		int inActiveCount = 0;
		int total = 0;
		int overExpression =0;
		int underExpression = 0;
		
		if (objects.hasNext()) {
			String prevType = "";
			String type = "";
			while (objects.hasNext()) {
				Object[] tuple = (Object[]) objects.next();

				if (!prevType.equals((String) tuple[2])) {
					json += "{" + "\"type\": " + "\"" + (String) tuple[2]
							+ "\","+ "\"epid\":\"" +tuple[3].toString() + "\",";
					
					String TimeQuery = "select distinct e.timepoint as time from endpoint e "
								+" inner join assay_has_participant a2p on (a2p.assay_assayid = e.assay_assayid) "
									+"   where a2p.participantid ="+input.trim()
										+"      and e.epname_epid ="+tuple[3].toString() ;
					SQLQuery timequery = ses.createSQLQuery(TimeQuery)
							.addScalar("time", Hibernate.INTEGER);

					Iterator timeobjects = null;
					try {
						if (!input.equals(null))
							timeobjects = timequery.list().iterator();
					} catch (HibernateException he) {
						log.info("Possibly a communications link failure, check database connectivity");
					}
					if (timeobjects.hasNext()) {
						json+= "\"timepoint\":[";
						while (timeobjects.hasNext()) {
							Integer timetuple = (Integer) timeobjects.next();
							json+= "\""+timetuple +"\"";
							if(timeobjects.hasNext()){
								json+=",";
							}
						}
						json+= "],";
					}
					String ConcQuery = "select distinct e.concentration as conc from endpoint e "
							+" inner join assay_has_participant a2p on (a2p.assay_assayid = e.assay_assayid) "
								+"   where a2p.participantid ="+input.trim()
									+"      and e.epname_epid ="+tuple[3].toString() ;
				SQLQuery concquery = ses.createSQLQuery(ConcQuery)
						.addScalar("conc", Hibernate.FLOAT);

				Iterator concobjects = null;
				try {
					if (!input.equals(null))
						concobjects = concquery.list().iterator();
				} catch (HibernateException he) {
					log.info("Possibly a communications link failure, check database connectivity");
				}
				if (concobjects.hasNext()) {
					json+= "\"concentration\":[";
					while (concobjects.hasNext()) {
						float conctuple = (Float) concobjects.next();
						json+= "\""+conctuple +"\"";
						if(concobjects.hasNext()){
							json+=",";
						}
					}
					json+= "],";
				}
					
							json+= " \"statistics\": [";
				}

				if (!type.equals((String) tuple[1])) {
					if (tuple[1].toString().equals("active")) {
						activeCount = (Integer) tuple[0];
					}else if (tuple[1].toString().equals("overexpression")){
						overExpression=(Integer) tuple[0];
					}
					else if (tuple[1].toString().equals("underexpression")){
						underExpression=(Integer) tuple[0];
					}
					else{
						inActiveCount = (Integer) tuple[0];
					}
				
				} 
				if (type.equals((String) tuple[1])) {
					if (tuple[1].toString().equals("inactive")) {
						inActiveCount = (Integer) tuple[0];
						
					}
					else{
						activeCount = (Integer) tuple[0];
					}
					
				}
			
				
		
				if (((String) tuple[1]).equals("inactive")) {
					total = activeCount + inActiveCount;
					
					float activePercent = ((float) activeCount / (float) total) * 100;
					float inActivePercent = ((float) inActiveCount / (float) total) * 100;
					//System.out.println(activePercent);
					json += "{\"measure\":\"" + "active" + "\","
							+ "\"count\":\"" + activeCount + "\"},";
					json += "{\"measure\":\"" + "inactive" + "\","
							+ "\"count\":\"" + inActiveCount + "\"}";
					json+="]}";
					if (objects.hasNext())
						json += ",";
						
						
					
					activeCount = 0;
					inActiveCount = 0;
				}else if (((String) tuple[1]).equals("underexpression")){
					total = overExpression + underExpression;
					float overExpressionPercent = ((float) overExpression / (float) total) * 100;
					float underExpressionPercent = ((float) underExpression / (float) total) * 100;
					//System.out.println(activePercent);
					json += "{\"measure\":\"" + "overexpression" + "\","
					+ "\"count\":\"" + overExpression + "\"},";
			json += "{\"measure\":\"" + "underexpression" + "\","
					+ "\"count\":\"" + underExpression + "\"}";
			json+="]}";
			if (objects.hasNext())
				json += ",";
					overExpression = 0;
					underExpression = 0;
				}
				 
				type = (String) tuple[1];
				prevType = (String) tuple[2];

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
