		<div id = "release-badge"></div>
		<div class = "topmenu">
			<span class = "button">
				<a class = "nav" href = "/life/" id = "home" title = "Home">&nbsp;</a>
			</span>
			<a class = "nav" href = "#">Structure</a>
			<form id = "searchForm" action = "/life/<%=session.getAttribute("index")%>" method = "GET" onSubmit = "return validateSearch();">
				<div id = "toggleContainer">Suggest Mode: <select class = "suggestToggle" href = "#" onChange = "toggleSuggest();"><option value = "concept">Concept</option><option selected value = "default">Default</option></select></div>
				<div id = "conceptSuggest" class = "suggestContainer">
					<input type = "text" id = "qConcept" name = "qConcept">
					<input type = "submit" class = "search" id = "goConcept" value = "">
				</div>
				<div id = "defaultSuggest" class = "suggestContainer">	
					<%
					if(request.getParameter("q") != null)
					{
					%>
					<input type = "text" id = "qDefault" name = "q" value = "<%=request.getParameter("q") %>"> 
					<%
					}
					else
					{
					%>
					<input type = "text" id = "qDefault" name = "q">
					<%
					}
					%>
					<input type = "submit" class = "search" id = "go" value = "">
				</div>		
				<input type="hidden" name="wt" value="json" />
				<input type="hidden" name="indent" value="true" />
				<input type="hidden" name="group" value="true" />
				<input type="hidden" name="group.field" id = "groupField" value="ProteinId" />
				<input type="hidden" name="rows" value="-1" />
			</form>	
			
			<div class = "buttonContainer">
				<span class = "button">
					<a class = "nav" href = "settings" id = "settings" title = "Change Search Settings">&nbsp;</a>
				</span>
				<span class = "button">
					<a class = "nav" href = "#" id = "video" title = "View Video">&nbsp;</a>
				</span>
				<span class = "button">
					<a class = "nav" href = "http://lifekb.org/index.php/resources/lifewrxguide" id = "help" title = "Help">&nbsp;</a>
				</span>
				
			</div>

		</div>