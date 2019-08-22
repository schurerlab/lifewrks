package edu.miami.ccs.life;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PerturbagenFetcher implements FactoryInterface{
	public Object getdata(Integer id, String fieldname,String functionName) {
		List s=new ArrayList();
		String query="select p.smallmoleculename,p.molecularweight,p.molecularformulae,p.perturbagenid,p.pubchemid,p.smallmoleculeid" 
				  + " from  smallmolecule p inner join endpoint e on (e.smallmolecule_participantid = p.smallmoleculeid)  where e.ID="+id;
		
		  try{
			  DBConnection dbcon = new DBConnection();
			  Connection con1 = dbcon.getConnection();
		  try{
			  
			  List<String> m = new ArrayList<String>();
			  
			  Statement st = con1.createStatement();  
			  
			  ResultSet res = st.executeQuery(query);
	  		  while (res.next()) {
	  			  s.add (res.getString("SmallMoleculeName"));
	  			  s.add(res.getString("MolecularWeight"));
	  			  s.add(res.getString("PubchemId"));
	  			  s.add(res.getString("PerturbagenId"));  
	  			 s.add(res.getString("SmallMoleculeId"));  
	  		  }

		  con1.close();
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
