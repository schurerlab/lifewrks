$(document).ready(function() {
	
	if (mode == "SmallMolecule") {
		mode = "SmallMolecule";
	}else {
		mode = mode.toLowerCase();
	}
	if (mode == "protein") {
		elemId = "ProteinId";
		$("#summary").load("/life/web/templates/summary-protein.html");
	}else if (mode == "SmallMolecule") {
		elemId = "SmallMoleculeId";
		$("#summary").load("/life/web/templates/summary-compound.html");
	}else if (mode == "gene") {
		elemId = "GeneId";
		$("#summary").load("/life/web/templates/summary-gene.html");
	}else if (mode == "cellline") {
		elemId = "CellLineId";
		$("#summary").load("/life/web/templates/summary-cellline.html");
	}else if (mode == "assay") {
		$("#summary").load("/life/web/templates/summary-assay.html");
	}else if (mode == "phosphoprotein") {
		elemId = "PhosphoProteinId";
		$("#summary").load("/life/web/templates/summary-phosphoprotein.html");
	}else if (mode == "shrna") {
		elemId = "ShRnaID";
		$("#summary").load("/life/web/templates/summary-shrna.html");
	}else if (mode == "cdna") {
		elemId = "CdnaID";
		$("#summary").load("/life/web/templates/summary-cdna.html");
	}else if (mode == "antibody") {
		elemId = "AntibodyId";
		$("#summary").load("/life/web/templates/summary-antibody.html");
	}else if (mode == "nonkinaseprotein") {
		elemId = "NonKinaseProteinId";
		$("#summary").load("/life/web/templates/summary-nonkinaseprotein.html");
	}else if (mode == "ligand") {
		elemId = "LigandProteinId";
		$("#summary").load("/life/web/templates/summary-ligand.html");
	}
	
	//Hide download button
	$('#header-table').html("");
	callProcessQueryWithDelay();
});

var elemId, signature;
var startIndex = 0, noOfRows = 20, start = 0;
var selectedAssay, totalRecords;
var solr_participant_id;

var participant_id = $(document).getUrlParam('input');
var mode = $(document).getUrlParam('mode');
var source = $(document).getUrlParam('source');


//Add delay so that summary html pages will have values
function callProcessQueryWithDelay(){
	var delay=100;
    setTimeout(function(){
    	processQuery();
    },delay);
}

function processQuery() {
	$.getJSON("/life/fetch-summary", {
		input : participant_id,
		mode : mode,
		source: source == null ? "null" : source
	}, loadSummary);
}
function fetchSolrData(solrParticipantId)
{	
	$.ajax({
		url : "http://lincs.ccs.miami.edu:8080/solr-example/select?wt=json&json.wrf=?&indent=true&"
			+ "facet=true&facet.field=AssayTypeName&rows=-1&facet.mincount=1",
		dataType : "jsonp",
		data : {
			q : elemId + ":" + solrParticipantId
			},
			success : getDataLinks
		});
}

function loadSummary(response) {
	
	if(response && mode != "assay")
		solr_participant_id = response.solrParticipantId;

	if(response && mode != "assay")	
		fetchSolrData(response.solrParticipantId);
	else
		displayErrMsg("Data fetch failed. Please retry");	
	
	var summaryStr = "";
	if (mode == "protein") {
		dataName = (emptyCheck(response.kinasename) ? response.kinasename : "");
		dataDescription = (emptyCheck(response.kinasedescription) ? response.kinasedescription
				: "");
		dataUniprotId = (emptyCheck(response.uniprotid) ? "<a href=\"http://www.uniprot.org/uniprot/"
				+ response.uniprotid
				+ "\" target=\"_blank\">"
				+ response.uniprotid + "</a>"
				: "");
		dataFacilityId = ((emptyCheck(response.sourceid) && (response.sourceid != response.proteinid)) ? "<a href=\"http://lincs.hms.harvard.edu/db/proteins/"
				+ response.sourceid
				+ "\" target=\"_blank\">"
				+ response.sourceid + "</a>"
				: "");
		dataSymbol = (emptyCheck(response.proteinname) ? response.proteinname
				: "");
		dataOrganism = (emptyCheck(response.organism) ? response.organism : "");
		dataCategory = (emptyCheck(response.kinasecategory) ? response.kinasecategory
				: "");
		dataGroup = (emptyCheck(response.kinasegroup) ? response.kinasegroup
				: "");
		dataGateKeeper = (emptyCheck(response.gatekeeper) ? response.gatekeeper
				: "");
		dataKinaseDomain = (emptyCheck(response.Spkinasedomain) ? response.Spkinasedomain
				: "");
		dataKinaseCategory = (emptyCheck(response.kinasecategory) ? response.kinasecategory
				: "");
		dataKinaseFamily = (emptyCheck(response.kinasefamily) ? response.kinasefamily
				: "");
		dataKinaseHinge1 = (emptyCheck(response.kinasehingei1) ? response.kinasehingei1
				: "");
		dataKinaseHinge3 = (emptyCheck(response.kinasehingei3) ? response.kinasehingei3
				: "");

		$('#summaryProteinName').html(dataName);
		$('#summaryProteinSymbol').html(dataSymbol);
		$('#uniprotId').html(dataUniprotId);
		$('#facilityId').html(dataFacilityId);
		$('#group').html(dataGroup);
		$('#kinaseFamily').html(dataKinaseFamily);
		$('#gateKeeper').html(dataGateKeeper);
		$('#kinaseHinge1').html(dataKinaseHinge1);
		$('#kinaseHinge3').html(dataKinaseHinge3);

	} else if (mode == "SmallMolecule") {
		
		var dataFaciIityId;
		
		var dataName = emptyCheck(response.smallmoleculename) ? response.smallmoleculename : "";
		var dataCategory = emptyCheck(response.allcategories) ? response.allcategories : "" ;
		
		var dataLincsId = emptyCheck(response.lincs_id) ? response.lincs_id : "";
		
		//If Facility ID is from Broad, don't display link
		if(response.Lincs_CompoundId.substring(0,3) == "BRD"){
			dataFaciIityId = ((emptyCheck(response.Lincs_CompoundId) && (response.salt_id != response.Lincs_CompoundId)) ? response.Lincs_CompoundId : "");
		}else{
			dataFaciIityId = ((emptyCheck(response.Lincs_CompoundId) && (response.salt_id != response.Lincs_CompoundId)) ? "<a href=\"http://lincs.hms.harvard.edu/db/sm/"
					+ response.Lincs_CompoundId
					+ "/"
					+ "\" target=\"_blank\">"
					+ response.Lincs_CompoundId + "</a>"
					: "");
		}
		
		var dataPubChemId = emptyCheck(response.pubchemid) ? "<a href=\"http://pubchem.ncbi.nlm.nih.gov/summary/summary.cgi?cid="
							+ response.pubchemid + "\" target=\"_blank\">" + response.pubchemid + "</a>" : "";
				
		if (null != response.lincs_id && "" != response.lincs_id) {
			dataImage = '/life/web/images/sm-images/400/' + response.lincs_id
					+ '.png';
			$("#data-image").attr("src", dataImage);
			$("#data-image-span").attr("src", dataImage);
		}
		
		var dataMw = (emptyCheck(response.molecularweight) ? parseFloat(response.molecularweight).toFixed(1) : "");
		var dataLipinsky5 = (emptyCheck(response.lipinski_r5) ? booleanCheck(response.lipinski_r5) : "");
		var dataLipinsky3 = (emptyCheck(response.lipinski_r3) ? booleanCheck(response.lipinski_r3) : "");
		var dataBioAvailability = (emptyCheck(response.bioavailability) ? booleanCheck(response.bioavailability) : "");
		var dataLeadLikeness = (emptyCheck(response.lead_likeness) ? booleanCheck(response.lead_likeness) : "");
		
		//  show green and red bars
		if (dataLipinsky5 == 'true')
			dataLipinsky5 = '<span class=\'true\'>&nbsp; Pass &nbsp;</span>';
		else
			dataLipinsky5 = '<span class=\'false\'>&nbsp; Fail &nbsp;</span>';
		if (dataLipinsky3 == 'true')
			dataLipinsky3 = '<span class=\'true\'>&nbsp; Pass &nbsp;</span>';
		else
			dataLipinsky3 = '<span class=\'false\'>&nbsp; Fail &nbsp;</span>';
		if (dataBioAvailability == 'true')
			dataBioAvailability = '<span class=\'true\'>&nbsp; Pass &nbsp;</span>';
		else
			dataBioAvailability = '<span class=\'false\'>&nbsp; Fail &nbsp;</span>';
		if (dataLeadLikeness == 'true')
			dataLeadLikeness = '<span class=\'true\'>&nbsp; Pass &nbsp;</span>';
		else
			dataLeadLikeness = '<span class=\'false\'>&nbsp; Fail &nbsp;</span>';
		
		$('#summarySmallMoleculeName').html(dataName);
		$('#summarySmallMoleculeCategory').html(dataCategory);
		
		$('#lincsId').html(dataLincsId);
		$('#facilityId').html(dataFaciIityId);
		$('#pubchemId').html(dataPubChemId);
		
		$('#data-lipinsky5').html(dataLipinsky5);
		$('#data-lipinsky3').html(dataLipinsky3);
		$('#data-leadLikeness').html(dataLeadLikeness);
		$('#data-bioAvailability').html(dataBioAvailability);

		$('#data-image').show();

	} else if (mode == "cellline") {
		var dataName = (emptyCheck(response.name) ? response.name : "");
		var dataType = (emptyCheck(response.celltype) ? response.celltype : "");
		var dataProviderId = (emptyCheck(response.providerid) ? response.providerid	: "");
		var dataLincsSourceId = (emptyCheck(response.lincssourceid) ? "<a href=\"http://lincs.hms.harvard.edu/db/cells/"
												+ response.lincssourceid
												+ "/"
												+ "\" target=\"_blank\">"
												+ response.lincssourceid + "</a>"
												: ""); 
		var dataDoId = (emptyCheck(response.doid) ? "<a href=\"http://bioportal.bioontology.org/ontologies/49838/?p=terms&conceptid="
												+ response.doid
												+ "\" target=\"_blank\">"
												+ response.doid + "</a>"
												: ""); 
		var dataDisease = (emptyCheck(response.disease) ? response.disease : "");
		var dataOrgan = (emptyCheck(response.organ) ? response.organ : "")
		var dataGender = (emptyCheck(response.gender) ? response.gender : "")
		var dataOrganism = (emptyCheck(response.organism) ? response.organism : "");
		var dataProvider = (emptyCheck(response.provider) ? response.provider : "");

		$('#summaryCellName').html(dataName);
		$('#summaryCellCategory').html(dataType);
		$('#providerId').html(dataProviderId);
		$('#lincsSourceId').html(dataLincsSourceId);
		$('#doId').html(dataDoId);
		$('#disease').html(dataDisease);
		$('#organ').html(dataOrgan);
		$('#gender').html(dataGender);
		$('#organism').html(dataOrganism);
		$('#provider').html(dataProvider);

	} else if (mode == "gene") {
		var geneId = (emptyCheck(response.entrezid) ? "<a href=\"http://www.ncbi.nlm.nih.gov/gene/?term="
				+ response.entrezid
				+ "\" target=\"_blank\">"
				+ response.entrezid + "</a>"
				: "");
		$('#organism').html(response.organism);
		$('#entrezId').html(geneId);
		$('#geneName').html(response.name);
	}else if (mode == "assay") {
		$('#bioAssayName').html(response.assayname);
		$('#bioAssayDescription').html(response.assaydescription);
		$('#bioAssayFormat').html(response.assayformat);
		$('#bioAssayType').html(response.assaytype);
		$('#projectNumber').html(response.projetnumber);
		$('#organisation').html(response.organization);
		$('#downloadData').attr('href',response.downloadurl);
		getSummaryBioAssayLinks(response.assayname);
	}else if(mode == "phosphoprotein"){
		$('#phosphosite').html(response.phosphosite);
		$('#basepeptide').html(response.basepeptide);
		$('#modifiedpeptide').html(response.modifiedpeptide);
		$('#cluster').html(response.cluster);
		$('#clustercode').html(response.clustercode);
		$('#phosphoProteinName').html(response.name);
	}else if(mode == "cdna"){
		$('#cdnaName').html(response.name);
		$('#cdnaId').html(response.solrParticipantId);
		$('#targetGene').html(response.name);
	}else if(mode == "shrna"){
		$('#shrnaName').html(response.name);
		$('#targetGene').html(response.name);
		$('#targetSeq').html(response.target_seq);
		$('#sevenMerSeq').html(response.seed_seq_7mer);
		$('#sixMerSeq').html(response.seed_seq_6mer);
	}else if(mode == "antibody"){
		$('#abName').html(response.name);
		$('#organism').html(response.organism);
		$('#uniprot').html(response.uniprot);
		$('#description').html(response.description);
		$('#refseqid').html(response.refseqid);
		$('#symbol').html(response.symbol);
	}else if(mode == "nonkinaseprotein"){
		$('#nkpame').html(response.name);
		$('#uniprot').html(response.uniprot);
		$('#description').html(response.description);
	}else if(mode == "ligand"){
		$('#nkpame').html(response.name);
		$('#uniprot').html(response.uniprot);
		$('#description').html(response.description);
	}
	afterLoad();
}

//create 2 arrays, assay without endpoints and assay without endpoints
function getDataLinks(data) {

	response = eval(data);
	var assayTypeName = response.facet_counts.facet_fields.AssayTypeName;

	if (emptyCheck(assayTypeName)) {
		
		var dataLinksArr = [],dataLinksWithEpArr = [];
		
		for ( var i = 0; i < assayTypeName.length; i++) {
			if(assayTypeName[i] == "KINOMEscan assay" || assayTypeName[i] == "KiNativ assay" ){
				dataLinksWithEpArr.push(getLinkNames(assayTypeName[i]));
			}else{
				dataLinksArr.push(getLinkNames(assayTypeName[i]));
			}
			i++;
		}
	}
	
//	alert(dataLinksWithEpArr.length);
	//if assay has enpoints, update the array with those endpoints
	if(dataLinksWithEpArr.length > 0){
		loadKinomeKinativEndpoints(dataLinksArr,dataLinksWithEpArr);
	}
	//if there are no enpoints, get the summary link buttons
	else{
		getSummaryLinkButtons(dataLinksArr);
	}
}

function getLinkNames(assayName) {
	if(assayName == "KINOMEscan assay") return "KINOMEScan Assay";
	else if (assayName == "Cell cycle state assay") return "Cell cycle state assay";
	else if (assayName == "Apoptosis assay") return "Apoptosis assay";
	else if (assayName == "Cell growth inhibition assay") return "Cell growth inhibition assay";
	else if (assayName == "L1000 transcriptional profiling assay") return "L1000 transcriptional profiling assay";
	else if (assayName == "KiNativ assay") return "KiNativ assay";
	else if (assayName == "P100 phosphoprotein profiling assay") return "P100 phosphoprotein profiling assay";
	else if (assayName == "Cue signal response assay") return "Cue signal response assay";
	else if (assayName == "Proteomics cell state profiling assay") return "Proteomics cell state profiling assay";
	else if (assayName == "Single cell protein secretion profiling assay") return "Single cell protein secretion profiling assay";
	else if (assayName == "Cell viability assay") return "Cell viability assay";
	else if (assayName == "Cell signal response assay") return "Cell signal response assay";
	else if (assayName == "Dynamic ERK signaling single cell imaging assay") return "Dynamic ERK signaling single cell imaging assay";
}

function getSummaryLinkButtons(linksArr){
	
	var linkStr = "";
	
	for(var i = 0 ;i < linksArr.length; i++){
		if(i == 0){
			linkStr += "<div class='span2'><a href='#' class='linkTab btn btn-small active' onClick = 'loadAssayData(\"";
		}else{
			linkStr += "<div class='span2'><a href='#' class='linkTab btn btn-small' onClick = 'loadAssayData(\"";
		}
		linkStr += linksArr[i]	+ "\");'>" + linksArr[i] + "</a> </div>";
	}
	$("#summaryLinks").html(linkStr);
	
	//load first assay data on page load
	loadAssayData(linksArr[0]);
	
	$(".linkTab").click(function() {
		$(".linkTab").removeClass("active");
		$(this).addClass("active");
	});
}

function loadAssayData(assay) {
	if (assay == "KINOMEScan Assay pKd") {
		loadKinomeKinativeData("KINOMEScan Assay","pKd");
	}else if (assay == "KINOMEScan Assay Percent kinase inhibition"){
		loadKinomeKinativeData("KINOMEScan Assay","Percent kinase inhibition");
	}else if (assay == "KiNativ assay IC50") {
		loadKinomeKinativeData("KiNativ assay","IC50");
	}else if(assay == "KiNativ assay Percent Kinase Inhibition"){
		loadKinomeKinativeData("KiNativ assay","Percent Kinase Inhibition");
	}else if (assay == "Cell cycle state assay") {
		loadCellStateData();
	} else if (assay == "Apoptosis assay") {
		loadApoptosisData();
	} else if (assay == "Cell growth inhibition assay") {
		loadCellGrowthData();
	}else if (assay == "L1000 transcriptional profiling assay") {
		loadL1000Data();
	}else if (assay == "P100 phosphoprotein profiling assay") {
		loadPhosphoProteinData();
	}else if (assay == "Single cell protein secretion profiling assay") {
		loadProteinSecretionData();
	}else if (assay == "P100 phosphoprotein profiling assay" || assay ==  "Cue signal response assay" || assay ==  "Proteomics cell state profiling assay" || 
			assay == "Cell viability assay" || assay == "Cell signal response assay" || assay == "Dynamic ERK signaling single cell imaging assay"){
		displayNoDataMessage();
	}
}

//methods to get summary page data  : START

function displayNoDataMessage(){
	$("#vis").hide(); //hide coffee wheel
	$("#legend").hide(); //hide coffee wheel legend
	
	//hide all crossfilter charts
	$("#smallMoleculeCrossFilterChart").hide();
	$("#smallMoleculePPCrossFilterChart").hide();
	$("#sMProteinSecretionCrossFilterChart").hide();
	
	$("#cellCrossFilterChart").hide();
	$("#cellPPCrossFilterChart").hide();
	$("#cellKinativCrossFilterChart").hide();
	$("#cellProteinSecretionCrossFilterChart").hide();
	
	$("#geneCrossFilterChart").hide();
	$("#ppCrossFilterChart").hide();
	$("#shrnaCrossFilterChart").hide();
	$("#cdnaCrossFilterChart").hide();
	
	$("#heatMapSection").hide();
	
	var noDataMsg = '<h2 style=" margin-left:30px;margin-top:30px;"> Normalized data is not yet available for this assay. Please check the <a href="http://lincs.hms.harvard.edu/data/">source</a> for access to un-normalized data </h2>';
	$("#waitImg").html(noDataMsg);
}

function loadL1000Data(){
	
	//load L1000 data for Shrna/Cdna/Small Molecule/Cell page
	if(mode == "shrna" || mode == "cdna" || mode == "SmallMolecule" || mode == "cellline"){
		loadShrnaCdnaData();
	}else if(mode == "gene" ){ //load L1000 data for gene page
		loadGeneData();
	}
}

function loadPhosphoProteinData(){
	
	$("#vis").hide(); //hide coffee wheel
	$("#legend").hide(); //hide coffee wheel legend
	
	if(mode == "SmallMolecule"){
		$("#smallMoleculePPCrossFilterChart").hide(); //hide CrossFilter before loading the data
		$("#sMProteinSecretionCrossFilterChart").hide();
		$("#smallMoleculeCrossFilterChart").hide(); //hide CrossFilter for this assay 
		$("#waitImg").html('Loading Data . . .');
	}else if( mode == "cellline"){
		$("#dynamicDataTab").hide();//
		$("#cellCrossFilterChart").hide();// hide CrossFilter for this assay 
		$("#cellKinativCrossFilterChart").hide();// hide CrossFilter for this assay 
		$("#cellProteinSecretionCrossFilterChart").hide();
		$("#cellPPCrossFilterChart").hide();//hide CrossFilter before loading the data
		$("#waitImg").html('Loading Data . . .');
	}else if(mode == "PhosphoProtein"){
		$("#ppCrossFilterChart").hide();
		$("#waitImg").html('Loading Data . . .');
	}
	
	var ppCrossFilterJson = "";
	
	$.ajax({url : "http://lincs.ccs.miami.edu:8080/solr-example/select?wt=json&indent=true&rows=10000000&fl=Concentration,TimePoint,CellLineName,SmallMoleculeName,PerturbationMeasure,PerturbationType,PhosphoProteinName",
		data : {q : elemId + ":" + solr_participant_id + " \"P100 phosphoprotein profiling assay\""
		},
		success : function(response) {
			var responseData = eval(response).response.docs;
			
			ppCrossFilterJson += "[";
			
			for ( var i = 0; i < responseData.length; i++) {
				ppCrossFilterJson += "{";
				ppCrossFilterJson += '"Id":'+ i + ',"Concentration":' +responseData[i].Concentration + ',"TimePoint":' +responseData[i].TimePoint +
									 ',"CellLineName":' + '"' +responseData[i].CellLineName + '"' + ',"SmallMoleculeName":' + '"' + responseData[i].SmallMoleculeName + '"' +
									 ',"PerturbationMeasure":' + responseData[i].PerturbationMeasure + ',"PhosphoProteinName":' + '"' + responseData[i].PhosphoProteinName + '"' +
									 ',"Activity":' + '"' + responseData[i].PerturbationType + '"' ;

			   ppCrossFilterJson += "}";						 
			   if(i < responseData.length-1){
				   ppCrossFilterJson += ",";	
			   }
				
			}
			ppCrossFilterJson += "]";
			if(mode == "SmallMolecule" || mode == "cellline"){
				getSmallMoleculePhosphoProteinCF(ppCrossFilterJson);
			}else{
				getPhosphoProteinCF(ppCrossFilterJson);
			}
		},
		complete : function(){
			if(mode == "SmallMolecule"){
				$("#smallMoleculePPCrossFilterChart").show();  //show CrossFilter once data is loaded
				$("#waitImg").html("");
			}else if( mode == "cellline"){
				$("#cellPPCrossFilterChart").show();//show CrossFilter once data is loaded
				$("#waitImg").html("");
			}else if(mode == "PhosphoProtein"){
				$("#ppCrossFilterChart").show();
				$("#waitImg").html("");
			}
		},
		dataType : "jsonp",
		type : "GET",
		'jsonp' : 'json.wrf'
  });
}

function loadShrnaCdnaData(){
	var rows = 10000000;
	
	if(mode == "SmallMolecule"){
		$("#vis").hide(); //hide coffee wheel
		$("#legend").hide(); //hide coffee wheel legend
		
		$("#kinomeScanEndpoints").html("");
		$("#smallMoleculePPCrossFilterChart").hide();//hide CrossFilter for this assay
		$("#sMProteinSecretionCrossFilterChart").hide();
		$("#smallMoleculeCrossFilterChart").hide();//hide CrossFilter before loading the data
		$("#waitImg").html('Loading Data . . .');
	}else if(mode == "cdna"){
		$("#cdnaCrossFilterChart").hide();
		$("#waitImg").html('Loading Data . . .');
	}else if(mode == "shrna"){
		$("#shrnaCrossFilterChart").hide();
		$("#waitImg").html('Loading Data . . .');
	}else if(mode == "cellline"){
		rows = "10000";
		$("#downloadSummary").hide();
		$("#cellCrossFilterChart").hide();
		$("#cellPPCrossFilterChart").hide();
		$("#cellKinativCrossFilterChart").hide();
		$("#cellProteinSecretionCrossFilterChart").hide();
		$("#waitImg").html('Loading Data . . .');
	}
	
	var crossFilterJson = "";
	
	$.ajax({url : "http://lincs.ccs.miami.edu:8080/solr-example/select?wt=json&rows="+rows+"&fl=Concentration,TimePoint,CellLineName,GeneName,PerturbationMeasure,Activity,SmallMoleculeName,ShRnaName,CdnaName",
		data : {q : elemId + ":" + solr_participant_id + " \"L1000 transcriptional profiling assay\"" 
		},
		success : function(response) {
			var responseData = eval(response).response.docs;
			
			//convert responseData to string and then replace solr document key SmallMoleculeName/ShRnaName/CdnaName with Perturbation
			responseData = JSON.stringify(responseData);
			responseData = responseData.replace(/SmallMoleculeName/g,"Perturbation");
			responseData = responseData.replace(/ShRnaName/g,"Perturbation");
			responseData = responseData.replace(/CdnaName/g,"Perturbation");
			
			//convert responseData string back to object
			responseData = eval(responseData);
			
			crossFilterJson += "[";
			
			for (var i = 0; i < responseData.length; i++) {
				crossFilterJson += "{";
				crossFilterJson += '"Id":'+ i + ',"Concentration":' +responseData[i].Concentration + ',"TimePoint":' +responseData[i].TimePoint +
									 ',"CellLineName":' + '"' +responseData[i].CellLineName + '"' + ',"PerturbationMeasure":' + responseData[i].PerturbationMeasure + 
									 ',"Activity":' + '"' + responseData[i].Activity + '"' + ',"GeneName":' + '"' + responseData[i].GeneName + '"' + ',"SmallMoleculeName":' + '"' + responseData[i].Perturbation + '"';

			   crossFilterJson += "}";						 
			   if(i < responseData.length-1){
				   crossFilterJson += ",";	
			   }
			}
			crossFilterJson += "]";
			getCrossFilter(crossFilterJson,mode);
		},
		complete : function(){
			if(mode == "SmallMolecule"){
				$("#smallMoleculeCrossFilterChart").show(); //show CrossFilter once data is loaded
				$("#waitImg").html("");
			}else if(mode == "cdna"){
				$("#cdnaCrossFilterChart").show();
				$("#waitImg").html("");
			}else if(mode == "shrna"){
				$("#shrnaCrossFilterChart").show();
				$("#waitImg").html("");
			}else if(mode == "cellline"){
				$("#cellCrossFilterChart").show();
				$("#waitImg").html("");
			}
		},
		dataType : "jsonp",
		type : "GET",
		'jsonp' : 'json.wrf'
  });
}

function loadGeneData(){
	
	var crossFilterJson = "";
	
	$("#geneCrossFilterChart").hide();
	$("#waitImg").html('Loading Data . . .');
	
	$.ajax({url : "http://lincs.ccs.miami.edu:8080/solr-example/select?wt=json&rows=5000&fl=Concentration,TimePoint,CellLineName,GeneName,PerturbationMeasure,Activity,SmallMoleculeName,ShRnaName,CdnaName",
		data : {q : elemId + ":" + solr_participant_id
		},
		success : function(response) {
			var responseData = eval(response).response.docs;
			
			//convert responseData to string and then replace solr document key SmallMoleculeName/ShRnaName/CdnaName with Perturbation
			responseData = JSON.stringify(responseData);
			responseData = responseData.replace(/SmallMoleculeName/g,"Perturbation");
			responseData = responseData.replace(/ShRnaName/g,"Perturbation");
			responseData = responseData.replace(/CdnaName/g,"Perturbation");
			
			//convert responseData string back to object
			responseData = eval(responseData);
			
			crossFilterJson += "[";
			
			for (var i = 0; i < responseData.length; i++) {
				crossFilterJson += "{";
				crossFilterJson += '"Id":'+ i + ',"Concentration":' +responseData[i].Concentration + ',"TimePoint":' +responseData[i].TimePoint +
									 ',"CellLineName":' + '"' +responseData[i].CellLineName + '"' + ',"PerturbationMeasure":' + responseData[i].PerturbationMeasure + 
									 ',"Activity":' + '"' + responseData[i].Activity + '"' + ',"Perturbation":' + '"' + responseData[i].Perturbation + '"' + ',"GeneName":' + '"' + responseData[i].GeneName +
									 '"' + ',"SmallMoleculeName":' + '"' + responseData[i].SmallMoleculeName + '"';

			   crossFilterJson += "}";						 
			   if(i < responseData.length-1){
				   crossFilterJson += ",";	
			   }
			}
			crossFilterJson += "]";
			getGeneCF(crossFilterJson,mode);
		},
		complete :function(){
			$("#geneCrossFilterChart").show();
			$("#waitImg").html("");
		},
		dataType : "jsonp",
		type : "GET",
		'jsonp' : 'json.wrf'
  });
}

function loadCellGrowthData() {
	
	$("#vis").hide(); //hide coffee wheel
	$("#legend").hide(); //hide coffee wheel legend
	
	if(mode == "SmallMolecule"){
		$("#kinomeScanEndpoints").html("");
		$("#smallMoleculePPCrossFilterChart").hide();//hide CrossFilter for this assay
		$("#sMProteinSecretionCrossFilterChart").hide();
		$("#smallMoleculeCrossFilterChart").hide();//hide CrossFilter before loading the data
		$("#waitImg").html('Loading Data . . .');
	}else if(mode == "cellline"){
		$("#dynamicDataTab").hide();
		$("#cellPPCrossFilterChart").hide();//hide CrossFilter for this assay
		$("#cellKinativCrossFilterChart").hide();// hide CrossFilter for this assay 
		$("#cellProteinSecretionCrossFilterChart").hide();
		$("#cellCrossFilterChart").hide();//hide CrossFilter before loading the data
		$("#waitImg").html('Loading Data . . .');
	}
	
	var crossFilterJson = "";
	
	$.ajax({url : "http://lincs.ccs.miami.edu:8080/solr-example/select?wt=json&rows=10000000&fl=CellLineName,SmallMoleculeName,PerturbationType,PerturbationMeasure,Concentration,TimePoint&"
				    + "group=true&group.field=PerturbationId",
		    data : { q : elemId + ":" + solr_participant_id	+ " \"Cell growth inhibition assay\"" },
			success : function(response) {
				
		    	var responseData = eval(response).grouped.PerturbationId.groups;
				
				crossFilterJson += "[";
				
				for (var i = 0; i < responseData.length; i++) {
					crossFilterJson += "{";
					crossFilterJson += '"Id":'+ i + ',"Concentration":' +responseData[i].doclist.docs[0].Concentration + ',"TimePoint":' +responseData[i].doclist.docs[0].TimePoint +
										 ',"CellLineName":' + '"' +responseData[i].doclist.docs[0].CellLineName + '"' + ',"PerturbationMeasure":' + responseData[i].doclist.docs[0].PerturbationMeasure + 
										 ',"Activity":' + '"' + responseData[i].doclist.docs[0].PerturbationType + '"' + ',"SmallMoleculeName":' + '"' + responseData[i].doclist.docs[0].SmallMoleculeName + '"';
	
				   crossFilterJson += "}";						 
				   if(i < responseData.length-1){
					   crossFilterJson += ",";	
				   }
				}
				crossFilterJson += "]";
				getCrossFilter(crossFilterJson);
		   },
		   complete : function(){
				if(mode == "SmallMolecule"){
					$("#smallMoleculeCrossFilterChart").show();  //show CrossFilter once data is loaded
					$("#waitImg").html("");
				}else if(mode == "cellline"){
					$("#cellCrossFilterChart").show();//hide CrossFilter before loading the data
					$("#waitImg").html("");
				}
			},
		   dataType : "jsonp",
		   type : "GET",
		   'jsonp' : 'json.wrf'
   });
}

function loadCellStateData() {
	
	$("#vis").hide(); //hide coffee wheel
	$("#legend").hide(); //hide coffee wheel legend
	
	if(mode == "SmallMolecule"){
		$("#smallMoleculePPCrossFilterChart").hide();//hide CrossFilter for this assay
		$("#sMProteinSecretionCrossFilterChart").hide();
		$("#smallMoleculeCrossFilterChart").hide();//hide CrossFilter before loading the data
		$("#waitImg").html('Loading Data . . .');
	}else if(mode == "cellline"){
		$("#dynamicDataTab").hide();
		$("#cellPPCrossFilterChart").hide();//hide CrossFilter for this assay
		$("#cellKinativCrossFilterChart").hide();// hide CrossFilter for this assay 
		$("#cellProteinSecretionCrossFilterChart").hide();
		$("#cellCrossFilterChart").hide();//hide CrossFilter before loading the data
		$("#waitImg").html('Loading Data . . .');
	}
	
	var crossFilterJson = "";
	
	$.ajax({url : "http://lincs.ccs.miami.edu:8080/solr-example/select?wt=json&indent=true&"
						+ "fl=CellLineName,SmallMoleculeName,PerturbationType,PerturbationMeasure,Concentration,TimePoint,AssayTypeName&"
						+ "group=true&group.field=PerturbationId&rows=10000000",
						
		    data : { q : elemId + ":" + solr_participant_id	+ " \" Cell cycle state assay\"" },
		    
			success : function(response) {
				
		    	var responseData = eval(response).grouped.PerturbationId.groups;
				
				crossFilterJson += "[";
				
				for (var i = 0; i < responseData.length; i++) {
					crossFilterJson += "{";
					crossFilterJson += '"Id":'+ i + ',"Concentration":' +responseData[i].doclist.docs[0].Concentration + ',"TimePoint":' +responseData[i].doclist.docs[0].TimePoint +
										 ',"CellLineName":' + '"' +responseData[i].doclist.docs[0].CellLineName + '"' + ',"PerturbationMeasure":' + responseData[i].doclist.docs[0].PerturbationMeasure + 
										 ',"Activity":' + '"' + responseData[i].doclist.docs[0].PerturbationType + '"' + ',"SmallMoleculeName":' + '"' + responseData[i].doclist.docs[0].SmallMoleculeName + '"';
	
				   crossFilterJson += "}";						 
				   if(i < responseData.length-1){
					   crossFilterJson += ",";	
				   }
				}
				crossFilterJson += "]";
				getCrossFilter(crossFilterJson);
		   },
		   complete : function(){
				if(mode == "SmallMolecule"){
					$("#smallMoleculeCrossFilterChart").show();
					$("#waitImg").html("");
				}else if(mode == "cellline"){
					$("#cellCrossFilterChart").show();//hide CrossFilter before loading the data
					$("#waitImg").html("");
				}
			},
		   dataType : "jsonp",
		   type : "GET",
		   'jsonp' : 'json.wrf'
   });
}

function loadApoptosisData() {
	
	$("#vis").hide(); //hide coffee wheel
	$("#legend").hide(); //hide coffee wheel legend
	
	if(mode == "SmallMolecule"){
		$("#smallMoleculePPCrossFilterChart").hide();//hide CrossFilter for this assay
		$("#sMProteinSecretionCrossFilterChart").hide();
		$("#smallMoleculeCrossFilterChart").hide();//hide CrossFilter before loading data
		$("#waitImg").html('Loading Data . . .');
	}else if(mode == "cellline"){
		$("#dynamicDataTab").hide();// hide canvas browser
		$("#cellPPCrossFilterChart").hide();//hide CrossFilter for this assay
		$("#cellKinativCrossFilterChart").hide();// hide CrossFilter for this assay 
		$("#cellProteinSecretionCrossFilterChart").hide();
		$("#cellCrossFilterChart").hide();//hide CrossFilter before loading the data
		$("#waitImg").html('Loading Data . . .');
	}
	
	var crossFilterJson = "";
	
	$.ajax({url : "http://lincs.ccs.miami.edu:8080/solr-example/select?wt=json&indent=true&"
						+ "fl=CellLineName,SmallMoleculeName,PerturbationType,PerturbationMeasure,Concentration,TimePoint,AssayTypeName&"
						+ "group=true&group.field=PerturbationId&rows=10000000",
						
		    data : { q : elemId + ":" + solr_participant_id	+ " \" Apoptosis assay\"" },
		    
			success : function(response) {
				
		    	var responseData = eval(response).grouped.PerturbationId.groups;
				
				crossFilterJson += "[";
				
				for (var i = 0; i < responseData.length; i++) {
					crossFilterJson += "{";
					crossFilterJson += '"Id":'+ i + ',"Concentration":' +responseData[i].doclist.docs[0].Concentration + ',"TimePoint":' +responseData[i].doclist.docs[0].TimePoint +
										 ',"CellLineName":' + '"' +responseData[i].doclist.docs[0].CellLineName + '"' + ',"PerturbationMeasure":' + responseData[i].doclist.docs[0].PerturbationMeasure + 
										 ',"Activity":' + '"' + responseData[i].doclist.docs[0].PerturbationType + '"' + ',"SmallMoleculeName":' + '"' + responseData[i].doclist.docs[0].SmallMoleculeName + '"';
	
				   crossFilterJson += "}";						 
				   if(i < responseData.length-1){
					   crossFilterJson += ",";	
				   }
				}
				crossFilterJson += "]";
				getCrossFilter(crossFilterJson);
		   },
		   complete : function(){
				if(mode == "SmallMolecule"){
					$("#smallMoleculeCrossFilterChart").show();
					$("#waitImg").html('');
				}else if(mode == "cellline"){
					$("#cellCrossFilterChart").show();//hide CrossFilter before loading the data
					$("#waitImg").html("");
				}
			},
		   dataType : "jsonp",
		   type : "GET",
		   'jsonp' : 'json.wrf'
   });
}

var iCount = 0;
function loadKinomeKinativEndpoints(linkArr,linkArrWithEp) {
	 
	if(iCount < linkArrWithEp.length){
		getEndPointsFromSolr(linkArr,linkArrWithEp);
	}
}

function getEndPointsFromSolr(linkArr,linkArrWithEp){
	
	$.ajax({url : "http://lincs.ccs.miami.edu:8080/solr-example/select?wt=json&indent=true&facet=true&facet.mincount=1&facet.field=PerturbationType&rows=0&facet.limit=-1",
		data : { q : elemId + ":" + solr_participant_id + " " + linkArrWithEp[iCount]},
		success : function(response) {

			response = eval(response);
			var endpointLinks = response.facet_counts.facet_fields.PerturbationType;

			for ( var i = 0; i < endpointLinks.length; i++) {
					linkArr.push(linkArrWithEp[iCount]+ " " +endpointLinks[i]);
					i++;
			}
		},
		complete:function(){
			iCount ++;
			
			if(iCount < linkArrWithEp.length){
				loadKinomeKinativEndpoints(linkArr,linkArrWithEp);
			}else{
				getSummaryLinkButtons(linkArr);
			}
		},
		dataType : "jsonp",
		type : "GET",
		'jsonp' : 'json.wrf'
	});	
}

function loadKinomeKinativeData(selectedAssay,selectedEp) {
	
	if(mode == "protein" || mode == "SmallMolecule" || mode == "cellline"){
		$("#cellPPCrossFilterChart").hide();
		$("#cellCrossFilterChart").hide();
		$("#cellProteinSecretionCrossFilterChart").hide();
		$("#smallMoleculePPCrossFilterChart").hide();
		$("#smallMoleculeCrossFilterChart").hide();
		$("#sMProteinSecretionCrossFilterChart").hide();
		$("#waitImg").html('Loading Data . . .');
	}
	
	//for protein load heatmap
	if(mode == "protein"){
		$("#downloadSummary").hide();
		var heatMapJson = "[{";
		
		$.ajax({url : "http://lincs.ccs.miami.edu:8080/solr-example/select?wt=json&indent=true&fl=SmallMoleculeName,ProteinName,PerturbationType,PerturbationMeasure,AssayTypeName&"
																		   + "group=true&group.field=PerturbationId&rows=10000000",
				data : { q : elemId + ":" + solr_participant_id + " " + selectedAssay + " " + selectedEp },
				success : function(response) {
	
					response = eval(response);
					var groups = response.grouped.PerturbationId.groups;
					
					heatMapJson += '"":"Perturbation Measure",';
					for ( var i = 0; i < groups.length; i++) {
						heatMapJson += '"' + groups[i].doclist.docs[0].SmallMoleculeName + '"' + ":";
						heatMapJson += '"' + groups[i].doclist.docs[0].PerturbationMeasure + '"';
						
						if(i<groups.length-1){
							heatMapJson += ",";
						}
					}
					heatMapJson += "}]";
					getHeatMap(heatMapJson,groups.length,selectedEp);
				},complete:function(){
					$("#waitImg").html("");
				},
				dataType : "jsonp",
				'jsonp' : 'json.wrf'
		});
	//for small molecule load coffee wheel	
	}else if(mode == "SmallMolecule"){
		$("#downloadSummary").hide();
		$.getJSON("/life/wheel-feeder", { input : solr_participant_id, assayType : selectedAssay, epName: selectedEp },
				function (response){
					getCoffeeWheel(response,selectedEp);
		});
		$("#waitImg").html("");
	}
	//for cell line load cross filter	
	else if(mode == "cellline"){
		$("#cellKinativCrossFilterChart").show();
		var crossFilterJson = "";
		
		$.ajax({url : "http://lincs.ccs.miami.edu:8080/solr-example/select?wt=json&indent=true&fl=SmallMoleculeName,PerturbationType,PerturbationMeasure,Concentration,ProteinName&"
			   + "group=true&group.field=PerturbationId&rows=10000000",
			
			data : { q : elemId + ":" + solr_participant_id + " " + selectedAssay + " " + selectedEp },
			success : function(response) {
				
				var responseData = eval(response).grouped.PerturbationId.groups;
				
				crossFilterJson += "[";
				
				for (var i = 0; i < responseData.length; i++) {
					crossFilterJson += "{";
					crossFilterJson += '"Id":'+ i + ',"Concentration":' +responseData[i].doclist.docs[0].Concentration + ',"PerturbationMeasure":' + responseData[i].doclist.docs[0].PerturbationMeasure + 
										 ',"Activity":' + '"' + responseData[i].doclist.docs[0].PerturbationType + '"' + ',"SmallMoleculeName":' + '"' + responseData[i].doclist.docs[0].SmallMoleculeName + '"' +
										 ',"ProteinName":' + '"' + responseData[i].doclist.docs[0].ProteinName + '"';
	
				   crossFilterJson += "}";						 
				   if(i < responseData.length-1){
					   crossFilterJson += ",";	
				   }
				}
				crossFilterJson += "]";
				getCellKinativCF(crossFilterJson);
			},complete:function(){
				$("#waitImg").html("");
			},
			dataType : "jsonp",
			'jsonp' : 'json.wrf'
		});
	}
	
}

function loadProteinSecretionData(){
	
	$("#vis").hide(); //hide coffee wheel
	$("#legend").hide(); //hide coffee wheel legend
	
	//load small molecule protein secretion data
	if(mode == "SmallMolecule"){
		$("#smallMoleculePPCrossFilterChart").hide();//hide CrossFilter for this assay
		$("#smallMoleculeCrossFilterChart").hide();//hide CrossFilter for this assay
		$("#sMProteinSecretionCrossFilterChart").hide();//hide CrossFilter before loading data
		$("#waitImg").html('Loading Data . . .');
	}else if(mode == "cellline"){
		$("#dynamicDataTab").hide();
		$("#cellPPCrossFilterChart").hide();//hide CrossFilter for this assay
		$("#cellKinativCrossFilterChart").hide();// hide CrossFilter for this assay 
		$("#cellCrossFilterChart").hide();//hide CrossFilterfor this assay 
		$("#cellProteinSecretionCrossFilterChart").hide();//hide CrossFilter before loading data
		$("#waitImg").html('Loading Data . . .');
	}else if(mode == "antibody"){
		$("#abProteinSecretionCrossFilterChart").hide();//hide CrossFilter before loading data
		$("#waitImg").html('Loading Data . . .');
	}
	
	var crossFilterJson = "";
	
	$.ajax({url : "http://lincs.ccs.miami.edu:8080/solr-example/select?wt=json" +
			      "&fl=Concentration,CellLineName,SmallMoleculeName,PerturbationMeasure,ProteinName,PerturbationType&rows=10000000&group=true&group.field=PerturbationId",
		
		data : { q : elemId + ":" + solr_participant_id + "\"Single cell protein secretion profiling assay\""},
		success : function(response) {
			
			var responseData = eval(response).grouped.PerturbationId.groups;
			
			crossFilterJson += "[";
			
			for (var i = 0; i < responseData.length; i++) {
				crossFilterJson += "{";
				crossFilterJson += '"Id":'+ i + ',"Concentration":' +responseData[i].doclist.docs[0].Concentration + ',"PerturbationMeasure":' + responseData[i].doclist.docs[0].PerturbationMeasure + 
								   ',"SmallMoleculeName":' + '"' + responseData[i].doclist.docs[0].SmallMoleculeName + '"' + ',"ProteinName":' + '"' + responseData[i].doclist.docs[0].ProteinName + '"'+
								   ',"CellLineName":' + '"' + responseData[i].doclist.docs[0].CellLineName + '"' + ',"Activity":' + '"' + responseData[i].doclist.docs[0].PerturbationType + '"';
			   crossFilterJson += "}";						 
			   if(i < responseData.length-1){
				   crossFilterJson += ",";	
			   }
			}
			crossFilterJson += "]";
			if(mode == "SmallMolecule"){
				getSmallMoleculeProteinSecretionCF(crossFilterJson);
			}else if(mode == "cellline"){
				getCellProteinSecretionCF(crossFilterJson);
			}else if(mode == "antibody"){
				getAntibodyProteinSecretionCF(crossFilterJson);
			}
		},
		complete : function(){
			if(mode == "SmallMolecule"){
				$("#sMProteinSecretionCrossFilterChart").show();
				$("#waitImg").html('');
			}else if(mode == "cellline"){
				$("#cellProteinSecretionCrossFilterChart").show();
				$("#waitImg").html('');
			}else if(mode == "antibody"){
				$("#abProteinSecretionCrossFilterChart").show();
				$("#waitImg").html('');
			}
		},
		dataType : "jsonp",
		'jsonp' : 'json.wrf'
	});
}

function getSummaryBioAssayLinks(summaryAssayName){
	
	$.ajax( { url : 'http://lincs.ccs.miami.edu:8080/solr-example/select?wt=json&q=' +'"' + summaryAssayName + '"' + '&facet=true&facet.field=GeneId&facet.field=CellLineId&facet.field=ProteinId&facet.field=SmallMoleculeId&facet.field=PhosphoProteinId&facet.field=ShRnaID&facet.field=CdnaID&facet.field=NonKinaseProteinId&facet.field=AntibodyId&facet.field=LigandProteinId&facet.mincount=1&facet.limit=-1&indent=false&wt=json&rows=0',
        success : function(response) {
				
				var bioAssayResponse = eval(response);

				var smallMoleculeCount = bioAssayResponse.facet_counts.facet_fields.SmallMoleculeId.length;
				var proteinCount = bioAssayResponse.facet_counts.facet_fields.ProteinId.length;
				var cellLineCount = bioAssayResponse.facet_counts.facet_fields.CellLineId.length;
				var geneCount = bioAssayResponse.facet_counts.facet_fields.GeneId.length;
				var phosphoproteinsCount = bioAssayResponse.facet_counts.facet_fields.PhosphoProteinId.length;
				var shrnaCount = bioAssayResponse.facet_counts.facet_fields.ShRnaID.length;
				var cdnaCount = bioAssayResponse.facet_counts.facet_fields.CdnaID.length;
				var antibodyCount = bioAssayResponse.facet_counts.facet_fields.AntibodyId.length;
				var nonkinaseproteinCount = bioAssayResponse.facet_counts.facet_fields.NonKinaseProteinId.length;
				var ligandCount = bioAssayResponse.facet_counts.facet_fields.LigandProteinId.length;

				if(smallMoleculeCount > 0)
					$("#bioAssaySLink").html("<a class = 'tool extLink uniprot' title = 'Small Molecule Count :  "  +smallMoleculeCount/2 + "'>S</a>");	
				if(proteinCount > 0)
					$("#bioAssayPLink").html("<a class = 'tool extLink pdb' title = 'Kinase protein Count : " + proteinCount/2 + "'>P</a>");	
				if(cellLineCount > 0)
					$("#bioAssayCLink").html("<a class = 'tool extLink lincs' title = 'Cell Count : " + cellLineCount/2 + "'>C</a>");	
				if(geneCount > 0)
					$("#bioAssayGLink").html("<a class = 'tool extLink entrez' title = 'Gene Count : " + geneCount/2 + "'>G</a>");
				if(phosphoproteinsCount > 0)
					$("#bioAssayPPLink").html("<a class = 'tool extLink entrez' title = 'Phospho Proteins Count : " + phosphoproteinsCount/2 + "'>PP</a>");
				if(shrnaCount > 0)
					$("#bioAssayShLink").html("<a class = 'tool extLink entrez' title = 'short hairpin RNAs Count : " + shrnaCount/2 + "'>SH</a>");
				if(cdnaCount > 0)
					$("#bioAssayCdLink").html("<a class = 'tool extLink entrez' title = 'Complementary DNAs Count : " + cdnaCount/2 + "'>CD</a>");
				if(antibodyCount > 0)
					$("#bioAssayAbLink").html("<a class = 'tool extLink entrez' title = 'Antibody Count : " + antibodyCount/2 + "'>AB</a>");
				if(nonkinaseproteinCount > 0)
					$("#bioAssayNkpLink").html("<a class = 'tool extLink entrez' title = 'Detected proteins Count : " + nonkinaseproteinCount/2 + "'>DP</a>");
				if(ligandCount > 0)
					$("#bioAssayLpLink").html("<a class = 'tool extLink entrez' title = 'Protein ligands Count : " + ligandCount/2 + "'>LP</a>");
		},complete : function(response){
			$("#tooltip-ex a").tooltip({
		        placement : 'top'
		    });
		},
		dataType : "jsonp",
		type : "GET",
		'jsonp' : 'json.wrf'
	});
	
	var imageSrc,logoUrl;
	if(summaryAssayName == 'L1000 transcriptional profiling assay' || summaryAssayName == 'P100 phosphoprotein profiling assay'){
		imageSrc = "/life/web/images/logo_broad.png";
		logoUrl = "http://lincscloud.org/";
	}else if(summaryAssayName == 'Single cell protein secretion profiling assay'){
		imageSrc = "/life/web/images/logo_yale.jpg";
		logoUrl = "http://www.yale.edu/";
	}else{
		imageSrc = "/life/web/images/logo_hms.png";
		logoUrl = "http://lincs.hms.harvard.edu/db/";
	}
	$("#assayLogoLinkId").attr("href", logoUrl);
	$("#assayImageId").attr("src", imageSrc)
}

//methods to get summary page data : END

var filterStr = "";
var asInitVals = new Array();
function filterInput() {

	$("thead input").keyup(function() {
		oTable.fnFilter(this.value, ($("thead input").index(this)));
	});
	$("thead input").each(function(i) {
		asInitVals[this.name] = this.value;
	});

	$("thead input").focus(function() {
		if (this.className == "search_init") {
			this.className = "";
			this.value = "";
		}
	});
	$("thead input").blur(function(i) {
		if (this.value == "") {
			this.className = "search_init";
			this.value = asInitVals[this.name];
		}
	});
}

// after Load
function afterLoad() {
	
	$('.true').mouseover(function() {
		$(this).addClass('text-white');
	});
	$('.true').mouseout(function() {
		$(this).removeClass('text-white');
	});
	$('.false').mouseover(function() {
		$(this).addClass('text-white');
	});
	$('.false').mouseout(function() {
		$(this).removeClass('text-white');
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