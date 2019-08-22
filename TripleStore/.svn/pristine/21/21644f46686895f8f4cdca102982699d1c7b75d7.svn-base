package edu.miami.ccs.life.thing.bioassay;

import com.hp.hpl.jena.ontology.Individual;
import edu.miami.ccs.life.LifeException;
import edu.miami.ccs.life.config.ConfigurationContext;
import edu.miami.ccs.life.thing.MeasureGroup;
import edu.miami.ccs.life.thing.SafLoadable;
import edu.miami.ccs.life.thing.specification.PubChemAid;
import edu.miami.ccs.life.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sam Abeyruwan
 * @version 0.1
 *          <p/>
 *          OWL label "binding assay"
 *          <p/>
 *          This class take care of the ABox entries for anything in the universe to type of a
 *          binding assay.
 *          <p/>
 *          This concept is defined in life_model_v1_07.26.2012.owl ontology.
 */
public class BindingAssay implements SafLoadable {

    public List<Individual> execute(ConfigurationContext configurationContext)
            throws LifeException {
        String bioassayID = (String) configurationContext.getJsonEntries().get("bioassayID");

        Individual subject = Util.createIndividual(configurationContext, getName(),
                Util.getID_(configurationContext, getName(), bioassayID), bioassayID);

        Util.createObjectProperty(configurationContext, subject, "has id", PubChemAid.class);


        String[] singletonRels = {"assayFormat", "assaySource", "assayKit"};
        String[] singletonRelsOWL = {"has assay format", "has specification", "has specification"};
        for (int rel = 0; rel < singletonRels.length; rel++) {
            Object relObject = configurationContext.getJsonEntries().get(singletonRels[rel]);
            if (relObject != null)
                Util.createObjectPropertyToSingleton(configurationContext, subject,
                        singletonRelsOWL[rel], ((String) relObject).toLowerCase(),
                        ((String) relObject).toLowerCase());
        }

        String[] otherSpecAttrArray =
                {"assayTitle", "assayDescription", "assayDepositionDate", "lab"};
        String[] otherSpecOWLArray =
                {"assay title", "assay description", "assay deposition date", "assay provider"};
        for (int spec = 0; spec < otherSpecAttrArray.length; spec++) {
            Object specObj = configurationContext.getJsonEntries().get(otherSpecAttrArray[spec]);
            if (specObj != null)
                Util.createIndividual(configurationContext, otherSpecOWLArray[spec],
                        Util.getID_(configurationContext, otherSpecOWLArray[spec],
                                configurationContext.currentCounterValue()), (String) specObj);
        }

        Util.createObjectProperty(configurationContext, subject, "has measure group",
                MeasureGroup.class);


        return new ArrayList<Individual>(); // dummy
    }

    public String getName() {
        return "binding assay";
    }
}
