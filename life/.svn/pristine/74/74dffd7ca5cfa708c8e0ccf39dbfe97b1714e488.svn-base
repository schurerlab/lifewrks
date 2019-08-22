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
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class to fetch Participant summary
 * 
 * @author A Koleti, UM Center for Computational Science
 * @version 1.0
 */
public class ProteinSummary  extends HttpServlet
{
	/**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1289131897014606851L;
	Logger log = Logger.getLogger(this.getClass().getName());
	SessionFactory sessions;
	Session ses;

	/**
	 * Overrides the method in GenericServlet class to create an active hibernate session  
	 */
	 //end of method
	public void init(ServletConfig config) throws ServletException
	{

		sessions = connect();
		ses = sessions.openSession();

	} 
	
	/* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
	    this.doGet(req, resp);
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		JSONObject jsonObj = new JSONObject();
		
		String input = request.getParameter("input");

		String json = "";
		String QueryStr = "select proteinname,sequence,mutation,modification,labelingsite,organism,uniprotid,proteinid from protein where proteinid ="+input.trim();
		
		SQLQuery query = ses.createSQLQuery(QueryStr).addScalar("proteinname", Hibernate.STRING).addScalar("sequence",  Hibernate.STRING).addScalar("mutation", Hibernate.STRING).addScalar("modification", Hibernate.STRING).addScalar("labelingsite", Hibernate.STRING).addScalar("organism", Hibernate.STRING).addScalar("uniprotid", Hibernate.STRING).addScalar("proteinid", Hibernate.STRING);
		
		Iterator objects = null; 
		try
		{
			objects = query.list().iterator();
		}
		catch(HibernateException he)
		{
			log.info("Possibly a communications link failure, check database connectivity");
		}
		String[] arr = new String[6];
		if(objects.hasNext())	
		{

			Object[] tuple = (Object[]) objects.next();
			try
            {
	            jsonObj.put("proteinname", (String)tuple[0]);
	            jsonObj.put("sequence", (String)tuple[1]);
	            jsonObj.put("mutation", (String)tuple[2]);  
	            jsonObj.put("modification", (String)tuple[3]); 
	            jsonObj.put("labelingsite", (String)tuple[4]);
	            jsonObj.put("organism", (String)tuple[5]);
	            jsonObj.put("uniprotid", (String)tuple[6]);
	            jsonObj.put("proteinid", (String)tuple[7]);
	            out.print(jsonObj.toString());
            }
            catch (JSONException e)
            {
	            e.printStackTrace();
            }
		
		}

	}

	/**
	 * Called by the init(...) method to generate hibernate Session object
	 * 
	 * @return Hibernate SessionFactory object
	 * 
	 */
	public static SessionFactory connect()
	{

		Configuration cfg = new Configuration();
		SessionFactory sessions = cfg.configure().buildSessionFactory();

		return sessions;
	}

}






