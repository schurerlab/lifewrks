<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html dir="ltr" lang="en-US">
<html>

	<head>
		<meta charset="UTF-8" />
		<title>Life - Search Results</title>
		

    	<%@ include file = "imports.jsp" %>
		<script type="text/javascript">
		var type = "Protein";


			$(function() {
		        loadProteins();
		        $(".tab").click(function()
		        {
					$(".tab").removeClass("active");
					$(this).addClass("active");
				});

			});
			
			
			
			function loadProteins()
			{
				$("#type").val("Protein");
				$("#groupField").val("ProteinId");
				isSorted = "false";
				
				$.ajax( {url: "/solr-example/select", data: $("#searchForm").serialize(), success: function(response) {
					response = eval(response);
					var groups = response.grouped.ProteinId.groups;	
					$("#resultsCount").html("("+groups.length+" Results)");
					var tableData = '[ ';
					for(var i = 0; i < groups.length; i++)
					{	
						if(groups[i].groupValue != null)
						{
							var proteinDetail = "<div id = 'proteinDetail"+i+"' style = 'display: none;'><table>";
							proteinDetail += "<tr><td>Kinase Name</td><td>"+groups[i].doclist.docs[0].KinaseName+"</td></tr>";
							proteinDetail += "<tr><td>Symbol</td><td>"+groups[i].doclist.docs[0].KinaseSymbol+"</td></tr>";
							proteinDetail += "<tr><td>Category</td><td>"+groups[i].doclist.docs[0].MainCategory+"</td></tr>";
							proteinDetail += "<tr><td>Family</td><td>"+groups[i].doclist.docs[0].KinaseFamily+"</td></tr>";
							proteinDetail += "<tr><td>Gatekeeper</td><td>"+groups[i].doclist.docs[0].KinaseGateKeeper+"</td></tr>";
							proteinDetail += "<tr><td>Hinge_i1</td><td>"+groups[i].doclist.docs[0].KnaseHingei1+"</td></tr>";
							proteinDetail += "<tr><td>Hinge_i3</td><td>"+groups[i].doclist.docs[0].KinaseHingei3+"</td></tr>";
							proteinDetail += "</table></div>";
							$("body").append(proteinDetail);
							tableData += '["'+ "<input type='checkbox' name='list' onClick = 'highlightRow(this);' value = '"+groups[i].doclist.docs[0].ProteinId+"'  />"	
									+'","'+groups[i].doclist.docs[0].ProteinName
									+'","'+groups[i].doclist.docs[0].UniprotId
									+'","'+groups[i].doclist.docs[0].Modification
									+'","'+groups[i].doclist.docs[0].Mutation 
									+'","'+'<a title = \'Protein Details\' class = \'localTip\' href = \'#proteinDetail'+i+'\' rel = \'#proteinDetail'+i+'\'>View</a>'	
									+'","<a href=\'summary?mode=Protein&input='+groups[i].doclist.docs[0].ProteinId+' \'><span title = \''+groups[i].doclist.docs[0].ProteinId+'\' class = \'profile\' id = \'profile'+i+'\'>View</span></a>"]';
							if(i != (groups.length - 1))
				                    tableData += ',';
						}

					}
					tableData += ' ]';

					var colSet = "[ { \"sTitle\": \"<input type='checkbox' title = 'Select All' name='selectAllPro' id='selectAllPro' onClick = 'checkAll(this.checked)' />\" }, { \"sTitle\": \"ProteinName\" }, { \"sTitle\": \"UniprotId\" }, { \"sTitle\": \"Modification\" }, { \"sTitle\": \"Mutation\" }, { \"sTitle\": \"More Details\" }, { \"sTitle\": \"View Profile\" }]";

					$('#dynamic').html( '<table cellpadding="0" cellspacing="0" border="0" class="display" id = "dynamicTable"></table>' );

					oTable = $('#dynamicTable').dataTable( {
								"aaData": eval(tableData),
								"aoColumns": eval(colSet),
								"sDom": '<"top"fp<"clear">>rt<"bottom"pi<"clear">>',
								"iDisplayLength": 25,
								"aLengthMenu": [[25, 50, 100], [25, 50, 100]],
								"fnInfoCallback": paginationCallback,
								"aoColumnDefs": [{ 'bSortable': false, 'aTargets': [ 0 ] }]
							  } ).bind("sort", fnSort).bind("page", fnPage);
				profileCountMax = groups.length;
				$('a.localTip').cluetip({activation: 'click', local:true, cursor: 'pointer'});
				}, dataType: "jsonp", type: "GET", 'jsonp': 'json.wrf'});
			}
			
			function fetchProfiles(){
				if(firstRecord <= lastRecord){
					if(firstRecord == 1){
						profileCount = 0;
					}
					//for records with no group value from solr
					if($("#profile"+profileCount).attr('title') == null){
						profileCount +=1;
					}
					$.getJSON("data-fetcher", {input: $("#profile"+profileCount).attr('title')}, loadProfile);
				}
			}
			
			function loadProfile(response)
			{
				var profileStr = "";
				for(var j = 0; j < response.data.length; j++)
				{
					profileStr += response.data[j].activePercent+":-"+response.data[j].inactivePercent;
					if(j != response.data.length-1)
						profileStr += ",";
				}
				$('#profile'+profileCount).html(profileStr);
				$('#profile'+profileCount).sparkline('html', {
											type: 'bar', 
											stackedBarColor: ['#ff8f26', '#2892fc'], 
											height: '32', 
											barWidth: '8'
				} );
				profileCount++;
				firstRecord++;
				fetchProfiles();
			}
			
			function loadCompounds()
			{
				$("#groupField").val("SmallMoleculeId");
				$("#type").val("SmallMolecule");
				isSorted = "false";
				
				$.ajax( {url: "/solr-example/select", data: $("#searchForm").serialize(), success: function(response) {
				response = eval(response);
				var groups = response.grouped.SmallMoleculeId.groups;	
				$("#resultsCount").html("("+groups.length+" Results)");
				var tableData = '[ ';
				for(var i = 0; i < groups.length; i++)
				{		
					if(groups[i].groupValue != null)
					{
					$("#demo").html(groups.length);
					var compoundDetail = "<div id = 'compoundDetail"+i+"' style = 'display: none;'><table>";
					compoundDetail += "<tr><td>Category</td><td>"+groups[i].doclist.docs[0].SMCategories+"</td></tr>";
					compoundDetail += "<tr><td>PDB Protein Code</td><td>"+groups[i].doclist.docs[0].PDBProteinCode+"</td></tr>";
					compoundDetail += "<tr><td>PDB Ligand Code</td><td>"+groups[i].doclist.docs[0].PDBLigandCode+"</td></tr>";
					compoundDetail += "<tr><td>Kinase Inhibitor Type</td><td>"+groups[i].doclist.docs[0].KinaseInhibitorType+"</td></tr>";
					compoundDetail += "<tr><td>Drug Name</td><td>"+groups[i].doclist.docs[0].DrugName+"</td></tr>";
					compoundDetail += "<tr><td>Drug Bank ID</td><td>"+groups[i].doclist.docs[0].DrugBankID+"</td></tr>";
					compoundDetail += "</table></div>";
					$("body").append(compoundDetail);
					tableData += '["'+ "<input type='checkbox' name='list'  onClick = 'highlightRow(this);' value = '"+groups[i].doclist.docs[0].SmallMoleculeId+"'  />"	
							+'","'+groups[i].doclist.docs[0].LincsSMId
							+'","'+groups[i].doclist.docs[0].PerturbagenId
							+'","'+groups[i].doclist.docs[0].SmallMoleculeName
							+'","'+groups[i].doclist.docs[0].PubchemId		
							+'","'+'<a title = \'Compound Details\' class = \'localTip\' href = \'#compoundDetail'+i+'\' rel = \'#compoundDetail'+i+'\'>View</a>'						
							+'","<a href=\'summary?mode=SmallMolecule&input='+groups[i].doclist.docs[0].SmallMoleculeId+' \'><span title = \''+groups[i].doclist.docs[0].SmallMoleculeId+'\' class = \'profile\' id = \'profile'+i+'\'>View</span></a>"]';
					if(i != (groups.length - 1))
						tableData += ',';
					}

				}
				tableData += ' ]';

				var colSet = "[ { \"sTitle\": \"<input type='checkbox' title = 'Select All' name='selectAllCom' id='selectAllCom' onClick = 'checkAll(this.checked)'  />\" }, { \"sTitle\": \"LINCS ID\" }, { \"sTitle\": \"PerturbagenId\" }, { \"sTitle\": \"Name\" }, { \"sTitle\": \"Pubchem CID\" }, { \"sTitle\": \"More Details\" }, { \"sTitle\": \"View Profile\" }]";

				$('#dynamic').html( '<table cellpadding="0" cellspacing="0" border="0" class="display" id = "dynamicTable"></table>' );

				oTable = $('#dynamicTable').dataTable( {
							"aaData": eval(tableData),
							"aoColumns": eval(colSet),
							"sDom": '<"top"fp<"clear">>rt<"bottom"pi<"clear">>',
							"iDisplayLength": 25,
							"aLengthMenu": [[25, 50, 100], [25, 50, 100]],
							"fnInfoCallback": paginationCallback,
							"aoColumnDefs": [{ 'bSortable': false, 'aTargets': [ 0 ] }]
						} ).bind("sort", fnSort).bind("page", fnPage);

				profileCountMax = groups.length;
				$('a.localTip').cluetip({local:true, cursor: 'pointer', activation: 'click'});
				}, dataType: "jsonp", type: "GET", 'jsonp': 'json.wrf'});
			}
			
			function loadAssays()
			{
				$("#groupField").val("AssayType");
				$("#type").val("experiment");
				$.ajax( {url: "/solr-example/select", data: $("#searchForm").serialize(), success: function(response) {
					response = eval(response);
					var groups = response.grouped.AssayType.groups;	
					$("#resultsCount").html("("+groups.length+" Results)");
					var tableData = '[ ';
					for(var i = 0; i < groups.length; i++)
					{		
						if(groups[i].groupValue != null)
						{
							var assayCountTerm = "";
							if(groups[i].doclist.docs[0].AssayType == "Apoptosis assay")
								assayCountTerm = 'Cell Cycle Assay (Apoptosis)';
							else if(groups[i].doclist.docs[0].AssayType == "binding assay")
							assayCountTerm = 'KINOMEscan';
							else if(groups[i].doclist.docs[0].AssayType == "gene expression assay")
								assayCountTerm = 'L1000 transcriptional profiling assay';
							else if(groups[i].doclist.docs[0].AssayType == "viability assay")
								assayCountTerm =  'Growth Inhibition Assay (MGH)';
							tableData += '['
									+ '"'+assayCountTerm	
									+ '","'+groups[i].doclist.docs[0].AssayFormat								
									+'","'+groups[i].doclist.docs[0].AssayType
									+'","<a href = \'#\' title = \'Participant Counts\' rel=\'/participant-solr/select/?q='+assayCountTerm+'&group=true&group.field=SmallMoleculeId&group.field=ProteinId&group.field=GeneId&group.field=CellLineId&wt=json&indent=true&rows=-1\' class = \'participantCount\'>View</a>"]';
	
							if(i != (groups.length - 1))
								tableData += ',';
						}

					}
					tableData += ' ]';

					var colSet = "[{ \"sTitle\": \"Assay Name\" }, { \"sTitle\": \"AssayFormat\" }, { \"sTitle\": \"AssayType\" }, { \"sTitle\": \"View Participant Counts\" }]";
					$('#dynamic').html( '<table cellpadding="0" cellspacing="0" border="0" class="display" id = "dynamicTable"></table>' );
	
					oTable = $('#dynamicTable').dataTable( {
								"aaData": eval(tableData),
								"aoColumns": eval(colSet),
								"sDom": '<"top"fp<"clear">>rt<"bottom"pi<"clear">>',
								"iDisplayLength": 25,
								"aLengthMenu": [[25, 50, 100], [25, 50, 100]],
								"fnInfoCallback": paginationCallback
							} );
					$('a.participantCount').cluetip({
												width: '200px',
												cluetipClass: 'rounded',
												arrows: true,
												ajaxProcess:      function(countsResponse) {
													countsResponse = eval("("+countsResponse+")");
													var countsStr = "<table><thead><tr><th>Compound</th><th>Protein</th><th>Cell Lines</th><th>Genes</th></tr></thead><tr>";
													countsStr += "<td>"+(countsResponse.grouped.SmallMoleculeId.groups.length-1)+"</td>";
													countsStr += "<td>"+(countsResponse.grouped.ProteinId.groups.length-1)+"</td>";
													countsStr += "<td>"+(countsResponse.grouped.CellLineId.groups.length-1)+"</td>";
													countsStr += "<td>"+(countsResponse.grouped.GeneId.groups.length-1)+"</td>";
													countsStr += "</tr></table>";
							                        return countsStr;
							    				}});
				}, dataType: "jsonp", type: "GET", 'jsonp': 'json.wrf'});
			}			
			
			function loadAssayCount(term)
			{
				
			}
			
			function loadData()
			{
				$("#groupField").val("PerturbationId");
				$("#type").val("Data");
				$.ajax( {url: "/solr-example/select", data: $("#searchForm").serialize(), success: function(response) {
					response = eval(response);
					var groups = response.grouped.PerturbationId.groups;	
					$("#resultsCount").html("("+groups.length+" Results)");
					var tableData = '[ ';
					for(var i = 0; i < groups.length; i++)
					{		
						if(groups[i].groupValue != null)
						{
							tableData += '["'+groups[i].doclist.docs[0].PerturbationType
									+'","'+groups[i].doclist.docs[0].AssayTypeName
									+'","'+groups[i].doclist.docs[0].PerturbationMeasure
									+'","'+groups[i].doclist.docs[0].Concentration
									+'","'+groups[i].doclist.docs[0].TimePoint
									+'","'+groups[i].doclist.docs[0].SmallMoleculeName 
									+'","'+groups[i].doclist.docs[0].DataTarget +'"]';
							if(i != (groups.length - 1))
								tableData += ',';
						}

					}
					tableData += ' ]';

	
					var colSet = "[ { \"sTitle\": \"Perturbation Type\" },{ \"sTitle\": \"Assay\" }, { \"sTitle\": \"Measure\" }, { \"sTitle\": \"Concentration\" }, { \"sTitle\": \"TimePoint\" }, { \"sTitle\": \"Compound\" } , { \"sTitle\": \"Target\" }]";
					$('#dynamic').html( '<table cellpadding="0" cellspacing="0" border="0" class="display" id = "dynamicTable"></table>' );
	
					oTable = $('#dynamicTable').dataTable( {
								"aaData": eval(tableData),
								"aoColumns": eval(colSet),
								"sDom": '<"top"fp<"clear">>rt<"bottom"pi<"clear">>',
								"iDisplayLength": 25,
								"aLengthMenu": [[25, 50, 100], [25, 50, 100]],
								"fnInfoCallback": paginationCallback
							} );
				}, dataType: "jsonp", type: "GET", 'jsonp': 'json.wrf'});
			}
			
			function loadGenes()
			{
				$("#groupField").val("GeneId");
				$("#type").val("Gene");
				isSorted = "false";
				
				$.ajax( {url: "/solr-example/select", data: $("#searchForm").serialize(), success: function(response) {
					response = eval(response);
					var groups = response.grouped.GeneId.groups;	
					$("#resultsCount").html("("+groups.length+" Results)");
					var tableData = '[ ';
					for(var i = 0; i < groups.length; i++)
					{		
						if(groups[i].groupValue != null)
						{
							tableData += '["'+"<input type='checkbox' name='list' onClick = 'highlightRow(this);' value = '"+groups[i].doclist.docs[0].GeneId+"' />"					
							        +'","'+groups[i].doclist.docs[0].EntrezId
									+'","'+groups[i].doclist.docs[0].GeneName
									+'","'+groups[i].doclist.docs[0].GeneDescription
									+'","'+groups[i].doclist.docs[0].GeneOrganism								
									+'","<a href=\'summary?mode=Gene&input='+groups[i].doclist.docs[0].GeneId+' \'><span title = \''+groups[i].doclist.docs[0].GeneId+'\' class = \'profile\' id = \'profile'+i+'\'>View</span></a>"]';
							if(i != (groups.length - 1))
								tableData += ',';
						}

					}
					tableData += ' ]';

	
					var colSet = "[ { \"sTitle\": \"<input type='checkbox' title = 'Select All' name='selectAllGene' id='selectAllGene' onClick = 'checkAll(this.checked)' />\" }, { \"sTitle\": \"Entrez ID\" }, { \"sTitle\": \"Gene Name\" }, { \"sTitle\": \"Description\" }, { \"sTitle\": \"Organism\" }, { \"sTitle\": \"View Profile\" }]";
					$('#dynamic').html( '<table cellpadding="0" cellspacing="0" border="0" class="display" id = "dynamicTable"></table>' );
	
					oTable = $('#dynamicTable').dataTable( {
								"aaData": eval(tableData),
								"aoColumns": eval(colSet),
								"sDom": '<"top"fp<"clear">>rt<"bottom"pi<"clear">>',
								"iDisplayLength": 25,
								"aLengthMenu": [[25, 50, 100], [25, 50, 100]],
								"fnInfoCallback": paginationCallback,
								"aoColumnDefs": [{ 'bSortable': false, 'aTargets': [ 0 ] }]
						     }).bind("sort", fnSort).bind("page", fnPage);
					profileCountMax = groups.length;
				}, dataType: "jsonp", type: "GET", 'jsonp': 'json.wrf'});
			}
			
			
			function loadCellLines()
			{
				$("#groupField").val("CellLineId");
				$("#type").val("CellLine");
				isSorted = "false";
				
				$.ajax( {url: "/solr-example/select", data: $("#searchForm").serialize(), success: function(response) {
					response = eval(response);
					var groups = response.grouped.CellLineId.groups;
					$("#resultsCount").html("("+groups.length+" Results)");
					var tableData = '[ ';
					for(var i = 0; i < groups.length; i++)
					{		
						if(groups[i].groupValue != null)
						{
							tableData += '["'+"<input type='checkbox' name='list' onClick = 'highlightRow(this);' value = '"+groups[i].doclist.docs[0].CellLineId+"' />"						
									+'","'+groups[i].doclist.docs[0].CellLineName
									+'","'+groups[i].doclist.docs[0].CellLineOrgan
									+'","'+groups[i].doclist.docs[0].CellLineGender
									+'","'+groups[i].doclist.docs[0]["CellLineLincsSourceId "]
									+'","'+groups[i].doclist.docs[0].Disease
									+'","'+groups[i].doclist.docs[0].CellLineOrganism
									+'","'+groups[i].doclist.docs[0].CellLineCatelogId
									+'","'+groups[i].doclist.docs[0].CellLineType
									+'","'+groups[i].doclist.docs[0].DoId
									+'","<a href=\'summary?mode=CellLine&input='+groups[i].doclist.docs[0].CellLineId+' \'><span title = \''+groups[i].doclist.docs[0].CellLineId+'\' class = \'profile\' id = \'profile'+i+'\'>View</span></a>"]';
							if(i != (groups.length - 1))
								tableData += ',';
						}

					}
					tableData += ' ]';

	
					var colSet = "[ { \"sTitle\": \"<input type='checkbox' title = 'Select All' name='selectAllCell' id='selectAllCell' onClick = 'checkAll(this.checked)' />\" }, { \"sTitle\": \"Name\" }, { \"sTitle\": \"Organ\" }, { \"sTitle\": \"Gender\" }, { \"sTitle\": \"Source ID\" }, { \"sTitle\": \"Disease\" }, { \"sTitle\": \"Organism\" }, { \"sTitle\": \"Catalog ID\" }, { \"sTitle\": \"Type\" },{ \"sTitle\": \"DOID\" }, { \"sTitle\": \"View Profile\" }]";
					$('#dynamic').html( '<table cellpadding="0" cellspacing="0" border="0" class="display" id = "dynamicTable"></table>' );
	
					oTable = $('#dynamicTable').dataTable( {
								"aaData": eval(tableData),
								"aoColumns": eval(colSet),
								"sDom": '<"top"fp<"clear">>rt<"bottom"pi<"clear">>',
								"iDisplayLength": 25,
								"aLengthMenu": [[25, 50, 100], [25, 50, 100]],
								"fnInfoCallback": paginationCallback,
								"aoColumnDefs": [{ 'bSortable': false, 'aTargets': [ 0 ] }]
							} ).bind("sort", fnSort).bind("page", fnPage);
					
					profileCountMax = groups.length;
				}, dataType: "jsonp", type: "GET", 'jsonp': 'json.wrf'});
			}
			
			
		</script>
		
	</head>

	<body>

    	<%@ include file = "topmenu.jsp" %>
		
		<div class = "content">
			<form name = "worksetForm" id = "worksetForm">
				<div id = "header">
					<div id = "logo"></div>
					<div id = "contextMenu">
						<div id = "message"></div>
						<a href = "download?input=<%=request.getParameter("q") %>" class = "cMenu manage" title = "Download Data">Download</a>
						<a href = "element-list?elementType=SmallMolecule" class = "cMenu manage" title = "Manage List">Manage List</a>
						<a href = "#" class = "cMenu add" title = "Add to List" onClick = "$('#mode').val('add');editList();">Add To List</a>
						<input type = "hidden" name = "mode" id = "mode"/>
						<input type = "hidden" name = "type" id = "type" value = "Protein"/>
					</div>
				</div>
				<div id="pageNum"></div>
				<div id = "resultsCount"></div>
				<div id = "tabHolder">
					<div id = "tabs">
						<a href = "#" class = "tab active" onClick = "loadProteins();">Proteins</a>
						<a href = "#" class = "tab" onClick = "loadCompounds();">Compounds</a>
						<a href = "#" class = "tab" onClick = "loadAssays();">Assays</a>
						<a href = "#" class = "tab" onClick = "loadCellLines();">Cell Lines</a>
					  	<a href = "#" class = "tab" onClick = "loadGenes();">Genes</a>
						<a href = "#" class = "tab" onClick = "loadData();">Data</a>
					</div>
	
				</div>
				<div id = "dynamic"></div>
			</form>
		</div>
		<%@ include file = "footer.jsp" %>
		
	</body>

</html>
