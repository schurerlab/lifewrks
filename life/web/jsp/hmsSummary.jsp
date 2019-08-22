<!DOCTYPE html>
<html>
<head>
<title>LIFE.wrx Summary</title> <%@ include file = "require.jsp" %> 
<%@ include file = "imports.jsp" %>

<script type="text/javascript" src="/life/web/js/summary.js"></script>
<link href="/life/web/css/summary-table.css" rel="stylesheet">
</head>

<body class="splash">

	<%@ include file = "nav.jsp" %>
	<section>
		<div class="container padding-top-m">
			<div id="summary" class="padding-top-m"></div>
			
			<div id="panination_bt" class="float-right">
				<a href = "#" id="prev" onclick="Pagination('prev');" title = 'Previous Page' class="paginate_disabled_previous"></a>
				<a href = "#" id="next" onclick="Pagination('next');" title = 'Next Page' class="paginate_disabled_next"></a>
			</div>
			<div class="padding-bottom-l">
			<div id="summaryLinks" class="span2"></div>
			<div id="dynamicDataTab" class="span10 float-right"></div>
			</div>
		</div>
	</section>

	<footer class="ui-bar-a text-blue text-shadow-white">
		<div id="header-content" class="horizontal-container"></div>
	</footer>

</body>
</html>