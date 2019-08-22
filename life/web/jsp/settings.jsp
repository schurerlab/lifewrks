<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html dir="ltr" lang="en-US">
<html>

	<head>
		<meta charset="UTF-8" />
		<title>Life - Search Settings</title>
		

    	<%@ include file = "imports.jsp" %>
    	<script type="text/javascript">
		 <%
		 
		 	if(session.getAttribute("index") == null)
		 		session.setAttribute("index", "perturbation");
		  String elements[] = new String[] {"SmallMolecule", "Protein", "Gene", "CellLine"};
		 
		  out.print("var elementsJSON = '( {\"lists\": [");
	      for(int i = 0; i < elements.length; i++)
	      { 
	    		if(session.getAttribute(elements[i]+"List") != null)
	    		{
	    			out.print("{ \"type\":\""+elements[i]+"\",\"elements\": \"");
		    		List elementList = (List)session.getAttribute(elements[i]+"List");
	   		  		for(int j = 0; j < elementList.size(); j++)
		   		  	{
			        	out.print(elementList.get(j));
			          	if(j != elementList.size()-1){
			             	out.print(",");
			          	}
			      	}
		   		  	out.print("\" }");
		    		if(i != elements.length-1)
			    		out.print(",");
	    		}
	      }
	      out.print("] } )';");
		 %>
		</script>
		<script type="text/javascript">
		var type = "Protein";


			$(function() {
		    
		        $(".tab").click(function()
		        {
					$(".tab").removeClass("active");
					$(this).addClass("active");
				});
		        elementsJSON = eval(elementsJSON);
		    	if(elementsJSON.lists.length == 0)
		        	$("#optsControl").hide();
			});
			
			function changeTab(tabId)
			{
				$(".tabContent").hide();
				$("#"+tabId+"Tab").show();
			}
			
			function setIndex()
			{
				$.get("set-index", {mode: $("#index").val()}, worksetResult);
			}
			
			function populateLists()
			{
				if(elementsJSON.lists.length == 0)
				{
					$("#searchOpts").html("<br><span id = 'message' class = 'errorHighlight'>No participants in the list! Please add some participants and try again.</span>");
					return;
				}
				if(requestCount < elementsJSON.lists.length)
				{
					
					$.getJSON( "list-fetcher", {input: elementsJSON.lists[requestCount].elements}, function(response){
						var termsStr = "<span class = 'subhead'>Select "+camelcase(elementsJSON.lists[requestCount].type)+"</span><select multiple = 'multiple'  class = 'picker' name = '"+elementsJSON.lists[requestCount].type+"' id ='"+elementsJSON.lists[requestCount].type+"'>";
						var terms = response.data;
						for(var i = 0; i < terms.length; i++)
						{
							termsStr += "<option value = '"+terms[i].Name+"'>"+terms[i].Name+"</option>";
						}
						termsStr += "</select>";
						$("#"+elementsJSON.lists[requestCount].type+"Well").html(termsStr);
						requestCount++;
						populateLists();
					});
				}
				else
				{
					$('select.picker').multiselect().multiselectfilter();
			        $("#go").click(function()
					        {
			        			var searchStr = $("#qDefault").val();
			        			for(var i = 0; i < elementsJSON.lists.length; i++)
			        			{
			        				var currList = $("#"+elementsJSON.lists[i].type+"").val() || [];
			        				
			        				if(currList.length > 0)
				        				searchStr += " "+ elementsJSON.lists[i].type + "Name: ("+currList.join(" || ")+") ";
			        				
			        			}
			        			$("#qDefault").val(searchStr);

			        			$("#searchForm").submit();
								//return false;
							});
				}
			}
			
		</script>
		
	</head>

	<body>

		<div id = "release-badge"></div>
		<div class = "topmenu">
			<span class = "button">
				<a class = "nav" href = "/life/" id = "home" title = "Home">&nbsp;</a>
			</span>
			<a class = "nav" href = "#">Structure</a>
			
			
			<div class = "buttonContainer">
				<span class = "button">
					<a class = "nav" href = "settings" id = "settings" title = "Change Search Settings">&nbsp;</a>
				</span>
				<span class = "button">
					<a class = "nav" href = "#" id = "video" title = "View Video">&nbsp;</a>
				</span>
				<span class = "button">
					<a class = "nav" href = "#" id = "help" title = "Help">&nbsp;</a>
				</span>
				
			</div>

		</div>
		<div class = "content">
				<div id = "header">
					<div id = "logo"></div>
					
				</div>
				<div id = "tabHolder">
					<div id = "tabs">
						<a href = "#" class = "tab active" onClick = "changeTab('index');">Switch Index</a>
						<a href = "#" class = "tab" onClick = "changeTab('restrict');populateLists();">Restrict Search</a>
						<a href = "#" class = "tab" onClick = "changeTab('advanced');">Advanced Search</a>
					</div>
	
				</div>
				<div id = "dynamic"></div>
				<div class = "tabContent" id = "indexTab">
					<span class = "sectionTitle">Select the Default Index</span>
					<br>
					Select the default index against which all queries are run. You can choose between the Perturbation Index () and the Participant Index(). Once you have set an index on this page, it will be applied to all searches across the site.
					<br>
					
					<select name = "index" id = "index"><option selected value = "perturbation">Perturbation Index</option><option value = "participant">Participant Index</option></select> 
					<a href = "#" onClick = "setIndex();" class = "cMenu save" title = "Apply Changes">Apply</a>
					<div id = "message"></div>
				</div>
				<div class = "tabContent" id = "restrictTab" style = "display: none;">
					<span class = "sectionTitle">Restrict Search</span>
					Restrict search to query only against items that you have added to the lists of participants from previous searches, compares or summary views. To use this functionality, select the individual participants you want from the dropdown lists below, type in the search term and submit.
					<br>
					<form id = "searchForm" action = "/life/<%=session.getAttribute("index")%>" method = "GET">
					<div id = "searchOpts">
					<%
						for(int i = 0; i < elements.length; i++)
					      {
					    		out.print("<div id = '"+elements[i]+"Well'></div>\n"); 
					      }
					
					%>

							<br><br>
							<div id = "searchWrapper" style = "position: relative;">
								<div id = "defaultSuggest" class = "suggestContainer">
									<span style = "position: absolute; left: 10px; top: 3px; display: inline-block;">Suggest Mode: Default</span>
									<input type = "text" id = "qDefault" name = "q">
									<input type = "submit" class = "search" id = "go" value = "">
								</div>
							</div>							
							<input type="hidden" name="wt" value="json" />
							<input type="hidden" name="indent" value="true" />
							<input type="hidden" name="group" value="true" />
							<input type="hidden" name="group.field" id = "groupField" value="ProteinId" />
							<input type="hidden" name="rows" value="-1" />	
					</div>
					</form>
				</div>
				<div class = "tabContent" id = "advancedTab" style = "display: none;">
					<span class = "sectionTitle">Advanced Search</span>
				
				</div>
		</div>
		<%@ include file = "footer.jsp" %>
		
	</body>

</html>
