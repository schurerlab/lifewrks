var selectedAssay;
var searchTerm = $("#searchForm input[name=search]").val();
var loader="<img id= 'parallel-preloader' src = '/life/web/images/preloader.gif'/>";
	
function fetchViewData(){
	$('#dynamic').html("");
	$("#download").attr('href','download?input='+searchTerm+'&mode='+$("#load").val());
	
	$.ajax( { url: path + "/solr-example/select?wt=json&json.wrf=?&indent=false&facet=true&facet.field=AssayTypeName&rows=-1&facet.mincount=1",
		     data: { q:searchTerm},
		     success: loadViewDataLinks,
		     dataType: "jsonp"
	});
}
		
function loadViewDataLinks(data){
	
	response = eval(data);
	var assayTypeName = response.facet_counts.facet_fields.AssayTypeName;
	
	if(emptyCheck(assayTypeName)){
		var linkStr = "<div id='navDiv'><ul>" ;
		
		for(var i=0; i<assayTypeName.length ;i++){
			linkStr += "<li><a href='#' class='tabLink";
			if(i == 0){
				linkStr += " active";
			}
			
			//Hardcoding assay names to hide these assays as we dont have data for these assays.
			if(assayTypeName[i] != "Cue signal response assay" && assayTypeName[i] != "Proteomics cell state profiling assay" &&
			   assayTypeName[i] != "Cell viability assay" && assayTypeName[i] != "Dynamic ERK signaling single cell imaging assay" &&
			   assayTypeName[i] != "Cell signal response assay"){
			
					linkStr += "' onClick = 'loadAssayData(\"" +assayTypeName[i]+ "\",0);'>"+getLinkNames(assayTypeName[i])+"</a></li>";
			}
			
			if(assayTypeName[i] == "L1000 transcriptional profiling assay"){
				linkStr += "<div id='sigLinks'></div>";
			}
			if(assayTypeName[i] == "Apoptosis assay"){
				linkStr += "<div id='sigApoptosisLinks'></div>";
			}
			if(assayTypeName[i] == "Cell growth inhibition assay"){
				linkStr += "<div id='sigCellGrowthLinks'></div>";
			}
			if(assayTypeName[i] == "Cell cycle state assay"){
				linkStr += "<div id='sigCellCycleLinks'></div>";
			}
			i++;
		}
		linkStr += "</ul></div>";
		$(".tabLink").removeClass("active");
		$("#dataHolder").html(linkStr);
		$(".tabLink").click(function() {
			$(".tabLink").removeClass("active");
			$(this).addClass("active");
		});
		loadAssayData(assayTypeName[0]);
	}
}
		
function getLinkNames(assayName){
	if(assayName == "KINOMEscan assay") return "KINOMEScan Assay";
	else if (assayName == "Cell cycle state assay") return "Cell cycle state assay";
	else if (assayName == "Apoptosis assay") return "Apoptosis assay";
	else if (assayName == "Cell growth inhibition assay") return "Cell growth inhibition assay";
	else if (assayName == "L1000 transcriptional profiling assay") return "L1000 transcriptional profiling assay";
	else if (assayName == "KiNativ assay") return "KiNativ assay";
	else if (assayName == "P100 phosphoprotein profiling assay") return "P100 phosphoprotein profiling assay";
	else if (assayName == "Cue signal response assay") return "Cue signal response assay";
	else if (assayName == "Proteomics cell state profiling assay") return "Proteomics cell state profiling assay";
	else if (assayName == "Single cell protein secretion profiling assay") return "Single cell protein secretion profiling assay";
	else if (assayName == "Cell viability assay") return "Cell viability assay";
	else if (assayName == "Cell signal response assay") return "Cell signal response assay";
	else if (assayName == "Dynamic ERK signaling single cell imaging assay") return "Dynamic ERK signaling single cell imaging assay";
}

function loadAssayData(assay){
	
	$("#download").attr('href','download?input='+searchTerm+'&mode='+$("#load").val());
	
	selectedAssay = assay;

	if(selectedAssay == "Cell cycle state assay" || selectedAssay == "Apoptosis assay" || selectedAssay == "Cell growth inhibition assay" || selectedAssay == "L1000 transcriptional profiling assay"){
		$("#viewDataCrossFilterChart").hide();
		$("#viewDataP100CrossFilterChart").hide();
		$("#viewDataKKCrossFilterChart").hide();
		$("#viewDataProteinSecretionCrossFilterChart").hide();
		
		loadDiseases(selectedAssay);
			
	}else if(selectedAssay == "P100 phosphoprotein profiling assay"){
		
		$("#viewDataP100CrossFilterChart").hide();
		$("#viewDataCrossFilterChart").hide();
		$("#viewDataKKCrossFilterChart").hide();
		$("#viewDataProteinSecretionCrossFilterChart").hide();
		$('#sigLinkDropDown').remove();
		$('#dynamic').html(loader);
		
		var crossFilterJson = "";
		$.ajax({url : "http://lincs.ccs.miami.edu:8080/solr-example/select?wt=json&rows=10000&" +  // update rows
				      "fl=TimePoint,Concentration,PerturbationType,CellLineName,PerturbationMeasure,SmallMoleculeName,PhosphoProteinName&group=true&group.field=PerturbationId",
				data : { q : searchTerm	+ ' AssayTypeName:"' + selectedAssay + '"' },
				success : function(response) {
		
					var responseData = eval(response).grouped.PerturbationId.groups;
					
					crossFilterJson += "[";
					
					for (var i = 0; i < responseData.length; i++) {
						crossFilterJson += "{";
						crossFilterJson += '"Id":'+ i + ',"Concentration":' +responseData[i].doclist.docs[0].Concentration + ',"TimePoint":' +responseData[i].doclist.docs[0].TimePoint +
											 ',"CellLineName":' + '"' +responseData[i].doclist.docs[0].CellLineName + '"' + ',"PerturbationMeasure":' + responseData[i].doclist.docs[0].PerturbationMeasure + 
											 ',"Activity":' + '"' + responseData[i].doclist.docs[0].PerturbationType + '"' + ',"SmallMoleculeName":' + '"' + responseData[i].doclist.docs[0].SmallMoleculeName + '"' +
											 ',"PhosphoProteinName":' + '"' + responseData[i].doclist.docs[0].PhosphoProteinName + '"';
				
					   crossFilterJson += "}";						 
					   if(i < responseData.length-1){
						   crossFilterJson += ",";	
					   }
					}
					crossFilterJson += "]";
					$("#viewData").load("/life/web/templates/viewDataCF.html");
				},
				complete : function(){
					setTimeout(function(){
						$("#viewDataP100CrossFilterChart").show();
						$("#viewDataCrossFilterChart").hide();
						$("#viewDataKKCrossFilterChart").hide();
						$("#viewDataProteinSecretionCrossFilterChart").hide();
						
						getViewDataPPCrossFilter(crossFilterJson);
				    },500);
					
					$('#dynamic').html("");
				},
				dataType : "jsonp",
				type : "GET",
				'jsonp' : 'json.wrf'
		});
	}else if(selectedAssay == "KINOMEscan assay" || selectedAssay == "KiNativ assay"){
		
		$("#viewDataKKCrossFilterChart").hide();
		$("#viewDataCrossFilterChart").hide();
		$("#viewDataP100CrossFilterChart").hide();
		$("#viewDataProteinSecretionCrossFilterChart").hide();
		$('#sigLinkDropDown').remove();
		$('#dynamic').html(loader);
		
		var crossFilterJson = "";
		$.ajax({url : "http://lincs.ccs.miami.edu:8080/solr-example/select?wt=json&rows=10000&" +  // update rows
				      "fl=Concentration,PerturbationType,SmallMoleculeName,PerturbationMeasure,CellLineName,TimePoint,ProteinName&group=true&group.field=PerturbationId",
				data : { q : searchTerm	+ ' AssayTypeName:"' + selectedAssay + '"' },
				success : function(response) {
		
					var responseData = eval(response).grouped.PerturbationId.groups;
					
					crossFilterJson += "[";
					
					for (var i = 0; i < responseData.length; i++) {
						crossFilterJson += "{";
						crossFilterJson += '"Id":'+ i + ',"Concentration":' +responseData[i].doclist.docs[0].Concentration + ',"TimePoint":' +responseData[i].doclist.docs[0].TimePoint +
										   ',"PerturbationMeasure":' + responseData[i].doclist.docs[0].PerturbationMeasure + ',"Activity":' + '"' + responseData[i].doclist.docs[0].PerturbationType + '"' +
										   ',"SmallMoleculeName":' + '"' + responseData[i].doclist.docs[0].SmallMoleculeName + '"' +
										   ',"ProteinName":' + '"' + responseData[i].doclist.docs[0].ProteinName + '"' + ',"CellLineName":' + '"' + responseData[i].doclist.docs[0].CellLineName + '"';
				
					   crossFilterJson += "}";						 
					   if(i < responseData.length-1){
						   crossFilterJson += ",";	
					   }
					}
					crossFilterJson += "]";
					$("#viewData").load("/life/web/templates/viewDataCF.html");
				},
				complete : function(){
					setTimeout(function(){
						$("#viewDataKKCrossFilterChart").show();
						$("#viewDataCrossFilterChart").hide();
						$("#viewDataP100CrossFilterChart").hide();
						$("#viewDataProteinSecretionCrossFilterChart").hide();
						
						getViewDataKKCrossFilter(crossFilterJson,selectedAssay);
				    },500);
					
					$('#dynamic').html("");
				},
				dataType : "jsonp",
				type : "GET",
				'jsonp' : 'json.wrf'
		});
	}else if(selectedAssay == "Single cell protein secretion profiling assay"){
		
		$("#viewDataP100CrossFilterChart").hide();
		$("#viewDataCrossFilterChart").hide();
		$("#viewDataKKCrossFilterChart").hide();
		$("#viewDataProteinSecretionCrossFilterChart").hide();
		$('#sigLinkDropDown').remove();
		$('#dynamic').html(loader);
		
		var crossFilterJson = "";
		$.ajax({url : "http://lincs.ccs.miami.edu:8080/solr-example/select?wt=json" +
		      "&fl=Concentration,CellLineName,SmallMoleculeName,PerturbationMeasure,ProteinName&rows=10000&group=true&group.field=PerturbationId",
			data : { q : searchTerm	+ ' AssayTypeName:"' + selectedAssay + '" -"Un Perturbed"' },
			success : function(response) {
				
				var responseData = eval(response).grouped.PerturbationId.groups;
				
				crossFilterJson += "[";
				
				for (var i = 0; i < responseData.length; i++) {
					crossFilterJson += "{";
					crossFilterJson += '"Id":'+ i + ',"Concentration":' +responseData[i].doclist.docs[0].Concentration + ',"PerturbationMeasure":' + responseData[i].doclist.docs[0].PerturbationMeasure + 
									   ',"SmallMoleculeName":' + '"' + responseData[i].doclist.docs[0].SmallMoleculeName + '"' + ',"ProteinName":' + '"' + responseData[i].doclist.docs[0].ProteinName + '"'+
									   ',"CellLineName":' + '"' + responseData[i].doclist.docs[0].CellLineName + '"' + ',"Activity":' + '"' + responseData[i].doclist.docs[0].PerturbationType + '"';
				   crossFilterJson += "}";						 
				   if(i < responseData.length-1){
					   crossFilterJson += ",";	
				   }
				}
				crossFilterJson += "]";
				$("#viewData").load("/life/web/templates/viewDataCF.html");
			},
			complete : function(){
				setTimeout(function(){
					$("#viewDataProteinSecretionCrossFilterChart").show();
					$("#viewDataKKCrossFilterChart").hide();
					$("#viewDataCrossFilterChart").hide();
					$("#viewDataP100CrossFilterChart").hide();
					
					getViewDataProteinSecretionCrossFilter(crossFilterJson,selectedAssay);
			    },500);
				
				$('#dynamic').html("");
			},
			dataType : "jsonp",
			'jsonp' : 'json.wrf'
		});
	}else{
		$("#viewDataKKCrossFilterChart").hide();
		$("#viewDataCrossFilterChart").hide();
		$("#viewDataP100CrossFilterChart").hide();
		$("#viewDataProteinSecretionCrossFilterChart").hide();
		$('#sigLinkDropDown').remove();
		var loader=' <h1 style=" margin-left:30px;"> Normalized data is not yet available for this assay. Please check the <a href="http://lincs.hms.harvard.edu/data/">source</a> for access to un-normalized data </h1>';
		$('#dynamic').html(loader);
		
	}
}	

function loadDiseases(selectedAssay) {
	$('#sigLinkDropDown').remove();
	
	$.ajax({url : "http://lincs.ccs.miami.edu:8080/solr-example/select?start=0&rows=0&indent=on&wt=json&facet=true&facet.field=Disease&facet.mincount=1&q="+ searchTerm +" AssayTypeName:\""+selectedAssay +"\"",
			success : function(response) {
				response = eval(response);
				var sigLinks = response.facet_counts.facet_fields.Disease;
				var linkStr = "<select  id ='sigLinkDropDown' class='sigLinks' onChange='loadDiseasesData(selectedAssay);' ><option value = \"default\" >-- Select --</option>";
				$('.sigLinks').html();
				for ( var i = 0; i < sigLinks.length; i++) {
					if(sigLinks[i] != "" && sigLinks[i] != " "){
						linkStr += "<option value = \"" + sigLinks[i] + "\">"
								+ sigLinks[i] + "</option>";
					}
					i++;
				}
				linkStr += "</select><br>";
				if (selectedAssay == "Cell cycle state assay") {
					$("#sigCellCycleLinks").html(linkStr);
				} else if (selectedAssay == "Apoptosis assay") {
					$("#sigApoptosisLinks").html(linkStr);
					console.log(selectedAssay);
				} else if (selectedAssay == "Cell growth inhibition assay") {
					$("#sigCellGrowthLinks").html(linkStr);
				} else if (selectedAssay == "L1000 transcriptional profiling assay") {
					$("#sigLinks").html(linkStr);
				}
		    },
			dataType : "jsonp",
			type : "GET",
			'jsonp' : 'json.wrf'
	});
}

function loadDiseasesData(){
	
	$('#dynamic').html(loader);
	
	var diseaseName = $('#sigLinkDropDown :selected').val();
	
	if(selectedAssay == "Cell cycle state assay" || selectedAssay == "Apoptosis assay" || selectedAssay == "Cell growth inhibition assay"){
			
			$("#viewDataCrossFilterChart").hide();
		
			var crossFilterJson = "";
			$.ajax({url : "http://lincs.ccs.miami.edu:8080/solr-example/select?wt=json&rows=10000&" +  // update rows
					      "fl=TimePoint,Concentration,PerturbationType,CellLineName,PerturbationMeasure,SmallMoleculeName&group=true&group.field=PerturbationId",
					data : { q : searchTerm	+ ' AssayTypeName:"' + selectedAssay + '" ' +diseaseName},
					success : function(response) {
			
						var responseData = eval(response).grouped.PerturbationId.groups;
						
						crossFilterJson += "[";
						
						for (var i = 0; i < responseData.length; i++) {
							crossFilterJson += "{";
							crossFilterJson += '"Id":'+ i + ',"Concentration":' +responseData[i].doclist.docs[0].Concentration + ',"TimePoint":' +responseData[i].doclist.docs[0].TimePoint +
												 ',"CellLineName":' + '"' +responseData[i].doclist.docs[0].CellLineName + '"' + ',"PerturbationMeasure":' + responseData[i].doclist.docs[0].PerturbationMeasure + 
												 ',"Activity":' + '"' + responseData[i].doclist.docs[0].PerturbationType + '"' + ',"SmallMoleculeName":' + '"' + responseData[i].doclist.docs[0].SmallMoleculeName + '"';
					
						   crossFilterJson += "}";						 
						   if(i < responseData.length-1){
							   crossFilterJson += ",";	
						   }
						}
						crossFilterJson += "]";
						$("#viewData").load("/life/web/templates/viewDataCF.html");
					},
					complete : function(){
						setTimeout(function(){
							$("#viewDataCrossFilterChart").show();
							$("#viewDataP100CrossFilterChart").hide();
							$("#viewDataKKCrossFilterChart").hide();
							$("#viewDataProteinSecretionCrossFilterChart").hide();
							getViewDataCrossFilter(crossFilterJson,selectedAssay);
					    },500);
						
						$('#dynamic').html("");
					},
					dataType : "jsonp",
					type : "GET",
					'jsonp' : 'json.wrf'
			});
	 }else if(selectedAssay == "L1000 transcriptional profiling assay"){
			
			$("#viewDataCrossFilterChart").hide();
			
			var crossFilterJson = "";
			 $.ajax({url : "http://lincs.ccs.miami.edu:8080/solr-example/select?wt=json&rows=10000&" +  // update rows
			               "fl=shRnaName,CdnaName,SmallMoleculeName, GeneName, Concentration,TimePoint,CellLineName,Activity,PerturbationMeasure&group=true&group.field=PerturbationId",
					data : { q : searchTerm	+ ' AssayTypeName:"' + selectedAssay + '" ' +diseaseName},
					success : function(response) {
						var responseData = eval(response).grouped.PerturbationId.groups;
						
						crossFilterJson += "[";	
						
						for (var i = 0; i < responseData.length; i++) {
							
							var updatedResponseData = JSON.stringify(responseData[i]).replace("shRnaName","perturbationName");
							updatedResponseData = updatedResponseData.replace("CdnaName","perturbationName");
							updatedResponseData = updatedResponseData.replace("SmallMoleculeName","perturbationName");
							
							updatedResponseData = JSON.parse(updatedResponseData);
							
							crossFilterJson += "{";
							crossFilterJson += '"Id":'+ i + ',"Concentration":' +updatedResponseData.doclist.docs[0].Concentration + ',"TimePoint":' +updatedResponseData.doclist.docs[0].TimePoint +
												 ',"CellLineName":' + '"' +updatedResponseData.doclist.docs[0].CellLineName + '"' + ',"PerturbationMeasure":' + updatedResponseData.doclist.docs[0].PerturbationMeasure + 
												 ',"Activity":' + '"' + updatedResponseData.doclist.docs[0].Activity + '"' + ',"perturbationName":' + '"' + updatedResponseData.doclist.docs[0].perturbationName + '"' +
												 ',"GeneName":' + '"' + updatedResponseData.doclist.docs[0].GeneName + '"';
					
						   crossFilterJson += "}";						 
						   if(i < responseData.length-1){
							   crossFilterJson += ",";	
						   }
						}
						crossFilterJson += "]";
						
						$("#viewData").load("/life/web/templates/viewDataCF.html");
					},
					complete : function(){
						setTimeout(function(){
							$("#viewDataCrossFilterChart").show();
							$("#viewDataP100CrossFilterChart").hide();
							$("#viewDataKKCrossFilterChart").hide();
							$("#viewDataProteinSecretionCrossFilterChart").hide();
							
							getViewDataCrossFilter(crossFilterJson,selectedAssay);
					    },500);
						
						$('#dynamic').html("");
					},
					dataType : "jsonp",
					type : "GET",
					'jsonp' : 'json.wrf'
			});
		}
}

function emptyCheck(field) {
	if (field == "") {
		return false;
	}else {
		return true;
	}
}