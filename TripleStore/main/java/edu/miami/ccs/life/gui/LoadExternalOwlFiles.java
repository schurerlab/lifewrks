package edu.miami.ccs.life.gui;

import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.RDFS;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 */

public class LoadExternalOwlFiles {

    private Map<String, String> owlUriMap = new ConcurrentHashMap<String, String>();

    private Map<String, OntInfo> prefixedInfo = new ConcurrentHashMap<String, OntInfo>();

    public void register(String prefix, String url) {
        if (!owlUriMap.containsKey(prefix)) {
            owlUriMap.put(prefix, url);
        }
    }

    public Map<String, OntInfo> getPrefixedInfo() {
        return prefixedInfo;
    }

    public void loadToModel() {
        for (Map.Entry<String, String> entry : owlUriMap.entrySet()) {
            OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
            //System.out.println("read=" + entry.getValue());
            ontModel.read(entry.getValue(), null);
            OntInfo info = new OntInfo();
            readOntology(info, ontModel);
            prefixedInfo.put(entry.getKey(), info);
            ontModel.close();
        }
    }

    public void readOntology(OntInfo info, OntModel tBoxOntology) {
        listClasses(info, tBoxOntology);
        listDataTypeProperties(info, tBoxOntology);
        listObjectTypeProperties(info, tBoxOntology);
        listIndividuals(info, tBoxOntology);
    }

    private void listClasses(OntInfo info, OntModel tBoxOntology) {
        ExtendedIterator<OntClass> iterator = tBoxOntology.listNamedClasses();
        while (iterator.hasNext()) {
            OntClass aClass = iterator.next();
            renderURI(info, aClass.getModel(), aClass.getURI(), aClass);
        }
    }

    private void listDataTypeProperties(OntInfo info, OntModel tBoxOntology) {
        ExtendedIterator<DatatypeProperty> iterator = tBoxOntology.listDatatypeProperties();
        while (iterator.hasNext()) {
            DatatypeProperty datatypeProperty = iterator.next();
            renderURI(info, datatypeProperty.getModel(), datatypeProperty.getURI(),
                      datatypeProperty);
        }
    }

    private void listObjectTypeProperties(OntInfo info, OntModel tBoxOntology) {
        ExtendedIterator<ObjectProperty> iterator = tBoxOntology.listObjectProperties();
        while (iterator.hasNext()) {
            ObjectProperty objectProperty = iterator.next();
            renderURI(info, objectProperty.getModel(), objectProperty.getURI(), objectProperty);
        }
    }

    private void listIndividuals(OntInfo info, OntModel tBoxOntology) {
        ExtendedIterator<Individual> iterator = tBoxOntology.listIndividuals();
        while (iterator.hasNext()) {
            Individual individual = iterator.next();
            renderURI(info, individual.getModel(), individual.getURI(), individual);
        }
    }


    private void renderURI(OntInfo info, PrefixMapping prefixes, String uri, Resource c) {

        Statement labelStatment = c.getProperty(RDFS.label);
        String l = null;
        if (labelStatment != null && labelStatment.getLiteral() != null) {
            l = labelStatment.getLiteral().getString().trim();
        }
        if (l != null && l.length() > 0 && uri != null && uri.length() > 0) {
            //System.out.println("l=" + l + " c=" + prefixes.shortForm(uri));
            info.put(l, prefixes.shortForm(uri));
        }
    }

}
