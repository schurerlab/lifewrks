<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Search Constituents</title>
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


<h2>Search Constituents</h2>
<p>This feature enables searching across LINCS Centers by
constituents.</p>
<br>

<a id="usage" class="anchor"></a>
<h2>Usage</h2>
<br>
<pre>

		 	  <dt class="emph"><code>GET /constituentinfo?searchTerm=&lt;query&gt;&constituentType=&lt;type&gt;[optional Parameters]</code></dt>
		 	  <dt><code>searchTerm=&lt;query&gt;</code> Get Assay Information and annotations for the specified search terms.</dt>
		 	  <dt><code>constituentType =&lt;type&gt;</code> Specify the Constituent Type for which to return results.  Supported types (case sensitive): Protein, Gene, SmallMolecule, ShRna, CellLine, cDNA, PhosphoProtein, Cytokines, Antibodies.</dt>
    		  <dt><code>limit=&lt;limit&gt;</code> Specify the number of records to return</dt>
			  <dt><code>skip=&lt;skip&gt;</code> Specify the number of records to skip</dt>
			  </pre> <a id="examples" class="anchor"></a>
			  <br>
<h2>Examples</h2>
<br>
<pre>

    <li id="nobullet">Return all proteins matching jnk*:</li>
    <li id="nobullet"><code><a href=/life/api/constituentinfo?searchTerm=jnk*&constituentType=KinaseProtein>/api/constituentinfo?searchTerm=jnk*&constituentType=KinaseProtein</code></a></li>
    <li id="nobullet">Return all the meta-info for jnk2:</li>
    <li id="nobullet"><code><a href=/life/api/constituentinfo?searchTerm=jnk2&constituentType=KinaseProtein>/api/constituentinfo?searchTerm=jnk2&constituentType=KinaseProtein</code></a></li>
    </pre>
    <br>
<h2>Description of fields</h2>
<br>
<p>The following types of fields are returned by the API. Field
returned are specific to the type of assay constituent requested (e.g.,
Kinase Protein)</p>
<br>
<p><b>&nbsp;&nbsp;&nbsp;Fields for kinase proteins</b></p>
<br>

<table>
	<tbody>
		<tr>
			<td width="20%">
			<p><b>Fields</b></p>
			</td>
			<td  width="80%">
			<p><b>Description</b></p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>ppProviderCatalogID</p>
			</td>
			<td valign="top">
			<p>Catalog ID assigned to the specific sample by the vendor</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>ppDescription</p>
			</td>
			<td valign="top">
			<p>The Description of the protein</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>ppHinge3</p>
			</td>
			<td valign="top">
			<p>Hinge 3 for the kinase</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>ppHinge1</p>
			</td>
			<td valign="top">
			<p>Hinge 1 for the kinase</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>ppName</p>
			</td>
			<td valign="top">
			<p>The primary name of the protein as chosen by LINCS</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>ppSymbol</p>
			</td>
			<td valign="top">
			<p>The symbol of the protein as chosen by LINCS</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>ppMutation</p>
			</td>
			<td valign="top">
			<p>Position where the protein is mutated</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>ppModification</p>
			</td>
			<td valign="top">
			<p>Post translation modification of the protein</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>ppGroup</p>
			</td>
			<td valign="top">
			<p>Kinase group</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>ppFamily</p>
			</td>
			<td valign="top">
			<p>Kinase family</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>ppOrganism</p>
			</td>
			<td valign="top">
			<p>Organism from which the protein is obtained</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>ppLincsID</p>
			</td>
			<td valign="top">
			<p>Unique LINCS internal identifier</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>ppGeneID</p>
			</td>
			<td valign="top">
			<p>Entrez ID</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>ppUniprotID</p>
			</td>
			<td valign="top">
			<p>Uniprot ID</p>
			</td>
		</tr>


		<tr>
			<td valign="top">
			<p>nameOfAssays</p>
			</td>
			<td valign="top">
			<p>List of assays in which the protein participated</p>
			</td>
		</tr>

	</tbody>
</table>
<br>
<p><b>&nbsp;&nbsp;&nbsp;Fields for phospho proteins</b></p>
<br>
<table>
	<tbody>
		<tr>
			<td width="20%">
			<p><b>Fields</b></p>
			</td>
			<td width="80%">
			<p><b>Description</b></p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>ppBasePeptide</p>
			</td>
			<td valign="top">
			<p>Phosphoprotein base peptide</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>ppCluster</p>
			</td>
			<td valign="top">
			<p>Cluster of the phosphoprotein</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>ppClusterCode</p>
			</td>
			<td valign="top">
			<p>Cluster code of the phosphoprotein</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>ppGeneID</p>
			</td>
			<td valign="top">
			<p>Gene ID of the phosphoprotein</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>ppGeneSymbol</p>
			</td>
			<td valign="top">
			<p>Gene symbol of the phosphoprotein</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>ppProteinID</p>
			</td>
			<td valign="top">
			<p>Phosphoprotein id choosen by LINCS</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>ppModifiedPeptide</p>
			</td>
			<td valign="top">
			<p>Modified peptide sequence</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>ppName</p>
			</td>
			<td valign="top">
			<p>Phophoprotein name choosen by links</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>ppPhosphosite</p>
			</td>
			<td valign="top">
			<p>Phosphosite of the protein</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>ppProbeID</p>
			</td>
			<td valign="top">
			<p>Probe id of the phosphoprotein</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>ppUniProtID</p>
			</td>
			<td valign="top">
			<p>Uniprot ID of the protein</p>
			</td>
		</tr>

		

		<tr>
			<td valign="top">
			<p>nameOfAssays</p>
			</td>
			<td valign="top">
			<p>List of assays in which the phosphoprotein participated</p>
			</td>
		</tr>

	</tbody>
</table>
<br>
<p><b>&nbsp;&nbsp;&nbsp;Fields for transcribed genes</b></p>
<br>

<table>
	<tbody>
		<tr>
			<td width="20%">
			<p><b>Fields</b></p>
			</td>
			<td width="80%">
			<p><b>Description</b></p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>gnID</p>
			</td>
			<td valign="top">
			<p>Gene ID chosen by LINCS</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>gnDescription</p>
			</td>
			<td valign="top">
			<p>Gene description</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>gnName</p>
			</td>
			<td valign="top">
			<p>Gene name selected by lincs</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>gnOrganism</p>
			</td>
			<td valign="top">
			<p>Organism to which the gene belongs to</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>gnEntrezID</p>
			</td>
			<td valign="top">
			<p>Entrez ID for the gene</p>
			</td>
		</tr>

		

		<tr>
			<td valign="top">
			<p>nameOfAssays</p>
			</td>
			<td valign="top">
			<p>List of assays in which the gene participated</p>
			</td>
		</tr>

	</tbody>
</table>
<br>
<p><b>&nbsp;&nbsp;&nbsp;Fields for cells</b></p>
<br>

<table>
	<tbody>
		<tr>
			<td width="20%">
			<p><b>Fields</b></p>
			</td>
			<td width="80%">
			<p><b>Description</b></p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>clCatalogID</p>
			</td>
			<td valign="top">
			<p>Catalog ID assigned to the specific sample by the vendor</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>clGender</p>
			</td>
			<td valign="top">
			<p>Gender of the organism from which the cell is obtained</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>clGeneticMutation</p>
			</td>
			<td valign="top">
			<p>Genetic modification of the cell</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>clLincsID</p>
			</td>
			<td valign="top">
			<p>Unique LINCS internal identifier</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>clLincsSourceID</p>
			</td>
			<td valign="top">
			<p>LINCS center-specific cell line ID</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>clName</p>
			</td>
			<td valign="top">
			<p>The primary name for the cell line as chosen by LINCS</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>clOrgan</p>
			</td>
			<td valign="top">
			<p>Organ to which the cell belongs to</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>clOrganism</p>
			</td>
			<td valign="top">
			<p>Organism to which the cell belongs to</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>clProvider</p>
			</td>
			<td valign="top">
			<p>Provider from which cells are procured</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>clProviderID</p>
			</td>
			<td valign="top">
			<p>Provided id for the cell</p>
			</td>
		</tr>

		

		<tr>
			<td valign="top">
			<p>nameOfAssays</p>
			</td>
			<td valign="top">
			<p>List of assays in which the cell participates</p>
			</td>
		</tr>

	</tbody>
</table>
<br>
<p><b>&nbsp;&nbsp;&nbsp;Fields for shRNAs</b></p>
<br>

<table>
	<tbody>
		<tr>
			<td width="20%">
			<p><b>Fields</b></p>
			</td>
			<td width="80%">
			<p><b>Description</b></p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>shrna6MerSequence</p>
			</td>
			<td valign="top">
			<p>6 mer sequence of the shRNA</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>shrna7MerSequence</p>
			</td>
			<td valign="top">
			<p>7 mer sequence of the shRNA</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>shrnaID</p>
			</td>
			<td valign="top">
			<p>Unique LINCS internal identifier</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>shrnaName</p>
			</td>
			<td valign="top">
			<p>The primary name for the shrna as chosen by LINCS</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>shrnaPerturbagenID</p>
			</td>
			<td valign="top">
			<p>LINCS center-specific shrna ID</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>shrnaTargetSequence</p>
			</td>
			<td valign="top">
			<p>Target sequence of the shRNA</p>
			</td>
		</tr>

	

		<tr>
			<td valign="top">
			<p>nameOfAssays</p>
			</td>
			<td valign="top">
			<p>List of assays in which the shRNA participates</p>
			</td>
		</tr>

	</tbody>
</table>
<br>
<p><b>&nbsp;&nbsp;&nbsp;Fields for cDNAs </b></p>
<br>

<table >
	<tbody>
		<tr>
			<td width="20%">
			<p><b>Fields</b></p>
			</td>
			<td width="80%">
			<p><b>Description</b></p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>cdnaID</p>
			</td>
			<td valign="top">
			<p>Unique LINCS internal identifier</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>cdnaName</p>
			</td>
			<td valign="top">
			<p>The primary name for the shrna as chosen by LINCS</p>
			</td>
		</tr>

	

		<tr>
			<td valign="top">
			<p>nameOfAssays</p>
			</td>
			<td valign="top">
			<p>List of assays in which the cdna participated</p>
			</td>
		</tr>

	</tbody>
</table>
<br>
<p><b>&nbsp;&nbsp;&nbsp;Fields for Small molecules</b></p>
<br>

<table>
	<tbody>
		<tr>
			<td width="20%">
			<p><b>Fields</b></p>
			</td>
			<td width="80%">
			<p><b>Description</b></p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>smCenterCompoundID</p>
			</td>
			<td valign="top">
			<p>Center specific compound ID of the (parent structure) assigned
			by the center</p>

			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>smSalt</p>
			</td>
			<td valign="top">
			<p>The primary name for the shrna as chosen by LINCS</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>smCenterSampleID</p>
			</td>
			<td valign="top">
			<p>Sample ID of the tested compound, referring to of the tested
			sample; assigned after local registry of the compound (center
			specific)</p>

			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>smLincsID</p>
			</td>
			<td valign="top">
			<p>The global LINCS ID (parent) compound (in a standardized
			representaiton)</p>

			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>smName</p>
			</td>
			<td valign="top">
			<p>The primary name for the (parent) compound (in a standardized
			representation) as chosen by LINCS</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>smAlternativeNames</p>
			</td>
			<td valign="top">
			<p>List of synonymous compound names, drug name (if applicable),
			and other alternative names</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>smProvider</p>
			</td>
			<td valign="top">
			<p>Information of the vendor or provider who supplied the sample</p>

			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>smProviderCatalogID</p>
			</td>
			<td valign="top">
			<p>ID or catalogue number assigned to the specific supplied
			sample by the vendor or provider</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>smPubchemCID</p>
			</td>
			<td valign="top">
			<p>CID that corresponds to the standardized parent compound in
			NCBI's PubChem database; after applying the same business rules</p>

			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>smChebiID</p>
			</td>
			<td valign="top">
			<p>ChEBI ID that corresponds to the standardized parent compound
			in NCBI's PubChem database; after applying the same business rules</p>

			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>smInChiParent</p>
			</td>
			<td valign="top">
			<p>InChi representation of parent (standardized) chemical
			structure chemical structure generated by LINCS business rules</p>
			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>smInChiKeyParent</p>
			</td>
			<td valign="top">
			<p>InChi key of parent (standardized) chemical structure
			generated by LINCS business rules</p>

			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>smSmilesParent</p>
			</td>
			<td valign="top">
			<p>Canonical SMILES representation of parent (standardized)
			chemical structure generated by LINCS business rules</p>

			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>smSmilesBatch</p>
			</td>
			<td valign="top">
			<p>Canonical SMILES representation of the actual sample (batch)
			structure</p>

			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>smMolecularMass</p>
			</td>
			<td valign="top">
			<p>Molecular mass of one molecule (including addends) of the
			compound in Daltons (unified atomic mass units)</p>

			</td>
		</tr>

		<tr>
			<td valign="top">
			<p>smMolecularFormula</p>
			</td>
			<td valign="top">
			<p>String representation of the compound with addends; type and
			number of the different atoms in the compound; without structural
			details</p>

			</td>
		</tr>


		<tr>
			<td valign="top">
			<p>nameOfAssays</p>
			</td>
			<td valign="top">
			<p>List of assays in which the small molecule participated</p>
			</td>
		</tr>

	</tbody>
</table>
<br>
<br>
<p>API call returns 10 records by default</p>
<br>
</div>

</div>
</div>

<%@ include file="footer.jsp"%>
</body>

</html>