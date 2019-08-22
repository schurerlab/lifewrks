package edu.miami.ccs.life.config;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import edu.miami.ccs.life.LifeException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * @author Sam Abeyruwan
 * @version 0.1
 */
public class Conceptualization {
    private Logger log;

    private Map<String, List<OntClass>>   namedClassesMap;
    private Map<String, List<Individual>> individualsMap;
    private Map<String, ObjectProperty>   objectPropertyMap;
    private Map<String, DatatypeProperty> datatypePropertyMap;

    public Conceptualization(Logger log) {
        this.log = log;
        namedClassesMap = new ConcurrentHashMap<String, List<OntClass>>();
        individualsMap = new ConcurrentHashMap<String, List<Individual>>();
        objectPropertyMap = new ConcurrentHashMap<String, ObjectProperty>();
        datatypePropertyMap = new ConcurrentHashMap<String, DatatypeProperty>();
    }


    public List<OntClass> getOntClass(String label) throws LifeException {
        if (!namedClassesMap.containsKey(label))
            throw new LifeException(label + " OntClass not found.");
        return namedClassesMap.get(label);
    }

    public List<Individual> getIndividual(String label) throws LifeException {
        if (!individualsMap.containsKey(label))
            throw new LifeException(label + " Individual not found.");
        return individualsMap.get(label);
    }

    public ObjectProperty getObjectProperty(String label) throws LifeException {
        if (!objectPropertyMap.containsKey(label))
            throw new LifeException(label + " ObjectProperty not found.");
        return objectPropertyMap.get(label);
    }

    public DatatypeProperty getDatatypeProperty(String label) throws LifeException {
        if (!datatypePropertyMap.containsKey(label))
            throw new LifeException(label + " DatatypeProperty not found.");
        return datatypePropertyMap.get(label);
    }


    public void addToNamedClassMap(String label, OntClass ontClass) throws LifeException {
        if (label != null && label.length() > 0) {
            if (namedClassesMap.containsKey(label)) {
                String msg = "WARNING! namedClass already exists! " + label + " " +
                        namedClassesMap.get(label) + " : " + ontClass.toString();
                log.warning(msg);
                namedClassesMap.get(label).add(ontClass);
            } else {
                List<OntClass> ontClassList = new ArrayList<OntClass>();
                ontClassList.add(ontClass);
                namedClassesMap.put(label, ontClassList);
                log.info("namedClassLabel=" + label + " ontClassID=" + ontClass.toString());
            }
        } else {
            log.warning("WARNING! the ontClass=" + ontClass.toString() + " has NULL label.");
        }
    }

    public void addToIndividualsMap(String label, Individual individual) throws LifeException {
        if (label != null && label.length() > 0) {
            if (individualsMap.containsKey(label)) {
                String msg = "WARNING! individual already exists! " + label + " " +
                        individualsMap.get(label) + " : " + individual.toString();
                log.warning(msg);
                individualsMap.get(label).add(individual);
            } else {
                List<Individual> individualList = new ArrayList<Individual>();
                individualList.add(individual);
                individualsMap.put(label, individualList);
                log.info("individualLabel=" + label + " individualID=" + individual.toString());
            }
        } else {
            log.warning("WARNING! the individual=" + individual.toString() + " has NULL label.");
        }
    }

    public void addToObjectPropertyMap(String label, ObjectProperty objectProperty)
            throws LifeException {
        if (label != null && label.length() > 0) {
            if (objectPropertyMap.containsKey(label)) {
                String msg = "ERROR! objectProperty already exists! " + label + " " +
                        objectPropertyMap.get(label) + " : " + objectProperty.toString();
                log.warning(msg);
                throw new LifeException(msg);
            } else {
                objectPropertyMap.put(label, objectProperty);
                log.info("objectPropertyLabel=" + label + " objectPropertyID=" +
                        objectProperty.toString());
            }
        } else {
            log.warning("WARNING! the objectProperty=" + objectPropertyMap.toString() +
                    " has NULL label.");
        }
    }

    public void addToDatatypePropertyMap(String label, DatatypeProperty datatypeProperty)
            throws LifeException {
        if (label != null && label.length() > 0) {
            if (datatypePropertyMap.containsKey(label)) {
                String msg = "ERROR! datatypeProperty already exists! " + label + " " +
                        datatypePropertyMap.get(label) + " : " + datatypeProperty.toString();
                log.warning(msg);
                throw new LifeException(msg);
            } else {
                datatypePropertyMap.put(label, datatypeProperty);
                log.info("datatypePropertyLabel=" + label + " datatypePropertyID=" +
                        datatypeProperty.toString());
            }
        } else {
            log.warning("WARNING! the datatypeProperty=" + datatypeProperty.toString() +
                    " has NULL label.");
        }
    }
}
