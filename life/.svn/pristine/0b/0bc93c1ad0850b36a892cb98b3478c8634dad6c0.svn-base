var elemId;
var start = 0;
$(document).ready(
		function() {
			if (mode == "SmallMolecule") {
				mode = "compound";
			} else {
				mode = mode.toLowerCase();
			}

			if (mode == "protein"){
				 elemId = "ProteinId";
				 $("#summary").load("/life/web/templates/summary-protein.html");
				 
			}else if (mode == "compound"){
				 elemId = "SmallMoleculeId";
				 $("#summary").load("/life/web/templates/summary-compound.html");
			
			}else if (mode == "gene"){
				elemId = "GeneId";
				$("#summary").load("/life/web/templates/summary-gene.html");
				
			}else if (mode == "cellline"){
				elemId = "CellLineId";
				$("#summary").load("/life/web/templates/summary-cellline.html");
			}

		});

var participant_id = $(document).getUrlParam('input');
var mode = $(document).getUrlParam('mode');

$(function() {
	processQuery();
});
function processQuery() {
	$.getJSON("/life/fetch-summary", {
		input : participant_id,
		mode : mode
	}, loadSummary);
	$.getJSON("/life/bars-fetcher?input=" + participant_id, processOverview);
	
	$.ajax( { url: "http://lincs.ccs.miami.edu:8080/solr-example/select?wt=json&json.wrf=?&indent=true&"+
					"facet=true&facet.field=AssayTypeName&rows=-1&facet.mincount=1",
			  dataType: "jsonp",
			  data: { q:elemId + ":" + participant_id},
			  success: getDataLinks
	});
		
}
function displayErrMsg(error) {
	$('#message').show();
	setTimeout("$('#message').fadeOut('slow')", 4000);
	$('#message').removeClass().addClass('errorHighlight');
	$('#message').html(error);
}
function emptyCheck(field) {
	if (field == "") {
		return false;
	} else {
		return true;
	}
}
function booleanCheck(field) {
	if (field == 1) {
		return "true";
	} else {
		return "false";
	}
}
function loadSummary(response) {
	var summaryStr = "";
	if (mode == "protein") {
		dataName = (emptyCheck(response.kinasename) ? response.kinasename : "");
		dataCategory = (emptyCheck(response.kinasecategory) ? response.kinasecategory
				: "");

		summaryStr = (emptyCheck(response.proteinname) ? " <br><strong>Symbol:</strong> "
				+ response.proteinname
				: "")
				+ (emptyCheck(response.modification) ? "<br><strong>Modification:</strong> "
						+ response.modification
						: "")
				+ (emptyCheck(response.mutation) ? "<br><strong>Mutation:</strong> "
						+ response.mutation
						: "")
				+ (emptyCheck(response.uniprotid) ? "<br><strong>Uniprot Id:</strong> <a href=\"http://www.uniprot.org/uniprot/"
						+ response.uniprotid
						+ "\" target=\"_blank\">"
						+ response.uniprotid + "</a>"
						: "")
				+ ((emptyCheck(response.sourceid) && (response.sourceid != response.proteinid)) ? "<br><strong>LINCS facility ID:</strong> <a href=\"http://dev.lincs.hms.harvard.edu/db/proteins/"
						+ response.sourceid
						+ "\" target=\"_blank\">"
						+ response.sourceid + "</a>"
						: "")
				+ (emptyCheck(response.kinasedescription) ? "<br><strong>Kinase Description:</strong> "
						+ response.kinasedescription
						: "")
				+ (emptyCheck(response.organism) ? "<br><strong>Organism:</strong> "
						+ response.organism
						: "")
				+ (emptyCheck(response.Spkinasedomain) ? "<br><strong>Kinase Domain:</strong> "
						+ response.Spkinasedomain
						: "")
				+ (emptyCheck(response.kinasegroup) ? "<br><strong>Group:</strong> "
						+ response.kinasegroup
						: "")
				+ (emptyCheck(response.kinasefamily) ? "<br><strong>Kinase Family:</strong> "
						+ response.kinasefamily
						: "")
				+ (emptyCheck(response.gatekeeper) ? "<br><strong>Gate Keeper:</strong> "
						+ response.gatekeeper
						: "")
				+ (emptyCheck(response.kinasehingei1) ? "<br><strong>Kinase Hingei1:</strong> "
						+ response.kinasehingei1
						: "")
				+ (emptyCheck(response.kinasehingei3) ? "<br><strong>Kinase Hingei3:</strong> "
						+ response.kinasehingei3
						: "");

	} else if (mode == "compound") {
		var pubchemId = response.pubchemid;
		if (null != response.lincs_id && "" != response.lincs_id) {
			dataImage = '/life/web/images/sm-images/' + response.lincs_id
					+ '.png';
			$("#data-image").attr("src", dataImage);
		}
		var pdbcode = response.pdbcode;
		var pdblinks = "";
		if (pdbcode != "") {
			pdbcode = pdbcode.split(",");
			for ( var i = 0; i < pdbcode.length; i++) {
				pdblinks += "<a href=\"http://www.rcsb.org/pdb/explore/explore.do?structureId="
						+ pdbcode[i]
						+ "\" target=\"_blank\">"
						+ pdbcode[i]
						+ "</a>";
				if (i < pdbcode.length - 1) {
					pdblinks += ",";
				}
			}
		}
		dataName = (emptyCheck(response.iupac_name) ? response.smallmoleculename
				: "");
		dataCategory = (emptyCheck(response.allcategories) ? response.allcategories
				: "");
		dataLincsId = (emptyCheck(response.lincs_id) ? response.lincs_id : "");
		dataFaciIityId = ((emptyCheck(response.Lincs_CompoundId) && (response.salt_id != response.Lincs_CompoundId)) ? "<a href=\"http://dev.lincs.hms.harvard.edu/db/sm/"
				+ response.salt_id
				+ "/"
				+ "\" target=\"_blank\">"
				+ response.Lincs_CompoundId + "</a>"
				: "");
		dataPubChemId = (emptyCheck(response.pubchemid) ? "<a href=\"http://pubchem.ncbi.nlm.nih.gov/summary/summary.cgi?cid="
				+ response.pubchemid
				+ "\" target=\"_blank\">"
				+ response.pubchemid + "</a>"
				: "")
		dataMwLogo = (emptyCheck(response.molecularweight) ? parseFloat(
				response.molecularweight).toFixed(1) : "");
		dataMf = (emptyCheck(response.molecularformulae) ? response.molecularformulae
				: "");
		dataTpsa = (emptyCheck(response.tpsa) ? response.tpsa : "");
		dataLogd = (emptyCheck(response.logd) ? parseFloat(response.logd)
				.toFixed(2) : "");
		dataLogp = (emptyCheck(response.logp) ? parseFloat(response.logp)
				.toFixed(2) : "");
		dataRb = (emptyCheck(response.rotatable_bonds) ? response.rotatable_bonds
				: "");
		dataHba = (emptyCheck(response.hba) ? response.hba : "");
		dataHbd = (emptyCheck(response.hba) ? response.hbd : "");
		dataLipinsky5 = (emptyCheck(response.lipinski_r5) ? booleanCheck(response.lipinski_r5)
				: "");
		dataLipinsky3 = (emptyCheck(response.lipinski_r3) ? booleanCheck(response.lipinski_r3)
				: "");
		dataBioAvailability = (emptyCheck(response.bioavailability) ? booleanCheck(response.bioavailability)
				: "");
		dataLeadLikeness = (emptyCheck(response.lead_likeness) ? booleanCheck(response.lead_likeness)
				: "");

		$('#data-name').html(dataName);
		$('#data-category').html(dataCategory);
		$('#data-lincsId').html(dataLincsId);
		$('#data-facilityId').html(dataFaciIityId);
		$('#data-pubChemId').html(dataPubChemId);
		$('#data-mwLogo').html(dataMwLogo);
		$('#data-mf').html(dataMf);
		$('#data-tpsa').html(dataTpsa);
		$('#data-logd').html(dataLogd);
		$('#data-logp').html(dataLogp);
		$('#data-rb').html(dataRb);
		$('#data-hba').html(dataHba);
		$('#data-hbd').html(dataHbd);
		$('#data-lipinsky5').html(dataLipinsky5);
		$('#data-lipinsky3').html(dataLipinsky3);
		$('#data-leadLikeness').html(dataLeadLikeness);
		$('#data-bioAvailability').html(dataBioAvailability);
		
		$('#data-image').show();


	} else if (mode == "cellline") {
		dataName = (emptyCheck(response.name) ? response.name : "");
		dataType =  (emptyCheck(response.celltype) ? response.celltype 	: "");
		dataProviderId = (emptyCheck(response.providerid) ? response.providerid	: "");
		dataLincsSourceId =(emptyCheck(response.lincssourceid) ?  response.lincssourceid	: "");
		dataDoId = (emptyCheck(response.doid) ? response.doid : "");
		dataDisease =(emptyCheck(response.disease) ? response.disease	: "");
		dataOrgan =  (emptyCheck(response.organ) ? response.organ : "")
		dataGeneticModification = (emptyCheck(response.geneticmodification) ?  response.geneticmodification : "")
		dataOrganism =  (emptyCheck(response.organism) ? response.organism	: "");
		dataProvider = (emptyCheck(response.provider) ? response.provider	: "");
		
		summaryStr = (emptyCheck(response.growthproperties) ?  response.growthproperties	: "")
		+ (emptyCheck(response.tissue) ?  response.tissue	: "");
		
		$('#data-name').html(dataName);
		$('#data-category').html(dataType);
		$('#data-providerId').html(dataProviderId);
		$('#data-lincSourceId').html(dataLincsSourceId);
		$('#data-doId').html(dataDoId);
		$('#data-disease').html(dataDisease);
		$('#data-organ').html(dataOrgan);
		$('#data-geneticModification').html(dataGeneticModification);
		$('#data-organism').html(dataOrganism);
		$('#data-provider').html(dataOrganism);

	} else if (mode == "gene") {
		dataName = (emptyCheck(response.name) ? response.name : "");
		dataCategory = "";
		summaryStr = (emptyCheck(response.name) ? response.name : "")
				+ (emptyCheck(response.entrezid) ? "<br><strong>Entrez Id:</strong> <a href=\"http://www.ncbi.nlm.nih.gov/gene/?term="
						+ response.entrezid
						+ "\" target=\"_blank\">"
						+ response.entrezid + "</a>"
						: "")
				+ (emptyCheck(response.description) ? "<br><strong>Description:</strong> "
						+ response.description
						: "")
				+ (emptyCheck(response.organism) ? "<br><strong>Organism:</strong> "
						+ response.organism
						: "");
	}

}

function processOverview(response) {
	var data = eval(response);
	for ( var i = 0; i < data.results.length; i++) {

		var currType = data.results[i];
		var epid = currType.epid;

		var hmStr = "<div id ='waitImg'></div><div id='hmType'>"
				+ data.results[i].type + "</div>";

		hmStr += "<div id='summaryEpTime'><select id = 'smryTpDrpDown"
				+ i
				+ "'><option value = 'default'>-- Select Time Point --</option>";
		for ( var j = 0; j < currType.timepoint.length; j++) {
			hmStr += "<option value = \"" + currType.timepoint[j] + "\">"
					+ currType.timepoint[j] + "</option>";
		}
		hmStr += "</select></div>";

		hmStr += "<div id='summaryEpCon'><select id = 'smryConDrpDown"
				+ i
				+ "'><option value = 'default'>-- Select Concentration --</option>";
		for ( var k = 0; k < currType.concentration.length; k++) {
			hmStr += "<option value = \"" + currType.concentration[k] + "\">"
					+ currType.concentration[k] + "</option>";
		}
		hmStr += "</select></div>";

		hmStr += "<div id='summarySubmit'><div id='contextMenu' style=\"margin-top: 15px;\">"
				+ " <a href = '#'  class = 'cMenu manage' style='margin-top: 0px;' title = 'Submit' onclick='getUpdatedHm(\""
				+ i + "\"," + epid + ");'>Submit</a>" + "</div></div>";

		hmStr += "<div class = 'hmOverview' id = 'hmOverview" + i + "'>";
		var activePct = (parseInt(currType.statistics[0].count) * 100 / (parseInt(currType.statistics[0].count) + parseInt(currType.statistics[1].count)));
		hmStr += "<a href = '#' onClick = 'loadDetails(\""
				+ currType.type
				+ "\",\"active\","
				+ i
				+ ","
				+ epid
				+ ");'><div style = 'float: left; background-color: green; height: 20px; width: "
				+ activePct + "%;'>" + currType.statistics[0].measure + " ("
				+ currType.statistics[0].count + ")</div></a>";
		hmStr += "<a href = '#' onClick = 'loadDetails(\""
				+ currType.type
				+ "\",\"inactive\","
				+ i
				+ ","
				+ epid
				+ ");'><div style = 'float: left; background-color: red; height: 20px; width: "
				+ (99.9 - activePct) + "%;'>" + currType.statistics[1].measure
				+ " (" + currType.statistics[1].count + ")</div></a>";
		hmStr += "</div><div class = 'hmDetails' id = 'hmDetails" + i
				+ "'></div><br><br>";
		$("#heatMapWell").append(hmStr);
	}
}

function loadDetails(type, measure, detailId, epid) {
	var newTp = $('#smryTpDrpDown' + detailId + ' :selected').val();
	var newConc = $('#smryConDrpDown' + detailId + ' :selected').val();

	if (newTp != selectedTime)
		newTp = selectedTime;
	if (newConc != selectedCon)
		newConc = selectedCon;

	var url = "/life/HeatMapDataFetcher?input=" + participant_id + "&type="
			+ type + "&measure=" + measure;

	// update HeatMapDataFetcher url with tp & conc only when tp & conc is
	// seleted and submit is clicked
	if (newTp !== "default" && newConc !== "default" && (currentHm == detailId)) {
		url += "&tp=" + newTp + "&conc=" + newConc + "&epid=" + epid;
	}
	$("#waitImg").html('<img src="/life/web/images/wait.gif"/>');
	$
			.getJSON(
					url,
					function(response) {
						var data = eval(response);
						var hmStr = "";
						for ( var i = 0; i < data.results.length; i++) {
							var currType = data.results[i];
							var r = parseFloat(currType.value);
							var percent = ((100 - r) / 10) * 100;
							var red = Math.ceil(percent);
							hmStr += "<a href = '#' class = 'cell'  rel='perturbation-summary?input="
									+ currType.eid
									+ "' title = '"
									+ currType.participant
									+ " ("
									+ currType.value
									+ ")' style = 'background-color:rgb("
									+ (red * 5)
									+ ","
									+ (red * 10)
									+ ","
									+ (255 - (red * 10)) + ");'></a>";
						}
						$("#hmDetails" + detailId).html(hmStr);
						$("#waitImg").html("");
						$('a.cell')
								.cluetip(
										{
											width : '200px',
											activation : 'click',
											cluetipClass : 'rounded',
											arrows : true,
											ajaxProcess : function(response) {
												var data = eval("(" + response
														+ ")");
												var currData = data.results[0];

												var countsStr = "Assay Name: "
														+ currData.assayname
														+ "<br>"
												countsStr += "Assay Type: "
														+ currData.assaytype
														+ "<br>"
												countsStr += "Assay Format: "
														+ currData.assayformat
														+ "<br>"
												countsStr += "Experiment Name: "
														+ currData.experimentname
														+ "<br>";

												for ( var i = 0; i < currData.participant.length; i++) {
													countsStr += currData.participant[i].type
															+ ":"
															+ currData.participant[i].name
															+ "<br>";
												}
												countsStr += "Value: "
														+ currData.value
														+ "<br>"
												countsStr += "Time: "
														+ currData.time
														+ "<br>"
												countsStr += "Time Unit: "
														+ currData.timeunit
														+ "<br>"
												countsStr += "Concentration: "
														+ currData.concentration
														+ "<br>"
												countsStr += "Concentration Unit: "
														+ currData.concentrationunit;
												return countsStr;
											}
										});
					});
}

var currentHm, selectedTime, selectedCon, updatedEpid;
function getUpdatedHm(hmId, epid) {
	currentHm = hmId;
	updatedEpid = epid;

	selectedTime = $('#smryTpDrpDown' + hmId + ' :selected').val();
	selectedCon = $('#smryConDrpDown' + hmId + ' :selected').val();

	if (selectedTime == "default" || selectedCon == "default") {
		displayErrMsg("ERROR: Please select Time Point/Concentration");
	} else {
		$.ajax({
			url : "ProcessingDataFetcher",
			data : {
				input : participant_id,
				epid : updatedEpid,
				tp : selectedTime,
				conc : selectedCon
			},
			success : processUpdatedHm,
			dataType : "json",
			type : "GET"
		});
	}
}

function processUpdatedHm(response) {
	var data = eval(response);
	var currType = data.results[0];

	// var updatedHmStr = "<div class = 'hmOverview' id =
	// 'hmOverview"+currentHm+"'>";
	var activePct = (parseInt(currType.statistics[0].count) * 100 / (parseInt(currType.statistics[0].count) + parseInt(currType.statistics[1].count)));
	var updatedHmStr = "<a href = '#' onClick = 'loadDetails(\""
			+ currType.type
			+ "\",\"active\","
			+ currentHm
			+ ","
			+ updatedEpid
			+ ");'><div style = 'float: left; background-color: green; height: 20px; width: "
			+ activePct + "%;'>" + currType.statistics[0].measure + " ("
			+ currType.statistics[0].count + ")</div></a>";
	updatedHmStr += "<a href = '#' onClick = 'loadDetails(\""
			+ currType.type
			+ "\",\"inactive\","
			+ currentHm
			+ ","
			+ updatedEpid
			+ ");'><div style = 'float: left; background-color: red; height: 20px; width: "
			+ (99.9 - activePct) + "%;'>" + currType.statistics[1].measure
			+ " (" + currType.statistics[1].count + ")</div></a>";
	// updatedHmStr += "</div><div class = 'hmDetails' id =
	// 'hmDetails"+currentHm+"'></div><br><br>";
	$('#hmOverview' + currentHm).html(updatedHmStr);
	$('#hmDetails' + currentHm).html("");
}



//Summary table 

function getDataLinks(data){
	
	response = eval(data);
	var assayTypeName = response.facet_counts.facet_fields.AssayTypeName;
	
	if(emptyCheck(assayTypeName)){
		var linkStr = "<div id='navDiv'><ul>" ;
		
		for(var i=0; i<assayTypeName.length ;i++){
			linkStr += "<li><a href='#' class='navLinks' onClick = 'loadAssayData(\"" +assayTypeName[i]+ "\");'>"+getLinkNames(assayTypeName[i])+"</a></li>";
			i++;
		}
		linkStr += "</ul></div>";
		
		$("#links").html(linkStr);
		
		loadAssayData(assayTypeName[0]);
	}
}

function getLinkNames(assayName){
	if(assayName == "KINOMEscan assay") return "KINOMEScan Assay";
	else if (assayName == "Cell cycle state assay") return "Cell cycle state assay";
	else if (assayName == "Apoptosis assay") return "Apoptosis assay";
	else if (assayName == "Cell growth inhibition assay") return "Cell growth inhibition assay";
	else if (assayName == "L1000 transcriptional profiling assay") return "L1000 transcriptional profiling assay";
	
}

function loadAssayData(assayName){
	if(assayName == "KINOMEscan assay"){
		loadKinomeData();
	}
	else if (assayName == "Cell cycle state assay"){
		loadCellState();
	}
	else if (assayName == "Apoptosis assay"){
		loadApoptosisData();
	}
	else if (assayName == "Cell growth inhibition assay"){
		loadCellGrowthData();
	}
	else if (assayName == "L1000 transcriptional profiling assay"){
		loadTranscriptionalData();
	}
}

function loadKinomeData(){
	
	$.ajax( {url: "http://lincs.ccs.miami.edu:8080/solr-example/select?wt=json&indent=true&"+
				  		"fl=SmallMoleculeName,ProteinName,PerturbationType,PerturbationMeasure,Concentration,TimePoint,AssayTypeName&"+
				  		"group=true&group.field=PerturbationId&rows=20&start="+start, 
				  		data: { q:elemId + ":" + participant_id + " KINOMEscan assay"}, success: function(response) {
					
		response = eval(response);
		var groups = response.grouped.PerturbationId.groups;	
		
		var tableData = '[ ';
		for(var i = 0; i < groups.length; i++){		
			if(groups[i].groupValue != null){
				tableData += '["'+groups[i].doclist.docs[0].AssayTypeName
						+'","'+groups[i].doclist.docs[0].PerturbationType
						+'","'+parseFloat(groups[i].doclist.docs[0].PerturbationMeasure).toFixed(2)
						+'","'+groups[i].doclist.docs[0].SmallMoleculeName 
						+'","'+groups[i].doclist.docs[0].ProteinName
						+'","'+groups[i].doclist.docs[0].Concentration
						+'","'+groups[i].doclist.docs[0].TimePoint +'"]';
				if(i != (groups.length - 1))
					tableData += ',';
			}
		}
		tableData += ' ]';
	
	
		var colSet = "[ " +
				"{ \"sTitle\": \"Assay\" },"+
				"{ \"sTitle\": \"Endpoint\" },"+
				"{ \"sTitle\": \"Endpoint value\" }," +
				"{ \"sTitle\": \"Compound\" } ," +
				" { \"sTitle\": \"Target\" }," +
				" { \"sTitle\": \"Concentration(uM)\" }," +
				" { \"sTitle\": \"TimePoint(hrs)\" }" +
				"]";
		$('#dynamicDataTab').html( '<table cellpadding="0" cellspacing="0" border="0" class="display" id = "dynamicTable"></table>' );
	
		oTable = $('#dynamicTable').dataTable( {
					"aaData": eval(tableData),
					"aoColumns": eval(colSet),
					"sDom": '<"top"fp<"clear">>rt<"bottom"pi<"clear">>',
					"iDisplayLength": 20,
					"aLengthMenu": [[25, 50, 100], [25, 50, 100]],
					"fnInfoCallback": paginationCallback,
				} );
				filterStr = "<tr>"; 
				filterStr += '<th><input type="text" name="filterAssay" style="width:50px;"	 value="Filter" class="search_init" /></th>';
				filterStr += '<th><input type="text" name="filterEndpoint" value="Filter" class="search_init" /></th>';
				filterStr += '<th><input type="text" name="filterEndpointvalue"  value="Filter" class="search_init" /></th>';
				filterStr += '<th><input type="text" name="filterCompound" value="Filter" class="search_init" /></th>';
				filterStr += '<th><input type="text" name="filterTarget"  value="Filter" class="search_init" /></th>';
				filterStr += '<th><input type="text" name="filterConcentration"  value="Filter" class="search_init" /></th>';
				filterStr += '<th><input type="text" name="filterTimePoint"  value="Filter" class="search_init" /></th>';
				filterStr += "</tr>";
				
				$("table#dynamicTable thead").append(filterStr);
				filterInput();
	    }, dataType: "jsonp",'jsonp': 'json.wrf'});
}
function loadCellState(){
	
	$.ajax( {url: "http://lincs.ccs.miami.edu:8080/solr-example/select?wt=json&indent=true&"+
					"fl=CellLineName,SmallMoleculeName,PerturbationType,PerturbationMeasure,Concentration,TimePoint,AssayTypeName&"+
					"group=true&group.field=PerturbationId&rows=20&start="+start, 
					data: { q:elemId + ":" + participant_id + " Cell cycle state assay"}, success: function(response) {
  			
		response = eval(response);
		var groups = response.grouped.PerturbationId.groups;	
		$("#resultsCount").html("("+groups.length+" Results)");
		var tableData = '[ ';
		for(var i = 0; i < groups.length; i++)
		{		
			if(groups[i].groupValue != null)
			{
				tableData += '["'+groups[i].doclist.docs[0].AssayTypeName
						+'","'+groups[i].doclist.docs[0].PerturbationType
						+'","'+parseFloat(groups[i].doclist.docs[0].PerturbationMeasure).toFixed(2)
						+'","'+groups[i].doclist.docs[0].Concentration 
						+'","'+groups[i].doclist.docs[0].TimePoint 
						+'","'+groups[i].doclist.docs[0].SmallMoleculeName 
						+'","'+groups[i].doclist.docs[0].CellLineName +'"]';
				if(i != (groups.length - 1))
					tableData += ',';
			}

		}
		tableData += ' ]';


		var colSet = "[ { \"sTitle\": \"Assay\" },{ \"sTitle\": \"Endpoint\" }, { \"sTitle\": \"Endpoint value\" },{ \"sTitle\": \"Concentration(uM)\" },{ \"sTitle\": \"TimePoint(hrs)\" },{ \"sTitle\": \"Compound\" } , { \"sTitle\": \"Cell Line\" }]";
		$('#dynamicDataTab').html( '<table cellpadding="0" cellspacing="0" border="0" class="display" id = "dynamicTable"></table>' );

		oTable = $('#dynamicTable').dataTable( {
					"aaData": eval(tableData),
					"aoColumns": eval(colSet),
					"sDom": '<"top"fp<"clear">>rt<"bottom"pi<"clear">>',
					"iDisplayLength": 20,
					"aLengthMenu": [[25, 50, 100], [25, 50, 100]],
					"fnInfoCallback": paginationCallback
				} );
		
				filterStr = "<tr>"; 
				filterStr += '<th><input type="text" name="filterAssay" value="Filter" class="search_init" style="width:50px;"/></th>';
				filterStr += '<th><input type="text" name="filterEndpoint" value="Filter" class="search_init" /></th>';
				filterStr += '<th><input type="text" name="filterEndpointVal" value="Filter" class="search_init" style="width:50px;"/></th>';
				filterStr += '<th><input type="text" name="filterConcentration"  value="Filter" class="search_init" /></th>';
				filterStr += '<th><input type="text" name="filterTimePoint"  value="Filter" class="search_init" /></th>';
				filterStr += '<th><input type="text" name="filterCompound" value="Filter" class="search_init" style="width:80px;"/></th>';
				filterStr += '<th><input type="text" name="filterCellLine" value="Filter" class="search_init" style="width:80px;"/></th>';
				filterStr += "</tr>";
				
				$("table#dynamicTable thead").append(filterStr);
				filterInput();
		
	}, dataType: "jsonp", type: "GET", 'jsonp': 'json.wrf'});
}
function loadApoptosisData(){
	
	$.ajax( {url: "http://lincs.ccs.miami.edu:8080/solr-example/select?wt=json&indent=true&"+
					"fl=CellLineName,SmallMoleculeName,PerturbationType,PerturbationMeasure,Concentration,TimePoint,AssayTypeName&"+
					"group=true&group.field=PerturbationId&rows=20&start="+start, 
			  		data: { q:elemId + ":" + participant_id + " Apoptosis assay"}, success: function(response) {
			  			
		response = eval(response);
		var groups = response.grouped.PerturbationId.groups;	
		
		var tableData = '[ ';
		for(var i = 0; i < groups.length; i++)
		{		
			if(groups[i].groupValue != null)
			{
				tableData += '["'+groups[i].doclist.docs[0].AssayTypeName
						+'","'+groups[i].doclist.docs[0].PerturbationType
						+'","'+parseFloat(groups[i].doclist.docs[0].PerturbationMeasure).toFixed(2)
						+'","'+groups[i].doclist.docs[0].Concentration 
						+'","'+groups[i].doclist.docs[0].TimePoint 
						+'","'+groups[i].doclist.docs[0].SmallMoleculeName 
						+'","'+groups[i].doclist.docs[0].CellLineName +'"]';
				if(i != (groups.length - 1))
					tableData += ',';
			}

		}
		tableData += ' ]';


		var colSet = "[ { \"sTitle\": \"Assay\" },{ \"sTitle\": \"Endpoint\" }, { \"sTitle\": \"Endpoint value\" },{ \"sTitle\": \"Concentration(uM)\" },{ \"sTitle\": \"TimePoint(hrs)\" },{ \"sTitle\": \"Compound\" } , { \"sTitle\": \"Cell Line\" }]";
		$('#dynamicDataTab').html( '<table cellpadding="0" cellspacing="0" border="0" class="display" id = "dynamicTable"></table>' );

		oTable = $('#dynamicTable').dataTable( {
					"aaData": eval(tableData),
					"aoColumns": eval(colSet),
					"sDom": '<"top"fp<"clear">>rt<"bottom"pi<"clear">>',
					"iDisplayLength": 20,
					"aLengthMenu": [[25, 50, 100], [25, 50, 100]],
					"fnInfoCallback": paginationCallback
				} );
		
				filterStr = "<tr>"; 
				filterStr += '<th><input type="text" name="filterAssay" value="Filter" class="search_init" style="width:50px;"/></th>';
				filterStr += '<th><input type="text" name="filterEndpoint" value="Filter" class="search_init" /></th>';
				filterStr += '<th><input type="text" name="filterEndpointVal" value="Filter" class="search_init" style="width:55px;"/></th>';
				filterStr += '<th><input type="text" name="filterConcentration" value="Filter" class="search_init" style="width:50px;"/></th>';
				filterStr += '<th><input type="text" name="filterTimePoint" value="Filter" class="search_init" style="width:50px;"/></th>';
				filterStr += '<th><input type="text" name="filterCompound" value="Filter" class="search_init" style="width:80px;"/></th>';
				filterStr += '<th><input type="text" name="filterCellLine" value="Filter" class="search_init" style="width:80px;"/></th>';
				filterStr += "</tr>";
				
				$("table#dynamicTable thead").append(filterStr);
				filterInput();
				
	}, dataType: "jsonp", type: "GET", 'jsonp': 'json.wrf'});
	
}
function loadCellGrowthData(){
	
	$.ajax( {url: "http://lincs.ccs.miami.edu:8080/solr-example/select?wt=json&indent=true&"+
					"fl=CellLineName,SmallMoleculeName,PerturbationType,PerturbationMeasure,Concentration,TimePoint,AssayTypeName&"+
					"group=true&group.field=PerturbationId&rows=20&start="+start, 
			  		data: { q:elemId + ":" + participant_id + " Cell growth inhibition assay"}, success: function(response) {
		
		response = eval(response);
		var groups = response.grouped.PerturbationId.groups;	
		var tableData = '[ ';
		for(var i = 0; i < groups.length; i++)
		{		
			if(groups[i].groupValue != null)
			{
				tableData += '["'+groups[i].doclist.docs[0].AssayTypeName
						+'","'+groups[i].doclist.docs[0].PerturbationType
						+'","'+parseFloat(groups[i].doclist.docs[0].PerturbationMeasure).toFixed(2)
						+'","'+groups[i].doclist.docs[0].Concentration 
						+'","'+groups[i].doclist.docs[0].TimePoint 
						+'","'+groups[i].doclist.docs[0].SmallMoleculeName 
						+'","'+groups[i].doclist.docs[0].CellLineName +'"]';
				if(i != (groups.length - 1))
					tableData += ',';
			}

		}
		tableData += ' ]';


		var colSet = "[ { \"sTitle\": \"Assay\" },{ \"sTitle\": \"Endpoint\" }, { \"sTitle\": \"Endpoint value\" },{ \"sTitle\": \"Concentration(uM)\" },{ \"sTitle\": \"TimePoint(hrs)\" },{ \"sTitle\": \"Compound\" } , { \"sTitle\": \"Cell Line\" }]";
		$('#dynamicDataTab').html( '<table cellpadding="0" cellspacing="0" border="0" class="display" id = "dynamicTable"></table>' );

		oTable = $('#dynamicTable').dataTable( {
					"aaData": eval(tableData),
					"aoColumns": eval(colSet),
					"sDom": '<"top"fp<"clear">>rt<"bottom"pi<"clear">>',
					"iDisplayLength": 20,
					"aLengthMenu": [[25, 50, 100], [25, 50, 100]],
					"fnInfoCallback": paginationCallback
				} );

				filterStr = "<tr>"; 
				filterStr += '<th><input type="text" name="filterAssay" value="Filter" class="search_init" style="width:50px;"/></th>';
				filterStr += '<th><input type="text" name="filterEndpoint" value="Filter" class="search_init" /></th>';
				filterStr += '<th><input type="text" name="filterEndpointVal" value="Filter" class="search_init" style="width:50px;"/></th>';
				filterStr += '<th><input type="text" name="filterConcentration"  value="Filter" class="search_init" /></th>';
				filterStr += '<th><input type="text" name="filterTimePoint"  value="Filter" class="search_init" /></th>';
				filterStr += '<th><input type="text" name="filterCompound" value="Filter" class="search_init" style="width:50px;" /></th>';
				filterStr += '<th><input type="text" name="filterCellLine" value="Filter" class="search_init" style="width:50px;"/></th>';
				filterStr += "</tr>";
				
				$("table#dynamicTable thead").append(filterStr);
				filterInput();

	}, dataType: "jsonp", type: "GET", 'jsonp': 'json.wrf'});
	
}
function loadTranscriptionalData(){
	$.ajax( {url: "http://lincs.ccs.miami.edu:8080/solr-example/select?wt=json&indent=true&"+
					"fl=CellLineName,GeneName,SmallMoleculeName,PerturbationType,Concentration,TimePoint,AssayTypeName&"+
					"group=true&group.field=PerturbationId&rows=20&start="+start,  
					data: { q:elemId + ":" + participant_id + " L1000 transcriptional profiling assay"}, success: function(response) {

		response = eval(response);
		var groups = response.grouped.PerturbationId.groups;	
		//numOfRows = response.grouped.PerturbationId.matches;
		
		var tableData = '[ ';
		for(var i = 0; i < groups.length; i++)
		{		
			if(groups[i].groupValue != null)
			{
				tableData += '["'+groups[i].doclist.docs[0].AssayTypeName
						+'","'+groups[i].doclist.docs[0].PerturbationType
						+'","'+groups[i].doclist.docs[0].Concentration 
						+'","'+groups[i].doclist.docs[0].TimePoint 
						+'","'+groups[i].doclist.docs[0].SmallMoleculeName 
						+'","'+groups[i].doclist.docs[0].CellLineName 
						+'","'+groups[i].doclist.docs[0].GeneName+'"]';
				if(i != (groups.length - 1))
					tableData += ',';
			}

		}
		tableData += ' ]';


		var colSet = "[ { \"sTitle\": \"Assay\" },{ \"sTitle\": \"Endpoint\" }, { \"sTitle\": \"Concentration(uM)\" },{ \"sTitle\": \"TimePoint(hrs)\" },{ \"sTitle\": \"Compound\" } , { \"sTitle\": \"Cell Line\" }, { \"sTitle\": \"Gene\" }]";
		$('#dynamicDataTab').html( '<table cellpadding="0" cellspacing="0" border="0" class="display" id = "dynamicTable"></table>' );

		oTable = $('#dynamicTable').dataTable( {
					"aaData": eval(tableData),
					"aoColumns": eval(colSet),
					"sDom": '<"top"fp<"clear">>rt<"bottom"pi<"clear">>',
					"iDisplayLength": 20,
					"aLengthMenu": [[25, 50, 100], [25, 50, 100]],
					"fnInfoCallback": paginationCallback
				} );
		
				filterStr = "<tr>"; 
				filterStr += '<th><input type="text" name="filterAssay" value="Filter" class="search_init" style="width:50px;"/></th>';
				filterStr += '<th><input type="text" name="filterEndpoint" value="Filter" class="search_init"/></th>';
				filterStr += '<th><input type="text" name="filterConcentration"  value="Filter" class="search_init" /></th>';
				filterStr += '<th><input type="text" name="filterTimePoint"  value="Filter" class="search_init" /></th>';
				filterStr += '<th><input type="text" name="filterCompound" value="Filter" class="search_init" style="width:50px;"/></th>';
				filterStr += '<th><input type="text" name="filterCellLine" value="Filter" class="search_init" style="width:50px;"/></th>';
				filterStr += '<th><input type="text" name="filterGene" value="Filter" class="search_init" style="width:50px;"/></th>';
				filterStr += "</tr>";
				
				$("table#dynamicTable thead").append(filterStr);
				filterInput();
				

	}, dataType: "jsonp", type: "GET", 'jsonp': 'json.wrf'});
}

var filterStr = "";
var asInitVals = new Array();
function filterInput(){
	
	$("thead input").keyup( function () {
		oTable.fnFilter( this.value, ($("thead input").index(this)) );
	} );
	$("thead input").each( function (i) {
		asInitVals[this.name] = this.value;
	} );
	
	$("thead input").focus( function () {
		if ( this.className == "search_init" )
		{
			this.className = "";
			this.value = "";
		}
	} );
	$("thead input").blur( function (i) {
		if ( this.value == "" )
		{
			this.className = "search_init";
			this.value = asInitVals[this.name];
		}
	} );
}