// Define the tour!
var tour = {
  id: "Tour-For-Life",
  steps: [
    {
      title: "Search",
      content: "Type terms here to search using free text and concepts",
      target: "search-box",
      placement: "bottom"
    },
    {
      title: "Sunburst",
      content: "This sunburst visualization shows the types of assays present within the LINCS dataset. \
                Clicking on a segment of the sunburst will display information associated with that \
                specific type (e.g., L1000) or class (e.g., Cell-Based) assay",
      target: document.querySelector("#chart-inner-circle-container"),
      placement: "right"
    },
    {
      title: "Kinase",
      content: "Clicking any of these links (e.g., Kinase proteins) will change the type of information \
                displayed by the sunburst, providing different perspectives \
                from which to explore the LINCS dataset",
      target: "chart-link-proteins",
      placement: "bottom"
    },
    {
      title: "Browse",
      content: "Click here to browse LINCS data using hierarchical categorizations of the dataset",
      target: "browse-tab",
      placement: "bottom"
    },
    {
      title: "Structure",
      content: "Click here to search the dataset using chemical compound substructure searches",
      target: "structure-tab",
      placement: "bottom"
    },
    {
      title: "Statistics",
      content: "Click here to view statistics about the LINCS dataset",
      target: "statistics-tab",
      placement: "bottom"
    },
    {
      title: "Sunburst",
      content: "Click on “Cell-Based” in the Bioassay sunburst and then start \
                the tour on the Results page to continue",
      target: document.querySelector("#chart-inner-circle-container"),
      placement: "right"
    },
  ],
  showPrevButton: true,
};

// Start the tour!
//$("#chart-link-assay").hover(function() {
//  hopscotch.startTour(tour);
//});

/* ===================== */
/* SETUP A TOUR FOR LIFE */
/* ===================== */
addClickListener = function(el, fn) {
  if (el.addEventListener) {
    el.addEventListener('click', fn, false);
  }
  else {
    el.attachEvent('onclick', fn);
  }
},

init = function() {
  var startBtnId = 'startTourBtn',
      calloutId = 'startTourCallout',
      mgr = hopscotch.getCalloutManager(),
      state = hopscotch.getState();

  if (state && state.indexOf('Tour-For-Life:') === 0) {
    // Already started the tour at some point!
    hopscotch.startTour(tour);
  }
  else {
    // Looking at the page for the first(?) time.
    setTimeout(function() {
      mgr.createCallout({
        id: calloutId,
        target: startBtnId,
        placement: 'top',
        title: 'Take a tour',
        content: 'Click this icon on any page to take a tour of available features',
        xOffset: 'center',
        arrowOffset: 'center',
        width: 240
      });
    }, 100);
  }

  addClickListener(document.getElementById(startBtnId), function() {
    if (!hopscotch.isActive) {
      mgr.removeAllCallouts();
      hopscotch.startTour(tour);
    }
  });
};

init();