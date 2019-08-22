<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>LIFE.wrx Search Results </title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
	<%@ include file = "require.jsp" %>
	<%@ page import="java.util.ArrayList" %>
	<%@ page import="java.util.Arrays" %>
	<%@ page import="java.util.List" %>
	<%@page import="java.util.*" %>
	
	<script type="text/javascript" language="javascript" src="/life/web/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="/life/web/js/jquery.getUrlParam.js"></script>
	<script type="text/javascript" src="/life/web/js/search.js"></script>
	<!-- Le styles -->
	<link href="/life/web/css/summary-table.css" rel="stylesheet">
	<link href="/life/web/css/search.css" rel="stylesheet">
	
	<script src="/life/web/js/crossFilter/d3.v2.js"></script>
	<script src="/life/web/js/crossFilter/dc.js"></script>
	<link rel="stylesheet" href="/life/web/css/dc.css">
	<link href="/life/web/css/hopscotch.min.css" rel="stylesheet"></link>  <!-- By JP -->
	<script type="text/javascript" src="/life/web/js/crossFilter/crossfilter.js"></script>
	<link rel="stylesheet" href="/life/web/css/viewData.css">
	<script type="text/javascript" src="/life/web/js/viewData.js"></script>
	<script type="text/javascript" src="/life/web/js/viewDataCrossFilter.js"></script>
</head>

<body>
<%@ include file = "nav.jsp" %>

<div class="container padding-top-xxl">	
	<%@ include file = "search-bar.jsp" %>	
	<div class = "row-fluid align-left padding-bottom-m">	
		<div class="span2" id = "left-tabs">
			
			Result Categories:
			<div id = "tabHolder" class = "span12"></div>
			<form id = "worksetForm">
				<input type = "hidden" id = "type" name = "type">
				<input type = "hidden" id = "mode" name = "mode">
				<input type = "hidden" id = "itemId" name = "list">
			</form>

			<div id="tabFilter" class="span12">
				<div id="filterSelection"><a id="selectFilterId">Select Filters:</a></div>
				<img id= 'filter-preloader' src = '/life/web/images/preloader.gif' style="display: none">
				<div id="listFilter" style="display: none">
					<select id="filterCellLine" multiple>
					</select> <select id="filterCompound" multiple>
					</select> 	<select id="filterGene" multiple> 
					</select> <select id="filterProtein" multiple>
					</select>
					<button class="btn btn-small filter-button"><i class="icon-filter"></i> Filter</button>
				</div>
			</div>
			<a href = "#"  id="viewDataLink" onClick = "reset();fetchViewData();">View Data</a>

			<div id = "dataHolder" class = "span12"></div>
		</div>
		<div class = "span10" id = "resultsContainer">
			<div class = "span12" id = "resultsHeader">
				<div class="span5">
					<a class = 'indexTabLink' id='constituentsIndex' href = '#' onclick = 'toggleIndex("participant")'>Constituents</a>
					<a class = 'indexTabLink' id='perturbationIndex' href = '#' onclick = 'toggleIndex("perturbation")'>Perturbations</a>
					<input type="hidden" name="selectedIndexLinkVal" id="selectedIndexLinkVal" value="participant">
				</div>
	
				<div class = "span3">
					<div id="pagination_bt" >
						<span id = "currPage"></span>
						<a class="btn btn-small" href="#" onClick = "loadPage('prev');"><i class="icon-chevron-left"></i> Prev</a>
						<a class="btn btn-small" href="#" onClick = "loadPage('next');">Next <i class="icon-chevron-right"></i></a>
					</div>
				</div>
	
				<div class = "span3 text-right">
					<a class="btn btn-small" id="viewListButton" href="element-list?elementType=SmallMolecule" style="display:none"><i class="icon-list"></i> View List</a>
					<a id = "download" onClick="_gaq.push(['_trackEvent', 'Downloads', 'Results', '<%=request.getParameter("q") %>']);" class="btn btn-small" href="download?input=<%=request.getParameter("q") %>"><i class="icon-download"></i> Download</a>
				</div>
				<div class = "span1"> <!-- By JP -->
			  		<p><img src="/life/web/images/tour_btn-tourBlue.png" id="startTourBtn"/></p> 
				</div>
			</div>
			
			<div class="span12" id = "dynamic"></div>
			<div class="span12" id = "noResultMsg"></div>
			
			<div id="viewData"></div>
		</div>
	</div>
</div> <!-- /container -->
	
<%@ include file = "footer-search.jsp" %>

	<!-- Le javascript
	================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->

	<script type="text/javascript">
	//get ids of cellLines, compounds, ... from session
	
	<%
			out.print("var cellLineList = \""+(List)session.getAttribute("CellLineList")+"\";");
			out.print("var compoundList = \""+(List)session.getAttribute("SmallMoleculeList")+"\";");
			out.print("var geneList = \""+(List)session.getAttribute("GeneList")+"\";");
			out.print("var proteinList = \""+(List)session.getAttribute("ProteinList")+"\";");
			out.print("var shrnasList = \""+(List)session.getAttribute("ShrnasList")+"\";");
			out.print("var phosphoproteinsList = \""+(List)session.getAttribute("PhosphoProteinsList")+"\";");
	%>		
	
		var load = "<%=request.getParameter("load") %>";
		$("#load").val(load);
		var type = load;
		var path = "http://lincs.ccs.miami.edu:8080/";
		var url = path + "solr-example/select";
		var qHolder = "<%=request.getParameter("q") %>";
		var index = "<%=request.getParameter("index") %>";
		var searchTerm = qHolder.split('&quot;').join('"');
		var searchTermForErrorMsg = "<%=request.getParameter("search") %>";
		
	/*	var qTerm= $(document).getUrlParam('q');
		qTerm.replace(/"/g, '\\"');
		$("#searchForm input[name=q]").val();
	*/	
		var elemId,signature;
		var startIndex = 0, noOfRows = 20 ,start = 0;
		var selectedAssay,totalRecords;
		var pagingMode = "tabs";
		var listOfResultCategories = [];
		
		$(function() {
			$("#download").attr('href','list-download?input='+searchTerm+'&mode='+$("#load").val());
			$("#download").click(function() {
				$("#download").html("<i class='icon-time'></i>Downloading...");
				setTimeout(function(){$("#download").html("<i class='icon-download'></i>Download");},5000);
			});

			$(".indexTabLink").click(function() {
				$(".indexTabLink").removeClass("active");
				$(this).addClass("active");
			});
			
			getIndex();
			loadFacetCounts();
		});
		
		function reset()
		{
			startIndex = 0;
			$("#start").val(0);
		}
		
		function toggleIndex(selectedIndxVal)
		{	
			reset();
			$("#selectedIndexLinkVal").val(selectedIndxVal);
			var removeField = 'input[name=indexVal]';
			$(removeField).remove();
			
			var indexVar = '<input type=\'hidden\' name=\'indexVal' + '\' value=\''+ $("#selectedIndexLinkVal").val()+ '\' />';
			$('#searchForm').append(indexVar);
			
			if($("#selectedIndexLinkVal").val() == "perturbation"){
				url = path +"/solr-example/select";
			}else {
				url = path + "/participant-solr/select";
			}
			loadFacetCounts();
		}
		
		function getIndex()
		{
			if($("#selectedIndexLinkVal").val() == "participant")
				url = path + "/participant-solr/select";
			else
				url = path +"/solr-example/select";
			return url;
		}
		
		function filterResults()
		{
			var txt = $('#filter').val();
			$('.resultElement').each(function(){
				$(this).hide();
			   if($(this).text().toUpperCase().indexOf(txt.toUpperCase()) != -1){
			       $(this).show();
			   }

			});
		}
		
		function loadPage(action) {
			if (startIndex == 0 && action == 'prev') return;
		    if ((startIndex+noOfRows) >= totalRecords  && action == 'next') return;
		    
			if(action == "next")
				startIndex += noOfRows;
			else if(action == "prev")
				startIndex -= noOfRows;
			
			$("#start").val(startIndex);
			
			if(pagingMode == "data"){
				if(selectedAssay == "L1000 transcriptional profiling assay")
					loadSignatureData(startIndex);
				else
					loadAssayData(selectedAssay,startIndex);
			}
			else
			{
				if($("#load").val() == "AssayTypeName"){
					loadAssays();
				}
				if($("#type").val() == "SmallMolecule")
					loadCompounds();
				if($("#type").val() == "Protein")
					loadProteins();
				if($("#type").val() == "Gene")
					loadGenes();
				if($("#type").val() == "CellLine")
					loadCellLines();
				if($("#type").val() == "Cdnas")
					loadCdnas();
				if($("#type").val() == "Shrnas")
					loadShRnas();
				if($("#type").val() == "PhosphoProteins")
					loadPhosphoProteins();
				if($("#type").val() == "Antibodies")
					loadAntibodies();
				if($("#type").val() == "NonKinaseProteins")
					loadNonkinaseproteins();
			}
		}
		
		function loadFacetCounts()
		{	
			listOfResultCategories = [];
			$("#group").val("false");
			$("#facet").val("true");
			$("#tabHolder").html("<img class = 'preloader' src = '/life/web/images/preloader.gif'>");
			$.ajax( {url: url, data: $("#searchForm").serialize(), success: function(response) {
				var tabString = "<ul>";
				response = eval(response);
				var totalCount = 0;
				
				if(response.facet_counts.facet_fields["ProteinId"].length > 0)
				{
					tabString += "<li><a class = 'tabLink' id='proteinLink' href = '#' onClick = 'reset();loadProteins();onClickTagsTime();'>Kinase proteins ["+(response.facet_counts.facet_fields["ProteinId"].length/2)+"]</a></li>";
					totalCount += response.facet_counts.facet_fields["ProteinId"].length/2;
					listOfResultCategories.push("Protein");
				}
				if(response.facet_counts.facet_fields["SmallMoleculeId"].length > 0)
				{
					tabString += "<li><a class = 'tabLink' id='compoundLink' href = '#' onClick = 'reset();loadCompounds();onClickTagsTime();'>Small molecules ["+(response.facet_counts.facet_fields["SmallMoleculeId"].length/2)+"]</a></li>";
					totalCount += response.facet_counts.facet_fields["SmallMoleculeId"].length/2;
					listOfResultCategories.push("SmallMolecule");
				}
				if(response.facet_counts.facet_fields["PhosphoProteinId"].length > 0)
				{
					tabString += "<li><a class = 'tabLink' id='phosphoProteinsLink' href = '#' onClick = 'reset();loadPhosphoProteins();onClickTagsTime();'>Phospho Proteins ["+(response.facet_counts.facet_fields["PhosphoProteinId"].length/2)+"]</a></li>";
					totalCount += response.facet_counts.facet_fields["PhosphoProteinId"].length/2;
					listOfResultCategories.push("PhosphoProteins");
				}
				if(response.facet_counts.facet_fields["ShRnaID"].length > 0)
				{
					tabString += "<li><a class = 'tabLink' id='shRnaLink' href = '#' onClick = 'reset();loadShRnas();onClickTagsTime();'>Short Hairpin RNAs ["+(response.facet_counts.facet_fields["ShRnaID"].length/2)+"]</a></li>";
					totalCount += response.facet_counts.facet_fields["ShRnaID"].length/2;
					listOfResultCategories.push("Shrnas");
				}
				if(response.facet_counts.facet_fields["CdnaID"].length > 0)
				{
					tabString += "<li><a class = 'tabLink' id='cDnaLink' href = '#' onClick = 'reset();loadCdnas();onClickTagsTime();'>Complementary DNAs ["+(response.facet_counts.facet_fields["CdnaID"].length/2)+"]</a></li>";
					totalCount += response.facet_counts.facet_fields["CdnaID"].length/2;
					listOfResultCategories.push("Cdnas");
				}
				if(response.facet_counts.facet_fields["AntibodyId"].length > 0)
				{
					tabString += "<li><a class = 'tabLink' id='antibodyLink' href = '#' onClick = 'reset();loadAntibodies();onClickTagsTime();'>Antibodies ["+(response.facet_counts.facet_fields["AntibodyId"].length/2)+"]</a></li>";
					totalCount += response.facet_counts.facet_fields["AntibodyId"].length/2;
					listOfResultCategories.push("Antibodies");
				}
				if(response.facet_counts.facet_fields["NonKinaseProteinId"].length > 0)
				{
					tabString += "<li><a class = 'tabLink' id='nonkinaseproteinsLink' href = '#' onClick = 'reset();loadNonkinaseproteins();onClickTagsTime();'>Detected proteins ["+(response.facet_counts.facet_fields["NonKinaseProteinId"].length/2)+"]</a></li>";
					totalCount += response.facet_counts.facet_fields["NonKinaseProteinId"].length/2;
					listOfResultCategories.push("NonKinaseProteins");
				}
				if(response.facet_counts.facet_fields["LigandProteinId"].length > 0)
				{
					tabString += "<li><a class = 'tabLink' id='ligandLink' href = '#' onClick = 'reset();loadLigands();onClickTagsTime();'>Protein ligands ["+(response.facet_counts.facet_fields["LigandProteinId"].length/2)+"]</a></li>";
					totalCount += response.facet_counts.facet_fields["LigandProteinId"].length/2;
					listOfResultCategories.push("Ligands");
				}
				if(response.facet_counts.facet_fields["CellLineId"].length > 0)
				{
					tabString += "<li><a class = 'tabLink' href = '#' id='cellLink' onClick = 'reset();loadCellLines();onClickTagsTime();'>Cells ["+(response.facet_counts.facet_fields["CellLineId"].length/2)+"]</a></li>";
					totalCount += response.facet_counts.facet_fields["CellLineId"].length/2;
					listOfResultCategories.push("CellLine");
				}
				if(response.facet_counts.facet_fields["GeneId"].length > 0)
				{
					tabString += "<li><a class = 'tabLink' href = '#' id='geneLink' onClick = 'reset();loadGenes();onClickTagsTime();'>Transcribed genes (L1000) ["+(response.facet_counts.facet_fields["GeneId"].length/2)+"]</a></li>";
					totalCount += response.facet_counts.facet_fields["GeneId"].length/2;
					listOfResultCategories.push("Gene");
				}
				if(response.facet_counts.facet_fields["AssayTypeName"].length > 0)
				{
					tabString += "<li><a class = 'tabLink' id='assayLink' href = '#' onClick = 'reset();loadAssays();onClickTagsTime();'>Bioassays ["+(response.facet_counts.facet_fields["AssayTypeName"].length/2)+"]</a></li>";
					totalCount += response.facet_counts.facet_fields["AssayTypeName"].length/2;
					listOfResultCategories.push("AssayTypeName");
				}

				tabString += "</ul>"
				$("#tabHolder").html(tabString);
				$("#resultsCount").html("Total Results: "+totalCount);
				$(".tabLink").click(function() {
					$(".tabLink").removeClass("active");
					$(this).addClass("active");
				});
			},
			complete:function(){
				loadResultCategory(listOfResultCategories);
			}, 
			dataType: "jsonp", type: "GET", 'jsonp': 'json.wrf'});
			
			$("#group").val("true");
			$("#facet").val("false");
		}
		
		function loadLigands()
		{
			
			loadTags('Protein');
			onClickTags();
			$("#dynamic").html("<img class = 'span6 preloader' src = '/life/web/images/preloader.gif'>");
			$("#type").val("Ligands");
			$("#mode").val("add");
			$('#load').val('Ligands');
			$("#download").attr('href','list-download?input='+searchTerm+'&mode='+$("#load").val());
			$("#groupField").val("LigandProteinId");
			isSorted = "false";
			$("#viewData").html("");
			
			
			$.ajax( {url: url, data: $("#searchForm").serialize(), success: function(response) {

				response = eval(response);

				var groups = response.grouped.LigandProteinId.groups;	
				totalRecords = response.grouped.LigandProteinId.ngroups;
				$("#currPage").text("Page "+ ((startIndex/noOfRows)+1)+ "/"+Math.ceil(totalRecords/noOfRows));
				$("#dynamic").html("");
				for(var i = 0; i < groups.length; i++)
				{		
					if(groups[i].groupValue != null)
					{
						var params= '&'+document.URL.split('?')[1];
						var resultDetail = "";

						
						resultDetail += "<div class = 'row-fluid res-row'><div class = 'resultElement span12' id = 'result"+i+"'>";
							
						resultDetail += "<div class = 'span12 resHead'><div class = 'span6 resultTitle'><a title = 'Add to List' class = 'tool add' href = 'javascript:void(0)' onClick = 'onAddToListClick();$(\"#itemId\").val(\""+groups[i].doclist.docs[0].LigandProteinId+"\");$(this).parent().parent().parent().css(\"background-color\", \"#ffffb7\");editList();'> </a><a href= 'summary?mode=ligand&input="+groups[i].doclist.docs[0].LigandProteinId+params+"'>"+groups[i].doclist.docs[0].NonProteinName+"</a></div><div class = 'span6 resultCaption'>"+groups[i].doclist.docs[0].NonProteinGeneSymbol+"</div></div>";
						resultDetail += "<div class = 'span12 resSub'><div class = 'span10'>";
						
						resultDetail += "<div class = 'row-fluid'>Description: "+groups[i].doclist.docs[0].NonProteinName+"</div>";
						resultDetail += "<div class = 'row-fluid'>Organism: "+"Homs sapiens (Human)"+"</div>";

						if(groups[i].doclist.docs[0].NonProteinUniProtId != ""){
							resultDetail += "</div>";
							resultDetail += "<div class = 'span2 resLinks'><div class = 'row-fluid'>Links</div><div class = 'row-fluid'>";
							resultDetail += "<a class = 'tool extLink uniprot' href=\"http://www.uniprot.org/uniprot/"+groups[i].doclist.docs[0].NonProteinUniProtId+"\" target=\"_blank\" title = 'View on Uniprot'>U</a>";
							resultDetail += "</div></div>";
						}
											
						resultDetail += "</div></div>";
						$("#dynamic").append(resultDetail);
					}
				}
			$(".tool").tooltip();
			pagingMode = "tabs";
			}, dataType: "jsonp", type: "GET", 'jsonp': 'json.wrf'});
		}
		function loadNonkinaseproteins()
		{
			
			loadTags('Protein');
			onClickTags();
			$("#dynamic").html("<img class = 'span6 preloader' src = '/life/web/images/preloader.gif'>");
			$("#type").val("NonKinaseProteins");
			$("#mode").val("add");
			$('#load').val('NonKinaseProteins');
			$("#download").attr('href','list-download?input='+searchTerm+'&mode='+$("#load").val());
			$("#groupField").val("NonKinaseProteinId");
			isSorted = "false";
			$("#viewData").html("");
			
			
			$.ajax( {url: url, data: $("#searchForm").serialize(), success: function(response) {

				response = eval(response);

				var groups = response.grouped.NonKinaseProteinId.groups;	
				totalRecords = response.grouped.NonKinaseProteinId.ngroups;
				$("#currPage").text("Page "+ ((startIndex/noOfRows)+1)+ "/"+Math.ceil(totalRecords/noOfRows));
				$("#dynamic").html("");
				for(var i = 0; i < groups.length; i++)
				{		
					if(groups[i].groupValue != null)
					{
						var params= '&'+document.URL.split('?')[1];
						var resultDetail = "";

						
						resultDetail += "<div class = 'row-fluid res-row'><div class = 'resultElement span12' id = 'result"+i+"'>";
							
						resultDetail += "<div class = 'span12 resHead'><div class = 'span6 resultTitle'><a title = 'Add to List' class = 'tool add' href = 'javascript:void(0)' onClick = 'onAddToListClick();$(\"#itemId\").val(\""+groups[i].doclist.docs[0].NonKinaseProteinId+"\");$(this).parent().parent().parent().css(\"background-color\", \"#ffffb7\");editList();'> </a><a href= 'summary?mode=NonKinaseProtein&input="+groups[i].doclist.docs[0].NonKinaseProteinId+params+"'>"+groups[i].doclist.docs[0].NonProteinName+"</a></div><div class = 'span6 resultCaption'>"+groups[i].doclist.docs[0].NonProteinGeneSymbol+"</div></div>";
						resultDetail += "<div class = 'span12 resSub'><div class = 'span10'>";
						
						resultDetail += "<div class = 'row-fluid'>Description: "+groups[i].doclist.docs[0].NonProteinName+"</div>";
						resultDetail += "<div class = 'row-fluid'>Organism: "+"Homs sapiens (Human)"+"</div>";

						if(groups[i].doclist.docs[0].NonProteinUniProtId != ""){
							resultDetail += "</div>";
							resultDetail += "<div class = 'span2 resLinks'><div class = 'row-fluid'>Links</div><div class = 'row-fluid'>";
							resultDetail += "<a class = 'tool extLink uniprot' href=\"http://www.uniprot.org/uniprot/"+groups[i].doclist.docs[0].NonProteinUniProtId+"\" target=\"_blank\" title = 'View on Uniprot'>U</a>";
							resultDetail += "</div></div>";
						}
											
						resultDetail += "</div></div>";
						$("#dynamic").append(resultDetail);
					}
				}
			$(".tool").tooltip();
			pagingMode = "tabs";
			}, dataType: "jsonp", type: "GET", 'jsonp': 'json.wrf'});
		}
		
		function loadCdnas()
		{
			
			loadTags('Protein');
			onClickTags();
			$("#dynamic").html("<img class = 'span6 preloader' src = '/life/web/images/preloader.gif'>");
			$("#type").val("Cdnas");
			$("#mode").val("add");
			$('#load').val('Cdnas');
			$("#download").attr('href','list-download?input='+searchTerm+'&mode='+$("#load").val());
			$("#groupField").val("CdnaID");
			isSorted = "false";
			$("#viewData").html("");
			
			
			$.ajax( {url: url, data: $("#searchForm").serialize(), success: function(response) {

				response = eval(response);

				var groups = response.grouped.CdnaID.groups;	
				totalRecords = response.grouped.CdnaID.ngroups;
				$("#currPage").text("Page "+ ((startIndex/noOfRows)+1)+ "/"+Math.ceil(totalRecords/noOfRows));
				$("#dynamic").html("");
				for(var i = 0; i < groups.length; i++)
				{		
					if(groups[i].groupValue != null)
					{
						var params= '&'+document.URL.split('?')[1];
						var resultDetail = "";

						
						resultDetail += "<div class = 'row-fluid res-row'><div class = 'resultElement span12' id = 'result"+i+"'>";
							
						resultDetail += "<div class = 'span12 resHead'><div class = 'span6 resultTitle'><a title = 'Add to List' class = 'tool add' href = 'javascript:void(0)' onClick = 'onAddToListClick();$(\"#itemId\").val(\""+groups[i].doclist.docs[0].CdnaID+"\");$(this).parent().parent().parent().css(\"background-color\", \"#ffffb7\");editList();'> </a><a href= 'summary?mode=Cdna&input="+groups[i].doclist.docs[0].CdnaID+params+"'>"+groups[i].doclist.docs[0].CdnaName+"</a></div><div class = 'span6 resultCaption'>"+groups[i].doclist.docs[0].CdnaName+"</div></div>";
						resultDetail += "<div class = 'span12 resSub'><div class = 'span10'>";
						
						resultDetail += "<div class = 'row-fluid'>CDNA Id: "+groups[i].doclist.docs[0].CdnaID+"</div>";
						resultDetail += "<div class = 'row-fluid'>Target Gene: "+groups[i].doclist.docs[0].CdnaName+"</div>";
						
						if(groups[i].doclist.docs[0].CdnaID != ""){
							resultDetail += "</div>";
							resultDetail += "<div class = 'span2 resLinks'><div class = 'row-fluid'>Links</div><div class = 'row-fluid'>";
							resultDetail += "<a class = 'tool extLink uniprot' href=\"http://www.broadinstitute.org/rnai/public/clone/details?cloneId="+groups[i].doclist.docs[0].CdnaID+"\" target=\"_blank\" title = 'View on Broad'>CD</a>";
							resultDetail += "</div></div>";
						}
						
						resultDetail += "</div></div>";
						$("#dynamic").append(resultDetail);
					}
				}
			$(".tool").tooltip();
			pagingMode = "tabs";
			}, dataType: "jsonp", type: "GET", 'jsonp': 'json.wrf'});
		}
		
		function loadShRnas()
		{
			
			loadTags('Protein');
			onClickTags();
			$("#dynamic").html("<img class = 'span6 preloader' src = '/life/web/images/preloader.gif'>");
			$("#type").val("Shrnas");
			$("#mode").val("add");
			$('#load').val('Shrnas');
			$("#download").attr('href','list-download?input='+searchTerm+'&mode='+$("#load").val());
			$("#groupField").val("ShRnaID");
			isSorted = "false";
			$("#viewData").html("");
			
			
			$.ajax( {url: url, data: $("#searchForm").serialize(), success: function(response) {

				response = eval(response);

				var groups = response.grouped.ShRnaID.groups;	
				totalRecords = response.grouped.ShRnaID.ngroups;
				$("#currPage").text("Page "+ ((startIndex/noOfRows)+1)+ "/"+Math.ceil(totalRecords/noOfRows));
				$("#dynamic").html("");
				for(var i = 0; i < groups.length; i++)
				{		
					if(groups[i].groupValue != null)
					{
						var params= '&'+document.URL.split('?')[1];
						var resultDetail = "";

						
						resultDetail += "<div class = 'row-fluid res-row'><div class = 'resultElement span12' id = 'result"+i+"'>";
							
						resultDetail += "<div class = 'span12 resHead'><div class = 'span6 resultTitle'><a title = 'Add to List' class = 'tool add' href = 'javascript:void(0)' onClick = 'onAddToListClick();$(\"#itemId\").val(\""+groups[i].doclist.docs[0].ShRnaID+"\");$(this).parent().parent().parent().css(\"background-color\", \"#ffffb7\");editList();'> </a><a href= 'summary?mode=Shrna&input="+groups[i].doclist.docs[0].ShRnaID+params+"'>"+groups[i].doclist.docs[0].ShRnaName+"</a></div><div class = 'span6 resultCaption'>"+groups[i].doclist.docs[0].ShRnaPerturbagenId+"</div></div>";
						resultDetail += "<div class = 'span12 resSub'><div class = 'span10'>";
						
						resultDetail += "<div class = 'row-fluid'>Target Gene: "+groups[i].doclist.docs[0].ShRnaName+"</div>";
						resultDetail += "<div class = 'row-fluid'>7 Mer Sequence: "+groups[i].doclist.docs[0].ShRna7MerSequence+"</div>";
						resultDetail += "<div class = 'row-fluid'>6 Mer Sequence: "+groups[i].doclist.docs[0].ShRna6MerSequence+"</div>";
						resultDetail += "<div class = 'row-fluid'>Target Sequence: "+groups[i].doclist.docs[0].ShRnaTargetSequence+"</div>";

						if(groups[i].doclist.docs[0].ShRnaPerturbagenId != ""){
							resultDetail += "</div>";
							resultDetail += "<div class = 'span2 resLinks'><div class = 'row-fluid'>Links</div><div class = 'row-fluid'>";
							resultDetail += "<a class = 'tool extLink uniprot' href=\"http://www.broadinstitute.org/rnai/public/clone/details?cloneId="+groups[i].doclist.docs[0].ShRnaPerturbagenId+"\" target=\"_blank\" title = 'View on Broad'>SH</a>";
							resultDetail += "</div></div>";
						}
											
						resultDetail += "</div></div>";
						$("#dynamic").append(resultDetail);
					}
				}
			$(".tool").tooltip();
			pagingMode = "tabs";
			}, dataType: "jsonp", type: "GET", 'jsonp': 'json.wrf'});
		}
		function loadAntibodies()
		{
			
			loadTags('Protein');
			onClickTags();
			$("#dynamic").html("<img class = 'span6 preloader' src = '/life/web/images/preloader.gif'>");
			$("#type").val("Antibodies");
			$("#mode").val("add");
			$('#load').val('Antibodies');
			$("#download").attr('href','list-download?input='+searchTerm+'&mode='+$("#load").val());
			$("#groupField").val("AntibodyId");
			isSorted = "false";
			$("#viewData").html("");
			
			
			$.ajax( {url: url, data: $("#searchForm").serialize(), success: function(response) {

				response = eval(response);

				var groups = response.grouped.AntibodyId.groups;	
				totalRecords = response.grouped.AntibodyId.ngroups;
				$("#currPage").text("Page "+ ((startIndex/noOfRows)+1)+ "/"+Math.ceil(totalRecords/noOfRows));
				$("#dynamic").html("");
				for(var i = 0; i < groups.length; i++)
				{		
					if(groups[i].groupValue != null)
					{
						var params= '&'+document.URL.split('?')[1];
						var resultDetail = "";

						
						resultDetail += "<div class = 'row-fluid res-row'><div class = 'resultElement span12' id = 'result"+i+"'>";
							
						resultDetail += "<div class = 'span12 resHead'><div class = 'span6 resultTitle'><a title = 'Add to List' class = 'tool add' href = 'javascript:void(0)' onClick = 'onAddToListClick();$(\"#itemId\").val(\""+groups[i].doclist.docs[0].AntibodyId+"\");$(this).parent().parent().parent().css(\"background-color\", \"#ffffb7\");editList();'> </a><a href= 'summary?mode=Antibody&input="+groups[i].doclist.docs[0].AntibodyId+params+"'>"+groups[i].doclist.docs[0].ProteinName+"</a></div><div class = 'span6 resultCaption'>"+groups[i].doclist.docs[0].KinaseSymbol+"</div></div>";
						resultDetail += "<div class = 'span12 resSub'><div class = 'span10'>";
						
						resultDetail += "<div class = 'row-fluid'>Description: "+groups[i].doclist.docs[0].KinaseDescription+"</div>";
						resultDetail += "<div class = 'row-fluid'>Organism: "+groups[i].doclist.docs[0].ProteinOrganism+"</div>";

						if(groups[i].doclist.docs[0].UniprotId != ""){
							resultDetail += "</div>";
							resultDetail += "<div class = 'span2 resLinks'><div class = 'row-fluid'>Links</div><div class = 'row-fluid'>";
							resultDetail += "<a class = 'tool extLink uniprot' href=\"http://www.uniprot.org/uniprot/"+groups[i].doclist.docs[0].UniprotId+"\" target=\"_blank\" title = 'View on Uniprot'>U</a>";
							resultDetail += "</div></div>";
						}
											
						resultDetail += "</div></div>";
						$("#dynamic").append(resultDetail);
					}
				}
			$(".tool").tooltip();
			pagingMode = "tabs";
			}, dataType: "jsonp", type: "GET", 'jsonp': 'json.wrf'});
		}
		function loadPhosphoProteins()
		{
			
			loadTags('Protein');
			onClickTags();
			$("#dynamic").html("<img class = 'span6 preloader' src = '/life/web/images/preloader.gif'>");
			$("#type").val("PhosphoProteins");
			$("#mode").val("add");
			$('#load').val('PhosphoProteins');
			$("#download").attr('href','list-download?input='+searchTerm+'&mode='+$("#load").val());
			$("#groupField").val("PhosphoProteinId");
			isSorted = "false";
			$("#viewData").html("");
			
			
			$.ajax( {url: url, data: $("#searchForm").serialize(), success: function(response) {

				response = eval(response);

				var groups = response.grouped.PhosphoProteinId.groups;	
				totalRecords = response.grouped.PhosphoProteinId.ngroups;
				$("#currPage").text("Page "+ ((startIndex/noOfRows)+1)+ "/"+Math.ceil(totalRecords/noOfRows));
				$("#dynamic").html("");
				for(var i = 0; i < groups.length; i++)
				{		
					if(groups[i].groupValue != null)
					{
						var params= '&'+document.URL.split('?')[1];
						var resultDetail = "";

						
						resultDetail += "<div class = 'row-fluid res-row'><div class = 'resultElement span12' id = 'result"+i+"'>";
							
						resultDetail += "<div class = 'span12 resHead'><div class = 'span6 resultTitle'><a title = 'Add to List' class = 'tool add' href = 'javascript:void(0)' onClick = 'onAddToListClick();$(\"#itemId\").val(\""+groups[i].doclist.docs[0].PhosphoProteinId+"\");$(this).parent().parent().parent().css(\"background-color\", \"#ffffb7\");editList();'> </a><a href= 'summary?mode=PhosphoProtein&input="+groups[i].doclist.docs[0].PhosphoProteinId+params+"'>"+groups[i].doclist.docs[0].PhosphoProteinName+"</a></div><div class = 'span6 resultCaption'>"+groups[i].doclist.docs[0].PhosphoProteinProbeId+"</div></div>";
						resultDetail += "<div class = 'span12 resSub'><div class = 'span4'>";
						
						
						resultDetail += "<div class = 'row-fluid'>Gene Symbol: "+groups[i].doclist.docs[0].PhosphoProteinGeneSymbol+"</div>";
						resultDetail += "<div class = 'row-fluid'>Base Peptide: "+groups[i].doclist.docs[0].PhosphoProteinBasePeptide+"</div>";
						resultDetail += "<div class = 'row-fluid'>Modified Peptide: "+groups[i].doclist.docs[0].PhosphoProteinModifiedPeptide+"</div>";
						resultDetail += "<div class = 'row-fluid'>Phosphosite: "+groups[i].doclist.docs[0].PhosphoProteinPhosphosite+"</div>";
						resultDetail += "</div>";
						resultDetail += "<div class = 'span4 resLinks'><div class = 'row-fluid'>Links</div><div class = 'row-fluid'>";
						resultDetail += "<a class = 'tool extLink uniprot' href=\"http://www.uniprot.org/uniprot/"+groups[i].doclist.docs[0].PhosphoProteinUniProtId+"\" target=\"_blank\" title = 'View on UniProt'>U</a>";
						resultDetail += "</div></div>"
						
						resultDetail += "</div></div>";
						$("#dynamic").append(resultDetail);
					}
				}
			$(".tool").tooltip();
			pagingMode = "tabs";
			}, dataType: "jsonp", type: "GET", 'jsonp': 'json.wrf'});
		}
		
		function loadProteins()
		{
			
			loadTags('Protein');
			onClickTags();
			$("#dynamic").html("<img class = 'span6 preloader' src = '/life/web/images/preloader.gif'>");
			$("#type").val("Protein");
			$("#mode").val("add");
			$('#load').val('Protein');
			$("#download").attr('href','list-download?input='+searchTerm+'&mode='+$("#load").val());
			$("#groupField").val("ProteinId");
			isSorted = "false";
			$("#viewData").html("");
			
			
			$.ajax( {url: url, data: $("#searchForm").serialize(), success: function(response) {

				response = eval(response);

				var groups = response.grouped.ProteinId.groups;	
				totalRecords = response.grouped.ProteinId.ngroups;
				$("#currPage").text("Page "+ ((startIndex/noOfRows)+1)+ "/"+Math.ceil(totalRecords/noOfRows));
				$("#dynamic").html("");
				for(var i = 0; i < groups.length; i++)
				{		
					if(groups[i].groupValue != null)
					{
						var params= '&'+document.URL.split('?')[1];
						var resultDetail = "";

						
						resultDetail += "<div class = 'row-fluid res-row'><div class = 'resultElement span12' id = 'result"+i+"'>";
							
						resultDetail += "<div class = 'span12 resHead'><div class = 'span6 resultTitle'><a title = 'Add to List' class = 'tool add' href = 'javascript:void(0)' onClick = 'onAddToListClick();$(\"#itemId\").val(\""+groups[i].doclist.docs[0].ProteinId+"\");$(this).parent().parent().parent().css(\"background-color\", \"#ffffb7\");editList();'> </a><a href= 'summary?mode=Protein&input="+groups[i].doclist.docs[0].ProteinId+params+"'>"+groups[i].doclist.docs[0].ProteinName+"</a></div><div class = 'span6 resultCaption'>"+groups[i].doclist.docs[0].KinaseName+"</div></div>";
						resultDetail += "<div class = 'span12 resSub'><div class = 'span4'>";
						
						
						resultDetail += "<div class = 'row-fluid'>Gene Symbol: "+groups[i].doclist.docs[0].KinaseSymbol+"</div>";
						resultDetail += "<div class = 'row-fluid'>Hierarchy: "+groups[i].doclist.docs[0].KinaseDomain[0]+"</div>";
						resultDetail += "<div class = 'row-fluid'>Group: "+groups[i].doclist.docs[0].MainCategory+"</div>";
						
						resultDetail += "</div><div class = 'span4'>";
						
						if(groups[i].doclist.docs[0].KinaseFamily != "")
							resultDetail += "<div class = 'row-fluid'>Family: "+groups[i].doclist.docs[0].KinaseFamily+"</div>";
						
						if(groups[i].doclist.docs[0].Mutation != "")
							resultDetail += "<div class = 'row-fluid'>Mutation: "+groups[i].doclist.docs[0].Mutation+"</div>";
						
						if(groups[i].doclist.docs[0].Modification != "")
							resultDetail += "<div class = 'row-fluid'>Modification: "+groups[i].doclist.docs[0].Modification+"</div>";
						resultDetail += "</div>";
						
						resultDetail += "<div class = 'span4 resLinks'><div class = 'row-fluid'>Links</div><div class = 'row-fluid'>";
						resultDetail += "<a class = 'tool extLink uniprot' href=\"http://www.uniprot.org/uniprot/"+groups[i].doclist.docs[0].UniprotId+"\" target=\"_blank\" title = 'View on UniProt'>U</a>";
						resultDetail += "<a class = 'tool extLink pdb' href=\"http://www.rcsb.org/pdb/protein/"+groups[i].doclist.docs[0].UniprotId+"\" target=\"_blank\" title = 'View on RCSB PDB'>R</a>";
						resultDetail += "<a class = 'tool extLink lincs' href=\"http://lincs.hms.harvard.edu/db/proteins/"+groups[i].doclist.docs[0].HMSProteinId+"\" target=\"_blank\" title = 'View on HMS'>H</a>";
						resultDetail += "<a class = 'tool extLink entrez' href=\"http://www.ncbi.nlm.nih.gov/gene/"+groups[i].doclist.docs[0].RefSeqId+"\" target=\"_blank\" title = 'View on Entrez'>E</a>";
						resultDetail += "</div></div>"
						
						resultDetail += "</div></div>";
						$("#dynamic").append(resultDetail);
					}
				}
			$(".tool").tooltip();
			pagingMode = "tabs";
			}, dataType: "jsonp", type: "GET", 'jsonp': 'json.wrf'});
		}
		

		
		function loadCompounds()
		{
			loadTags('Compound');
			$("#dynamic").html("<div class = 'span12 loaderContainer'><img class = 'preloader' src = '/life/web/images/preloader.gif'></div>");
			$("#type").val("SmallMolecule");
			$("#mode").val("add");
			$('#load').val('SmallMolecule');
			$("#download").attr('href','list-download?input='+searchTerm+'&mode='+$("#load").val());
			$("#groupField").val("SmallMoleculeId");
			isSorted = "false";
			$("#viewData").html("");
			
			$.ajax( {url: url, data: $("#searchForm").serialize(), success: function(response) {

				response = eval(response);

				var groups = response.grouped.SmallMoleculeId.groups;	
				totalRecords = response.grouped.SmallMoleculeId.ngroups;

				$("#currPage").text("Page "+ ((startIndex/noOfRows)+1)+ "/"+Math.ceil(totalRecords/noOfRows));
				$("#dynamic").html("");
				for(var i = 0; i < groups.length; i++)
				{		
					if(groups[i].groupValue != null)
					{
						var params= '&'+document.URL.split('?')[1];
						var resultDetail = "";

						
						resultDetail += "<div class = 'row-fluid res-row'><div class = 'resultElement span12' id = 'result"+i+"'>";
							
						resultDetail += "<div class = 'span12 resHead'><div class = 'span12 resultTitle'><a title = 'Add to List' class = 'tool add' href = 'javascript:void(0)' onClick = 'onAddToListClick();$(\"#itemId\").val(\""+groups[i].doclist.docs[0].SmallMoleculeId+"\");$(this).parent().parent().parent().css(\"background-color\", \"#ffffb7\");editList();'> </a><a href= 'summary?mode=SmallMolecule&input="+groups[i].doclist.docs[0].SmallMoleculeId+params+"'>"+groups[i].doclist.docs[0].SmallMoleculeName+"</a></div></div>";
						resultDetail += "<div class = 'span12 resSub'><div class = 'span4'>";
						
						if(groups[i].doclist.docs[0].MolecularWeight != "")
							resultDetail += "<div class = 'row-fluid'>Mol. Weight: "+groups[i].doclist.docs[0].MolecularWeight+"</div>";
						if(groups[i].doclist.docs[0].MolecularFormulae != "")
							resultDetail += "<div class = 'row-fluid'>Mol. Formula: "+groups[i].doclist.docs[0].MolecularFormulae+"</div>";
						if(groups[i].doclist.docs[0].KinaseInhibitorType != "")
							resultDetail += "<div class = 'row-fluid'>Inhibitor Type: "+groups[i].doclist.docs[0].KinaseInhibitorType+"</div>";
						if(groups[i].doclist.docs[0].SMCategories != "")
							resultDetail += "<div class = 'row-fluid'>Category: "+groups[i].doclist.docs[0].SMCategories+"</div>";
						
						resultDetail += "</div><div class = 'span4'>";
						resultDetail += "<a class='thumbnail no-border' href='#thumb'><img class='search-image' style = 'height: 7em; width 7em;' src = '/life/web/images/sm-images/100/"+groups[i].doclist.docs[0].LincsSMId+".png'>" +
								"<span><img src = '/life/web/images/sm-images/400/"+groups[i].doclist.docs[0].LincsSMId+".png'/></span></a>";
						
						resultDetail += "</div>";
						
						resultDetail += "<div class = 'span4 resLinks'><div class = 'row-fluid'>Links</div><div class = 'row-fluid'>";
						
						if((groups[i].doclist.docs[0].PerturbagenId).substring(0,3) != "BRD")
							resultDetail += "<a class = 'tool extLink lincs' href=\"http://lincs.hms.harvard.edu/db/sm/"+groups[i].doclist.docs[0].PerturbagenId+"\" target=\"_blank\" title = 'View on HMS'>H</a>";
						else
							resultDetail += "<a class = 'tool extLink lincs' href=\"http://eh3.uc.edu/GenomicsPortals/lincsSignature.do?lincs_pert_id="+groups[i].doclist.docs[0].LincsSMId+"\" target=\"_blank\" title = 'View on iLINCS'>I</a>";	
						if(groups[i].doclist.docs[0].PubchemId != "")
							resultDetail += "<a class = 'tool extLink pubchem' href=\"http://pubchem.ncbi.nlm.nih.gov/summary/summary.cgi?cid="+groups[i].doclist.docs[0].PubchemId+"\" target=\"_blank\" title = 'View on PubChem'>P</a>";
						if(groups[i].doclist.docs[0].PDBLigandCode != "")
							resultDetail += "<a class = 'tool extLink pdb' href=\"http://www.rcsb.org/pdb/ligand/ligandsummary.do?hetId="+groups[i].doclist.docs[0].PDBLigandCode+"\" target=\"_blank\" title = 'View on RCSB PDB'>R</a>";
						resultDetail += "</div></div>"
						
						resultDetail += "</div></div>";
						$("#dynamic").append(resultDetail);
						
					}
				}
				pagingMode = "tabs";
				$("#dynamic").append("<div class = 'span12 paging'><a href = ");
				$(".tool").tooltip();
			}, dataType: "jsonp", type: "GET", 'jsonp': 'json.wrf'});
		}		
		

		function loadGenes()
		{
			loadTags('Gene');
			$("#dynamic").html("<img class = 'preloader' src = '/life/web/images/preloader.gif'>");
			$("#type").val("Gene");
			$("#mode").val("add");
			$('#load').val('Gene');
			$("#download").attr('href','list-download?input='+searchTerm+'&mode='+$("#load").val());
			$("#groupField").val("GeneId");
			isSorted = "false";
			$("#viewData").html("");
		
			
			$.ajax( {url: url, data: $("#searchForm").serialize(), success: function(response) {

				response = eval(response);

				var groups = response.grouped.GeneId.groups;	
				totalRecords = response.grouped.GeneId.ngroups;
				$("#currPage").text("Page "+ ((startIndex/noOfRows)+1)+ "/"+Math.ceil(totalRecords/noOfRows));
				
				$("#dynamic").html("");
				for(var i = 0; i < groups.length; i++)
				{		
					if(groups[i].groupValue != null)
					{
						var params= '&'+document.URL.split('?')[1];
						var resultDetail = "";

						
						resultDetail += "<div class = 'row-fluid res-row'><div class = 'resultElement span12' id = 'result"+i+"'>";
							
						resultDetail += "<div class = 'span12 resHead'><div class = 'span6 resultTitle'><a title = 'Add to List' class = 'tool add' href = 'javascript:void(0)' onClick = 'onAddToListClick();$(\"#itemId\").val(\""+groups[i].doclist.docs[0].GeneId+"\");$(this).parent().parent().parent().css(\"background-color\", \"#ffffb7\");editList();'> </a><a href= 'summary?mode=Gene&input="+groups[i].doclist.docs[0].GeneId+params+"'>"+groups[i].doclist.docs[0].GeneName+"</a></div><div class = 'span6 resultCaption'></div></div>";
						resultDetail += "<div class = 'span12 resSub'><div class = 'span6'>";
						
						
						resultDetail += "<div class = 'row-fluid'>Description: "+groups[i].doclist.docs[0].GeneDescription+"</div>";
						resultDetail += "<div class = 'row-fluid'>Organism: "+groups[i].doclist.docs[0].GeneOrganism+"</div>";
						
						resultDetail += "</div><div class = 'span2'>";
						
						resultDetail += "</div>";
						
						resultDetail += "<div class = 'span4 resLinks'><div class = 'row-fluid'>Links</div><div class = 'row-fluid'>";
						resultDetail += "<a class = 'tool extLink entrez' href=\"http://www.ncbi.nlm.nih.gov/gene/?term="+groups[i].doclist.docs[0].EntrezId+"\" target=\"_blank\" title = 'View on Entrez'>E</a>";

						resultDetail += "</div></div>"
						
						resultDetail += "</div></div>";
						$("#dynamic").append(resultDetail);
						
					}
				}
			$(".extLink").tooltip();
			pagingMode = "tabs";
			}, dataType: "jsonp", type: "GET", 'jsonp': 'json.wrf'});
		}
				
		
		function loadCellLines()
		{
			loadTags('Cell');
			$("#dynamic").html("<img class = 'preloader' src = '/life/web/images/preloader.gif'>");
			$("#type").val("CellLine");
			$("#mode").val("add");
			$('#load').val('CellLine');
			$("#download").attr('href','list-download?input='+searchTerm+'&mode='+$("#load").val());
			$("#groupField").val("CellLineId");
			isSorted = "false";
			$("#viewData").html("");
			
			
			$.ajax( {url: url, data: $("#searchForm").serialize(), success: function(response) {

				response = eval(response);

				var groups = response.grouped.CellLineId.groups;	
				totalRecords = response.grouped.CellLineId.ngroups;
				$("#currPage").text("Page "+ ((startIndex/noOfRows)+1)+ "/"+Math.ceil(totalRecords/noOfRows));
				
				$("#dynamic").html("<span class = 'giant-note'></span>"); //no results tagline
				

				if(groups.length > 1)
					$("#dynamic").html("");
				for(var i = 0; i < groups.length; i++)
				{		
					if(groups[i].groupValue != null)
					{
						var params= '&'+document.URL.split('?')[1];
						var resultDetail = "";

						
						resultDetail += "<div class = 'row-fluid res-row'><div class = 'resultElement span12' id = 'result"+i+"'>";
							
						resultDetail += "<div class = 'span12 resHead'><div class = 'span6 resultTitle'><a title = 'Add to List' class = 'tool add' href = 'javascript:void(0)' onClick = 'onAddToListClick();$(\"#itemId\").val(\""+groups[i].doclist.docs[0].CellLineId+"\");$(this).parent().parent().parent().css(\"background-color\", \"#ffffb7\");editList();'> </a><a href= 'summary?mode=CellLine&input="+groups[i].doclist.docs[0].CellLineId+params+"'>"+groups[i].doclist.docs[0].CellLineName+"</a></div><div class = 'span6 resultCaption'>"+groups[i].doclist.docs[0].Disease+"</div></div>";
						resultDetail += "<div class = 'span12 resSub'><div class = 'span4'>";
						
						
						resultDetail += "<div class = 'row-fluid'>Organism: "+groups[i].doclist.docs[0].CellLineOrganism+"</div>";
						
						resultDetail += "</div><div class = 'span4'>";
						
						resultDetail += "<div class = 'row-fluid'>Organ: "+groups[i].doclist.docs[0].CellLineOrgan+"</div>";
						
						resultDetail += "</div>";
						
						resultDetail += "<div class = 'span4 resLinks'><div class = 'row-fluid'>Links</div><div class = 'row-fluid'>";
						resultDetail += "<a class = 'tool extLink lincs' href=\"http://lincs.hms.harvard.edu/db/cells/"+groups[i].doclist.docs[0]['CellLineLincsSourceId ']+"\" target=\"_blank\" title = 'View cells info on HMS'>H</a>";
						resultDetail += "<a class = 'tool extLink bioportal' href=\"http://bioportal.bioontology.org/ontologies/49838/?p=terms&conceptid="+groups[i].doclist.docs[0].DoId+"\" target=\"_blank\" title = 'View disease info on BioPortal'>B</a>";
						resultDetail += "</div></div>"
						
						resultDetail += "</div></div>";
						$("#dynamic").append(resultDetail);
						
					}
				}
			$(".tool").tooltip();
			pagingMode = "tabs";
			}, dataType: "jsonp", type: "GET", 'jsonp': 'json.wrf'});
			
		}
		
		
		function loadAssays()
		{
			loadTags('Assay');
			$("#dynamic").html("<img class = 'preloader' src = '/life/web/images/preloader.gif'>");
			$("#type").val("experiment");
			$("#mode").val("add");
			$('#load').val('AssayTypeName');
			$("#download").attr('href','download?input='+searchTerm+'&mode='+$("#load").val());
			$("#groupField").val("AssayTypeName");
			isSorted = "false";
			var bioAssayGroups,count=0;
			$("#viewData").html("");
			
			$.ajax( {url: url, data: $("#searchForm").serialize(), success: function(response) {

				response = eval(response);

				bioAssayGroups = response.grouped.AssayTypeName.groups;	
				totalRecords = response.grouped.AssayTypeName.ngroups;
				$("#currPage").text("Page "+ ((startIndex/noOfRows)+1)+ "/"+Math.ceil(totalRecords/noOfRows));

				$("#dynamic").html("");
				
				for(var i = 0; i < bioAssayGroups.length; i++)
				{		
					if(bioAssayGroups[i].groupValue != null)
					{
				
						var resultDetail = "",image="",logoUrl="";
						
						if(bioAssayGroups[i].doclist.docs[0].AssayTypeName == 'L1000 transcriptional profiling assay' || bioAssayGroups[i].doclist.docs[0].AssayTypeName == 'P100 Phosphosite Profiling Assay'){
							image = "/life/web/images/logo_broad.png";
							logoUrl = "http://lincscloud.org/";
						}else if(bioAssayGroups[i].doclist.docs[0].AssayTypeName == 'Single cell protein secretion profiling assay'){
							image = "/life/web/images/logo_yale.jpg";
							logoUrl = "http://www.yale.edu/";
						}else{
							image = "/life/web/images/logo_hms.png";
							logoUrl = "http://lincs.hms.harvard.edu/db/";
						}
						
						
						resultDetail += "<div class = 'row-fluid res-row'><div class = 'resultElement span12' id = 'result"+i+"'>";
							
						resultDetail += "<div class = 'span12 resHead'><div class = 'span7 resultTitle'><a href= 'summary?mode=assay&input="+unescape(bioAssayGroups[i].doclist.docs[0].AssayTypeName) +"'>" +bioAssayGroups[i].doclist.docs[0].AssayTypeName+"</a></div>"+
										"<div class = 'span5 resultCaption'><a href=" + logoUrl +" target='_blank'><img class='size-s' src=" +image +"></a></div></div>";
						resultDetail += "<div class = 'span12 resSub'><div class = 'span4'>";
						
						
						resultDetail += "<div class = 'row-fluid'>Assay Format: "+bioAssayGroups[i].doclist.docs[0].AssayFormat+"</div>";
						resultDetail += "<div class = 'row-fluid'>Assay Type: "+bioAssayGroups[i].doclist.docs[0].AssayType+"</div>";
						
						resultDetail += "</div><div class = 'span4'>";
						
						
						resultDetail += "</div>";
						
						resultDetail += "<div class = 'span4 resLinks'><div class = 'row-fluid'></div><div class = 'row-fluid'>";
						resultDetail += "<div id='tooltip-ex'><div id='bioAssaySLink" + i + "' class='bioAssayLink'></div>";
						resultDetail += "<div id='bioAssayPLink" + i + "' class='bioAssayLink'></div>";
						resultDetail += "<div id='bioAssayCLink" + i + "' class='bioAssayLink'></div>";
						resultDetail += "<div id='bioAssayPPLink" + i + "' class='bioAssayLink'></div>";
						resultDetail += "<div id='bioAssayShLink" + i + "' class='bioAssayLink'></div>";
						resultDetail += "<div id='bioAssayCdLink" + i + "' class='bioAssayLink'></div>";
						resultDetail += "<div id='bioAssayAbLink" + i + "' class='bioAssayLink'></div>";
						resultDetail += "<div id='bioAssayNkpLink" + i + "' class='bioAssayLink'></div>";
						resultDetail += "<div id='bioAssayLpLink" + i + "' class='bioAssayLink'></div>";
						resultDetail += "<div id='bioAssayGLink" + i + "' class='bioAssayLink'></div></div>";
						resultDetail += "</div></div>"
						
						resultDetail += "</div></div>";
						$("#dynamic").append(resultDetail);
						
					}
				}
			$(".extLink").tooltip();	
			pagingMode = "tabs";		
			},
			complete:function(response){
			   if(count < bioAssayGroups.length){	
					getBioAssayLinks(bioAssayGroups,count);
			   }
			}, dataType: "jsonp", type: "GET", 'jsonp': 'json.wrf'});
		}

function getBioAssayLinks(bioGrps,cnt){

	if(bioGrps[cnt].groupValue != null)	{
		
		$.ajax( { url : url + '?q=' + '"' +bioGrps[cnt].doclist.docs[0].AssayTypeName + '"' + '&facet=true&facet.field=GeneId&facet.field=CellLineId&facet.field=ProteinId&facet.field=SmallMoleculeId&facet.field=PhosphoProteinId&facet.field=ShRnaID&facet.field=CdnaID&facet.field=NonKinaseProteinId&facet.field=AntibodyId&facet.field=LigandProteinId&facet.mincount=1&facet.limit=-1&indent=false&wt=json&rows=0',
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
							$("#bioAssaySLink"+cnt).html("<a class = 'tool extLink uniprot' title = 'Small Molecule Count :  "  +smallMoleculeCount/2 + "'>S</a>");	
						if(proteinCount > 0)
							$("#bioAssayPLink"+cnt).html("<a class = 'tool extLink pdb' title = 'Protein Count : " + proteinCount/2 + "'>P</a>");	
						if(cellLineCount > 0)
							$("#bioAssayCLink"+cnt).html("<a class = 'tool extLink lincs' title = 'Cell Count : " + cellLineCount/2 + "'>C</a>");	
						if(geneCount > 0)
							$("#bioAssayGLink"+cnt).html("<a class = 'tool extLink entrez' title = 'Gene Count : " + geneCount/2 + "'>G</a>");
						if(phosphoproteinsCount > 0)
							$("#bioAssayPPLink"+cnt).html("<a class = 'tool extLink entrez' title = 'Phospho Proteins Count : " + phosphoproteinsCount/2 + "'>PP</a>");
						if(shrnaCount > 0)
							$("#bioAssayShLink"+cnt).html("<a class = 'tool extLink entrez' title = 'short hairpin RNAs Count : " + shrnaCount/2 + "'>SH</a>");
						if(cdnaCount > 0)
							$("#bioAssayCdLink"+cnt).html("<a class = 'tool extLink entrez' title = 'Complementary DNAs Count : " + cdnaCount/2 + "'>CD</a>");
						if(antibodyCount > 0)
							$("#bioAssayAbLink"+cnt).html("<a class = 'tool extLink entrez' title = 'Antibody Count : " + antibodyCount/2 + "'>AB</a>");
						if(nonkinaseproteinCount > 0)
							$("#bioAssayNkpLink"+cnt).html("<a class = 'tool extLink entrez' title = 'detected proteins Count : " + nonkinaseproteinCount/2 + "'>DP</a>");
						if(ligandCount > 0)
							$("#bioAssayLpLink"+cnt).html("<a class = 'tool extLink entrez' title = 'protein ligands Count : " + ligandCount/2 + "'>LP</a>");

				},complete : function(response){
					cnt++;
					if(cnt < bioGrps.length){
						getBioAssayLinks(bioGrps,cnt);
					}	
					$("#tooltip-ex a").tooltip({
				        placement : 'top'
				    });
				    	
				},
				dataType : "jsonp",
				type : "GET",
				'jsonp' : 'json.wrf'
	 });
	}else{
	   cnt++;
	   getBioAssayLinks(bioGrps,cnt);
    }
}

function loadResultCategory(resultCatList){
 	var errorMessage = "<div id='resNotFoundMsg'>" +
 	 				   "We did not find results for <strong><u>'" + searchTermForErrorMsg + "'</u></strong>. Try the suggestions below or <br> type a new query above.	<br><br>" +
	  				   "<ul><li type='square'>Check other index </li>" + 
	  				   "<li> Check your spelling. </li>" +
	   				   "<li> Try more general keywords. </li>" + 
	   				   "<li> Broaden your search by using fewer words.</li></ul></div>";

	if(resultCatList.length == 0){
			$("#dynamic").html("");
			$("#noResultMsg").html(errorMessage);
	}else {

		//if load parameter is not matching with result category, then load data in first result category
		if(resultCatList.indexOf($("#load").val()) == -1)
				$("#load").val(resultCatList[0]);
				
		$("#noResultMsg").html("");
		
		if($("#load").val() == "AssayTypeName"){
			loadAssays();
			setTimeout(function(){$("#assayLink").addClass("active")},1000);
		}
		if($("#load").val() == "SmallMolecule"){
			loadCompounds();
			setTimeout(function(){$("#compoundLink").addClass("active")},1000);
		}
		if($("#load").val() == "Protein"){
			loadProteins();
			setTimeout(function(){$("#proteinLink").addClass("active")},1000);
		}
		if($("#load").val() == "Gene"){
			loadGenes(); 
			setTimeout(function(){$("#geneLink").addClass("active")},1000);
		}
		if($("#load").val() == "CellLine"){
			loadCellLines();
			setTimeout(function(){$("#cellLink").addClass("active")},1000);
		}
		if($("#load").val() == "PhosphoProtein"){
			loadPhosphoProteins();
			setTimeout(function(){$("#phosphoProteinsLink").addClass("active")},1000);
		}
		if($("#load").val() == "Cdnas"){
			loadCdnas();
			setTimeout(function(){$("#cDnaLink").addClass("active")},1000);
		}
		if($("#load").val() == "Shrnas"){
			loadShRnas();
			setTimeout(function(){$("#shRnaLink").addClass("active")},1000);
		}
		if($("#load").val() == "Antibodies"){
			loadAntibodies();
			setTimeout(function(){$("#antibodyLink").addClass("active")},1000);
		}
		if($("#load").val() == "NonKinaseProteins"){
			loadNonkinaseproteins();
			setTimeout(function(){$("#nonkinaseproteinsLink").addClass("active")},1000);
		}
		
	}
}

</script>
	
<script src="/life/web/js/hopscotch.js"></script> <!-- By JP -->
    <script src="/life/web/js/search_results_tour.js"></script> <!-- By JP -->


</body>
</html>