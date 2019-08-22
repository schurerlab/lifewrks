<html>
<head>
   <title>Protein Summary</title>
   <link rel = "stylesheet" href="/life/web/css/style.css" type="text/css"/>
		
   <script type = "text/javascript" src = "/life/web/js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="/life/web/js/jquery.tipsy.js"></script>
	<link rel="stylesheet" href="/life/web/css/tipsy.css" type="text/css" />
	<script language="javascript">

	  var participant_id = <%=request.getParameter("input")%>;
	  $(function() {
	    processQuery();
	  });
	  function processQuery(){
	      $.getJSON("/life/protein-summary?input="+participant_id, proteinInfo);
	      $.getJSON("/life/ProcessingDataFetcher?input="+participant_id, processOverview);
	  }
	  function proteinInfo(response){
	       var proteinDetails = " <br><strong>Name:</strong> "+response.proteinname
	                             +"<br><strong>Modification:</strong> "+response.modification
	                             +"<br><strong>Mutation:</strong> "+response.mutation
	                             +"<br><strong>Uniprot Id:</strong> "+response.uniprotid
							     +"<br><strong>Organism:</strong> "+response.organism
								 +"<br><strong>Protein Id:</strong> "+response.proteinid;
	                        	 $("#proteinDetails").html(proteinDetails);
	  }
		function processOverview(response) 
		{
			var data = eval(response);
			for ( var i = 0; i < data.results.length; i++) 
			{
				var currType = data.results[i];
				var hmStr = data.results[i].type
						+ "<br><div class = 'overview' style = 'height: 20px; width: 800px;' id = 'hmOverview'"+i+">";
				var activePct = (parseInt(currType.statistics[0].count) * 100 / (parseInt(currType.statistics[0].count) + parseInt(currType.statistics[1].count)));
				hmStr += "<a href = '#' onClick = 'loadDetails(\""
						+ currType.type
						+ "\",\"active\","
						+ i
						+ ");'><div style = 'float: left; background-color: green; height: 20px; width: "
						+ activePct + "%;'>" + currType.statistics[0].measure
						+ " (" + currType.statistics[0].count + ")</div></a>";
				hmStr += "<a href = '#' onClick = 'loadDetails(\""
						+ currType.type
						+ "\",\"inactive\","
						+ i
						+ ");'><div style = 'float: left; background-color: red; height: 20px; width: "
						+ (99.9 - activePct) + "%;'>"
						+ currType.statistics[1].measure + " ("
						+ currType.statistics[1].count + ")</div></a>";
				hmStr += "</div><div id = 'hmDetails"+i+"'></div><br><br>";
				$("#heatMapWell").append(hmStr);
			}
		}
		function loadDetails(type, measure, detailId) {
			$.getJSON("/life/HeatMapDataFetcher?input=" + participant_id + "&type="
					+ type + "&measure=" + measure, function(response) {
				var data = eval(response);
				var hmStr = "";
				for ( var i = 0; i < data.results.length; i++) {
					var currType = data.results[i];
					var r = parseFloat(currType.value);
					var percent = ((100 - r) / 10) * 100;
					var red = Math.ceil(percent);
					hmStr += "<a href = '#' class = 'cell' title = '"
							+ currType.participant + " (" + currType.value
							+ ")' style = 'background-color:rgb(" + (red * 5) + ","
							+ (red * 10) + "," + (255 - (red * 10)) + ");'></a>";
				}
				$("#hmDetails" + detailId).html(hmStr);
				$('.cell').tipsy();
			});
		}
  </script>
 </head>
 
	<body class = "splash">
		<div class = "topmenu">
			<span class = "button">
				<a href = "/life/" class = "home" title = "Home">&nbsp;</a>
			</span>
			<a href = "#">Ontology Concept</a>
			<a href = "#">Structure</a>
			<div id = "qContainer">
				<form id = "searchForm" action = "search" method = "GET" onSubmit = "return validateSearch();">
					<input type = "text" id = "q" name = "q" value = "<%= request.getParameter("q") %>">
					<input type = "submit" class = "search" id = "go" value = "">
					<input type="hidden" name="wt" value="json" />
					<input type="hidden" name="indent" value="true" />
					<input type="hidden" name="group" value="true" />
					<input type="hidden" name="group.field" value="ProteinId" />
					<input type="hidden" name="group.field" value="SmallMoleculeID" />
					<input type="hidden" name="group.field" value="AssayId" />
					<input type="hidden" name="group.field" value="PerturbationId" />
					<input type="hidden" name="rows" value="-1" />
				</form>
			</div>			
			<a href = "#">Search Options</a>
			<div class = "buttonContainer">

				<span class = "button">
					<a href = "#" class = "video" title = "View Video">&nbsp;</a>
				</span>
				<span class = "button">
					<a href = "#" class = "help" title = "Help">&nbsp;</a>
				</span>
				
			</div>
		</div>
		<div class = "content">
			<div id = "header">
				<div id = "logo"></div>
				
			</div>
			<div id = "proteinDetails"></div>
			<br><br>
			<div id="heatMapWell"></div>
			<div id = "dynamic"></div>
		</div>
		<div class = "footer">
			<div class = "footerLeft">
			</div>
			<div class = "footerMiddle">
			</div>
			<div class = "footerRight">
			</div>		
		</div>
		
	</body>


