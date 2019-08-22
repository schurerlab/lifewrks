<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<title>Summary</title>
	<%@ include file = "require.jsp" %>
   	<%@ include file = "imports.jsp" %>
	<script language="javascript">
		
		var participant_id = <%=request.getParameter("input")%>;
		var mode = "<%=request.getParameter("mode")%>";
		if(mode == "SmallMolecule"){
			mode = "compound";
		}else{
			mode = mode.toLowerCase();
		}
		
		$(function() {
			processQuery();
		});
		function processQuery()
		{
			$.getJSON("/life/fetch-summary", {input: participant_id, mode: mode}, loadSummary);
	     	$.getJSON("/life/bars-fetcher?input="+participant_id, processOverview);
		}
		function displayErrMsg(error){
			 $('#message').show();
			 setTimeout("$('#message').fadeOut('slow')", 4000);
			 $('#message').removeClass().addClass('errorHighlight');
			 $('#message').html(error);
		}
		function emptyCheck(field){
			if(field == ""){ 
				return false;
			}else{
				 return true;
			}	 
		}
		function booleanCheck(field){
			if(field == 1){ 
				return "true";
			}else{
				 return "false";
			}	 
		}
	  	function loadSummary(response){
			var summaryStr = "";
			if(mode=="protein"){
	       		summaryStr = (emptyCheck(response.kinasename)  ? "<br><strong>Name:</strong> "+response.kinasename : "")
		       				+(emptyCheck(response.proteinname) ? " <br><strong>Symbol:</strong> "+response.proteinname : "")
		            		+(emptyCheck(response.modification) ? "<br><strong>Modification:</strong> "+response.modification : "")
		                    +(emptyCheck(response.mutation) ? "<br><strong>Mutation:</strong> "+response.mutation : "")
		                    +(emptyCheck(response.uniprotid) ? "<br><strong>Uniprot Id:</strong> <a href=\"http://www.uniprot.org/uniprot/"+response.uniprotid+"\" target=\"_blank\">"+response.uniprotid+"</a>": "")
		                    +((emptyCheck(response.sourceid) && (response.sourceid != response.proteinid))? "<br><strong>LINCS facility ID:</strong> <a href=\"http://dev.lincs.hms.harvard.edu/db/proteins/"+response.sourceid+"\" target=\"_blank\">"+response.sourceid+"</a>": "")
		                    +(emptyCheck(response.kinasedescription)  ? "<br><strong>Kinase Description:</strong> "+response.kinasedescription : "")
							+(emptyCheck(response.organism) ? "<br><strong>Organism:</strong> "+response.organism : "")
		       				+(emptyCheck(response.Spkinasedomain)  ? "<br><strong>Kinase Domain:</strong> "+response.Spkinasedomain : "")
		       				+(emptyCheck(response.kinasegroup)  ? "<br><strong>Group:</strong> "+response.kinasegroup : "")
		       				+(emptyCheck(response.kinasecategory)  ? "<br><strong>Kinase Category:</strong> "+response.kinasecategory : "" )
		       				+(emptyCheck(response.kinasefamily)  ? "<br><strong>Kinase Family:</strong> "+response.kinasefamily : "")
		       				+(emptyCheck(response.gatekeeper)  ? "<br><strong>Gate Keeper:</strong> "+response.gatekeeper : "")
		       				+(emptyCheck(response.kinasehingei1)  ? "<br><strong>Kinase Hingei1:</strong> "+response.kinasehingei1 : "")
		       				+(emptyCheck(response.kinasehingei3)  ? "<br><strong>Kinase Hingei3:</strong> "+response.kinasehingei3 : "");
		       				
			}else if(mode == "compound"){
				   var pubchemId = response.pubchemid;
				   if(null!= response.pubchemid && ""!= response.pubchemid){
		           		 var imageStr = "<div id=\"imageStr\"><img src = '/chemfile?type=cid&format=jpeg:w300,h300&id="+pubchemId+"'/>";  
		           		 $("#summaryImg").html(imageStr); 
				   }
				   var pdbcode = response.pdbcode;
				   var pdblinks = "";
				   if(pdbcode != ""){
						pdbcode = pdbcode.split(",");
						for(var i = 0; i<pdbcode.length;i++){
							pdblinks += "<a href=\"http://www.rcsb.org/pdb/explore/explore.do?structureId="+pdbcode[i]+"\" target=\"_blank\">"+pdbcode[i]+"</a>";
							if(i < pdbcode.length-1){
								pdblinks += ",";
							}
						}
				   }
				   summaryStr = (emptyCheck(response.iupac_name)? "<br><strong>Name:</strong> "+response.smallmoleculename : "")
					  			+(emptyCheck(response.lincs_id)  ? "<br><strong>LINCS ID:</strong> "+response.lincs_id : "")
					  			+(emptyCheck(response.pubchemid)  ? "<br><strong>PubChem CID:</strong> <a href=\"http://pubchem.ncbi.nlm.nih.gov/summary/summary.cgi?cid="+response.pubchemid+"\" target=\"_blank\">"+response.pubchemid+"</a>" : "")
						        +( (emptyCheck(response.Lincs_CompoundId) && (response.salt_id != response.Lincs_CompoundId))  ? "<br><strong>LINCS facility ID:</strong> <a href=\"http://dev.lincs.hms.harvard.edu/db/sm/"+response.salt_id+"/"+"\" target=\"_blank\">"+response.Lincs_CompoundId+"</a>" : "")
				                +(emptyCheck(response.allcategories)  ? "<br><strong>Categories:</strong> "+response.allcategories : "")
				                +"<table class = 'sub-summary'>"
									+"<tr><td>" +(emptyCheck(response.molecularweight)  ? "<strong>MW:</strong> "+parseFloat(response.molecularweight).toFixed(1) : "")+"</td>"
									+"<td>"+(emptyCheck(response.molecularformulae)  ? "<strong>MF:</strong> "+response.molecularformulae : "")+"</td></tr>"
									+"<tr><td>"+(emptyCheck(response.tpsa)  ? "<strong>TPSA:</strong> "+response.tpsa : "")+"</td>"
									+"<td>"+(emptyCheck(response.logp)  ? "<strong>Logp:</strong> "+parseFloat(response.logp).toFixed(2) : "")+"</td>"
									+"<td>"+(emptyCheck(response.logd)  ? "<strong>Logd:</strong> "+parseFloat(response.logd).toFixed(2) : "")+"</td></tr>"
									+"<tr><td>"+(emptyCheck(response.rotatable_bonds)  ? "<strong>RB:</strong> "+response.rotatable_bonds : "")+"</td>"
									+"<td>"+(emptyCheck(response.hba)  ? "<strong>HBA:</strong> "+response.hba : "")+"</td>"
									+"<td>"+(emptyCheck(response.hbd)  ? "<strong>HBD:</strong> "+response.hbd : "")+"</td></tr>"
									+"<tr><td>"+(emptyCheck(response.lipinski_r5)  ? "<strong>Lipinski Rule of 5:</strong> "+booleanCheck(response.lipinski_r5) : "") +"</td>"
									+"<td>"+(emptyCheck(response.lipinski_r3)  ? "<strong>Lipinski Rule of 3:</strong> "+booleanCheck(response.lipinski_r3) : "")+"</td></tr>"
									+"<tr><td>"+(emptyCheck(response.bioavailability)  ? "<strong>Bioavailability:</strong> "+booleanCheck(response.bioavailability) : "")+"</td>"
									+"<td>"+(emptyCheck(response.lead_likeness)  ? "<strong>Lead Likeness:</strong> "+booleanCheck(response.lead_likeness) : "")+"</td></tr>"
								+"</table>"
								+(emptyCheck(response.pdbligandcode)  ? "<strong>Pdb Ligand Code:</strong> <a href=\"http://www.rcsb.org/pdb/ligand/ligandsummary.do?hetId="+response.pdbligandcode+"\" target=\"_blank\">"+response.pdbligandcode+"</a>" : "")
								+(emptyCheck(pdblinks) ? "<br><strong>PDB Code:</strong>	"+pdblinks : "") 
								+(emptyCheck(response.drugbankid)  ? "<br><strong>Drug Bank Id:</strong> "+response.drugbankid : "")
								+(emptyCheck(response.probeurl)  ? "<br><strong>Probe Url:</strong> "+response.probeurl : "");

			}else if(mode == "cellline"){
				  summaryStr = (emptyCheck(response.name)  ? "<br><strong>Name:</strong> "+ response.name : "")
							+(emptyCheck(response.providerid)  ? "<br><strong>Provider Id:</strong> "+response.providerid : "")
							+(emptyCheck(response.lincssourceid)  ? "<br><strong>Lincs Source Id:</strong> "+response.lincssourceid : "")
							+(emptyCheck(response.disease)  ? "<br><strong>Disease:</strong> "+response.disease : "")
							+(emptyCheck(response.celltype)  ? "<br><strong>Cell Type:</strong> "+response.celltype : "")
			                +(emptyCheck(response.growthproperties)  ? "<br><strong>Growth Properties:</strong> "+response.growthproperties : "")
			                +(emptyCheck(response.organ)  ? "<br><strong>Organ:</strong> "+response.organ : "")
							+(emptyCheck(response.geneticmodification)  ? "<br><strong>Genetic Modification:</strong> "+response.geneticmodification : "")
							+(emptyCheck(response.gender)  ? "<br><strong>Gender:</strong> "+response.gender : "")
							+(emptyCheck(response.tissue) ? "<br><strong>Tissue:</strong> "+response.tissue : "")
							+(emptyCheck(response.provider) ? "<br><strong>Provider:</strong> "+response.provider : "")
							+(emptyCheck(response.organism)  ? "<br><strong>Organism:</strong> "+response.organism : "")
							+(emptyCheck(response.doid) ? "<br><strong>Do Id:</strong> "+response.doid : "");

			}else if(mode == "gene"){
				  summaryStr = (emptyCheck(response.name)  ? "<br><strong>Name:</strong> "+ response.name : "")
							  +(emptyCheck(response.entrezid)  ? "<br><strong>Entrez Id:</strong> <a href=\"http://www.ncbi.nlm.nih.gov/gene/?term="+response.entrezid+"\" target=\"_blank\">"+response.entrezid+"</a>" : "")
							  +(emptyCheck(response.description)  ? "<br><strong>Description:</strong> "+ response.description : "")
							  +(emptyCheck(response.organism)  ? "<br><strong>Organism:</strong> "+ response.organism : "");
			}

			$("#summaryWell").html(summaryStr);
	  	}

	  	function processOverview(response){
			var data = eval(response);
			for ( var i = 0; i < data.results.length; i++){

				var currType = data.results[i];
				var epid = currType.epid;

				var hmStr ="<div id ='waitImg'></div><div id='hmType'>"+ data.results[i].type + "</div>";
				
				hmStr += "<div id='summaryEpTime'><select id = 'smryTpDrpDown"+i+"'><option value = 'default'>-- Select Time Point --</option>";
				for(var j = 0; j < currType.timepoint.length; j++){
					hmStr += "<option value = \""+ currType.timepoint[j]+"\">"+currType.timepoint[j]+"</option>";
				}
				hmStr += "</select></div>"; 
				
				
				hmStr += "<div id='summaryEpCon'><select id = 'smryConDrpDown"+i+"'><option value = 'default'>-- Select Concentration --</option>";
				for(var k = 0; k< currType.concentration.length; k++){
					hmStr += "<option value = \""+ currType.concentration[k]+"\">"+currType.concentration[k]+"</option>";
				}
				hmStr += "</select></div>";

				hmStr += "<div id='summarySubmit'><div id='contextMenu' style=\"margin-top: 15px;\">"+
					     " <a href = '#'  class = 'cMenu manage' style='margin-top: 0px;' title = 'Submit' onclick='getUpdatedHm(\"" +i+ "\","+epid+");'>Submit</a>"+
					     "</div></div>";
				
				
			    hmStr += "<div class = 'hmOverview' id = 'hmOverview"+i+"'>";
				var activePct = (parseInt(currType.statistics[0].count) * 100 / (parseInt(currType.statistics[0].count) + parseInt(currType.statistics[1].count)));
				hmStr += "<a href = '#' onClick = 'loadDetails(\""
						+ currType.type
						+ "\",\"active\","
						+ i+ "," 
						+ epid + ");'><div style = 'float: left; background-color: green; height: 20px; width: "
						+ activePct + "%;'>" + currType.statistics[0].measure
						+ " (" + currType.statistics[0].count + ")</div></a>";
				hmStr += "<a href = '#' onClick = 'loadDetails(\""
						+ currType.type
						+ "\",\"inactive\","
						+ i + "," 
						+ epid + ");'><div style = 'float: left; background-color: red; height: 20px; width: "
						+ (99.9 - activePct) + "%;'>"
						+ currType.statistics[1].measure + " ("
						+ currType.statistics[1].count + ")</div></a>";
				hmStr += "</div><div class = 'hmDetails' id = 'hmDetails"+i+"'></div><br><br>";
				$("#heatMapWell").append(hmStr);
			}
		}
		
	    function loadDetails(type, measure, detailId,epid) {
		    var newTp = $('#smryTpDrpDown'+detailId+' :selected').val();
			var newConc = $('#smryConDrpDown'+detailId+' :selected').val();

			if(newTp != selectedTime) 	newTp = selectedTime;
			if(newConc != selectedCon) 	newConc = selectedCon;
			
			var url = "/life/HeatMapDataFetcher?input=" + participant_id + "&type=" + type + "&measure=" + measure;

			//update HeatMapDataFetcher url with tp & conc only when tp & conc is seleted and submit is clicked 
			if(newTp !== "default" && newConc !== "default" && (currentHm == detailId)){
				url += "&tp=" + newTp + "&conc=" +newConc + "&epid=" +epid;
			}
			$("#waitImg").html('<img src="/life/web/images/wait.gif"/>');
			$.getJSON(url, function(response) {
							var data = eval(response);
							var hmStr = "";
							for ( var i = 0; i < data.results.length; i++) {
								var currType = data.results[i];
								var r = parseFloat(currType.value);
								var percent = ((100 - r) / 10) * 100;
								var red = Math.ceil(percent);
								hmStr += "<a href = '#' class = 'cell'  rel='perturbation-summary?input="+currType.eid+"' title = '"
										+ currType.participant + " (" + currType.value
										+ ")' style = 'background-color:rgb(" + (red * 5) + ","
										+ (red * 10) + "," + (255 - (red * 10)) + ");'></a>";
							}
							$("#hmDetails" + detailId).html(hmStr);
							$("#waitImg").html("");
							$('a.cell').cluetip({
								width: '200px',
								activation: 'click',
								cluetipClass: 'rounded',
								arrows: true,
								ajaxProcess:      function(response) {
									var data =  eval("("+response+")");
									var currData = data.results[0];

									var countsStr = "Assay Name: "+currData.assayname +"<br>"
									countsStr += "Assay Type: "+currData.assaytype +"<br>"
									countsStr += "Assay Format: "+currData.assayformat +"<br>"
									countsStr += "Experiment Name: "+currData.experimentname +"<br>";

									for(var i = 0; i<currData.participant.length; i++){
										countsStr += currData.participant[i].type + ":" + currData.participant[i].name + "<br>";
									}
									countsStr += 	"Value: "+currData.value +"<br>"
									countsStr += "Time: "+currData.time +"<br>"
									countsStr += "Time Unit: "+currData.timeunit +"<br>"
									countsStr += "Concentration: "+currData.concentration+"<br>"
									countsStr +=  "Concentration Unit: "+currData.concentrationunit;  
				                    return countsStr;
								}});
		  });
	   }

	   var currentHm,selectedTime,selectedCon,updatedEpid;	   
	   function getUpdatedHm(hmId,epid){
		   currentHm = hmId;
		   updatedEpid = epid;	
			
		   selectedTime = $('#smryTpDrpDown'+hmId+' :selected').val();
		   selectedCon = $('#smryConDrpDown'+hmId+' :selected').val();

		   if(selectedTime == "default" || selectedCon == "default"){
			   displayErrMsg("ERROR: Please select Time Point/Concentration");
		   }else{
			   	$.ajax( {url: "ProcessingDataFetcher", data: {input: participant_id, epid:updatedEpid, tp:selectedTime, conc:selectedCon},success: processUpdatedHm , dataType: "json", type: "GET" } );  
		   }	
	   }	

	   function processUpdatedHm(response){
		   var data = eval(response);
		   var currType = data.results[0];

		   //var updatedHmStr =  "<div class = 'hmOverview' id = 'hmOverview"+currentHm+"'>";
		   var activePct = (parseInt(currType.statistics[0].count) * 100 / (parseInt(currType.statistics[0].count) + parseInt(currType.statistics[1].count)));
		   var updatedHmStr = "<a href = '#' onClick = 'loadDetails(\""
					+ currType.type
					+ "\",\"active\","
					+ currentHm+ "," 
					+ updatedEpid + ");'><div style = 'float: left; background-color: green; height: 20px; width: "
					+ activePct + "%;'>" + currType.statistics[0].measure
					+ " (" + currType.statistics[0].count + ")</div></a>";
			updatedHmStr += "<a href = '#' onClick = 'loadDetails(\""
					+ currType.type
					+ "\",\"inactive\","
					+ currentHm+ "," 
					+ updatedEpid + ");'><div style = 'float: left; background-color: red; height: 20px; width: "
					+ (99.9 - activePct) + "%;'>"
					+ currType.statistics[1].measure + " ("
					+ currType.statistics[1].count + ")</div></a>";
			//updatedHmStr += "</div><div class = 'hmDetails' id = 'hmDetails"+currentHm+"'></div><br><br>"; 
		    $('#hmOverview'+currentHm).html(updatedHmStr);
		    $('#hmDetails' + currentHm).html("");
		}   

  </script>
 </head>
 
	<body class = "splash">
	<%@ include file = "topmenu.jsp" %>
		<div class = "content">
			<div id = "header">
				<div id = "logo"></div>
			</div>
			<div><span id = "summaryImg"></span></div>
			<div id = "summaryWell" ></div>
			<br><br>
			<div id="heatMapWell"></div>
			<div id = "dynamic"></div>
		</div>
	<%@ include file = "footer.jsp" %>
		
	</body>


