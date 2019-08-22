package edu.miami.ccs.life;

//import LoadingOntologies;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import uk.ac.ebi.kraken.uuw.services.remoting.EntryRetrievalService;
import uk.ac.ebi.kraken.uuw.services.remoting.UniProtJAPI;

import uk.ac.ebi.kraken.interfaces.uniprot.Keyword;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;
import uk.ac.ebi.kraken.interfaces.uniprot.description.Field;
import uk.ac.ebi.kraken.interfaces.uniprot.description.FieldType;
import uk.ac.ebi.kraken.uuw.services.remoting.EntryIterator;
import uk.ac.ebi.kraken.uuw.services.remoting.EntryRetrievalService;
import uk.ac.ebi.kraken.uuw.services.remoting.Query;
import uk.ac.ebi.kraken.uuw.services.remoting.UniProtJAPI;
import uk.ac.ebi.kraken.uuw.services.remoting.UniProtQueryBuilder;
import uk.ac.ebi.kraken.uuw.services.remoting.UniProtQueryService;
import uk.ac.ebi.kraken.interfaces.uniprot.description.Name;

public class TragetFetcher  implements FactoryInterface{
	public Object getdata(Integer id, String fieldname,String functionName) {
		List s=new ArrayList();
		String query="select p.proteinname,p.mutation,p.modification,p.organism,p.labelingsite,p.uniprotid,p.proteinid" 
				  + " from protein p inner join endpoint e on (p.proteinid = e.protein_participantid)  where e.ID="+id;
		
		  try{
			  DBConnection dbcon = new DBConnection();
			  Connection con1 = dbcon.getConnection();
		  try{
			  
			  List<String> m = new ArrayList<String>();
			  
			  Statement st = con1.createStatement();  
			  
			  ResultSet res = st.executeQuery(query);
			  while (res.next()) {
	  			  s.add (res.getString("ProteinName"));
	  			  s.add(res.getString("Mutation"));
	  			  s.add(res.getString("Modification"));
	  			  s.add(res.getString("Organism"));  
	  			 s.add(res.getString("LabelingSite"));  
	  			s.add(res.getString("UniprotId"));  
	  			 s.add(res.getString("ProteinId"));
			  
				EntryRetrievalService entryRetrievalService = UniProtJAPI.factory.getEntryRetrievalService();
			   	UniProtEntry entry = (UniProtEntry) entryRetrievalService.getUniProtEntry(res.getString("uniprotid"));
			    if (entry != null) {
			    	
			    	 s.add ( entry.getDatabaseCrossReferences());
			    	 s.add ( res.getString("uniprotid"));
			    	 s.add (entry.getOrganism());
			    	 s.add ( entry.getUniProtId());
			    	 final List<Field> fullFields = entry.getProteinDescription().getRecommendedName().getFieldsByType(FieldType.FULL);
			    	 
//			    	 LoadingOntologies lo = new LoadingOntologies();
//				 	 s.add ( lo.getOntologyClasses("Organism", res.getString("Organism") ));
//				 	 
			    	
			    	 if (!fullFields.isEmpty()) {
			             String desc = fullFields.get(0).getValue();
			             s.add (desc);
			         }
	 
			    	 for (Name name : entry.getProteinDescription().getAlternativeNames()) {
			             final List<Field> AltFields = name.getFieldsByType(FieldType.FULL);
			            
			             for (int i = 0; i<AltFields.size(); i++){
			            	 s.add ( AltFields.get(i).getValue());
			             }
			         }
			    	 
			    	 for (Name name : entry.getProteinDescription().getAlternativeNames()) {
			             final List<Field> ShortFields = name.getFieldsByType(FieldType.SHORT);
			            
			             for (int i = 0; i<ShortFields.size(); i++){
			            	 s.add ( ShortFields.get(i).getValue());
			             }
			         }

			    }
			    
	  		   
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
