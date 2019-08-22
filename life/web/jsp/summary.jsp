<!DOCTYPE html>
<html>
<head>
<title>LIFE.wrx Summary</title>
<%@ include file="require.jsp"%>
<%@ include file="imports.jsp"%>
<script src="/life/web/js/d3.v2.js"></script>
<script src="/life/web/js/d3.min.js"></script>
<script type="text/javascript" src="/life/web/js/summary.js"></script>
<script type="text/javascript" src="/life/web/js/coffee-wheel.js"></script>
<link href="/life/web/css/summary-table.css" rel="stylesheet">
<script type="text/javascript" src="/life/web/js/crossFilterSummary.js"></script>
<script type="text/javascript" src="/life/web/js/crossFilter/crossfilter.js"></script>
<script type="text/javascript" src="/life/web/js/heatMap.js"></script>
<script src="/life/web/js/crossFilter/d3.v2.js"></script>
<script src="/life/web/js/crossFilter/dc.js"></script>
<link rel="stylesheet" href="/life/web/css/dc.css">
<link rel="stylesheet" href="/life/web/css/search.css">
</head>

<body class="splash">

	<%@ include file="nav.jsp"%>
	<section>
		<div class="container padding-top-m">
			<div id="summary" class="padding-top-m"></div>
			
			<div class="span12 text-center" id="header-table">
				<a id="download"
					onClick="_gaq.push(['_trackEvent', 'Downloads', 'Summary', '<%=request.getParameter("mode")%>']);"
					class="btn btn-small btn-info"
					href="summary-download?input=<%=request.getParameter("input")%>&mode=<%=request.getParameter("mode")%>"><i
					class="icon-download icon-white"></i> Download</a> <a href="#" id="prev"
					onclick="Pagination('prev');" title='Previous Page'
					class="paginate_disabled_previous"></a> <a href="#" id="next"
					onclick="Pagination('next');" title='Next Page'
					class="paginate_disabled_next"></a>
			</div>
		</div>
		<div class="padding-vertical-m"></div>
	</section>

	<footer class="ui-bar-a text-blue text-shadow-white">
		<div id="header-content" class="horizontal-container"></div>
	</footer>

	<!-- Le javascript
	================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->

	<script type="text/javascript">
		$(function() {
			$("#download").click(
					function() {
						$("#download").html(
								"<i class='icon-time'></i>Downloading...");
						setTimeout(function() {
							$("#download").html(
									"<i class='icon-download'></i>Download");
						}, 5000);
					});
		});
		
		
	</script>
</body>
</html>