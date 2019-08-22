<!DOCTYPE html>
<html>
<head>
<title>LIFE - LINCS Information FramEwork</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link href="/life/web/css/hopscotch.min.css" rel="stylesheet"></link>  <!-- By JP -->

<%@ include file="require.jsp"%>
<script src="/life/web/js/d3.v2.js"></script>
<script type="text/javascript" src="/life/web/js/home.js"></script>
<script type="text/javascript" src="/life/web/js/jquery.cookie.js"></script>

<script type="text/javascript">
	 $(document).ready(function() {
		 setCookie();
	 });
</script> 
	 
</head>
<body>
	<%@ include file="nav.jsp"%>
	<section>
		<div class="container padding-top-xxl">
			<%@ include file="search-bar.jsp"%>
			<div class="row">
				<div class="span3 chart-link" id="chart-link-assay">
					<a class="current-link">Browse by Bioassays(13)</a>
				</div>
				<div class="span3 chart-link" id="chart-link-cell-lines">
					<a>Browse by Cells(1076)</a>
				</div>
				<div class="span3 chart-link" id="chart-link-compounds">
					<a>Browse by Small molecules(9072)</a>
				</div>
				<div class="span3 chart-link" id="chart-link-proteins">
					<a>Browse by Kinase proteins(475)</a>
				</div>
			</div>
			
			<div id="chart"	class="position-relative row padding-top-m align-center">
		
				<!--<form id="chart-controls">
				  <label><input type="radio" name="mode" value="size"> Size</label>
				  <label><input type="radio" name="mode" value="count" checked> Count</label>
				</form>-->
				  <div id="chart-inner-circle-container">
					  <div id="chart-inner-circle">
						<div id="chart-sector-name" class="text-shadow-white text-blue">Bioassays</div>
					  </div>
				  </div>
				  <div id=chart-1>
						<div id="chart1-inner-circle-container">
							  <div id="chart1-inner-circle">
									<div class="text-shadow-white text-blue" id="organ-chart-sector-name"></div>
							  </div>
						</div>
				  </div>
				  <div id=chart-2>
						<div id="chart2-inner-circle-container">
						  <div id="chart2-inner-circle">
						   	 <div class="text-shadow-white text-blue" id="disease-chart-sector-name"></div>		
						  </div>
						</div>
				 </div>
			</div>
			<img style="margin-left:650px" src="/life/web/images/tour_btn-tourBlue.png" id="startTourBtn">
			<p style="color:Gray;margin-left:70px;margin-right:60px">The LIFE search engine integrates all LINCS content leveraging a semantic knowledge model and common <a href= "http://www.lincsproject.org/data/data-standards/">LINCS metadata standards</a>.
			<br>
			LIFE makes LINCS content discoverable and includes aggregate results linked to the <a href="http://lincs.hms.harvard.edu">Harvard Medical School</a> and <a href="http://www.lincscloud.org">Broad Institute</a> and other LINCS centers, who provide more information including experimental conditions and raw data. 
			</p>
		</div>
		
		<div id="presentationDialog">
			<iframe frameborder="0" webkitallowfullscreen="" mozallowfullscreen="" allowfullscreen="" width="550" height="400" src="http://prezi.com/embed/rbrtk-gyuase/?bgcolor=ffffff&amp;lock_to_path=0&amp;autoplay=0&amp;autohide_ctrls=0#"></iframe>
		</div>
	</section>
	<%@ include file = "footer.jsp" %>
<!--	<footer class="ui-bar-a text-blue text-shadow-white">
		<div id="header-content" class="horizontal-container"></div>
		 
	</footer>
	-->
	<script src="/life/web/js/hopscotch.js"></script> <!-- By JP -->
    <script src="/life/web/js/life_tour.js"></script> <!-- By JP -->
	
</body>
</html>