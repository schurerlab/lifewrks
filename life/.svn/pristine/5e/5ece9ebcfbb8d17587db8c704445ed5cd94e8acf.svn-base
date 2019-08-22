<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.List" %>
<%
	List<String>  originalList = new ArrayList();
	StringBuffer  compareList = new StringBuffer();
	
	//Adding elements in the list
	if(request.getParameter("compareMode").equals("add")){
		
			if(request.getParameterValues("compareList") == null){
				out.print("ERROR: Please select at least one "+ request.getParameter("compareType"));
			}else{
				String[] compareParams = request.getParameterValues("compareList");
				out.print("ADD");
				
				if(session.getAttribute(request.getParameter("compareType")+"List")!= null){
					originalList = (List) session.getAttribute(request.getParameter("compareType")+"List");
				}
				
				for(int i = 0; i < compareParams.length; i++){
					if(originalList.indexOf(compareParams[i]) == -1){
							originalList.add(compareParams[i]);
					}
				}
				session.setAttribute(request.getParameter("compareType")+"List", originalList);
				out.print("Element list updated.");
				
		  }
			
 	}else if(request.getParameter("compareMode").equals("filter")){
 		
 			List compTypeList = (List)session.getAttribute(request.getParameter("compareType")+"List");
 			
 			if(compTypeList == null || compTypeList.size() == 0){
				out.print("ERROR: Please add "+request.getParameter("compareType")+ "s to list");
			}else{
					List filterList = compTypeList;
					 for(int i = 0; i < filterList.size(); i++){
	                     out.print(filterList.get(i));
	                      if(i != filterList.size()-1){
	                			 out.print(",");
	                			 
	                      }
	                 }out.print(";");
	                 
	     			 List filterTrgList = (List)session.getAttribute(request.getParameter("elementType")+"List");
	     			 if(filterTrgList != null){
		     				for(int i = 0; i < filterTrgList.size(); i++){
		     					  out.print(filterTrgList.get(i));
		     			          if(i != filterTrgList.size()-1){
		     			        	  out.print(",");
		     			          }
		     			    }
	     		    }
		   }
	}
%>

