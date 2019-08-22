
			<div id="content" class="row ">
				<div class="span3">
					<a href="/life/"><img class="size-m" src="/life/web/images/logo-splash.png" /></a>
				</div>
				<div class="span9 padding-top-s position-relative">
					<form id="searchForm" class="position-relative"
						action="/life/search" method = "GET" onSubmit = "return validateSearch();">
					<input type = "hidden" id="load" name = "load" value="SmallMolecule">
						<div class="text-field-container" >

							<div id="Suggest">
								<input type="text" id="search-box" name="search"
									value="<% if(request.getParameter("search") != null) 
										out.print(request.getParameter("search"));
									else 
										out.print("Search across all LINCS data: compounds,cells,genes..."); %>"
									class="span8 qConcept width-almost-all text-field"/>
							</div>

							<input type="hidden" name="q" value="<%=request.getParameter("q") %>" class="qConcept qDefault" />
							<input type="hidden" name="wt" value="json" /> 
							<input type="hidden" name="indent" value="true" /> 
			
							<input type="hidden" id = "group" name="group" value="false" /> 
							<input type="hidden" id = "facet" name="facet" value="true" /> 
							
							<input type="hidden" name="facet.field" value="ProteinId" /> 
							<input type="hidden" name="facet.field" value="SmallMoleculeId" /> 
							<input type="hidden" name="facet.field" value="GeneId" /> 
							<input type="hidden" name="facet.field" value="CellLineId" /> 
							<input type="hidden" name="facet.field" value="AssayTypeName" /> 
							<input type="hidden" name="facet.field" value="PhosphoProteinId" /> 
							<input type="hidden" name="facet.field" value="ShRnaID" /> 
							<input type="hidden" name="facet.field" value="CdnaID" /> 
							<input type="hidden" name="facet.field" value="AntibodyId" />
							<input type="hidden" name="facet.field" value="NonKinaseProteinId" />
							<input type="hidden" name="facet.field" value="LigandProteinId" />
							
							<input type="hidden" name="group.field" id="groupField" value="ProteinId" /> 
							
							<input type="hidden" name="facet.mincount" value="1" /> 
							<input type="hidden" name="facet.limit" value="-1" />  
							<input type="hidden" name="rows" value="20" /> 
							<input type="hidden" id = "start" name="start" value="0" /> 
							<input type="hidden" id = "ngroups" name="group.ngroups" value = "true"/> 
							
							<div id="suggestions" class="position-relative"></div>
							<div id="suggestions2" class="position-relative"></div>

						</div>
						<div id="btn-search-wrapper">
							<button id="goConcept"
								class="search btn-info btn-large display-inline" value="">
									<i class="icon-search icon-white"></i>
							</button>
						</div>
					</form>
					<div id="tags-applied" class="align-left padding-top-xs"></div>
				</div>
			</div>
