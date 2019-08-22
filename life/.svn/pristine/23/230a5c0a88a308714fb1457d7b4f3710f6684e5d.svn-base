var commonSearchTag = "";

/* ********************************* Add/Manage List : Start *********************************  */
function deleteList(){
	$.post("update-list?type="+elementType, $('#elementListForm').serialize(), processUpdateResponse);
}
		
function processUpdateResponse(response){
	if("ERROR" == response.trim().substring(0,5)){
		 showError(response);
	} else
	{
		switch(elementType) {
			case elementTypeSmallMolecule:
				elementWorksetSmallMolecule = response;
				break;
			case elementTypeProtein:
				elementWorksetProtein = response;
				break;
			case elementTypeGene:
				elementWorksetGene = response;
				break;
			case elementTypeCellLine:
				elementWorksetCellLine = response
				break;
			case elementTypePhosphoProteins:
				elementWorksetPhosphoProteins = response;
				break;
			case elementTypeCdna:
				elementWorksetCdna = response;
				break;
			case elementTypeShrnas:
				elementWorksetShrnas = response;
				break;
		}
		processInTab(response);
	}
}

function processInTab(elementWorkSet) {

	var currentWorkSet = elementWorkSet;
	var colSet;
	var aDataSet = "[ ";
	$.ajax( {url: "list-fetcher",
			data: {input: currentWorkSet},
			success:  function(response) {
						var data = response.data;
						for(var i = 0; i < data.length; i++){
							aDataSet += '["<input type = \'checkbox\' name = \'list\' onClick = \'highlightRow(this);\' value = \''+data[i].Id+'\'>'+'","'+ data[i].Name +'"'+',';
							  
							 for(var j = 0; j < response.assayList.length; j++){
								check = 0;
								for(var k = 0; k < data[i].Assays.length; k++){
									if(response.assayList[j] == data[i].Assays[k].AssayName){
										 aDataSet += data[i].Assays[k].count;
										 check = 1;
									}
								}
								if(check == 0)
									aDataSet += "'-'";
								if(j != (response.assayList.length - 1))
											aDataSet += ",";
							}
							aDataSet += " ]";
							if(i != (data.length - 1)){
							   aDataSet += ",";
							}
						}
						aDataSet += " ]";
						colSet = "[ { \"sTitle\": \"Select\"},"+"{ \"sTitle\": \"Name\" }," ;
					   for(var i = 0; i < response.assayList.length; i++){
						   colSet += "{ \"sTitle\": \""+response.assayList[i] +"\"}";
						   if(i != (response.assayList.length - 1))
							   colSet += ",";
					   }
						colSet += "]";	
			},
			complete : function() {
						processResponse(colSet, aDataSet);
			},
			dataType: "json", type: "GET"
	});
}

function processResponse(cSet, dSet) {
	var colSet = cSet;
	var aDataSet=dSet;
	var oTable = $('#' + elementType + 'Table').dataTable( {
							   "aaData": eval(aDataSet),
							   "aoColumns": eval(colSet),
							   "sDom": '<"top"<"clear">>rt<"bottom"<"clear">>',
							   "iDisplayLength": 25,
							   "aLengthMenu": [[25, 50, 100], [25, 50, 100]],
							   "aoColumnDefs": [{ "sWidth": "0px", "aTargets": [ 0 ] }],
							   "bDestroy": true,
							   "bAutoWidth" : false
	}).fnDestroy();
}

 function showError(response){
	$('#message').show();
	setTimeout("$('#message').fadeOut('slow')", 3000);

	if($.trim(response).substr(0,5) == "ERROR")
		$('#message').removeClass().addClass('errorHighlight');
	else
		$('#message').removeClass().addClass('successHighlight');
	
	$('#message').html(response);
}
 
function highlightRow(cbox){
	if(cbox.checked)
			cbox.parentNode.parentNode.setAttribute('style', 'background: #ebffad');
	else
			cbox.parentNode.parentNode.setAttribute('style', 'background: #FFFFFF');
}


function prepareFormSubmit() {

	var filterCompound = [];
	var filterProtein = [];
	var filterGene = [];
	var filterCellLine = [];
	var filterPhosphoProteins = [];
	var filterCdna = [];
	var filterShrnas = [];
	var query = '';

	if ($("#CellLineTable input[type=checkbox]:checked").length > 0 ) { 
		$('#CellLineTable tbody tr').each(function () { 
	        if ($('input[type=checkbox]:checked', this).length) {
	        	filterCellLine.push($(this).find('td:eq(1)').text());
	        }
	    });		
		filterCellLine = filterCellLine.join('" || "');
		filterCellLine = 'CellLineName:("' + filterCellLine
				+ '") ';
		query = query + filterCellLine;	
	}
		
	if ($("#SmallMoleculeTable input[type=checkbox]:checked").length > 0 ) { 
		$('#SmallMoleculeTable tbody tr').each(function () { 
	        if ($('input[type=checkbox]:checked', this).length) {
	        	filterCompound.push($(this).find('td:eq(1)').text());
	        }
	    });		
		filterCompound = filterCompound.join('" || "');
		filterCompound = 'SmallMoleculeName:("'
				+ filterCompound + '") ';
		query = query + filterCompound;
	}

	if ($("#GeneTable input[type=checkbox]:checked").length > 0 ) { 
		$('#GeneTable tbody tr').each(function () { 
	        if ($('input[type=checkbox]:checked', this).length) {
	        	filterGene.push($(this).find('td:eq(1)').text());
	        }
	    });		
		filterGene = filterGene.join('" || "');
		filterGene = 'GeneName:("'
				+ filterGene + '") ';
		query = query + filterGene;
	}
	
	
	if ($("#ProteinTable input[type=checkbox]:checked").length > 0 ) { 
		$('#ProteinTable tbody tr').each(function () { 
	        if ($('input[type=checkbox]:checked', this).length) {
	        	filterProtein.push($(this).find('td:eq(1)').text());
	        }
	    });		
		filterProtein = filterProtein.join('" || "');
		filterProtein = 'ProteinName:("'
				+ filterProtein + '") ';
		query = query + filterProtein;
	}

	if ($("#PhosphoProteinsTable input[type=checkbox]:checked").length > 0 ) { 
		$('#PhosphoProteinsTable tbody tr').each(function () { 
	        if ($('input[type=checkbox]:checked', this).length) {
	        	filterPhosphoProteins.push($(this).find('td:eq(1)').text());
	        }
	    });		
		filterPhosphoProteins = filterPhosphoProteins.join('" || "');
		filterPhosphoProteins = 'PhosphoProteinName:("'
				+ filterPhosphoProteins + '") ';
		query = query + filterPhosphoProteins;
	}		
	
	if ($("#CdnaTable input[type=checkbox]:checked").length > 0 ) { 
		$('#CdnaTable tbody tr').each(function () { 
	        if ($('input[type=checkbox]:checked', this).length) {
	        	filterCdna.push($(this).find('td:eq(1)').text());
	        }
	    });		
		filterCdna = filterCdna.join('" || "');
		filterCdna = 'CdnaName:("'
				+ filterCdna + '") ';
		query = query + filterCdna;
	}
	
	if ($("#ShrnasTable input[type=checkbox]:checked").length > 0 ) { 
		$('#ShrnasTable tbody tr').each(function () { 
	        if ($('input[type=checkbox]:checked', this).length) {
	        	filterShrnas.push($(this).find('td:eq(1)').text());
	        }
	    });		
		filterShrnas = filterShrnas.join('" || "');
		filterShrnas = 'ShRnaName:("'
				+ filterShrnas + '") ';
		query = query + filterShrnas;
	}
	
	$("#searchForm input[name=search]").val(query);
	updateSentValueOnSubmission();
	$("#searchForm").submit();	

}

//update q at start
function updateSentValueOnSubmission() {
	var max = 30; // hardcoded
	var searchTag = '';

	var searchTerm = $("#searchForm input[name=search]").val();
	searchTerm = searchTerm.replace(/\"/g, "&quot;");
	$("#searchForm input[name=search]").val(searchTerm);

	for ( var i = 0; i < max; i++) {
		var inputName = 'tag' + i;
		var input = '#searchForm input[name=\'' + inputName + '\']';
		var inputValue = $(input).val();
		if (inputValue != null) {
			searchTag = searchTag + ' ' + inputValue;
			commonSearchTag = searchTag;
		}
	}
	console.log(searchTag);
	$("#searchForm input[name=q]").val(addAndOperator(searchTerm + searchTag));
	return false;
}

function addAndOperator(qValue){
	if(commonSearchTag != "")
		return "{!lucene q.op=AND df=text}"+qValue;
	else
		return	qValue;
}

/* ********************************* Add/Manage List : End *********************************  */

/* *********************************  Compare List : Start  *****************************/

function compare(){
	if(validateCheckBox()){
		$("#elementListForm").submit();
	}else{
		return false;
	}
}

//checks whether at least one check box is selected for compare 
function validateCheckBox(){
	if($("input[@name='list[]']:checked").length < 1){  
		  $('#message').show();
	      setTimeout("$('#message').hide()", 5000);
	      $('#message').removeClass().addClass('errorHighlight');
	      $('#message').html("ERROR: Please select at least one element to compare");
	      return false;
	}else{
		return true;
	}
}

//load google packages for chart
//google.load('visualization', '1', {'packages':['corechart']});

//Pie Chart:start
function processPieChartResponse(response){
	 var pieChartData = response.results;
	 
	 if(pieChartData.rows.length == 0){
		 displayErrMsg("ERROR: No chart data availble for selected elements");
	 }else{
		 google.setOnLoadCallback(drawPieChart(pieChartData));
	 }
}
function drawPieChart(pieChartData){
	
	var pieData = new google.visualization.DataTable({
		cols: pieChartData.cols,
    	rows: pieChartData.rows
    });

	var pieChart = new google.visualization.PieChart(document.getElementById('pieChart'))
	
	pieChart.draw(pieData, {title:"Element Summary" });
	
}
//Pie Chart:end


//Bar chart:start
var cData = {}, chart;
var pager = { currentPage: 0, countPages: 0, pageSize: 10 };
var options = { title: 'Life',  hAxis: {titleTextStyle: {color: 'red'} , title:'Participant'}, min:0};
var updateFlag = 0 , errorFlag = 0, filterFlag =0;

function processBarChartResponse(response){
	var chartData = response.results;
	if(chartData.rows.length == 0){
		 errorFlag = 1;
		 displayErrMsg("ERROR: No chart data availble for selected elements");
	}else{
		pager.currentPage = 0;  pager.countPages = 0; pager.pageSize = 10;
		options.max = chartData.rows[0].c[1].v; //set Y axis max value
		//options.hAxis.title = chartData.cols[1].id;
		google.setOnLoadCallback(drawBarChart(chartData));
	 }
	displayExperiments(chartData.experiments);
}

//display bar chart
function drawBarChart(chartData){
	cData = new google.visualization.DataTable({
				cols: chartData.cols,
		    	rows: chartData.rows
		    });
	chart = new google.visualization.ColumnChart(document.getElementById('barChart'));
	pager.countPages = Math.ceil(cData.getNumberOfRows() / pager.pageSize);
    
	BarPagination('next');
}

//bar chart pagination
function BarPagination(dir) {
    // moving page number
    if (pager.currentPage == 1 && dir == 'prev') return;
    if (pager.currentPage == pager.countPages && dir == 'next') return;
    
    if (dir == 'next'){
    	pager.currentPage += 1;
    	
    	if(updateFlag == 0 && filterFlag == 0){
    		$.ajax( {url: "compare-table", data: {listArr : trgArr, elementType : elementType , i : pager.currentPage},
    			     success: processCompTableResponse, dataType: "json", type: "GET" } );
    	}else if(updateFlag == 1 && filterFlag == 0){
    		$.ajax( {url: "compare-table", data: {listArr : trgArr, elementType : elementType,  i : pager.currentPage, epid:selectedEp ,tp:selectedEpTime, conc:selectedEpCon},
    			     success: processCompTableResponse, dataType: "json", type: "GET" } );
        }else if(updateFlag == 0 && filterFlag == 1){
        	$.ajax( {url: "filter-table", data: {trgArr : filterTrgArr, compArray : filterCompTypeArr,  elementType : compareType , i : pager.currentPage}, 
        			 success: processCompTableResponse, dataType: "json", type: "GET" } );
        }else if(updateFlag == 1 && filterFlag == 1){
        	$.ajax( {url: "filter-table", data: {trgArr : filterTrgArr, compArray : filterCompTypeArr,  elementType : compareType , i : pager.currentPage,epid:selectedEp ,tp:selectedEpTime, conc:selectedEpCon }, 
   			 success: processCompTableResponse, dataType: "json", type: "GET" } );
        }
    }
    if (dir == 'prev'){
    	pager.currentPage -= 1;
    	if(updateFlag == 0 && filterFlag == 0){
    		$.ajax( {url: "compare-table", data: {listArr : trgArr, elementType : elementType , i : pager.currentPage},
    			     success: processCompTableResponse, dataType: "json", type: "GET" } );
    	}else if(updateFlag == 1 && filterFlag == 0){
    		$.ajax( {url: "compare-table", data: {listArr : trgArr, elementType : elementType,  i : pager.currentPage, epid:selectedEp ,tp:selectedEpTime, conc:selectedEpCon},
    			     success: processCompTableResponse, dataType: "json", type: "GET" } );
        }else if(updateFlag == 0 && filterFlag == 1){
        	$.ajax( {url: "filter-table", data: {trgArr : filterTrgArr, compArray : filterCompTypeArr,  elementType : compareType , i : pager.currentPage}, 
        			 success: processCompTableResponse, dataType: "json", type: "GET" } );
        }else if(updateFlag == 1 && filterFlag == 1){
        	$.ajax( {url: "filter-table", data: {trgArr : filterTrgArr, compArray : filterCompTypeArr,  elementType : compareType , i : pager.currentPage,epid:selectedEp ,tp:selectedEpTime, conc:selectedEpCon }, 
   			 success: processCompTableResponse, dataType: "json", type: "GET" } );
        }
    }
    
    var cloneData = cData.clone();
    var numOfRows = cloneData.getNumberOfRows();
    // validate existance of data for pager, and remove rows
    if (numOfRows > 0 && pager.currentPage <= pager.countPages) {
        // remove previous rows 
    	cloneData.removeRows(0, (pager.currentPage - 1) * pager.pageSize);
        // remove next rows 
    	cloneData.removeRows(pager.pageSize, cloneData.getNumberOfRows() - pager.pageSize);
    }

    // filling empty values for rest of last page
    numOfRows = cloneData.getNumberOfRows();
    if (numOfRows < pager.pageSize)
    {
        for (var i = numOfRows; i < pager.pageSize; i++) {
        	cloneData.addRow(['', 0]);
        }
    }
    chart.draw(cloneData, options);
    $("#prev").html("Prev");
	$("#next").html("Next");
}

//get experiments drop down
function displayExperiments(experiments){
	var expSet = "<select  id = \"expList\"  style = \"width:200px \" onChange = \"ajaxCall('endpoint-fetcher');\"><option value = \"default\" >-- Select Experiments --</option>";

	for(var i = 0; i < experiments.length; i++){
		expSet += "<option value = \""+ experiments[i]+"\">"+experiments[i]+"</option>";
	}
	expSet += "</select><br>";
		
	$("#experiments").html(expSet);
	
}

//get end points drop down
function displayEpoints(response){
	var endpoints = response.results.endpoints;
	
    var epSet = "<select   id = \"epList\" style = \"width:200px \" onChange = \"ajaxCall('condition-fetcher');\" ><option value = \"default\">-- Select End Point --</option>";
	for(var i = 0; i < endpoints.length; i++){
		epSet += "<option value = \""+ endpoints[i].EpId+"\">"+endpoints[i].Endpointname+"</option>";
	}
	epSet += "</select><br>"; 
	
	$("#endPoints").html(epSet);
}

//get time and concentration drop downs
function displayEpConditions(response){
	var conditions = response.results;
	
	var epTimeSet = "<select   id = \"epTimeList\"  style = \"width:200px \"><option value = \"default\">-- Select Time Point --</option>";
	for(var i = 0; i < conditions.timepoint.length; i++){
		epTimeSet += "<option value = \""+ conditions.timepoint[i]+"\">"+conditions.timepoint[i]+"</option>";
	}
	epTimeSet += "</select><br>"; 
	
	
	var epConSet = "<select id = \"epConList\" style = \"width:200px \"><option value = \"default\">-- Select Concentration --</option>";
	for(var i = 0; i < conditions.concentration.length; i++){
		epConSet += "<option value = \""+ conditions.concentration[i]+"\">"+conditions.concentration[i]+"</option>";
	}
	epConSet += "</select><br>";
		
	$("#epTime").html(epTimeSet);
	$("#epConcentration").html(epConSet);
	$("#submit").html('<div id = "contextMenu"><a href = "#"   class = "cMenu manage"   style="float:left;" title = "Submit" onclick="updateBarChart();">Submit</a><div>');

}

//update bar chart with selected experiment,endpoint,time and concentration
var selectedExp,selectedEp,selectedEpTime,selectedEpCon;

function updateBarChart(){
	updateFlag = 1;
	selectedExp = $('#expList :selected').val();
	selectedEp = $('#epList :selected').val();
	selectedEpTime = $('#epTimeList :selected').val();
	selectedEpCon = $('#epConList :selected').val();
	
	//drop down error handling
	if(selectedEp == "default")
		displayErrMsg("ERROR: Please select End Point");
	else if(selectedEpTime =="default" || selectedEpCon == "default")
		displayErrMsg("ERROR: Please select Time Point/Concentration");
	else{
		    if(filterFlag == 0){
				$.ajax( {url: "compare-barchart", data: {listArr : trgArr, elementType : elementType, epid:selectedEp ,tp:selectedEpTime, conc:selectedEpCon}, 
					     success: processBarChartResponse, dataType: "json", type: "GET" } );
				$.ajax( {url: "compare-piechart", data: {listArr : trgArr, elementType : elementType, epid:selectedEp ,tp:selectedEpTime, conc:selectedEpCon}, 
			             success: processPieChartResponse, dataType: "json", type: "GET" } );
		    }else if(filterFlag == 1){
		    	 $.ajax( {url: "filter-barchart", data: {trgArr : filterTrgArr, compArray : filterCompTypeArr ,elementType : compareType, epid:selectedEp ,tp:selectedEpTime, conc:selectedEpCon}, 
		    		      success: processBarChartResponse, dataType: "json", type: "GET" } );
				 $.ajax( {url: "filter-piechart", data: {trgArr : filterTrgArr, compArray : filterCompTypeArr , elementType : compareType, epid:selectedEp ,tp:selectedEpTime, conc:selectedEpCon}, 
					      success: processPieChartResponse, dataType: "json", type: "GET" } );

		    }
		}
	}

function ajaxCall(servletName){
	
	if(servletName == "endpoint-fetcher"){
		var ename = $("#expList").val();
		$.ajax( {url: servletName, data: {Ename : ename}, success: displayEpoints, dataType: "json", type: "GET" } );
			
	}else if(servletName == "condition-fetcher"){
		var epid = $("#epList").val();
		if(filterFlag ==0)
			$.ajax( {url: servletName, data: {epid:epid,listArr:trgArr}, success: displayEpConditions, dataType: "json", type: "GET" } );
		else
			$.ajax( {url: servletName, data: {epid:epid,listArr:filterTrgArr}, success: displayEpConditions, dataType: "json", type: "GET" } );
		
	}
}

function displayErrMsg(error){
	 $('#message').show();
	 setTimeout("$('#message').fadeOut('slow')", 4000);
	 $('#message').removeClass().addClass('errorHighlight');
	 $('#message').html(error);
}

function displaySucessMsg(error){
	 $('#message').show();
	 setTimeout("$('#message').fadeOut('slow')", 4000);
	 $('#message').removeClass().addClass('successHighlight');
	 $('#message').html(error);
}
//Bar chart:end


//Bar chart Table :start
var compareType;
function processCompTableResponse(response){
	var tempK,matchFound =0;

	var data =  response.data;	
	var xList = response.XList.elem;	
	var yList = response.YList;
	var cType = response.XList.compareType;
    
	if(cType == "Small molecule"){
    	compareType = "SmallMolecule";
    }else{
    	compareType = cType;
    }
	var table = "<TABLE class=comparetable ><TR><TD></TD>";
    var rowSet;
    
    for(var i=0; i< xList.length; i++){
    	table += "<TH>"+
    				"<input type=checkbox name=compareList value = \""+ xList[i].id+"\">"+
    				 xList[i].name+ 
    			"</TH>";
    }
    table += "</TR>";
    
    for(var i = 0; i < data.length; i++){ 
        table += "<TR><TH>"+yList[i]+ "</TH>";
    	
    	for(var j = 0; j < xList.length; j++){
    		check = 0;
    		
    		for(var k = 0; k < data[i].combi.length; k++){
    			if(xList[j].name == data[i].combi[k].xEle){
    					table +=  "<TD>"+data[i].combi[k].value;
					
						tempK = k+1;
						for(var m=tempK; m < data[i].combi.length; m++){
							if(xList[j].name == data[i].combi[m].xEle){
								table +=  "/" + data[i].combi[m].value ;
								k++;
							}
						}
						table += "</TD>";
						check = 1;
		        }
    	    }//end for loop
		    if(check == 0)
		       table += "<TD>"+ "-" + "</TD>";
			    
    	}
    	table += "</TR>";
	 }
     table += "</TABLE>";
     
     $('#dynamicTable').html(table);
     $('#addToList').html('<div id = "contextMenu"><a href = "#"   class = "cMenu manage"   title = "Add To List"  style="float:left" onclick="$(\'#compareMode\').val(\'add\');addFilterList(\'add\');">Add to List</a><div>');
     $('#manageList').html(' <div id = "contextMenu"><a href = "element-list?elementType=SmallMolecule" class = "cMenu manage" title = "Manage List"  target="_blank">Manage List</a><div>');
     $('#filterByList').html(' <div id = "contextMenu"><a href = "#" class = "cMenu manage" title = "Filter By List"  onclick="$(\'#compareMode\').val(\'filter\');addFilterList(\'filter\');">Filter By List</a><div>');
}

function addFilterList(mode){
	$.post("update-complist?compareType="+compareType+"&elementType="+elementType, $('#compareForm').serialize(),processAddToList);
}

var filterTrgArr , filterCompTypeArr;
function processAddToList(response){
	if("ERROR" == response.trim().substring(0,5)){
		displayErrMsg(response);
	}else if("ADD" == response.trim().substring(0,3)){
		displaySucessMsg(response.trim().substring(3))
	}else if("ADD" != response.trim().substring(0,3)){
			filterFlag = 1; updateFlag = 0;
			
			var responseList = response.split(";");
			filterTrgArr = responseList[0].trim();
			filterCompTypeArr = responseList[1].trim();
			
		    $.ajax( {url: "filter-barchart", data: {trgArr : filterTrgArr, compArray : filterCompTypeArr ,elementType : compareType}, success: processBarChartResponse, dataType: "json", type: "GET" } );
			$.ajax( {url: "filter-piechart", data: {trgArr : filterTrgArr, compArray : filterCompTypeArr , elementType : compareType}, success: processPieChartResponse, dataType: "json", type: "GET" } );
	}
		
}
//Bar chart Table :end
/* Compare List : End */

$(function() { 
	function activateTab( obj ) {
		  $( obj ).parents( '.tabs_group' ).find( '.tab_item' ).removeClass( 'active' );
		  $( obj ).addClass( 'active' );
		  $( obj ).parents( '.tabs_group' ).find( '.tab_section' ).hide( ).removeClass( 'active' ).css({
			  'display':'none',
			  'opacity':'0'
		  });
		  $( obj ).parents( '.tabs_group' ).find( '.tab_section#' + elementType ).css({ 
			  'display':'block' 
		  }).animate({
				  'opacity':'1'
		  }, 100 ).addClass( 'active' );
	}	
	$( "#tab_items" ).empty();
    if(elementWorksetSmallMolecule.length > 0){
		$("#tab_items").append('<a href="#" rel="SmallMolecule" id="tab_item_sm" class="tab_item">Compound</a>' );
		$("#tabs_group").append('<div class="tab_section" id="SmallMolecule"> <div class="clearfix prettycheckbox labelright"><div class="span12" id = "dynamicSmallMolecule"><table cellpadding="0" cellspacing="0" border="0" class="display" id="SmallMoleculeTable"></table></div></div></div>');
    } 
    if(elementWorksetProtein.length > 0) {
		$("#tab_items").append('<a href="#" rel="Protein" id="tab_item_protein" class="tab_item">Protein</a>');
		$("#tabs_group").append('<div class="tab_section " id="Protein"><div class="clearfix prettycheckbox labelright"><div class="span12" id = "dynamicProtein"><table cellpadding="0" cellspacing="0" border="0" class="display" id="ProteinTable"></table></div></div></div>');
	}
    if(elementWorksetGene.length > 0){
		$("#tab_items").append('<a href="#" rel="Gene" id="tab_item_gene" class="tab_item">Gene</a>');
		$("#tabs_group").append('<div class="tab_section " id="Gene"><div class="clearfix prettycheckbox labelright"><div class="span12" id = "dynamicGene"><table cellpadding="0" cellspacing="0" border="0" class="display" id="GeneTable"></table></div></div></div>');
	}
    if(elementWorksetCellLine.length > 0){
		$("#tab_items").append('<a href="#" rel="CellLine" id="tab_item_cellline" class="tab_item">Cell line</a>');
		$("#tabs_group").append('<div class="tab_section " id="CellLine"><div class="clearfix prettycheckbox labelright"><div class="span12" id = "dynamicCellLine"><table cellpadding="0" cellspacing="0" border="0" class="display" id="CellLineTable"></table></div></div></div>');
	}
    if(elementWorksetPhosphoProteins.length > 0){
		$("div#tab_items").append('<a href="#" rel="PhosphoProteins" id="tab_item_pp" class="tab_item">Phospho Proteins</a>');
		$("#tabs_group").append('<div class="tab_section " id="PhosphoProteins"><div class="clearfix prettycheckbox labelright"> <div class="span12" id = "dynamicPhosphoProteins"><table cellpadding="0" cellspacing="0" border="0" class="display" id="PhosphoProteinsTable"></table></div></div></div>');
	}
    if(elementWorksetCdna.length > 0){ 
		$("div#tab_items").append('<a href="#" rel="Cdna" id="tab_item_cdna" class="tab_item">Complementary DNAs</a>');
		$("#tabs_group").append('<div class="tab_section " id="Cdna"><div class="clearfix prettycheckbox labelright"><div class="span12" id = "dynamicCdna"><table cellpadding="0" cellspacing="0" border="0" class="display" id="CdnaTable"></table></div></div></div>');
	}
    if(elementWorksetShrnas.length > 0){
		$("div#tab_items").append('<a href="#" rel="Shrnas" id="tab_item_shrnas" class="tab_item">Short Hairpin RNAs</a>');
		$("#tabs_group").append('<div class="tab_section " id="Shrnas"><div class="clearfix prettycheckbox labelright"><div class="span12" id = "dynamicShrnas"><table cellpadding="0" cellspacing="0" border="0" class="display" id="ShrnasTable"></table></div></div></div>');
	}

    if(elementWorksetSmallMolecule.length > 0){
        $('#elementType').val('SmallMolecule');
        processInTab(elementWorksetSmallMolecule);
        smVisited = true;
        activateTab( '#tab_item_sm' );
    } 
    else
    if(elementWorksetProtein.length > 0) {
		$('#elementType').val('Protein');
		elementType = 'Protein';
		pVisited = true;
		processInTab(elementWorksetProtein);
		activateTab( '#tab_item_protein' );
	}
    else
    if(elementWorksetGene.length > 0){
		$('#elementType').val('Gene');
		gVisited = true;
		elementType = 'Gene';
		processInTab(elementWorksetGene);
		activateTab( '#tab_item_gene' );
	}
    else
    if(elementWorksetCellLine.length > 0){
		$('#elementType').val('CellLine');
		clVisited = true;
		elementType='CellLine';
		processInTab(elementWorksetCellLine);
		activateTab( '#tab_item_cellline' );
	}
    else
    if(elementWorksetPhosphoProteins.length > 0){
		$('#elementType').val('PhosphoProteins');
		ppVisited = true;
		elementType='PhosphoProteins';
		processInTab(elementWorksetPhosphoProteins);
		activateTab( '#tab_item_pp' );
	}
    else
    if(elementWorksetCdna.length > 0){ 
		$('#elementType').val('Cdna');
		cdnaVisited = true;
		elementType='Cdna';
		processInTab(elementWorksetCdna);
		activateTab( '#tab_item_cdna' );
	}
    else
    if(elementWorksetShrnas.length > 0){
		$('#elementType').val('Shrnas');
		shVisited = true;
		elementType='Shrnas';
		processInTab(elementWorksetShrnas);
		activateTab( '#tab_item_shrnas' );
	}    
   
    
	$( '.tabs_nav .tab_item' ).bind( 'click', function( e ) {
		  if ( ! $( this ).hasClass( 'active' ) )
		  {
				  elementType = $( this ).attr( 'rel' ); 
				  switch(elementType) { 
					case elementTypeProtein:
						if(elementWorksetProtein.length > 0 && pVisited == false) {
							$('#elementType').val('Protein');
							pVisited = true;
							processInTab(elementWorksetProtein);
						}
						break;
					case elementTypeGene:
						if(elementWorksetGene.length > 0 && gVisited == false){
							$('#elementType').val('Gene');
							gVisited = true;
							processInTab(elementWorksetGene);
						} 
						break;
					case elementTypeCellLine:
						if(elementWorksetCellLine.length > 0 && clVisited == false){
							$('#elementType').val('CellLine');
							clVisited = true;
							processInTab(elementWorksetCellLine);
						} 
						break;
					case elementTypePhosphoProteins:
						if(elementWorksetPhosphoProteins.length > 0 && ppVisited == false){
							$('#elementType').val('PhosphoProteins');
							ppVisited = true;
							processInTab(elementWorksetPhosphoProteins);
						} 
						break;
					case elementTypeCdna:
						if(elementWorksetCdna.length > 0 && cdnaVisited == false){
							$('#elementType').val('Cdna');
							cdnaVisited = true;
							processInTab(elementWorksetCdna);
						} 
						break;
					case elementTypeShrnas:
						if(elementWorksetShrnas.length > 0 && shVisited == false){
							$('#elementType').val('Shrnas');
							shVisited = true;
							processInTab(elementWorksetShrnas);
						} 
						break;
				  } 
				  activateTab( this );
		  }
		  e.preventDefault( );
	});
	
});