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
public class CompoundSummary  extends HttpServlet
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
		String QueryStr = "select smallmoleculename,smile,molecularweight,molecularformulae,perturbagenid as Lincs_CompoundId,pubchemid,smallmoleculeid from smallmolecule where smallmoleculeid ="+input.trim();
		
		SQLQuery query = ses.createSQLQuery(QueryStr).addScalar("smallmoleculename", Hibernate.STRING).addScalar("smile",  Hibernate.STRING).addScalar("molecularweight", Hibernate.STRING).addScalar("molecularformulae", Hibernate.STRING).addScalar("Lincs_CompoundId", Hibernate.STRING).addScalar("pubchemid", Hibernate.STRING).addScalar("smallmoleculeid", Hibernate.STRING);
		
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
	            jsonObj.put("smallmoleculename", (String)tuple[0]);
	            jsonObj.put("smile", (String)tuple[1]);
	            jsonObj.put("molecularweight", (String)tuple[2]);  
	            jsonObj.put("molecularformulae", (String)tuple[3]); 
	            jsonObj.put("Lincs_CompoundId", (String)tuple[4]);
	            jsonObj.put("pubchemid", (String)tuple[5]);
	            jsonObj.put("smallmoleculeid", (String)tuple[6]);
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






