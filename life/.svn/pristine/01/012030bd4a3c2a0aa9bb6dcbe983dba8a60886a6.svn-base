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
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author akoleti - UM Center for Computational Science
 * 
 *         Servlet implementation to return the Participant details given the Id
 */
public class RestrictListFetcher extends HttpServlet {

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
		String json = "";
		String input;
		if(request.getParameter("input").equals("")){
		System.out.println(request.getParameter("input"));
			String outputJson = "";
			outputJson+="{ \"data\":[]}";
			out.print(outputJson);
		}else{
		input = request.getParameter("input");
		System.out.println(request.getParameter("input"));
		String[] str;
		str = input.split(",");
		json +="{ \"data\":[";
		
			for (int i = 0; i < str.length; i++) {
				String QueryStr = "select p.id as id,p.name as name from participant p where p.id =" + str[i];
				System.out.println(QueryStr);
				SQLQuery query = ses.createSQLQuery(QueryStr)
					.addScalar("id", Hibernate.STRING)
					.addScalar("name", Hibernate.STRING);
				Iterator objects = null;
				try {
					objects = query.list().iterator();
					} catch (HibernateException he) {
						log.info("Possibly a communications link failure, check database connectivity");
						out.print("Error retrieving data, please try again");
						sessions.close();
					return;
					}
			
					if (objects.hasNext()) {

						Object[] tuple = (Object[]) objects.next();
						try {
							json += "{\"Id\":" + "\"" + tuple[0].toString()
							+ "\",";
							json += "\"Name\":" + "\"" + tuple[1].toString()
							+ "\"}";
								if(i<str.length-1){
								json +=",";
								}
							}

						catch (Exception e) {
							e.printStackTrace();
						}
			}	
		}
		json +="]}";
		out.print(json);
		sessions.close();
		}

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
