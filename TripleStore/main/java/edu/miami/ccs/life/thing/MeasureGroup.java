package edu.miami.ccs.life.thing;

import com.hp.hpl.jena.ontology.Individual;
import edu.miami.ccs.life.LifeException;
import edu.miami.ccs.life.config.ConfigurationContext;
import edu.miami.ccs.life.read.Csv;
import edu.miami.ccs.life.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sam Abeyruwan
 * @version 0.1
 */
public class MeasureGroup implements OWLConcept {
    public List<Individual> execute(ConfigurationContext configurationContext)
            throws LifeException {

        List<Individual> subjects = new ArrayList<Individual>();

        Csv resultFile = configurationContext.getResultsCsv();
        if (resultFile != null) {
            // This information is kept per SAF file. Two different assays with same target,
            // endpoint, and pertubagen belong to two different measure groups.
            Map<Integer, List<String>> measureGroupInfoMap = new HashMap<Integer, List<String>>();
            Map<Integer, Individual> measureGroupIndividualMap = new HashMap<Integer, Individual>();

            Map<Integer, List<String>> results = resultFile.getDataStore();
            Map<String, Integer> headerToIndex = resultFile.getHeaderToIndex();
            for (List<String> tuple : results.values()) {

                List<String> sufficientInfo = new ArrayList<String>();
                sufficientInfo.add(tuple.get(headerToIndex.get("targetType")).toLowerCase());
                sufficientInfo.add(tuple.get(headerToIndex.get("targetName")).toLowerCase());
                sufficientInfo.add(tuple.get(headerToIndex.get("endpointName")).toLowerCase());
                Integer measureGroupId = null;
                Individual subject;
                if (measureGroupInfoMap.isEmpty() || measureGroupIndividualMap.isEmpty()) {
                    measureGroupId = configurationContext.currentCounterValue();
                    subject = Util.createIndividual(configurationContext, getName(),
                            Util.getID_(configurationContext, getName(), measureGroupId),
                            getName());
                    measureGroupInfoMap.put(measureGroupId, sufficientInfo);
                    measureGroupIndividualMap.put(measureGroupId, subject);
                } else {
                    // stupid unification
                    boolean isExistingListFound = false;
                    for (Map.Entry<Integer, List<String>> entry : measureGroupInfoMap.entrySet()) {
                        boolean isSameList = true;
                        // both lists should be same length
                        for (int u = 0; u < sufficientInfo.size(); u++) {
                            if (!sufficientInfo.get(u).equals(entry.getValue().get(u))) {
                                isSameList = false;
                                break;
                            }
                        }
                        if (isSameList) {
                            isExistingListFound = true;
                            measureGroupId = entry.getKey();
                            break;
                        }
                    }
                    if (isExistingListFound)
                        subject = measureGroupIndividualMap.get(measureGroupId);
                    else {
                        // this is a completely new measure group
                        measureGroupId = configurationContext.currentCounterValue();
                        subject = Util.createIndividual(configurationContext, getName(),
                                Util.getID_(configurationContext, getName(), measureGroupId),
                                getName());
                        measureGroupInfoMap.put(measureGroupId, sufficientInfo);
                        measureGroupIndividualMap.put(measureGroupId, subject);
                    }
                }

                if (measureGroupId == null || subject == null) {
                    configurationContext.getLog()
                            .warning("ERROR! missing information measureGroupId=" +
                                    measureGroupId + " subject=" + subject);
                    System.exit(-1);
                }


                Individual object = Util.createSingletonIndividualToSingletonObject(
                        configurationContext,
                        tuple.get(headerToIndex.get("pertubagenType")).toLowerCase(),
                        tuple.get(headerToIndex.get("pertubagenType")).toLowerCase(),
                        "has role",
                        "measured entity", "measured entity");
                subject.addProperty(configurationContext.getConceptualization()
                        .getObjectProperty("has measured entity"), object);


                object = Util.createSingletonIndividualToSingletonObject(configurationContext,
                        "molecular entity",
                        tuple.get(headerToIndex.get("pertubagenName")).toLowerCase(),
                        "has role",
                        "perturbagen", "perturbagen");
                subject.addProperty(configurationContext.getConceptualization()
                        .getObjectProperty("has_participant"), object);


                object = Util.createIndividualToSingletonObject(configurationContext,
                        tuple.get(headerToIndex.get("targetType")).toLowerCase(),
                        tuple.get(headerToIndex.get("targetName")).toLowerCase(),
                        "has role",
                        "target", "target");
                subject.addProperty(configurationContext.getConceptualization()
                        .getObjectProperty("has_participant"), object);

                // just the endpoint type
                object = Util.createIndividual(configurationContext,
                        tuple.get(headerToIndex.get("endpointName")).toLowerCase(),
                        Util.getID_(configurationContext,
                                tuple.get(headerToIndex.get("endpointName")).toLowerCase(),
                                configurationContext.currentCounterValue()),
                        tuple.get(headerToIndex.get("endpointName")).toLowerCase());
                subject.addProperty(configurationContext.getConceptualization()
                        .getObjectProperty("has endpoint"), object);

                subjects.add(subject);

            }

        }

        return subjects;
    }

    public String getName() {
        return "measure group";
    }
}
