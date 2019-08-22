<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Get Data</title>
<link rel="stylesheet" type="text/css" href="/life/web/css/life_api.css" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<%@ include file="require.jsp"%>
<script type="text/javascript" src="/life/web/js/home.js"></script>
</head>
<body>
<%@ include file="nav.jsp"%>
<div class="container padding-top-xxl"><%@ include
	file="search-bar.jsp"%>
<div id="acknowledgement">
<div class="content padding-left-s">


<h2>Get Data</h2>
<br>
<p>This feature allows the user to search various assays generated
by LINCS Centers and get the data in a comma delimited file.</p>
<br>

<a id="usage" class="anchor"></a>
<h2>Usage</h2>
<br>
<pre>

		 	  <dt class="emph"><code>GET /getdata?searchTerm=&lt;query&gt;[optional Parameters]</code></dt>
		 
		 	  <dt><code>searchTerm=&lt;query&gt;</code> Get Assay Information and annotations for the specified search terms.</dt>
    		  <dt><code>limit=&lt;limit&gt;</code> Specify the number of records to return</dt>
			  <dt><code>skip=&lt;skip&gt;</code> Specify the number of records to skip</dt>
			  </pre> <a id="examples" class="anchor"></a> <br>
<h2>Examples</h2>
<br>
<pre>

    <li id="nobullet">Return all the data for Kinomescan Assay:</li>
    <li id="nobullet"><code><a
	href=/life/api/getdata?searchTerm=Kinomescan>/api/getdata?searchTerm=Kinomescan</code></a></li>
    <li id="nobullet">Return assay data for which Rock1 is a constituent:</li>
    <li id="nobullet"><code><a
	href=/life/api/getdata?searchTerm=Rock1>/api/getdata?searchTerm=Rock1</code></a></li>
    </pre> <br>
<h2>Description of fields</h2>
<br>
<table>
	<tbody>
	<tr>
			<td valign="top">
			<p><b>Fields</b></p>
			</td>
			<td  valign="top">
			<p><b>Description</b></p>
			</td>
		</tr>
		<tr>
			<td valign="top">
			<p>datarecordID</p>
			</td>
			<td valign="top">
			<p>A unique identifier for the row</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>datasetID</p>
			</td>
			<td valign="top"></td>
		</tr>

		<tr>
			<td valign="top">
			<p>assayName</p>
			</td>
			<td valign="top">
			<p>Name of the assay</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>bioAssay</p>
			</td>
			<td valign="top">
			<p>Bioassay Ontology (BAO) assay type (e.g., binding assay)</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>smCenterCompoundID</p>
			</td>
			<td valign="top">
			<p>Compound identifier assigned by the LINCS center</p>
			</td>
		</tr>


		<tr>
			<td valign="top">
			<p>smLincsID</p>
			</td>
			<td valign="top">
			<p>Global unique LINCS identifier (LSM ID)</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>smName</p>
			</td>
			<td valign="top">
			<p>Primary name of the molecule</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>clName</p>
			</td>
			<td valign="top">
			<p>Cell line name</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>clLincsID</p>
			</td>
			<td valign="top">
			<p>Global LINCS ID for the cell line</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>clCenterSpecificID</p>
			</td>
			<td valign="top">
			<p>ID assigned by the LINCS center for the cell line</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>ppName</p>
			</td>
			<td valign="top">
			<p>Protein name</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>ppLincsID</p>
			</td>
			<td valign="top">
			<p>Global LINCS ID for the protein</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>recordedPlate</p>
			</td>
			<td valign="top">
			<p>Plate ID if applicable</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>recordedWell</p>
			</td>
			<td valign="top">
			<p>Well ID if applicable</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>controlType</p>
			</td>
			<td valign="top"></td>
		</tr>

		<tr>
			<td valign="top">
			<p>datapointName</p>
			</td>
			<td valign="top">
			<p>BAO endpoint name (e.g., ic50)</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>datapointUnit</p>
			</td>
			<td valign="top">
			<p>Concentration<a name="_GoBack"></a> unit</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>datapointValue</p>
			</td>
			<td valign="top">
			<p>Endpoint value</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>smCenterSampleID</p>
			</td>
			<td valign="top">
			<p>Sample identifier assigned by the LINCS center</p>
			</td>
		</tr>

	</tbody>
</table>

<br>
<p>API call returns 1000 records by default</p>
<br>
</div>
</div>
</div>


<%@ include file="footer.jsp"%>
</body>

</html>