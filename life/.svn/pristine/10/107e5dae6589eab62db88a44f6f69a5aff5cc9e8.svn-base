<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Search Assays</title>
<link rel="stylesheet" type="text/css" href="/life/web/css/life_api.css" />
<link rel="stylesheet" type="text/css"
	href="/life/web/css/font-awesome.css" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<%@ include file="require.jsp"%>
<script type="text/javascript" src="/life/web/js/home.js"></script>
</head>
<body>
	<%@ include file="nav.jsp"%>
	<div class="container padding-top-xxl">
		<%@ include file="search-bar.jsp"%>
		<div id="acknowledgement">
		 	<div class = "content padding-left-s">


<h2>Search Assays</h2>
<br>
<p>This feature enables searching of various LINCS assays and
returns the meta-information describing the assays.</p>
<br>

<a id="usage" class="anchor"></a>
<h2>Usage</h2>
<br>
<pre>

		 	  <dt class="emph"><code>GET /assayinfo?searchTerm=&lt;query&gt;[optional Parameters]</code></dt>
		 
		 	  <dt><code>searchTerm=&lt;query&gt;</code> Get Assay Information and annotations for the specified search terms.</dt>
    		  <dt><code>limit=&lt;limit&gt;</code> Specify the number of records to return</dt>
			  <dt><code>skip=&lt;skip&gt;</code> Specify the number of records to skip</dt>
			  </pre> <a id="examples" class="anchor"></a>
			  <br>
<h2>Examples</h2>
<br>
<pre>

    <li id="nobullet">Return all the meta-info for Kinomescan:</li>
    <li id="nobullet"><code><a
		href=/life/api/assayinfo?searchTerm=Kinomescan>/api/assayinfo?searchTerm=Kinomescan</code></a></li>
    <li id="nobullet">Return only 2  assays in which the protein Rock1 is a constituent:</li>
    <li id="nobullet"><code><a
		href=/life/api/assayinfo?searchTerm=Rock1&limit=2>/api/assayinfo?searchTerm=Rock1&limit=2</code></a></li>
    </pre>
    <br>
<h2>Description of fields</h2>
<br>
<table>
    <tbody>
        <tr>
            <td width="147" valign="top">
                <p>
                    <u><b>Field Name</b></u>
                </p>
            </td>
            <td width="333" valign="top">
                <p>
                    <u><b>Description</b></u>
                </p>
            </td>
        </tr>
        <tr>
            <td width="147" valign="top">
                <p>
                    bioAssay
                </p>
            </td>
            <td width="333" valign="top">
                <p>
                    Bioassay Ontology (BAO) assay type (e.g., binding assay)
                </p>
            </td>
        </tr>
        <tr>
            <td width="147" valign="top">
                <p>
                    assayFormat
                </p>
            </td>
            <td width="333" valign="top">
                <p>
                    Assay format is a conceptualization of assays based on the biological and / or chemical features of the experimental system. For example
                    assay formats include biochemical assays - referring to assays with purified protein, cell-based - referring to assays in whole cells, or
                    organism-based - referring to assays performed in an organism.
                </p>
            </td>
        </tr>
        <tr>
            <td width="147" valign="top">
                <p>
                    assayTitle
                </p>
            </td>
            <td width="333" valign="top">
                <p>
                    Descriptive title of the assay (e.g., KINOMEScan assay)
                </p>
            </td>
        </tr>
        <tr>
            <td width="147" valign="top">
                <p>
                    assayProtocol
                </p>
            </td>
            <td width="333" valign="top">
                <p>
                    Protocol description
                </p>
            </td>
        </tr>
        <tr>
            <td width="147" valign="top">
                <p>
                    screeningFacility
                </p>
            </td>
            <td width="333" valign="top">
                <p>
                    The center which performed the assay
                </p>
            </td>
        </tr>
        <tr>
            <td width="147" valign="top">
                <p>
                    assayDescription
                </p>
            </td>
            <td width="333" valign="top">
                <p>
                    A brief summary of the assay
                </p>
            </td>
        </tr>
        <tr>
            <td width="147" valign="top">
                <p>
                    assaySourcelink
                </p>
            </td>
            <td width="333" valign="top">
                <p>
                    Source from where one can download the data
                </p>
            </td>
        </tr>
        <tr>
            <td width="147" valign="top">
                <p>
                    screeningPrincipalInvestigator
                </p>
            </td>
            <td width="333" valign="top">
                <p>
                    Information about the assay principal investigator
                </p>
            </td>
        </tr>
        <tr>
            <td width="147" valign="top">
                <p>
                    typesOfConstituents
                </p>
            </td>
            <td width="333" valign="top">
                <p>
                    List of types of constituents that participated in the assay e.g., Cells, Genes, Small Molecules
                </p>
            </td>
        </tr>
        <tr>
            <td width="147" valign="top">
                <p>
                    distinctConstituents
                </p>
            </td>
            <td width="333" valign="top">
                <p>
                    List of unique assay constituents
                </p>
            </td>
        </tr>
    </tbody>
</table>
<br>
<p>API call returns 10 records by default</p>
<br>
</div>
</div>
</div>


<%@ include file="footer.jsp"%>
</body>

</html>