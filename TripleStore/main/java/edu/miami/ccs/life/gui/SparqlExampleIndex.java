package edu.miami.ccs.life.gui;

/**
 *
 */

public class SparqlExampleIndex {

    private String ns =
            "PREFIX rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                    "PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\n" +
                    "PREFIX xsd:   <http://www.w3.org/2001/XMLSchema#>\n" +
                    "PREFIX owl:   <http://www.w3.org/2002/07/owl#>\n" +
                    "PREFIX bao:   <http://www.bioassayontology.org/bao#>\n" +
                    "PREFIX go:    <http://purl.org/obo/owl/GO#>\n" +
                    "PREFIX ncbit: <http://purl.org/obo/owl/NCBITaxon#>\n";

    public void populate(DerbyIndexManager db) {

        String q0 = "SELECT ?assay\n" +
                "WHERE { \n" +
                "\t ?assay rdf:type bao:BAO_0000015 .\n" +
                "}";
        db.insert(new Index("all the assays", q0, ns, 1));

    }

}
