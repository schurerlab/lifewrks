package edu.miami.ccs.life;



import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.postgresql.util.PSQLException;

import java.sql.*;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

import com.csvreader.CsvReader;

import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;
import uk.ac.ebi.kraken.interfaces.uniprot.description.Field;
import uk.ac.ebi.kraken.interfaces.uniprot.description.FieldType;
import uk.ac.ebi.kraken.interfaces.uniprot.description.Name;
import uk.ac.ebi.kraken.uuw.services.remoting.EntryRetrievalService;
import uk.ac.ebi.kraken.uuw.services.remoting.UniProtJAPI;


public class Journalist {

	public static void main(String[] args)throws IOException, SolrServerException {
			
		CommonsHttpSolrServer server = new CommonsHttpSolrServer("http://lincs.ccs.miami.edu:8080/solr-example/");
		
		  Connection con = null;
		  String url = "jdbc:postgresql://cheddar.ccs.miami.edu:5432/life_db";
		  String db = "life_db";
		  String driver = "org.postgresql.Driver";
		  String user = "postgres";
		  String pass = "postgres";
		  try{
	    	  Class.forName(driver).newInstance();
	    	  con = DriverManager.getConnection(url, user, pass);

	    	  Statement st = con.createStatement();
	    	  String query = "select e.id,ep.name from endpoint e inner join epname ep on (e.epname_epid = ep.epid) where e.id >90578";
	    	  ResultSet res = st.executeQuery(query);

	    	  while (res.next())
	          {
	    		  SolrInputDocument doc = new SolrInputDocument();
	    		  CsvReader products = new CsvReader("/Users/akoleti/Documents/workspace/Journalist/CsvFile/Test.csv");
	  	  		
					products.readHeaders();
					doc.addField("PerturbationId",res.getInt("ID"));
					doc.addField("PerturbationType", res.getString("name"));
	    		  while (products.readRecord())
					{
	    			  
	    			  if(products.get("TypeOfPerturbation").toString().toLowerCase().equalsIgnoreCase(res.getString("name").toString().toLowerCase()))
	    			  {
	    				  	
	    				    GetDataFactory df = new GetDataFactory();
	    					Object o = df.getD(res.getInt("ID"),products.get("FieldName"),products.get("Methods"));
//	    				  System.out.println(o);
	    				  String FieldName = products.get("FieldName");
	    				  
	    				 System.out.println(res.getInt("ID"));
	    				  doc.addField(FieldName,o);
	    			  }		
					}
	    		  products.close();
	    		  server.add(doc);
	 	    	 server.commit();
	    	  }
		  	}
	    	  catch (SQLException s){
	    	  System.out.println("SQL code does not execute.");
	    	  }  
		  
		  catch (Exception e){
		  e.printStackTrace();
		  }

		}

}