<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Statistics Page</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	
	<%@ include file = "require.jsp" %>
	<!-- <script type="text/javascript" src="/life/web/js/statistics.js"></script>  -->
	<script src="/life/web/js/d3.v2.js"></script>
	<script src="/life/web/js/d3.min.js"></script>
	<script src="/life/web/js/d3.tip.v0.6.3.js"></script>

	<style>
	path {  stroke: #b6b6b6; }
	path:hover {  opacity:0.9; }
	.axis {  font: 10px sans-serif; }
	.legend tr{    border-bottom:1px solid grey; }
	.legend tr:first-child{    border-top:1px solid grey; }
	
	.axis path,
	.axis line {
	  fill: none;
	  stroke: #b6b6b6;
	  shape-rendering: crispEdges;
	}
	
	.x.axis path {  display: none; }
	.legend{
	    margin-bottom:76px;
	    display:inline-block;
	    border-collapse: collapse;
	    border-spacing: 0px;
	}
	.legend td{
	    padding:4px 5px;
	    vertical-align:bottom;
	}
	.legendType {
	    align:right;
	    width:50px;
	}
	
	</style>
</head>
<body>
    <%@ include file = "nav.jsp" %>
    
    <div id="statisticsCharts">
        <div class="container padding-top-l">
      
   		 <p  style="color:#07418F;margin-left:70px;margin-right:60px;font-size:11px; text-decoration:bold; ">Welcome to the new Summary Page. This page displays summary statistics regarding the data indexed by LIFEwrx.  Statistics are currently calculated only for those assays for which normalized perturbation data is available (9 out of 13 assays searchable in LIFEwrx).  Help is available by clicking on the help icon next to Bioassays.  All feedback is welcome and can be provided by clicking the "Provide Feedback" tab at the right side of the page.</p>           
    	
        	<i class="icon-info-sign" title="This page displays summary statistics regarding the data indexed by LIFEwrx. Statistics are displayed aggregated by Bioassay (e.g., L1000, KINOMEscan), Endpoint (e.g., z-score, percent inhibition), type of Perturbagen (e.g., Small Molecule) and Measured Entity (e.g., Kinase Protein). The display is interactive.  When first displayed, both the Bioassays and Endpoints charts display counts (log-scale) of perturbations for each category in the chart.  The Perturbagens chart displays counts by perturbagen category (e.g., cDNA), and the Measured Entities chart display counts by measure entity category. Clinking on a section in any of the charts will redisplay the statistics based on that selection.  For example clicking on the KINOMEscan bar in the Bioassayschart displays the number of KINOMEscan perturbations grouped by type (i.e., pKd and Percent Kinase Inhibition) in the Endpoints chart, the total number of Kinase Proteins tested in theMeasured Entities chart and the total number of Small Molecules tested in the Perturbagens chart." >?</i>
            <div id="assayStats" class="span12">
                <!-- <label><strong>Assay Constituents Summary</strong></label> -->
                <div id="participantChart"></div>	
            </div>
            
            <!-- 
            <div id="perturbationStats" class="span12">
                <label><strong>Perturbation Summary</strong></label>
                <div id="perturbationChart"></div>
            </div>  -->
        </div>
    </div>	
    <script type="text/javascript" src="/life/web/js/assayStatisticsData.js"></script>
    <script type="text/javascript" src="/life/web/js/assayStatistics.js"></script>
</body>
<%@ include file = "footer.jsp" %>
</html>	