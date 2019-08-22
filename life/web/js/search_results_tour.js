// Define the tour!
var tour = {
  id: "Tour-For-Life",
  steps: [
    {
      title: "Search Results",
      content: "This page shows search results",
      target: "dynamic",
      placement: "top"
    },
    {
      title: "Result Categories",
      content: "Click on a category to change the types of results displayed",
      target: "tabHolder",
      placement: "right"
    },
    {
      title: "Index controls - constituents",
      content: "Selecting constituents or perturbations will change the way \
               that the results are displayed \
               Constituent searches show results based on information \
               matching attributes of assay participants (e.g., synonyms) as well \
               as the types of available related experimental data (e.g., L1000). ",
      target: "constituentsIndex",
      placement: "right"
    },
    {
        title: "Index controls - perturbations",
        content: "Selecting perturbations will change the way \
                 that the results are displayed \
                 Perturbation searches show results based on these matching attributes, \
                 but also include other results based on links between assay constitutes \
                 produced by experimental results (i.e., perturbations)",
        target: "perturbationIndex",
        placement: "right"
      },
    {
      title: "View tags:",
      content: "Click here to further filter search results using context \
                dependent attributes.  For example restricting results based \
                on protein family.",
      target: "tags-header",
      placement: "top"
    },
    {
      title: "Download",
      content: "Click to download information related to the currently \
                displayed results.<br /> <a></a>",
      target: "download",
      placement: "right"
    },
    {
      title: "View data",
      content: "Click here to graphically explore data associated with search results",
      target: "viewDataLink",
      placement: "right"
    }
  ],
  showPrevButton: true,
};

/* ================================== */
/* SETUP A TOUR IN LIFE SEARCH RESULT */
/* ================================== */
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
//  else {
    // Looking at the page for the first(?) time.
//    setTimeout(function() {
//      mgr.createCallout({
//        id: calloutId,
//        target: startBtnId,
//        placement: 'top',
//        title: 'Take a tour',
//        content: 'Click this icon on any page to take a tour of available features',
//        xOffset: 'center',
//        arrowOffset: 'center',
//        width: 240
//      });
//    }, 100);
//  }
  addClickListener(document.getElementById(startBtnId), function() {
    if (!hopscotch.isActive) {
      mgr.removeAllCallouts();
      hopscotch.startTour(tour);
    }
  });
};
init();
