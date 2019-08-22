<html>
<head>
   <title>Compound Summary</title>
   <link rel = "stylesheet" href="/life/web/css/style.css" type="text/css"/>
   <link rel="stylesheet" href="/life/web/css/jquery.datatables.css" type="text/css">
   <link rel="stylesheet" type="text/css" href="element.css" />
		
   <script type = "text/javascript" src = "/life/web/js/jquery-1.7.1.min.js"></script>
   <script type="text/javascript" language="javascript" src="/life/web/js/jquery.dataTables.min.js"></script>
 
   <script language="javascript">

    $(function() {
     processQuery();
    });
	function processQuery(){
         $.getJSON("/life/compound-summary?input="+<%=request.getParameter("input")%>, processDetails);
     }
     function processDetails(response){	   
           var pubchemId = response.pubchemid;
		   if(null!= response.pubchemid && ""!= response.pubchemid){
           			var displayImage = "<div style = 'float:left; margin-top: 0px;'><img src = '/chemfile?type=cid&format=jpeg:w300,h300&id="+pubchemId+"'/>";  
           			$("#displayImage").html(displayImage); 
		   }
           
           var compoundDetails ="<br><br><strong>SID:</strong> "+response.Lincs_CompoundId
                    +"<br><strong>Name:</strong> "+response.smallmoleculename
                    +"<br><strong>Molecular Weight:</strong> "+response.molecularweight
                    +"<br><strong>Molecular Formulae:</strong> "+response.molecularformulae
					+"<br><strong>Smiles:</strong> "+response.smile
					+"<br><strong>Pubchem Cid:</strong> "+response.pubchemid;
                        $("#compoundDetails").html(compoundDetails);
      }

   </script>
</head>
           
  <body class = "splash">
 	<div class = "topmenu"></div> 
	<div class = "content">
		<div id = "header">
				<div id = "logo"></div>
		</div>
		<div id = "tabHolder" >
		     <div id = "tabs" > Substance Summary</div>
		</div>
		<div >
   	  		<span id = "displayImage" style="float:left;"></span>
   	    </div>
		<div> <span id = "compoundDetails" ></span></div>
	 </div>
 </body>


</html>


