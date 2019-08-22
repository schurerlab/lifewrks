<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>LIFE.wrx Saved List - <%=request.getParameter("elementType") %></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <%@ include file = "require.jsp" %>
    <script type="text/javascript" language="javascript" src="/life/web/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="/life/web/js/jquery.getUrlParam.js"></script>
    
    <!-- Le styles -->
    <link href="/life/web/css/summary-table.css" rel="stylesheet">
    <link href="/life/web/css/search.css" rel="stylesheet">

    <%@ page import="java.util.ArrayList" %>
    <%@ page import="java.util.Arrays" %>
    <%@ page import="java.util.List" %>
    <%@page import="java.util.*" %>

</head>
<body>
<%@ include file = "nav.jsp" %>
    <div class="container padding-top-xxl">
<%@ include file = "search-bar.jsp" %>
        
		<div class = "row-fluid align-left">    
		    <form name = "elementListForm" id = "elementListForm"> <!-- action=compare-list" -->
		        <input type = "hidden" name = "mode" id = "mode"/>
		        <input type = "hidden" name = "elementType" id = "elementType"/>
				<div id = "resultsContainer" class = "row-fluid align-left">
					<div class = "span12" id = "resultsHeader">
						<div class = "span3" id = "resultsCount"></div>
						<div class = "span4">
							<a id = "message"></a>
						</div>
						<div class = "span5 text-right pull-right">
							<a class="btn btn-small" href = "#"  onClick = "$('#mode').val('delete');deleteList();"><i class="icon-trash"></i> Delete</a>
							<!--  <a class="btn btn-small" href = "#"  onClick = "compare();"><i class="icon-align-left"></i> Compare</a> -->
						</div>
					</div>
					<div class="span12" id = "dynamic">
						<fieldset class="filters_grid checkbox_grid">
							<div id="tabs_group" class="tabs_group">
								<div id="tab_items" class="tabs_nav colored_bar">
								</div>
							</div>
						</fieldset>  
						<div class = "text-right pull-left">
							<a class="btn btn-small" href = "#"  onclick="prepareFormSubmit();"><i class="icon-filter"></i>Filter</a>
						</div>
					</div> 

				</div>
		    </form>
		</div>
    </div> <!-- /container -->
    <script type="text/javascript" language="javascript">
        elementType = "SmallMolecule";
        smVisited = false;
        pVisited = false;
        gVisited = false;
        clVisited = false;
        ppVisited = false;
        cdnaVisited = false;
        shVisited = false;
        
        <%
            //For errorMessage text
            out.print("elementTypeSmallMolecule = \""+"SmallMolecule"+"\";");
            out.print("elementWorksetSmallMolecule = \"");
            String elementType = "SmallMolecule"; 
            List<String> elementArr = new ArrayList();
            if(session.getAttribute("SmallMolecule"+"List")!= null){
                elementArr = (List)session.getAttribute("SmallMolecule"+"List");
                for(int i = 0; i < elementArr.size(); i++){
                        out.print(elementArr.get(i));
                        if(i != elementArr.size()-1){
                           out.print(",");
                        }
                }
            }
             out.print("\";");
        %>
        <%
              //For errorMessage text
              out.print("elementTypeProtein = \""+"Protein"+"\";");
              out.print("elementWorksetProtein = \"");
              String elementTypeP = "Protein"; 
              List<String> elementArrP = new ArrayList();
              if(session.getAttribute("Protein"+"List")!= null){
                elementArrP = (List)session.getAttribute("Protein"+"List");
                for(int i = 0; i < elementArrP.size(); i++){
                    out.print(elementArrP.get(i));
                    if(i != elementArrP.size()-1){
                     out.print(",");
                    }
                }
              }
              out.print("\";");
        %>
        <%
              //For errorMessage text
              out.print("elementTypeGene = \""+"Gene"+"\";");
              
              out.print("elementWorksetGene = \"");
              String elementTypeG = "Gene"; 
              List<String> elementArrG = new ArrayList();
              if(session.getAttribute("Gene"+"List")!= null){
                  elementArrG = (List)session.getAttribute("Gene"+"List");
                  for(int i = 0; i < elementArrG.size(); i++){
                      out.print(elementArrG.get(i));
                      if(i != elementArrG.size()-1){
                         out.print(",");
                      }
                  }
              }
              out.print("\";");
        %>
        <%
            //For errorMessage text
            out.print("elementTypeCellLine = \""+"CellLine"+"\";");
            out.print("elementWorksetCellLine = \"");
            String elementTypeC = "CellLine"; 
            List<String> elementArrC = new ArrayList();
            if(session.getAttribute("CellLine"+"List")!= null){
              elementArrC = (List)session.getAttribute("CellLine"+"List");
              for(int i = 0; i < elementArrC.size(); i++){
                  out.print(elementArrC.get(i));
                  if(i != elementArrC.size()-1){
                     out.print(",");
                  }
              }
            }
            out.print("\";");
        %>
        <%
            //For errorMessage text
            out.print("elementTypePhosphoProteins = \""+"PhosphoProteins"+"\";");
            out.print("elementWorksetPhosphoProteins = \"");
            String elementTypePP = "PhosphoProteins"; 
            List<String> elementArrPP = new ArrayList();
            if(session.getAttribute("PhosphoProteins"+"List")!= null){
              elementArrPP = (List)session.getAttribute("PhosphoProteins"+"List");
              for(int i = 0; i < elementArrPP.size(); i++){
                  out.print(elementArrPP.get(i));
                  if(i != elementArrPP.size()-1){
                     out.print(",");
                  }
              }
            }
            out.print("\";");
        %>
        <%
            //For errorMessage text
            out.print("elementTypeCdna = \""+"Cdna"+"\";");
            out.print("elementWorksetCdna = \"");
            String elementTypeCdna = "Cdna"; 
            List<String> elementArrCdna = new ArrayList();
            if(session.getAttribute("Cdna"+"List")!= null){
              elementArrCdna = (List)session.getAttribute("Cdna"+"List");
              for(int i = 0; i < elementArrCdna.size(); i++){
                  out.print(elementArrCdna.get(i));
                  if(i != elementArrCdna.size()-1){
                     out.print(",");
                  }
              }
            }
            out.print("\";");
        %>
        <%
            //For errorMessage text
            out.print("elementTypeShrnas = \""+"Shrnas"+"\";");
            out.print("elementWorksetShrnas = \"");
            String elementTypeShrnas = "Shrnas"; 
            List<String> elementArrShrnas = new ArrayList();
            if(session.getAttribute("Shrnas"+"List")!= null){
              elementArrShrnas = (List)session.getAttribute("Shrnas"+"List");
              for(int i = 0; i < elementArrShrnas.size(); i++){
                  out.print(elementArrShrnas.get(i));
                  if(i != elementArrShrnas.size()-1){
                     out.print(",");
                  }
              }
            }
            out.print("\";");
        %>
    </script>
        <!-- make sure that this custom js script tag is added only after jquery library script tag--> 
    <script type="text/javascript" language="javascript" src="/life/web/js/element.list.js"></script>
    
</body>
</html>