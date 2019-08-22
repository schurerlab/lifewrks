<%/**********************************************************************
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

***********************************************************************/

/**
 * @author ndatar
 * @version 1
 */


%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.List" %>
<%
	List<String>  worksetArr = new ArrayList();
	//There is no selected mode
	if(request.getParameter("mode") == null)
	{
		out.print("ERROR: No Mode Selected");
	}
	//We don't have a workset in the request and we don't want to clear the existing session
        else if(request.getParameter("list") == null && !request.getParameter("mode").equals("clear"))
        {
		out.print("ERROR: No "+ request.getParameter("type")+ "s were selected");
	}
	//We don't have a workset in the request but we want to clear the existing session
	else if(request.getParameter("mode").equals("clear"))
	{
			session.setAttribute(request.getParameter("type")+"WorksetCount",0);
			session.setAttribute(request.getParameter("type")+"List",null);
			out.print("Workset Cleared");
	}
	//We have a workset in the request
	else if(!request.getParameter("mode").equals("clear"))
	{
		if(session.getAttribute(request.getParameter("type")+"WorksetCount") == null){
				session.setAttribute((request.getParameter("type"))+"WorksetCount", 0);
		}
		int count = Integer.parseInt(session.getAttribute(request.getParameter("type")+"WorksetCount").toString());
		
		String[] typeList = request.getParameterValues("list");

		for (String s : typeList) {
			worksetArr.add(s);
    	}
		//We have a current workset, just need to update it
        if(session.getAttribute(request.getParameter("type")+"List") != null){
			List<String> typeArr = new ArrayList();
			typeArr = (List)session.getAttribute(request.getParameter("type")+"List");

			//We have additions to the workset
			if(request.getParameter("mode").equals("add")){
				if(count > 200){
					out.print("Update Failed - List Limit Reached");
				}else {	
						int i = 0;
						for(i = 0; i < worksetArr.size(); i++){
							if(typeArr.indexOf(worksetArr.get(i)) == -1){
								if(count < 200){
									typeArr.add(worksetArr.get(i));
									count++;
								}else {
									break;
								}
							}
						}
						session.setAttribute(request.getParameter("type")+"List", typeArr);
						if(count >= 200)
							out.print("Update Failed - List Limit Reached");
						else
							out.print("List Updated");
				}
			}
			//We need to remove elements from the workset
			else if(request.getParameter("mode").equals("delete")){
                 for(int i = 0; i < worksetArr.size(); i++){
					int delIndex = typeArr.indexOf(worksetArr.get(i));
                    if(delIndex != -1){
						 typeArr.remove(delIndex);
						 if(count >= 0 )
							count--;
					}
				}
                for(int i = 0; i < typeArr.size(); i++){
                     out.print(typeArr.get(i));
                      if(i != typeArr.size()-1){
                			 out.print(",");
                			 
                      }
                  }
                 session.setAttribute(request.getParameter("type")+"List", typeArr);
                 //out.print("Workset Updated - "+count+" Assays");
			}else
			  out.print("ERROR: Invalid Mode");
			  session.setAttribute(request.getParameter("type")+"WorksetCount", count);
		}
		//We don't have a current workset, create a new one
		else
		{
			session.setAttribute(request.getParameter("type")+"WorksetCount", 0);
			session.setAttribute(request.getParameter("type")+"List", worksetArr);
			out.print("List Created");
		}
	}	
	
%>
