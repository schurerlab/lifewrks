package edu.miami.ccs.life.gui;

import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerFactory;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.hp.hpl.jena.vocabulary.ReasonerVocabulary;
import org.mindswap.pellet.jena.PelletReasoner;

/**
 * @author Sam Abeyruwan
 * @version 0.1
 */
public class SafReasonerFactory implements ReasonerFactory {


    private static final String URI = "http://saf.sam.pellet.owldl.com";

    private static SafReasonerFactory theInstance;

    public static OntModelSpec THE_SPEC;

    static {
        theInstance = new SafReasonerFactory();

        THE_SPEC = new OntModelSpec(OntModelSpec.OWL_MEM);
        THE_SPEC.setReasonerFactory(theInstance);

        ReasonerRegistry.theRegistry().register(SafReasonerFactory.theInstance());
    }

    public static SafReasonerFactory theInstance() {
        return theInstance;
    }

    private Model reasonerCapabilities;

    private SafReasonerFactory() {
    }

    public Reasoner create() {
        return new PelletReasoner(getCapabilities().getGraph());
    }

    public Reasoner create(Resource configuration) {
        return new PelletReasoner(getCapabilities().getGraph());
    }

    public Model getCapabilities() {
        if (reasonerCapabilities == null) {
            reasonerCapabilities = ModelFactory.createDefaultModel();
            Resource base = reasonerCapabilities.createResource(URI);
            base.addProperty(ReasonerVocabulary.nameP, "SAF (Sam Abeyruwan) Pellet Reasoner")
                    .addProperty(ReasonerVocabulary.descriptionP,
                            "Reasoner that is backed by the OWL DL reasoner Pellet for SAF.")
                    .addProperty(ReasonerVocabulary.supportsP, RDFS.subClassOf)
                    .addProperty(ReasonerVocabulary.supportsP, RDFS.subPropertyOf)
                    .addProperty(ReasonerVocabulary.supportsP, RDFS.member)
                    .addProperty(ReasonerVocabulary.supportsP, RDFS.range)
                    .addProperty(ReasonerVocabulary.supportsP, RDFS.domain);

            //.addProperty(ReasonerVocabulary.supportsP,
            //        ReasonerVocabulary.individualAsThingP)
            //.addProperty(ReasonerVocabulary.supportsP, ReasonerVocabulary.directSubClassOf)
            //.addProperty(ReasonerVocabulary.supportsP,
            //        ReasonerVocabulary.directSubPropertyOf)
            //.addProperty(ReasonerVocabulary.supportsP, ReasonerVocabulary.directRDFType);
        }

        return reasonerCapabilities;
    }

    public String getURI() {
        return URI;
    }
}
