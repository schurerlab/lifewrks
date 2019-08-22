<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Acknowledgement</title>
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
			     <p>
			     	LIFE is addressing a range of technical and scientific challenges related to collecting, integrating, searching, visualizing and analyzing large and feature-rich (big) data.  
			     	All LINCS experimental assays can be searched using a simple User Interface LIFEwrx, which indexes the data from all the LINCS platforms. 
			     	LIFEwrx is the project demonstration that connects KINOMEScan profiles, KiNativ profiles, broad cancer cell line growth inhibition profiles, cue signal response profiles, 
			     	very large-scale gene expression signatures, phosphoproteomics signatures, single cell metabolomics profiles, single cell protein secretion profiles and other datasets. 
			     	LIFE is a knowledge based search system with an underlying ontology that semantically integrates the various LINCS signatures generated at different Centers based on relevant molecular entities
			        (such as small molecules, genes, proteins), model systems (such as cell lines) and other concepts (such as diseases).  
			        LINCS data is further enriched and cross-referenced by various external data sources.
			    </p><br>
			  
			 	<p>
			 		<a href="http://lifekb.org" target="_blank">The University of Miami LINCS Center</a> is a member of the <a href="http://www.lincsproject.org/" target="_blank">LINCS Consortium</a> 
			 		supported by the <a href="http://commonfund.nih.gov/LINCS/index" target="_blank">NIH Common Fund</a> under grant U01HL111561.  
			    </p> <br>
			    
				<strong>We gratefully acknowledge the following contributions:</strong><br><br>
				The LINCS Data Production Centers (U54) and Technology Centers (U01) for taking the time to work with us and provide the data and metadata.<br><br>
				
				<ul  style="padding-left:5%;">
					<li><a href="http://lincs.hms.harvard.edu" target="_blank">Harvard Medical School (HMS) LINCS Center (U54)</a>
						<ul style="padding-left:5%;">
							<li>LINCS assays indexed in LIFEwrx:  
								<a href="http://life.ccs.miami.edu/life/summary?mode=assay&input=Apoptosis%20Assay" target="_blank">Apoptosis assay</a>,
								<a href="http://life.ccs.miami.edu/life/summary?mode=assay&input=Cell%20Cycle%20State%20Assay" target="_blank"> Cell cycle state assay</a>,
								<a href= "http://life.ccs.miami.edu/life/summary?mode=assay&input=Cell%20Growth%20Inhibition%20Assay" target="_blank">Cell growth inhibition assay</a>,
								<a href="http://life.ccs.miami.edu/life/summary?mode=assay&input=KiNativ%20assay" target="_blank"> KiNativ assay</a>,
								<a href="http://life.ccs.miami.edu/life/summary?mode=assay&input=KINOMEscan%20assay" target="_blank">KINOMEScan assay</a>
							</li>
							<li>LINCS assays in progress to be indexed in LIFEwrx: Cue Signal Response</li>
						</ul>
					</li>	
					
					<li><a href="http://www.lincscloud.org" target="_blank">Broad Institute LINCS Center (U54)</a>
						<ul style="padding-left:5%;">
							<li>LINCS assay indexed in LIFEwrx:  <a href="http://life.ccs.miami.edu/life/summary?mode=assay&input=L1000%20Transcriptional%20Response%20Assay" target="_blank">L1000 transcriptional profiling assay</a></li>
						</ul>
					</li>
					<li><a href="http://www.lincscloud.org" target="_blank">Broad Institute LINCS Center (U54)</a>
						<ul style="padding-left:5%;">
							<li>LINCS assay indexed in LIFEwrx: <a href="http://life.ccs.miami.edu/life/summary?mode=assay&input=P100%20Phosphosite%20Profiling%20Assay" target="_blank">P100 phosphoproteomics profile assay </a></li>
						</ul>
					</li>
					<li>Arizona State University LINCS Center (U01)
						<ul style="padding-left:5%;">
							<li>LINCS assay in progress to be indexed in LIFEwrx: Single cell metabolomics</li>
						</ul>
					</li>
					<li>Columbia University LINCS Center (U01)
						<ul style="padding-left:5%;">
							<li>LINCS assay in progress to be indexed in LIFEwrx: Drug synergy</li>
						</ul>
					</li>
					<li>Yale University LINCS Center (U01)
						<ul style="padding-left:5%;">
							<li class="bioAssaySummaryText">LINCS assay in progress to be indexed in LIFEwrx: Single cell protein secretion</li>
						</ul>
					</li>
				</ul>
				
				<br>
				<p>
					The LINCS Data Working Group for the development of LINCS standards to define and annotate the metadata.The LINCS metadata specifications were used for a simple annotation format (SAF) to describe assays
				 	and screening results with explicit controlled vocabularies.  To view the <a href= "http://www.lincsproject.org/data/data-standards/" target="_blank">LINCS metadata standards click here</a>.  
				 	A recent publication on the LINCS metadata standards and SAF is available in PMID: <a href= "http://www.ncbi.nlm.nih.gov/pubmed/24518066" target="_blank">24518066</a> 
				 	<a href = "http://jbx.sagepub.com/content/early/2014/02/11/1087057114522514.full" target="_blank">doi:10.1177/1087057114522514 </a>
				</p><br>
				
				<p>
					Dr. Shamu and team at Harvard Medical School (HMS) LINCS Center (U54) have been key on the understanding of the complex assays in their platform, metadata annotations and the implementation of SAF into the
				    <a href = "http://lincs.hms.harvard.edu/resources/software-tools/" target="_blank">HMS DB API.</a>
				</p><br>
				
				<p>
					The cancer cell lines was manual curation led by Dr. Benes (MGH), the effort has been a collaborative effort between Harvard Medical School (HMS) LINCS Center, 
					Massachusset General Hospital (MGH) and University of Miami LINCS Center. 
				</p><br>
				
				The LINCS standardized small molecules have been annotated using external sources
				<ul style="padding-left:5%;">
					<li><a href="http://pubchem.ncbi.nlm.nih.gov" target="_blank">PubChem</a></li>
					<li><a href="http://www.drugbank.ca" target="_blank">DrugBank</a></li>
					<li><a href="http://tripod.nih.gov/npc/" target="_blank">NCATS Pharmaceutical Collection (NPC)</a></li>
					<li><a href="http://www.rcsb.org/pdb/home/home.do" target="_blank">Protein Data Bank (PDB)</a></li>
				</ul>	
				<br>
				The LINCS cell annotations were collected from the following external sources
				<ul style="padding-left:5%;">
					<li><a href="http://obofoundry.org/wiki/index.php/UBERON:Main_Page" target="_blank">Tissues - Uberon </a></li>
					<li><a href="http://disease-ontology.org" target="_blank">Diseases - Disease Ontology  </a></li> 
				</ul>	
				<br>
				The kinase classification from the following external sources
				<ul style="padding-left:5%;">
				 	<li><a href="http://kinase.com" target="_blank">Sugen phylogenetic tree - kinase.com</a></li>
				</ul>
				<br>
				Extended kinase domain classification based in internal research LINCS Assays
				<ul style="padding-left:5%;">
					<li><a href="http://bioassayontology.org" target="_blank">BioAssay Ontology - BAO </a></li>
				</ul>
				<br>
				Additional liinks to external sources
				<ul style="padding-left:5%;">
					<li><a href="http://www.drugbank.ca" target="_blank">Uniprot </a></li>
					<li><a href="http://www.drugbank.ca" target="_blank">Entrez Gene </a></li>
					<li><a href="http://www.drugbank.ca" target="_blank">BioPortal </a></li>	
				</ul>
				<br>
				The LINCS Computational Centers (U01) and computational collaborators
				<ul style="padding-left:5%;">
					<li><a href="http://icahn.mssm.edu/research/labs/maayan-laboratory" target="_blank">	Canvas Browser </a> visualization is contribution from Dr. Ma'ayan (Mt Sinai) </li>
					<li>Integration with <a href="http://www.eh3.uc.edu/GenomicsPortals/" target="_blank">iLINCS </a>is contribution from Dr. Medvedovic (Univerisity of Cincinnati)</li>
				</ul>
				<br>
				<p>Our work is based on the inspiration of being able to contribute back to the community by providing useful tools to address scientific questions.</p> <br>
		   </div>
	 	</div>
   </div>
   
   <%@ include file = "footer.jsp" %>
 </body>
 
</html>
