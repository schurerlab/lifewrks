<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>LIFE.wrx Structure Search</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">

	<%@ include file = "require.jsp" %>
	<link type="text/css" rel="stylesheet" href="/life/web/js/marvin/css/doc.css">
	<link type="text/css" rel="stylesheet" href="/life/web/js/marvin/css/github.css" />
	<script src="/life/web/js/marvin/rainbow-custom.min.js"></script>
	<script src="/life/web/js/marvin/jquery-1.9.1.min.js"></script>
	<script src="/life/web/js/marvin/webservices.js"></script>
	<script src="/life/web/js/marvin/util.js"></script>
<script>
		function submitSmile() {
				var tempsmileString = JSON.stringify(smileString).split('"').join('');
				var searchString = tempsmileString.split('\\').join('');
				
					$.ajax( {url : 'structure-search?molecule=' + searchString , success : function(response) {
						window.location.href=response;
        }})	
				}

var marvinSketcherInstance;
var smileString;
		$(document).ready(function handleDocumentReady (e) {
			getMarvinPromise("#sketch").done(function (sketcherInstance) {
				marvinSketcherInstance = sketcherInstance;

				$("#getSmilesButton").on("click", function handleGetSmilesButton () {
					smilesControl.getSmiles();
				});

				

			}).fail(function () {
				
			});
		});

		var errorConsole = (function () {
			var controlObject = {
				"reset" : function() {
					$("#error").children("p").html("");
				}
				,"print" : function(txt) {
					$("#error").children("p").html(txt);
				}
				,"isEmpty" : function() {
					return ($("#error").children("p").html() === "");
				}
			};

			return controlObject;

		}());

		var smilesControl = (function () {

			function getMolConvertURL() {
				var ws = getDefaultServices();
				return ws['molconvertws'];
			};
			
			function handleRequestError (qXHR, textStatus, errorThrown) {
				if (qXHR.status == 0) { // UNSENT request
					var errMsg = "ERROR: MolConvert failed.\nThe request has not been sent.\nThe request may violate the cross domain policy.";
					errorConsole.print(errMsg);
				} else {
					errorConsole.print("ERROR: MolConvert failed (status code "+ qXHR.status + ")\n" + qXHR.responseText);
				}
			};
				
			var controlObject = {

				"getSmiles": function getSmiles () {
					errorConsole.reset();
					var s = marvinSketcherInstance.exportAsMrv();
					var	data = JSON.stringify({
						"structure" : s,
						"inputFormat" : "mrv",
						"parameters" : "smiles"
					});

					$.ajax({
						"url": getMolConvertURL()
						,"type": "POST"
						,"dataType": "json"
						,"contentType": "application/json"
						,"data": data
					}).done(function (data, textStatus, jqXHR) {
						$("#smiles").val(data['structure']);
						smileString = JSON.stringify($("#smiles").val());
						submitSmile();
					}).fail(handleRequestError);
				}

		
			}

			return controlObject;

		}());

	</script>
	<style type="text/css">
		body {
			font-family: 'Strait',sans-serif;
		}
	</style>
</head>
<body>
  <div class="container padding-top-xxl">
	<div>
		<h4>Draw a Structure and Search</h4>
		
		<div style="margin-right: 50px;"></div>	
		<div class="resizable">
			<iframe src="/life/web/js/marvin/editor.html" id="sketch" class="sketcher-frame"></iframe>
		</div>
		<div id="btn-search-wrapper style="margin-right:30px;"><h4>SubStructure Search</h4>
			<button id="getSmilesButton"
			class="search btn-info btn-large display-inline" value="">
			<i class="icon-search icon-white"></i>
			</button>
	
			<h5 id="smiles"></h5>
			<a style="margin-left: 550px;" href = "http://www.chemaxon.com" target = "blank"><img src = "http://www.chemaxon.com/images/powered_100px.gif"></a>
		</div>
		
		<div id="error"></div>
	</div>
  </div>	
		
	
<%@ include file = "nav.jsp" %>
	<div class="container padding-top-xxl">
		<div class="row" id = "dynamic"></div>
	</div> <!-- /container -->

	<!-- Le javascript
	================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="http://code.jquery.com/jquery.min.js"></script>
	<script src="/life/web/js/bootstrap.min.js"></script>
	<script type="text/javascript" language="javascript" src="/life/web/js/jquery-ui-1.8.6.custom.min.js"></script>
	<script type="text/javascript" src="/life/web/js/life.js"></script>
	<script type="text/javascript" src="/life/web/js/suggest.js"></script>
</body>

<%@ include file = "footer.jsp" %>

</html>
