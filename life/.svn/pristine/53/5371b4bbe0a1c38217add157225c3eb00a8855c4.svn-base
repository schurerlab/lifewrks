<%@ page import="java.util.ArrayList" %> <%@ page
import="java.util.Arrays" %> <%@ page import="java.util.List" %> <%@
page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<title>Life - LINCS Information FramEwork</title>
<meta name="viewport" content="width=1000, height=1000, initial-scale=1" />

<%@ include file = "require.jsp" %> <%@ include file = "imports.jsp" %>
<script type="text/javascript" src="/life/web/js/search.js"></script>
</head>
<body>
	<%@ include file = "nav.jsp" %>
	<section>
		<div class="page-container horizontal-container padding-top-m">
			<div id="content">
					<%@ include file = "search-bar.jsp" %>
				<div>
					<form name="worksetForm" id="worksetForm">
						<div id="header">
							<div id="view-control" class="float-left">
								<button title="Table View"
									class="view-table-button button-clicked drop-shadow-xs ui-btn-b ui-corner-all display-inline">Table
								</button>
								<button title="List View"
									class="view-list-button drop-shadow-xs ui-btn-b ui-corner-all display-inline">List
								</button>
							</div>
							<div id="contextMenu">
								<div id="message"></div>
								<button title="Download Data"
									class="drop-shadow-xs ui-btn-b ui-corner-all display-inline"
									onclick="download?input=<%=request.getParameter("q") %>">Download
								</button>
								<button title="Manage List"
									class="drop-shadow-xs ui-btn-b ui-corner-all display-inline"
									onclick="element-list?elementType=SmallMolecule">Manage
								</button>

								<button title="Add to List"
									class="drop-shadow-xs ui-btn-b ui-corner-all display-inline"
									onClick="$('#mode').val('add');editList();">Add To
									List</button>

								<input type="hidden" name="mode" id="mode" /> <input
									type="hidden" name="type" id="type" value="Protein" />
							</div>
						</div>
						<div id="pageNum"></div>
						<div id="resultsCount"></div>
						<div id="table-view">
							<div id="tabHolder" class="ui-btn-a">
								<div id="tabs">
									<a href="#" class="tab active" onClick="loadProteins();">Proteins</a>
									<a href="#" class="tab" onClick="loadCompounds();">Compounds</a>
									<a href="#" class="tab" onClick="loadAssays();">Assays</a> <a
										href="#" class="tab" onClick="loadCellLines();">Cell Lines</a>
									<a href="#" class="tab" onClick="loadGenes();">Genes</a> <a
										href="#" class="tab" onClick="loadData();">Data</a>
								</div>

							</div>


							<div id="dynamic"></div>
						</div>
						<div id="list-view"></div>
					</form>
				</div>
			</div>
		</div>
	</section>

	<%@ include file = "footer-search.jsp" %>

</body>
</html>