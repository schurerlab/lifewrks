<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>LIFE.wrx Compare</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
	<%@ include file = "require.jsp" %>
	<script type="text/javascript" language="javascript" src="/life/web/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="/life/web/js/jquery.getUrlParam.js"></script>
	
	<script type="text/javascript" src="https://www.google.com/jsapi"></script>
	<!-- make sure that this custom js script tag is added only after jquery library script tag--> 
    <script type="text/javascript" language="javascript" src="/life/web/js/element.list.js"></script>
	<!-- Le styles -->
	<link href="/life/web/css/summary-table.css" rel="stylesheet">
	<link href="/life/web/css/search.css" rel="stylesheet">

	<%@ page import="java.util.ArrayList" %>
	<%@ page import="java.util.Arrays" %>
	<%@ page import="java.util.List" %>
	<%@page import="java.util.*" %>

</head>
<body>
<%@ include file = "nav.jsp" %>
	<div class="container padding-top-xxl">
<%@ include file = "search-bar.jsp" %>
		
<div class = "row-fluid align-left">	

	<div class="span4" id = "left-tabs">
		
		<div id = "tabHolder" class = "span12">
		 		<!-- for all drop downs -->
			<div id="dropDowns">
				  <div id = "experiments" ></div>
				  <div id = "endPoints" ></div>
				  <div id = "epTime"></div>
				  <div id = "epConcentration" ></div>
				  
				  <div id="submit"></div>
			</div>
			<div  id="pieChart" > </div>
		</div>

	</div>
	<form name = "compareForm" id = "compareForm">
    	<input type = "hidden" name = "elementType" value= "SmallMolecule"/>
        <input type = "hidden" name = "mode" id = "mode"/>
	<div class = "span8" id = "resultsContainer">
		<div class = "span12" id = "resultsHeader">

			<div class = "span4">
				<a id = "message"></a>
			</div>
			<div class = "span8 text-right">
				<div id="manageList" > </div>
				<div id="filterByList"></div>
				<div id="addToList"> </div>
			</div>

		<!-- for bar chart -->

		</div>
				<div id="charts">
	  		  <div  id="barChart"> </div>
			  <span id="prev"  onclick="BarPagination('prev');" ></span>&nbsp;
   			  <span id="next"  onclick="BarPagination('next');" ></span>

  		</div>
	</div>
	<div style = "clear: both; position: relative;">
		<div id = "dynamicTable" class = "clearfix"></div>
    	<input type = "hidden" name = "compareMode" id = "compareMode"/>
    </div>
	</form>
</div>


	</div> <!-- /container -->
	<script type="text/javascript">
			<%
			out.print("var elementType = \""+request.getParameter("elementType")+"\";");
			out.print("var compareType = \""+request.getParameter("compareType")+"\";");
			
			//Target array for compare
			out.print("var trgArr = \"");
			if(request.getParameter("list") != null){
				 String [] list = request.getParameterValues("list");
				 for(int i = 0; i < list.length; i++){
					  out.print(list[i]);
			          if(i != list.length-1){
			        	  out.print(",");
			          }
			      }
		    }
			out.print("\";");
		%>
		
		$(function() { 
			$.ajax( {url: "compare-barchart", data: {listArr : trgArr, elementType : elementType}, success: processBarChartResponse, dataType: "json", type: "GET" } );
			$.ajax( {url: "compare-piechart", data: {listArr : trgArr, elementType : elementType}, success: processPieChartResponse, dataType: "json", type: "GET" } );
		});

	</script>
</body>
</html>



