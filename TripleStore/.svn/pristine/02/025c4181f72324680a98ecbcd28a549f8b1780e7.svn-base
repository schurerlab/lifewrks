package edu.miami.ccs.life.util;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import edu.miami.ccs.life.LifeException;
import edu.miami.ccs.life.config.ConfigurationContext;
import edu.miami.ccs.life.thing.OWLConcept;

import java.util.List;

/**
 * @author Sam Abeyruwan
 * @version 0.1
 */
public class Util {
    public static void createTypedLiteral(ConfigurationContext configurationContext,
                                          Individual subject, String predicate, String object,
                                          XSDDatatype xsdDatatype) throws LifeException {
        subject.addLiteral(
                configurationContext.getConceptualization().getDatatypeProperty(predicate),
                configurationContext.getOntModel().createTypedLiteral(object, xsdDatatype));
    }

    public static void createObjectPropertyToSingleton(ConfigurationContext configurationContext,
                                                       Individual subject, String predicate,
                                                       String owlConcept, String comment)
            throws LifeException {
        Individual object = createSingletonIndividual(configurationContext, owlConcept, comment);
        subject.addProperty(
                configurationContext.getConceptualization().getObjectProperty(predicate),
                object);

    }

    public static void createObjectProperty(ConfigurationContext configurationContext,
                                            Individual subject, String predicate,
                                            Class<?> classObject)
            throws LifeException {
        ClassLoader classLoader = Util.class.getClassLoader();
        try {
            Class<?> clazz = classLoader.loadClass(classObject.getName());
            OWLConcept owlConcept = (OWLConcept) clazz.newInstance();
            List<Individual> objectList = owlConcept.execute(configurationContext);
            for (Individual object : objectList) {
                subject.addProperty(
                        configurationContext.getConceptualization().getObjectProperty(predicate),
                        object);
            }
        } catch (ClassNotFoundException e) {
            throw new LifeException(e);
        } catch (InstantiationException e) {
            throw new LifeException(e);
        } catch (IllegalAccessException e) {
            throw new LifeException(e);
        }
    }

    public static Individual createSingletonIndividual(ConfigurationContext configurationContext,
                                                       String owlConcept, String comment)
            throws LifeException {
        if (configurationContext.getSingletonIndividualMap().containsKey(owlConcept))
            return configurationContext.getSingletonIndividualMap().get(owlConcept);
        Individual individual = createIndividual(configurationContext, owlConcept,
                getID_(configurationContext, owlConcept,
                        configurationContext.currentCounterValue()), comment);
        configurationContext.addToSingletonIndividualMap(comment, individual);
        return individual;
    }

    public static Individual createSingletonIndividualToSingletonObject(
            ConfigurationContext configurationContext,
            String owlSubjectConcept,
            String subjectComment,
            String predicate,
            String owlObjectConcept,
            String objectComment)
            throws LifeException {
        if (configurationContext.getSingletonIndividualMap().containsKey(subjectComment))
            return configurationContext.getSingletonIndividualMap().get(subjectComment);

        Individual subject = createIndividual(configurationContext, owlSubjectConcept,
                getID_(configurationContext, owlSubjectConcept,
                        configurationContext.currentCounterValue()), subjectComment);
        configurationContext.addToSingletonIndividualMap(subjectComment, subject);


        Individual object = createIndividual(configurationContext, owlObjectConcept,
                getID_(configurationContext, owlObjectConcept,
                        configurationContext.currentCounterValue()), objectComment);
        configurationContext.addToSingletonIndividualMap(owlObjectConcept, subject);

        subject.addProperty(
                configurationContext.getConceptualization().getObjectProperty(predicate), object);

        return subject;
    }

    public static Individual createIndividualToSingletonObject(
            ConfigurationContext configurationContext,
            String owlSubjectConcept,
            String subjectComment,
            String predicate,
            String owlObjectConcept,
            String objectComment)
            throws LifeException {
        Individual subject = createIndividual(configurationContext, owlSubjectConcept,
                getID_(configurationContext, owlSubjectConcept,
                        configurationContext.currentCounterValue()), subjectComment);
        Individual object =
                createSingletonIndividual(configurationContext, owlObjectConcept, objectComment);
        subject.addProperty(
                configurationContext.getConceptualization().getObjectProperty(predicate), object);
        return subject;
    }


    public static Individual createIndividual(ConfigurationContext configurationContext,
                                              String owlConcept, String individualName,
                                              String comment)
            throws LifeException {
        if (individualName == null || individualName.length() == 0)
            throw new LifeException("@@>> invalid individualName=" + individualName);
        Individual individual = configurationContext.getOntModel()
                .createIndividual(configurationContext.getMainNamespace() + individualName,
                        configurationContext.getConceptualization().getOntClass(owlConcept).get(0));
        individual.addLabel(comment, null);
        return individual;
    }

    public static String getID(ConfigurationContext configurationContext, String owlConcept)
            throws LifeException {
        OntClass ontClass =
                configurationContext.getConceptualization().getOntClass(owlConcept).get(0);
        return ontClass.getLocalName();
    }

    public static String getID_(ConfigurationContext configurationContext, String owlConcept,
                                String suffix) throws LifeException {
        return getID(configurationContext, owlConcept).concat("_").concat(suffix);
    }

    public static String getID_(ConfigurationContext configurationContext, String owlConcept,
                                int suffix) throws LifeException {
        return getID(configurationContext, owlConcept).concat("_").concat(Integer.toString(suffix));
    }

}
