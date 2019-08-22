package edu.miami.ccs.life.config;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import edu.miami.ccs.life.read.Csv;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Sam Abyeyruwan
 * @version 0.1
 */
public class ConfigurationContext {
    private Map<String, Object>      safSchemaEntries;
    private Map<String, Object>      jsonEntries;
    private Csv                      resultsCsv;
    private OntModel                 ontModel;
    private ConfigurationDescription configurationDescription;
    private Logger                   log;
    private Conceptualization        conceptualization;
    private Map<String, Individual>  singletonIndividualMap;
    private int                      globalCounter;

    public ConfigurationContext() {
        singletonIndividualMap = new HashMap<String, Individual>();
    }

    public void addToSingletonIndividualMap(String key, Individual value) {
        if (singletonIndividualMap.containsKey(key))
            return;
        singletonIndividualMap.put(key, value);
    }

    public Map<String, Individual> getSingletonIndividualMap() {
        return singletonIndividualMap;
    }

    public int currentCounterValue() {
        return ++globalCounter;
    }

    public ConfigurationDescription getConfigurationDescription() {
        return configurationDescription;
    }

    public void setConfigurationDescription(ConfigurationDescription configurationDescription) {
        this.configurationDescription = configurationDescription;
    }

    public Map<String, Object> getSafSchemaEntries() {
        return safSchemaEntries;
    }

    public void setSafSchemaEntries(Map<String, Object> safSchemaEntries) {
        this.safSchemaEntries = safSchemaEntries;
    }

    public Map<String, Object> getJsonEntries() {
        return jsonEntries;
    }

    public void setJsonEntries(Map<String, Object> jsonEntries) {
        this.jsonEntries = jsonEntries;
    }

    public Csv getResultsCsv() {
        return resultsCsv;
    }

    public void setResultsCsv(Csv resultsCsv) {
        this.resultsCsv = resultsCsv;
    }

    public OntModel getOntModel() {
        return ontModel;
    }

    public void setOntModel(OntModel ontModel) {
        this.ontModel = ontModel;
    }

    public Logger getLog() {
        return log;
    }

    public void setLog(Logger log) {
        this.log = log;
    }

    public Conceptualization getConceptualization() {
        return conceptualization;
    }

    public void setConceptualization(Conceptualization conceptualization) {
        this.conceptualization = conceptualization;
    }

    public String getMainNamespace() {
        return configurationDescription.getMainNamespace();
    }
}
