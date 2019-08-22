$(document).ready(function() {
	$('#tabFilter').hide();
	setIndxDrpDwnSelection();
	initiateListFilters();
	initiateFilters();
	manageFilterSelects();
	viewControls();
	manageTagContainer();
	manageButtons();
});

var commonSearchTag = "";

function setIndxDrpDwnSelection(){
	$("#constituentsIndex").removeClass("active");
	$("#perturbationIndex").removeClass("active");
	var removeField = 'input[name=indexVal]';
	
	if($(document).getUrlParam("indexVal") == null || $(document).getUrlParam("indexVal") == "participant"){
		$("#selectedIndexLinkVal").val("participant");
		$("#constituentsIndex").addClass("active");
		$(removeField).remove();
	}else{
		$("#selectedIndexLinkVal").val("perturbation");
		$("#perturbationIndex").addClass("active");
		$(removeField).remove();
		
		var indexVar = '<input type=\'hidden\' name=\'indexVal' + '\' value=\''+ $("#selectedIndexLinkVal").val()+ '\' />';
		$('#searchForm').append(indexVar);
	}
}

var type = "Protein";

$(function() {
	setTimeout(function(){
	manageLinks();
	},1000);

});

function manageLinks(){
	$(".tabLink").click(function() {
		$(".tabLink").removeClass("active");
		$(this).addClass("active");
	});
}

function manageButtons() {
	$("#download").click(function() {
		searchTerm = searchTerm.replace(/\"/g, "&quot;");
		$("#searchForm input[name=q]").val(searchTerm);
	});
	$(".filter-button")
			.click(
					function() {
						var query = '';
						// get selected options cellLine
						var filterCellLine = '';
						if ($("#filterCellLine ").multiselect("getChecked").length > 0) {
							filterCellLine = $.map(
									$("#filterCellLine option:selected"),
									function(el, i) {
										return $(el).text();
									});
							filterCellLine = filterCellLine.join('" || "');
							filterCellLine = 'CellLineName:("' + filterCellLine
									+ '")';
							query = query + filterCellLine;
							console.log(query);
						}

						// get selected options compound
						var filterCompound = '';
						if ($("#filterCompound ").multiselect("getChecked").length > 0) {
							filterCompound = $.map(
									$("#filterCompound option:selected"),
									function(el, i) {
										return $(el).text();
									});
							filterCompound = filterCompound.join('" || "');
							filterCompound = 'SmallMoleculeName:("'
									+ filterCompound + '")';
							query = query + filterCompound;
							console.log(query);
						}

						// get selected options gene
						var filterGene = '';
						if ($("#filterGene ").multiselect("getChecked").length > 0) {
							filterGene = $.map(
									$("#filterGene option:selected"), function(
											el, i) {
										return $(el).text();
									});
							filterGene = filterGene.join('" || "');
							filterGene = 'GeneName:("' + filterGene + '")';
							query = query + filterGene;
							console.log(query);
						}

						// get selected options protein
						var filterProtein = '';
						if ($("#filterProtein ").multiselect("getChecked").length > 0) {
							filterProtein = $.map(
									$("#filterProtein option:selected"),
									function(el, i) {
										return $(el).text();
									});
							filterProtein = filterProtein.join('" || "');
							filterProtein = 'ProteinName:("' + filterProtein
									+ '")';
							query = query + filterProtein;
							console.log(query);
						}

						// var query = 'SmallMoleculeName:("JNK-IN-5A" ||
						// "JNK-9L") CellLineName:("KMH-2" || "8305C")';
						alert(query);
						var originalQuery = $("#searchForm input[name=search]")
								.val();
						query = originalQuery + ' ' + query;
						console.log(query);
						$("#searchForm input[name=search]").val(query);
						updateSentValueOnSubmission();
						$("#searchForm").submit();
					});

}

function loadTags(category) {

	var q = $(document).getUrlParam('q');
	q = q.replace(/\%26quot%3B/g, ""); // solve double quotes issue
	var tagSource = '/life/tags-fetcher?q=' + q + '&limit=30&category='
			+ category + 'Tags&fq';
	$('#tags').html('');
	$.getJSON(tagSource, function(data) {
		var tags = [];
		for ( var i = 0; i < data.words.length; i++) {
			for (property in data.words[i]) {
				var tagId = 'tag' + i;
				if (!$(document).getUrlParam(tagId)) {
					tags.push('<li> <button value= \'tag' + i
							+ '\' class=\'tag-button ui-btn-a ui-corner-all\'>'
							+ property + '</button></li>');
				}
			}
		}

		$('<ul/>', {
			html : tags.join('')
		}).appendTo('#tags');
	});

	updateSentValue();
	// load hidden input tags

	var newTag = '';
	var max = 30; // hardcoded

	$('#searchForm .tag-input').remove(); // fix bug of multiple loadTags
	// calls
	for ( var i = 0; i < max; i++) {
		var tagId = 'tag' + i;
		var tagValue = $(document).getUrlParam(tagId);
		if ($(document).getUrlParam(tagId)) {
			console.log('true');

			tagValue = tagValue.replace(/\+/g, " ");
			var hiddenInput = '<input class=\'tag-input\' type=\'hidden\' name=\''
					+ tagId + '\' value=\'' + tagValue + '\' />';
			$('#searchForm').append(hiddenInput);
		}
	}

	// load button applied tags unders search box
	for ( var i = 0; i < max; i++) {
		var tagId = 'tag' + i;
		var tagValue = $(document).getUrlParam(tagId);
		tagValue = decodeURIComponent(tagValue);
		if ($(document).getUrlParam(tagId)) {
			$('#tags-applied').html('');
			tagValue = tagValue.replace(/\+/g, " ");
			newTag += '<button class=\'tag-applied ui-btn-a ui-corner-all position-relative\' value=\''
					+ tagId
					+ '\'>'
					+ tagValue
					+ '<img class=\'tag-close\' src=\'/life/web/images/close-button.png\' /></button>';
			$('#tags-applied').append(newTag);
		}
	}
	onClickHideTags();
	onClickTags();
}

// on tag click add tag term to search box and submit form
function onClickTags() {
	$(".tag-button").unbind('click');
	$(".tag-button").click(
			function() {
				var hiddenInput = '';
				var tag = $(this).text();
				var tagId = $(this).val();
				var q = $("#searchForm input[name=q]").val();
				$("#searchForm input[name=q]").val(q + ' ' + tag);
				hiddenInput = '<input type=\'hidden\' name=\'' + tagId
						+ '\' value=\'' + tag + '\' />';
				$('#searchForm').append(hiddenInput);
				var load = $("#load").val();
				$("#load").val(load);
				updateSentValueOnSubmission();
				$("#searchForm").submit();
				return false;
			});
}

// on tag click with timeout for quick fix
function onClickTagsTime() {
	setTimeout(function() {
		onClickTags()
	}, 500);
	setTimeout(function() {
		onClickTags()
	}, 5000);
}
function onClickHideTags() {// on tag click remove input tag and button

	$(".tag-applied").click(function() {
		var tagId = $(this).val();
		var input = 'input[name=' + tagId + ']';
		var tagValue = $(input).val();
		var inputQ = "input[name='q']";
		var inputQValue = $(inputQ).val();
		var newQValue = inputQValue.replace(tagValue, '');

		$(inputQ).val(newQValue);
		$(input).remove();
		$(this).remove();

		// $("#searchForm").submit();
		submitForm();
	});

}
// update q at start
function updateSentValue() {
	var max = 30; // hardcoded
	var searchTag = '';
	var searchTerm = $("#searchForm input[name=search]").val();

	for ( var i = 0; i < max; i++) {
		var tagId = 'tag' + i;
		var tagValue = $(document).getUrlParam(tagId);
		tagValue = decodeURIComponent(tagValue);

		if ($(document).getUrlParam(tagId)) {
			tagValue = tagValue.replace(/\+/g, " ");
			searchTag = searchTag + ' ' + tagValue;
			commonSearchTag = searchTag; 
		}
	}
	$("#searchForm input[name=q]").val(addAndOperator(searchTerm + ' ' + searchTag));
}

// update q at start
function updateSentValueOnSubmission() {
	var max = 30; // hardcoded
	var searchTag = '';

	var searchTerm = $("#searchForm input[name=search]").val();
	searchTerm = searchTerm.replace(/\"/g, "&quot;");
	$("#searchForm input[name=search]").val(searchTerm);

	for ( var i = 0; i < max; i++) {
		var inputName = 'tag' + i;
		var input = '#searchForm input[name=\'' + inputName + '\']';
		var inputValue = $(input).val();
		if (inputValue != null) {
			searchTag = searchTag + ' ' + inputValue;
			commonSearchTag = searchTag;
		}
	}
	console.log(searchTag);
	$("#searchForm input[name=q]").val(addAndOperator(searchTerm + searchTag));
	return false;
}
// submit form

function submitForm() {
	updateSentValueOnSubmission();
	$("#searchForm").submit();
	return false;
}

// move body up on load
function animateSearchBox() {
	$('#content').transition({
		x : '0',
		y : '-50px'
	}, 3500, 'cubic-bezier(0.9,0.9,0.3,1)');
}

function viewControls() {
	// switch table view to list view
	$(".view-list-button").click(function() {
		$(this).addClass('button-clicked');
		$('.view-table-button').removeClass('button-clicked');
		$('#table-view').hide();
		$('#list-view').show();
		return false;
	});
	// switch list view to table view
	$(".view-table-button").click(function() {
		$(this).addClass('button-clicked');
		$('.view-list-button').removeClass('button-clicked');
		$('#list-view').hide();
		$('#table-view').show();
		return false;
	});
}

function manageTagContainer() {
	// show and hide tags container
	// Javascript
	var firefox = /Firefox/i.test(navigator.userAgent);

	$("#footer-header").click(function() {
		// temporary attach on click events : change it later
		// with callback
		onClickTags();

		if (!$('footer').hasClass('clicked')) {

			$('footer').css('bottom', '110px');

			$('footer').addClass('clicked');
			$('#tags-header').html('Hide Tags');
			$('#tags-container').show();
		} else {
			$('footer').css('bottom', '0');
			$('footer').removeClass('clicked');
			$('#tags-header').html('View Tags');
			$('#tags-container').hide();
		}
	});
}

// load button applied tags unders search box
function hideTags() {
	var max = 30;
	var newTag = '';
	for ( var i = 0; i < max; i++) {
		var tagId = 'tag' + i;
		var tagValue = $(document).getUrlParam(tagId);
		if ($(document).getUrlParam(tagId))
			$('li button[value="' + tagId + '"]').hide();
	}
}

// on click show filters and

function initiateListFilters() {
	$("#filterSelection").click(function() {
		initiateFilters();
		$("#filter-preloader").show();
		setTimeout(function() {
			$("#filter-preloader").hide();
			$("#listFilter").show();
		}, 1500);
	});
	$(".refresh-filter").click(function() {
		emptyFilterSelects();
		
	});
}

// if lists variable stored from iframe have data populate filters
function initiateFilters() {
		updateLists();
		destroyFilterSelects();
		emptyFilterSelects();
	setTimeout(function() {
		getCellLineFilters();
	}, 1500);
	
	/*getCellLineFilters(function() {
		getCompoundFilters(function() {
			getGeneFilters(function() {
					getProteinFilters();
			});
		});
	});
	*/
}
function getCellLineFilters() {
	if (cellLineList != 'null' && cellLineList != '[]') {
		console.log('initiate cellLine filters:' + cellLineList);
		cellLineList = cellLineList.replace(/[^a-z0-9\s]/gi, '');
		cellLineList = cellLineList.replace(/[ \s]/g, ',');
		// $.getJSON('restrict-fetcher?input='+cellLineList, function(data) {
		// populateCellLineFilters(data);
		// });
		$.ajax({
			url : "restrict-fetcher",
			data : {
				input : cellLineList
			},
			success : populateCellLineFilters,
			dataType : "json",
			type : "GET",
			error : function() {
				console.log('error cellLineList restrict-fetcher');
			}
		});
		
		$('#viewListButton').show(); //show button view list if any elements in the list
	}
	else 
		getCompoundFilters();
}
function getCompoundFilters() {
	if (compoundList != 'null' && compoundList != '[]') {
		console.log('initiate compound filters:' + compoundList);
		compoundList = compoundList.replace(/[^a-z0-9\s]/gi, '');
		compoundList = compoundList.replace(/[ \s]/g, ',');
		// $.getJSON('restrict-fetcher?input=' + compoundList, function(data) {
		// populateCompoundFilters(data);
		// });
			
			$.ajax({
				url : "restrict-fetcher",
				data : {
					input : compoundList
				},
				success : populateCompoundFilters,
				dataType : "json",
				type : "GET",
				error : function(jqxhr, status, errorThrown) {
					console.log('error compound restrict-fetcher');
					alert("Failure,Unabletorecievecontentrequest:"+compoundList);
					alert(jqxhr.responseText);
				}
			});
			
			$('#viewListButton').show(); //show button view list if any elements in the list
	}
	else
	getGeneFilters();
}
function getGeneFilters() {
	// gene
	if (geneList != 'null' && geneList != '[]') {
		console.log('initiate gene filters:' + geneList);
		geneList = geneList.replace(/[^a-z0-9\s]/gi, '');
		geneList = geneList.replace(/[ \s]/g, ',');
		// $.getJSON('restrict-fetcher?input=' + geneList, function(data) {
		// populateGeneFilters(data);
		// });

		$.ajax({
			url : "restrict-fetcher",
			data : {
				input : geneList
			},
			success : populateGeneFilters,
			dataType : "json",
			type : "GET",
			error : function(jqxhr, status, errorThrown) {
				console.log('error gene restrict-fetcher');
				alert("Failure,Unabletorecievecontentrequest:"+geneList);
				alert(jqxhr.responseText);
			}
		});
		
		$('#viewListButton').show(); //show button view list if any elements in the list
	}
	else
	getProteinFilters();
}
function getProteinFilters() {
	// protein
	if (proteinList != 'null' && proteinList != '[]') {
		console.log('initiate protein filters:' + proteinList);
		proteinList = proteinList.replace(/[^a-z0-9\s]/gi, '');
		proteinList = proteinList.replace(/[ \s]/g, ',');
		// $.getJSON('restrict-fetcher?input=' + proteinList, function(data) {
		// populateProteinFilters(data);
		// });
			$.ajax({
				url : "restrict-fetcher",
				data : {
					input : proteinList
				},
				success : populateProteinFilters,
				dataType : "json",
				type : "GET",
				error : function(jqxhr, status, errorThrown) {
					console.log('error protein restrict-fetcher');
					alert("Failure,Unabletorecievecontent request:"+ proteinList );
					alert(jqxhr.responseText);
				}
			});
			
			$('#viewListButton').show(); //show button view list if any elements in the list
	}
	else
		destroyFilterSelects();
		manageFilterSelects();
}
function populateCellLineFilters(response) {
	var data = response.data;
	console.log(data.length);
	var select = '#filterCellLine';
	for ( var i = 0; i < data.length; i++) {
		$(select).append(
				$("<option></option>").attr("value", data[i].Id).text(
						data[i].Name));
		console.log(data[i].Id + "," + data[i].Name);
	}
	console.log('populateCellLineFilters');
	getCompoundFilters();  //load compound after completion
}

function populateCompoundFilters(response) {
	var data = response.data;
	var select = '#filterCompound';
	console.log(data.length);
	for ( var i = 0; i < data.length; i++) {
		$(select).append(
				$("<option></option>").attr("value", data[i].Id).text(
						data[i].Name));
		console.log(data[i].Id + "," + data[i].Name);
	}
	console.log('populateCompoundFilters');
	getGeneFilters();  //load gene after completion
}

function populateGeneFilters(response) {
	var data = response.data;
	console.log(data.length);
	var select = '#filterGene';
	for ( var i = 0; i < data.length; i++) {
		$(select).append(
				$("<option></option>").attr("value", data[i].Id).text(
						data[i].Name));
		console.log(data[i].Id + "," + data[i].Name);
	}
	console.log('populateGeneFilters');
	getProteinFilters(); //load protein after completion
}

function populateProteinFilters(response) {
	console.log(proteinList);
	var data = response.data;
	console.log(data.length);
	var select = '#filterProtein';
	for ( var i = 0; i < data.length; i++) {
		$(select).append(
				$("<option></option>").attr("value", data[i].Id).text(
						data[i].Name));
		console.log(data[i].Id + "," + data[i].Name);
	}
	console.log('populateProteinFilters');
	destroyFilterSelects();
	manageFilterSelects();  //load selects
}

function updateLists() {
	// load iframe
	$('#iframe').remove();
	var iframe = '<iframe id="iframe" src="filter" style="display:none"></iframe>';
	$('#tabFilter').append(iframe);

	// on iframe load complete
	document.getElementById("iframe").onload = function() {
		// update variables
		cellLineList = document.getElementById('iframe').contentWindow.cellLineList;
		compoundList = document.getElementById('iframe').contentWindow.compoundList;
		geneList = document.getElementById('iframe').contentWindow.geneList;
		proteinList = document.getElementById('iframe').contentWindow.proteinList;
	};
}
function destroyFilterSelects() {
	// destroy selects
	$("#filterCellLine").multiselect("destroy").multiselect();
	$("#filterCompound").multiselect("destroy").multiselect();
	$("#filterGene").multiselect("destroy").multiselect();
	$("#filterProtein").multiselect("destroy").multiselect();

	// console.log('selects destroyed');
}

function emptyFilterSelects() {
	// empty selects
	$("#filterCellLine").empty();
	$("#filterCompound").empty();
	$("#filterGene").empty();
	$("#filterProtein").empty();
	// console.log('selects empty');
}

function manageFilterSelects() {
	// load selects
	
	$("#filterCellLine").multiselect({
		noneSelectedText : "CellLine"
	}).multiselectfilter();
	$("#filterCompound").multiselect({
		noneSelectedText : "Compound"
	}).multiselectfilter();
	$("#filterGene").multiselect({
		noneSelectedText : "Gene"
	}).multiselectfilter();
	$("#filterProtein").multiselect({
		noneSelectedText : "Protein"
	}).multiselectfilter();
	
	console.log('selects loaded');
}

function onAddToListClick() {
	//blink select filter link once iterm is added to list
	blink("#selectFilterId", 2, 300);
	
	$('#viewListButton').show(); //show button view list if any elements in the list
	emptyFilterSelects();
	$("#listFilter").hide();
}

function blink(elem, times, speed) {
    if (times > 0 || times < 0) {
        if ($(elem).hasClass("blink")) $(elem).removeClass("blink");
        else $(elem).addClass("blink");
    }

    clearTimeout(function () {
        blink(elem, times, speed);
    });

    if (times > 0 || times < 0) {
        setTimeout(function () {
            blink(elem, times, speed);
        }, speed);
        times -= .5;
    }
}

function addAndOperator(qValue){
	if(commonSearchTag != "")
		return "{!lucene q.op=AND df=text}"+qValue;
	else
		return	qValue;
}