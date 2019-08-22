function drawChart(id, aData, tData) {
    var hG = {}, hGDim = {
        t : 60,
        r : 0,
        b : 30,
        l : 0
    };
    hGDim.w = 500 - hGDim.l - hGDim.r, hGDim.h = 300 - hGDim.t - hGDim.b;
    // create svg for assayTypeGraph.
    var hGsvg = d3.select(id).append("svg").attr("width",
            hGDim.w + hGDim.l + hGDim.r+30).attr("height",
            hGDim.h + hGDim.t + hGDim.b + 50).append("g").attr("transform",
            "translate(" + hGDim.l + "," + hGDim.t + ")");

    function assayTypeColor(c) {
        return {
            "KINOMEscan assay" : "#98ABC5",
            "Kinative assay" : "#2ca02c",
            "L1000 transcriptional profiling assay" : "#A05D56",  
            "Apoptosis assay" : "#6B486B",
            "Cell cycle state assay" : "#A05D56",
            "Cell viability assay" : "#DAA520",
            "P100 phospho protein profiling assay": "#B8860B",
            "Single cell protein secretion profiling assay": "#CD853F",
            "Cell growth inhibition assay": "#D2691E"
        }[c];
    }
    function perturbagenTypeColor(c) {
        return {
            "SmallMolecule" : "#807dba",
            "shRNA" : "#e08214",
            "cDNA" : "#41ab5d"
        }[c];
    }
    function measuredEntityColor(c) {
        return {
            "Cells" : "#291815",
            "KinaseProteins" : "#80606B",
            "Antibodies" : "#5D3D30",
            "TranscribedGenes" : "#D0743C",
            "Proteins" : "#E4B292",
            "PhosphoProteins" : "#D0BE3C"
        }[c];
    }
    function perturbationTypeColor(c) {
        return {
            "percent G2-M arrested" : "#7B6888", 
            "percent Interphase" : "#FF8C00",
            "percent Mitosis" : "#98ABC5", 
            "percent Non Arrested" : "#6B486B",
            "IC50" : "#D0743C",  
            "percent Apoptosis" : "#A05D56",  
            "percent Cell Viability" : "#6B486B",
            "percent Kinase Inhibition" : "#8A89A6",  
            "fold Change" : "#7c4524",
            "z-score" : "#91512a",
            "cell viability z-score" : "#56485f",
            "pKd" : "#6a7789"  
        }[c];
    }

    // compute total for each AssayType.
    aData.forEach(function(d) {
        d.total = d.perturbationType["percent G2-M arrested"]
                + d.perturbationType["percent Interphase"]
                + d.perturbationType["percent Mitosis"]
                + d.perturbationType["percent Non Arrested"]
                + d.perturbationType["IC50"]
                + d.perturbationType["percent Apoptosis"]
                + d.perturbationType["percent Cell Viability"]
                + d.perturbationType["percent Kinase Inhibition"]
                + d.perturbationType["fold Change"]
                + d.perturbationType["z-score"]
                + d.perturbationType["cell viability z-score"]
                + d.perturbationType["pKd"];
    });

    // function to handle assayTypeGraph.
    function assayTypeGraph(fD) {

        // create function for x-axis mapping.
        var x = d3.scale.ordinal().rangeRoundBands([ 18, hGDim.w ], 0.1).domain(
                fD.map(function(d) {
                    return d[0];
                }));

        hGsvg.append("text")
        .attr("x", (hGDim.w / 2))             
        .attr("y", 0 - (hGDim.t / 2))
        .attr("text-anchor", "middle")  
        .style("font-size", "16px") 
        .style("text-decoration", "underline")  
        .text("Biosssays");

        // Add x-axis to the assayTypeGraph svg.
        hGsvg.append("g").attr("class", "x axis").attr("transform",
                "translate(0," + hGDim.h + ")").call(
                d3.svg.axis().scale(x).orient("bottom"))
                .selectAll(".tick text").call(wrap, x.rangeBand());

        // Add y-axis label to assayTypeGraph
        hGsvg.append("g")
            .attr("class", "y axis")
            .append("text")
            .attr("transform", "rotate(-90)")
            .attr("y", -4)
            .attr("x", -160)
            .attr("dy", ".95em")
            .style("font-size", "16px") 
            .style("text-anchor", "top")
            .text("Count of perturbations");
         
        // Create function for y-axis map.
        var y = d3.scale.linear()
            .domain( [ 0, log10(d3.max(fD, function(d) {
                return d[1]; })) ])
            .range([ hGDim.h, 0 ]);

        // Create bars for assayTypeGraph to contain rectangles and Type labels.
        var bars = hGsvg.selectAll(".bar").data(fD).enter().append("g").attr(
                "class", "bar");

        // create the rectangles.
        bars.append("rect").attr("x", function(d) { 
            return x(d[0]);
        }).attr("y", function(d) {
            return y(log10(d[1]));
        }).attr("width", (x.rangeBand()-4)).attr("height", function(d) { 
            return hGDim.h - y(log10(d[1]));
        }).attr('fill', function(d) {
            return assayTypeColor(d[0]);
        }).on("click", mouseover) 		// mouseover is defined below.
        .on("mouseout", mouseout); 		// mouseout is defined below.

        // Create the Type labels above the rectangles.
        bars.append("text").text(function(d) { 
            return d3.format(",")(d[1])
        }).attr("x", function(d) {
            return x(d[0]) + x.rangeBand() / 2;
        }).attr("y", function(d) {
            return y(log10(d[1])) - 5;
        }).attr("text-anchor", "middle");

        function mouseover(d) { // utility function to be called on mouseover.
            // filter for selected AssayType.
            var at = aData.filter(function(s) {
                return s.AssayType == d[0];
                
            })[0], nD = d3.keys(at.perturbagenType).map(function(s) {
                return {
                    type : s,
                    perturbagenType : at.perturbagenType[s]
                };
            }), mD = d3.keys(at.measuredEntity).map(function(s) {
                return {
                    type : s,
                    measuredEntity : at.measuredEntity[s]
                };
            }), bD = d3.keys(at.perturbationType).map(function(s) {
                return [ s, at.perturbationType[s] ];
            });
            // call update functions of pie-chart, legend, and bar-chart.
            bC.update(bD, assayTypeColor(d[0]));
            pC.update(nD);
            leg.update(nD);
            mC.update(mD);
            mleg.update(mD);

        }

        function mouseout(d) { // utility function to be called on mouseout.
            // reset the pie-chart, legend, and bar-chart.
            bC.update(perturbationTypeTotal, function(d) {
                return perturbationTypeColor(d[0]);
            });
            pC.update(perturbagenTypeTotal);
            leg.update(perturbagenTypeTotal);
            mC.update(measuredEntityTotal);
            mleg.update(measuredEntityTotal);
        }

        // create function to update the bars. This will be used by pie-chart
        // and bar-chart
        hG.update = function(nD, color) {
            // update the domain of the y-axis map to reflect change in Types.
            y.domain([ 0, log10(d3.max(nD, function(d) {
                return d[1];
            })) ]);

            // Attach the new data to the bars.
            var bars = hGsvg.selectAll(".bar").data(nD);

            // transition the height and color of rectangles.
            bars.select("rect").transition().duration(500).attr("y",
                    function(d) {
                        return y(log10(d[1]));
                    }).attr("height", function(d) {
                return hGDim.h - y(log10(d[1]));
            }).attr("fill", color);

            // transition the Type labels location and change value.
            bars.select("text").transition().duration(500).text(function(d) {
                return d3.format(",")(d[1])
            }).attr("y", function(d) {
                return y(log10(d[1])) - 5;
            });
        }
        return hG;
    }

    // function to handle PerturbagenType pieChart.
    function perturbagenTypePieChart(pD) {
        var pC = {}, pieDim = {
            w : 250,
            h : 350
        };
        pieDim.r = Math.min(pieDim.w, pieDim.h) / 2;

        // create svg for pie chart.
        var perturbagenTypePieSvg = d3.select(id).append("svg").attr("width", pieDim.w).attr(
                "height", pieDim.h).append("g").attr("transform",
                "translate(" + pieDim.w / 2 + "," + pieDim.h / 2 + ")");

        perturbagenTypePieSvg.append("text")
        .attr("x", 15)             
        .attr("y", -130)
        .attr("text-anchor", "middle")  
        .style("font-size", "16px") 
        .style("text-decoration", "underline")  
        .text("Perturbagens");

        // create function to draw the arcs of the pie slices.
        var arc = d3.svg.arc().outerRadius(pieDim.r - 10).innerRadius(0);

        // create a function to compute the pie slice angles.
        var perturbagenTypePie = d3.layout.pie().sort(null).value(function(d) {
            return d.perturbagenType;
        });

        // Draw the pie slices.
        perturbagenTypePieSvg.selectAll("path").data(perturbagenTypePie(pD)).enter().append("path").attr("d",
                arc).each(function(d) {
            this._current = d;
        }).style("fill", function(d) { 
            return perturbagenTypeColor(d.data.type);
        }).on("click", mouseover).on("mouseout", mouseout);

        // create function to update pie-chart. This will be used by
        // assayTypeGraph.
        pC.update = function(nD) {
            perturbagenTypePieSvg.selectAll("path").data(perturbagenTypePie(nD)).transition().duration(500)
                    .attrTween("d", arcTween);
        }

        // Utility function to be called on mouseover a pie slice.
        function mouseover(d) {
            // call the update function of assayTypeGraph with new data.
            hG.update(aData.map(function(v) {
                return [ v.AssayType, v.perturbagenType[d.data.type] ];
            }), perturbagenTypeColor(d.data.type));
            
            var yAxis = hGsvg.select("g.y.axis");
            yAxis.select("text").text("Count of perturbagens ")
            
            var at = aData.map(function(v) {
                return [ v.AssayType, v.perturbagenType[d.data.type] ];
            }), bD = ['percent G2-M arrested', 'percent Interphase',
                    'percent Mitosis', 'percent Non Arrested', 'IC50',
                    'percent Apoptosis', 'percent Cell Viability',
                    'percent Kinase Inhibition', 'fold Change', 'z-score',
                    'cell viability z-score', 'pKd' ].map(function(i) {
                return [ i,
                        tData[0].perturbationType[d.data.type + ' Total ' + i] ];

            }),  mD = [ 'Cells', 'KinaseProteins', 'Antibodies',
                        'TranscribedGenes',
                        'PhosphoProteins' ].map(function(i) {
                            return {
                                type : i,
                                measuredEntity : tData[0].perturbationType[d.data.type + ' Total ' + i]
                            };
                        });

            // call update functions of pie-chart, legend, and bar-chart.
            bC.update(bD, perturbagenTypeColor(d.data.type));
            mC.update(mD);
            mleg.update(mD);
        }
        // Utility function to be called on mouseout a pie slice.
        function mouseout(d) {
            // call the update function of assayTypeGraph with all data.
            hG.update(aData.map(function(v) {
                return [ v.AssayType, v.total ];
            }), function(d) {
                return assayTypeColor(d[0]);
            });
            var yAxis = hGsvg.select("g.y.axis");
            yAxis.select("text").text("Count of perturbations")
            bC.update(perturbationTypeTotal, function(d) {
                return perturbationTypeColor(d[0]);
            });
            mC.update(measuredEntityTotal);
            mleg.update(measuredEntityTotal);
        }
        // Animating the pie-slice requiring a custom function which specifies
        // how the intermediate paths should be drawn.
        function arcTween(a) {
            var i = d3.interpolate(this._current, a);
            this._current = i(0);
            return function(t) {
                return arc(i(t));
            };
        }
        return pC;
    }

	// function to handle measuredEntityType pieChart.
	function measuredEntityPieChart(mD) {
		var mC = {}, pieDim = {
			w : 250,
			h : 350
		};
		pieDim.r = Math.min(pieDim.w, pieDim.h) / 2;
		
		// create svg for pie chart.
		var measuredEntityPieSvg = d3.select(id).append("svg").attr("width", pieDim.w).attr(
				"height", pieDim.h).append("g").attr("transform",
				"translate(" + pieDim.w / 2 + "," + pieDim.h / 2 + ")");
	
		measuredEntityPieSvg.append("text")
        .attr("x", 15)             
        .attr("y", -130)
        .attr("text-anchor", "middle")  
        .style("font-size", "16px") 
        .style("text-decoration", "underline")  
        .text("Measured entities");
		
		// create function to draw the arcs of the pie slices.
		var arc = d3.svg.arc().outerRadius(pieDim.r - 10).innerRadius(0);

		// create a function to compute the pie slice angles.
		var measuredEntityPie = d3.layout.pie().sort(null).value(function(d) {
			return d.measuredEntity;
		});

		// Draw the pie slices.
		measuredEntityPieSvg.selectAll("path").data(measuredEntityPie(mD)).enter().append("path").attr("d",
				arc).each(function(d) {
			this._current = d;
		}).style("fill", function(d) {
			return measuredEntityColor(d.data.type);
		}).on("click", mouseover).on("mouseout", mouseout);

		// create function to update pie-chart. This will be used by
		// perturbationTypeBarChart.
		mC.update = function(d) {
			measuredEntityPieSvg.selectAll("path").data(measuredEntityPie(d)).transition().duration(500)
					.attrTween("d", arcTween);
		}

		// Utility function to be called on mouseover a pie slice.
		function mouseover(d) {
			// call the update function of assayTypeGraph with new data.
			hG.update(aData.map(function(v) {
				return [ v.AssayType, v.measuredEntity[d.data.type] ];
			}), measuredEntityColor(d.data.type));
            var yAxis = hGsvg.select("g.y.axis");
            yAxis.select("text").text("Count of measured entities ")
			var at = aData.map(function(v) {
				return [ v.AssayType, v.measuredEntity[d.data.type] ];
			}) // ,
			bD = [ 'percent G2-M arrested', 'percent Interphase', 'percent Mitosis',
					'percent Non Arrested', 'IC50', 'percent Apoptosis',
					'percent Cell Viability', 'percent Kinase Inhibition',
					'fold Change', 'z-score', 'cell viability z-score', 'pKd' ]
					.map(function(i) {
						return [
								i,
								tData[0].perturbationType[d.data.type + ' Total '
										+ i] ];
					}),
			perturbagenData = ['SmallMolecule',
			      'shRNA',
			      'cDNA' ].map(function(i) {
			    	  return {
			    		  type : i,
			    		  perturbagenType : tData[0].perturbationType[d.data.type + ' Total ' + i]
			    	  };
			});

			// call update functions of pie-chart, legend, and bar-chart.
			bC.update(bD, measuredEntityColor(d.data.type));
			pC.update(perturbagenData);
			leg.update(perturbagenData);
		}
		// Utility function to be called on mouseout a pie slice.
		function mouseout(d) {
			// call the update function of assayTypeGraph with all data.
			hG.update(aData.map(function(v) {
				return [ v.AssayType, v.total ];
			}), function(d) {
				return assayTypeColor(d[0]);
			});
            var yAxis = hGsvg.select("g.y.axis");
            yAxis.select("text").text("Count of perturbations")
			bC.update(perturbationTypeTotal, function(d) {
				return perturbationTypeColor(d[0]);
			});
			pC.update(perturbagenTypeTotal);
			leg.update(perturbagenTypeTotal);
			
		}
		// Animating the pie-slice requiring a custom function which specifies
		// how the intermediate paths should be drawn.
		function arcTween(a) {
			var i = d3.interpolate(this._current, a);
			this._current = i(0);
			return function(t) {
				return arc(i(t));
			};
		}
		return mC;
	}

	// function to handle PerturbationType barChart.
	function perturbationTypeBarChart(bD) {

		var bC = {}, bCDim = {
			t : 60,
			r : 0,
			b : 30,
			l : 0
		};
		bCDim.w = 500 - bCDim.l - bCDim.r, bCDim.h = 300 - bCDim.t - bCDim.b;

		// create svg
		var bCsvg = d3.select(id).append("svg").attr("width",
				bCDim.w + bCDim.l + bCDim.r).attr("height",
				bCDim.h + bCDim.t + bCDim.b + 50).append("g").attr("transform",
				"translate(" + bCDim.l + 10 + "," + bCDim.t + ")");

		// create function for x-axis mapping.
		var x = d3.scale.ordinal().rangeRoundBands([ 0, bCDim.w ], 0.1).domain(
				bD.map(function(d) {
					return d[0];
				}));

        bCsvg.append("text")
        .attr("x", (bCDim.w / 2))             
        .attr("y", 0 - (bCDim.t / 2))
        .attr("text-anchor", "middle")  
        .style("font-size", "16px") 
        .style("text-decoration", "underline")  
        .text("Endpoints");

        // Add x-axis to the svg.
        bCsvg.append("g").attr("class", "x axis").attr("transform",
                "translate(0," + bCDim.h + ")").call(
                d3.svg.axis().scale(x).orient("bottom"))
                .selectAll(".tick text").call(wrap, x.rangeBand());

        // Create function for y-axis map.
        var y = d3.scale.linear().range([ bCDim.h, 0 ]).domain(
                [ 0, log10(d3.max(bD, function(d) {
                    return d[1];
                })) ]);

        // Create bars to contain rectangles and Type labels.
        var bars = bCsvg.selectAll(".bar1").data(bD).enter().append("g").attr(
                "class", "bar1");

        // create the rectangles.
        bars.append("rect").attr("x", function(d) {
            return x(d[0]);
        }).attr("y", function(d) {
            return y(log10(d[1]));
        }).attr("width", x.rangeBand()).attr("height", function(d) {
            return bCDim.h - y(log10(d[1]));
        }).attr('fill', function(d) {
            return perturbationTypeColor(d[0]);
        }).on("click", mouseover)// mouseover is defined below.
        .on("mouseout", mouseout);// mouseout is defined below.

        // Create the Type labels above the rectangles.
        bars.append("text").text(function(d) {
            return d3.format(",")(d[1])
        }).attr("x", function(d) {
            return x(d[0]) + x.rangeBand() / 2;
        }).attr("y", function(d) {
            return y(log10(d[1])) - 5;
        }).attr("text-anchor", "middle");

        // create function to update the bars. This will be used by
        // assayTypeGraph.
        bC.update = function(bD, color) {
            // update the domain of the y-axis map to reflect change in Types.
            y.domain([ 0, log10(d3.max(bD, function(d) {
                return d[1];
            }) )]);

            // Attach the new data to the bars.
            var bars = bCsvg.selectAll(".bar1").data(bD);

            // transition the height and color of rectangles.
            bars.select("rect").transition().duration(500).attr("y",
                    function(d) {
                        return y(log10(d[1]));
                    }).attr("height", function(d) {
                return bCDim.h - y(log10(d[1]));
            }).attr("fill", color); // function(d) { return
                                    // perturbationTypeColor(d[0]); });

            // transition the Type labels location and change value.
            bars.select("text").transition().duration(500).text(function(d) {
                return d3.format(",")(d[1])
            }).attr("y", function(d) {
                return y(log10(d[1])) - 5;
            });
        }

        function mouseover(d) { // utility function to be called on mouse-over.
            // call the update function of assayTypeGraph with new
            // PerturbationType data.
            hG.update(aData.map(function(v) {
                return [ v.AssayType, v.perturbationType[d[0]] ];
            }), perturbationTypeColor(d[0]));
            var yAxis = hGsvg.select("g.y.axis");
            yAxis.select("text").text("Count of perturbations")        
            var nD = [ 'SmallMolecule', 'shRNA', 'cDNA' ].map(function(i) { 
                return {
                    type : i,
                    perturbagenType : tData[0].perturbationType[d[0] + ' Total '
                            + i]
                };
            }), mD = [ 'Cells', 'KinaseProteins', 'Antibodies',
                    'TranscribedGenes', 'PhosphoProteins' ]
                    .map(function(i) {
                        return {
                            type : i,
                            measuredEntity : tData[0].perturbationType[d[0]
                                    + ' Total ' + i]
                        };
                    });

            // call update functions of pie-chart, legend, and bar-chart.
            pC.update(nD);
            leg.update(nD);
            mC.update(mD);
            mleg.update(mD);
        }

        function mouseout(d) { // utility function to be called on mouse-out.
            // call the update function of assayTypeGraph with all data.
            hG.update(aData.map(function(v) {
                return [ v.AssayType, v.total ];
            }), function(d) {
                return assayTypeColor(d[0]);
            });
            var yAxis = hGsvg.select("g.y.axis");
            yAxis.select("text").text("Count of perturbations")
            pC.update(perturbagenTypeTotal);
            leg.update(perturbagenTypeTotal);
            mC.update(measuredEntityTotal);
            mleg.update(measuredEntityTotal);
        }
        return bC;
    }

    // function to handle perturbagenType legend.
    function legend(lD) {
        var leg = {};

        // create table for legend.
        var legend = d3.select(id).append("table").attr('class', 'legend');

        // create one row per segment.
        var tr = legend.append("tbody").selectAll("tr").data(lD).enter()
                .append("tr");

        // create the first column for each segment.
        tr.append("td").append("svg").attr("width", '16').attr("height", '16')
                .append("rect").attr("width", '16').attr("height", '16').attr(
                        "fill", function(d) {
                            return perturbagenTypeColor(d.type);
                        });

        // create the second column for each segment.
        tr.append("td").text(function(d) {
            return d.type;
        });

        // create the third column for each segment.
        tr.append("td").attr("class", 'legendType').text(function(d) {
            return d3.format(",")(d.perturbagenType);
        });

        // Utility function to be used to update the legend.
        leg.update = function(nD) {
            // update the data attached to the row elements.
            var l = legend.select("tbody").selectAll("tr").data(nD);

            // update the Type.
            l.select(".legendType").text(function(d) {
                return d3.format(",")(d.perturbagenType);
            });
        }
        return leg;
    }

    // function to handle perturbagenType legend.
    function measuredEntityLegend(lD) {
        var leg = {};

        // create table for legend.
        var legend = d3.select(id).append("table").attr('class', 'legend');

        // create one row per segment.
        var tr = legend.append("tbody").selectAll("tr").data(lD).enter()
                .append("tr");

        // create the first column for each segment.
        tr.append("td").append("svg").attr("width", '16').attr("height", '16')
                .append("rect").attr("width", '16').attr("height", '16').attr(
                        "fill", function(d) {
                            return measuredEntityColor(d.type);
                        });

        // create the second column for each segment.
        tr.append("td").text(function(d) {
            return d.type;
        });

        // create the third column for each segment.
        tr.append("td").attr("class", 'legendType').text(function(d) {
            return d3.format(",")(d.measuredEntity);
        });

        // Utility function to be used to update the legend.
        leg.update = function(nD) {
            // update the data attached to the row elements.
            var l = legend.select("tbody").selectAll("tr").data(nD);

            // update the Type.
            l.select(".legendType").text(function(d) {
                return d3.format(",")(d.measuredEntity);
            });
        }
        return leg;
    }

    function wrap(text, width) {

        text
                .each(function() {
                    var text = d3.select(this), words = text.text()
                            .split(/\s+/).reverse(), word, line = [], lineNumber = 0, lineHeight = 1.1, // ems
                    y = text.attr("y"), dy = parseFloat(text.attr("dy")), tspan = text
                            .text(null).append("tspan").attr("x", 0).attr("y",
                                    y).attr("dy", dy + "em");

                    while (word = words.pop()) {
                        line.push(word);
                        tspan.text(line.join(" "));
                        if (tspan.node().getComputedTextLength() > width) {
                            line.pop();
                            tspan.text(line.join(" "));
                            line = [ word ];
                            tspan = text.append("tspan").attr("x", 0).attr("y",
                                    y).attr("dy",
                                    ++lineNumber * lineHeight + dy + "em")
                                    .text(word);
                        }
                    }
                });
    }

    function log10(val) {
          return val > 0 ? Math.log(val) / Math.LN10 : 0;
    }

    var perturbagenTypeTotal = [ 'SmallMolecule', 'shRNA', 'cDNA' ]
            .map(function(d) {
                return {
                    type : d,
                    perturbagenType : tData[0].perturbagenType[d + 'TotalCount']
                };
            });
    var measuredEntityTotal = [ 'Cells', 'KinaseProteins', 'Antibodies',
            'TranscribedGenes', 'PhosphoProteins' ]
            .map(function(d) {
                return {
                    type : d,
                    measuredEntity : tData[0].measuredEntityType[d
                            + 'TotalCount']
                };
            });
    var perturbationTypeTotal = [ 'percent G2-M arrested', 'percent Interphase',
            'percent Mitosis', 'percent Non Arrested', 'IC50', 'percent Apoptosis',
            'percent Cell Viability', 'percent Kinase Inhibition', 'fold Change',
            'z-score', 'cell viability z-score', 'pKd' ].map(function(d) {
        return [ d, d3.sum(aData.map(function(t) {
            return t.perturbationType[d];
        })) ];
    });
    var assayTypeTotal = aData.map(function(d) {
        return [ d.AssayType, d.total ];
    });
    var hG = assayTypeGraph(assayTypeTotal),
        bC = perturbationTypeBarChart(perturbationTypeTotal),
        pC = perturbagenTypePieChart(perturbagenTypeTotal),
        leg = legend(perturbagenTypeTotal),
        mC = measuredEntityPieChart(measuredEntityTotal),
        mleg = measuredEntityLegend(measuredEntityTotal);
}

drawChart('#participantChart', assayData, totalData);