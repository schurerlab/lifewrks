package edu.miami.ccs.life;
/*
Chinmay Murugkar<br>
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SmallMoleculeDataHandler implements FactoryInterface{
	public Object getdata(Integer id, String fieldname,String functionName) {
		List s=new ArrayList();
		String query="select s."+fieldname
				  + " from endpoint e inner join smallmolecule s on (s.smallmoleculeid = e.smallmolecule_participantid) where e.ID = "+id;
//		System.out.println("Getting All Rows from a table!");
		 try{
			  DBConnection dbcon = new DBConnection();
			  Connection con = dbcon.getConnection();
		  try{
			  
			  Map<String,String> m = new HashMap<String,String>();
			  m.put(functionName,fieldname);
			  
			  Statement st = con.createStatement();  
			  
			  ResultSet res = st.executeQuery(query);
	  		  while (res.next()) {
	  			  s.add (res.getString(m.get(functionName)));
	  		  }

		  con.close();
		  }
		  catch (SQLException k){
		  System.out.println(k+"SQL code does not execute.");
		  }  
		  }
		  catch (Exception e){
		  e.printStackTrace();
		  } 
		  return s ;
	 }
}


