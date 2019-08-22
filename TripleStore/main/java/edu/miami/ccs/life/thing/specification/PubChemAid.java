package edu.miami.ccs.life.thing.specification;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.ontology.Individual;
import edu.miami.ccs.life.LifeException;
import edu.miami.ccs.life.config.ConfigurationContext;
import edu.miami.ccs.life.thing.OWLConcept;
import edu.miami.ccs.life.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sam Abeyruwan
 * @version 0.1
 */
public class PubChemAid implements OWLConcept {

    public List<Individual> execute(ConfigurationContext configurationContext)
            throws LifeException {

        String bioassayID = (String) configurationContext.getJsonEntries().get("bioassayID");
        Individual subject = Util.createIndividual(configurationContext, getName(),
                Util.getID_(configurationContext, getName(), bioassayID), bioassayID);
        Util.createTypedLiteral(configurationContext, subject, "has id value", bioassayID,
                XSDDatatype.XSDinteger);
        List<Individual> individualList = new ArrayList<Individual>();
        individualList.add(subject);
        return individualList;
    }

    public String getName() {
        return "PubChem AID".toLowerCase();
    }
}
