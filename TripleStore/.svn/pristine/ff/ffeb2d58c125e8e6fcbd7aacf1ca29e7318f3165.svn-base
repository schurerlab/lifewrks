package edu.miami.ccs.life;

import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.tdb.TDB;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.RDFS;
import edu.miami.ccs.life.config.*;
import edu.miami.ccs.life.gui.Gui;
import edu.miami.ccs.life.read.Csv;
import edu.miami.ccs.life.saf.SafJsonSchemaProvider;
import edu.miami.ccs.life.thing.SafLoadable;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.openjena.atlas.logging.java.TextFormatter;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static edu.miami.ccs.life.LifeConstants.*;

/**
 * @author Sam Abeyruwan
 * @version 0.1
 *          <p/>
 *          Entry point
 */
public class Controller {
    private File                     configurationFile;
    private OntModel                 ontModel;
    private Logger                   log;
    private Map<String, Object>      safSchemaEntries;
    private ConfigurationContext     configurationContext;
    private ConfigurationDescription configurationDescription;
    private Conceptualization        conceptualization;

    public Controller(File configurationFile) {
        this.configurationFile = configurationFile;
    }

    public void init() throws IOException, LifeException {
        log = Logger.getLogger(Controller.class.getName());
        log.setLevel(Level.ALL);
        try {
            FileHandler handler = new FileHandler("LIFE.log");
            handler.setFormatter(new TextFormatter());
            log.addHandler(handler);
        } catch (IOException e) {
            throw e;
        }

        log.info("# loading LIFE framework configuration");
        Constructor constructor = new Constructor(ConfigurationDescription.class);
        TypeDescription configDescription = new TypeDescription(ConfigurationDescription.class);
        configDescription.putListPropertyType(BIO_ASSAY_TYPE_BINDINGS, AssayTypeBinding.class);
        configDescription.putListPropertyType(ALT_ENTRIES, AltEntry.class);
        constructor.addTypeDescription(configDescription);
        Yaml yaml = new Yaml(constructor);
        configurationDescription =
                (ConfigurationDescription) yaml.load(new FileInputStream(configurationFile));

        if (configurationDescription.getLifeMode() != null &&
                (configurationDescription.getLifeMode().equals(LIFE_GUI) ||
                        configurationDescription.getLifeMode().equals(LIFE_SCHEMA)))
            return;

        log.info("# loading TBox and preparing ABox to load data ...");
        TDB.getContext().set(TDB.symLogExec, true);
        Model model = TDBFactory.createModel(configurationDescription.getTripleStore());
        ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM, model);
        for (AltEntry entry : configurationDescription.getAltEntries())
            ontModel.getDocumentManager().addAltEntry(entry.getDocURI(), entry.getLocationURL());
        ontModel.read(configurationDescription.getLifeOntology(), null);

        //ontModel.read(configurationDescription.getLifeOntology(), null);
        log.info("@ loading conceptualization ...");
        conceptualization = new Conceptualization(log);

        // read all name classes
        for (ExtendedIterator<OntClass> iterator = ontModel.listNamedClasses();
                iterator.hasNext(); ) {
            OntClass ontClass = iterator.next();
            Statement statement = ontClass.getProperty(RDFS.label);
            String propertyLabel = null;
            if (statement != null) propertyLabel = statement.getString().toLowerCase();
            conceptualization.addToNamedClassMap(propertyLabel, ontClass);

        }
        // read all object properties
        for (ExtendedIterator<ObjectProperty> iterator = ontModel.listObjectProperties();
                iterator.hasNext(); ) {
            ObjectProperty objectProperty = iterator.next();
            Statement statement = objectProperty.getProperty(RDFS.label);
            String propertyLabel = null;
            if (statement != null) propertyLabel = statement.getString().toLowerCase();
            conceptualization.addToObjectPropertyMap(propertyLabel, objectProperty);
        }
        // read all data type properties
        for (ExtendedIterator<DatatypeProperty> iterator = ontModel.listDatatypeProperties();
                iterator.hasNext(); ) {
            DatatypeProperty datatypeProperty = iterator.next();
            Statement statement = datatypeProperty.getProperty(RDFS.label);
            String propertyLabel = null;
            if (statement != null) propertyLabel = statement.getString().toLowerCase();
            conceptualization.addToDatatypePropertyMap(propertyLabel, datatypeProperty);
        }

        // read all individuals
        for (ExtendedIterator<Individual> iterator = ontModel.listIndividuals();
                iterator.hasNext(); ) {
            Individual individual = iterator.next();
            Statement statement = individual.getProperty(RDFS.label);
            String propertyLabel = null;
            if (statement != null) propertyLabel = statement.getString().toLowerCase();
            conceptualization.addToIndividualsMap(propertyLabel, individual);
        }


        log.info("@ loading SAF entries ...");
        try {
            safSchemaEntries = new ObjectMapper().readValue(
                    new File(configurationDescription.getSafSchema()),
                    new TypeReference<Map<String, Object>>() {
                    });
        } catch (IOException e) {
            throw e;
        }


    }

    public void run() throws Exception {
        if (configurationDescription.getLifeMode() != null &&
                configurationDescription.getLifeMode().equals(LIFE_SCHEMA)) {
            new SafJsonSchemaProvider(configurationDescription, log);
            return;
        }

        if (configurationDescription.getLifeMode() != null &&
                configurationDescription.getLifeMode().equals(LIFE_GUI)) {
            new Gui(configurationDescription, log).run();
            return;
        }


        // read the saf files
        // list specific files
        File tmp = new File(configurationDescription.getSafFilesBase());
        File[] safFiles = tmp.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.getAbsolutePath()
                        .endsWith(configurationDescription.getSafExtension());
            }
        });

        tmp = new File(configurationDescription.getResultFilesBase());
        File[] resultFiles = tmp.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.getAbsolutePath()
                        .endsWith(configurationDescription.getResultExtension());
            }
        });

        Map<String, File> resultFilesSimpleMetaData = new HashMap<String, File>();
        for (File file : resultFiles)
            resultFilesSimpleMetaData.put(file.getName(), file);

        configurationContext = new ConfigurationContext();
        configurationContext.setSafSchemaEntries(safSchemaEntries);
        configurationContext.setOntModel(ontModel);
        configurationContext.setConfigurationDescription(configurationDescription);
        configurationContext.setConceptualization(conceptualization);
        configurationContext.setLog(log);
        // main control loop
        for (File file : safFiles) {
            // read the json file
            Map<String, Object> jsonEntries = new ObjectMapper().readValue(
                    file,
                    new TypeReference<Map<String, Object>>() {
                    });
            configurationContext.setJsonEntries(jsonEntries);
            configurationContext.setResultsCsv(null);

            Object endpointFile = jsonEntries.get(ENDPOINT_FILE);
            if (endpointFile == null)
                log.warning("# endpointFile attribute does not exists!");
            else {
                String resultFileName =
                        (String) ((Map<String, Object>) endpointFile).get(FILE_NAME);
                if (resultFileName == null)
                    log.warning(" endpointFile.fileName does not exits!");
                else {
                    File resultFile = resultFilesSimpleMetaData.get(resultFileName);
                    if (resultFile == null)
                        log.warning("# endpointFile.fileName.resultFile can not be located!");
                    else {
                        Csv csvFile = new Csv();
                        csvFile.read(new FileInputStream(resultFile), ';');
                        configurationContext.setResultsCsv(csvFile);
                    }
                }
            }

            String bioAssayType = (String) jsonEntries.get(BIO_ASSAY_TYPE);
            if (bioAssayType != null) {
                String bindsTo = null;
                for (AssayTypeBinding assayTypeBinding : configurationDescription
                        .getBioAssayTypeBindings()) {
                    if (assayTypeBinding.getBioAssayType().equals(bioAssayType)) {
                        bindsTo = assayTypeBinding.getBindsTo();
                        break;
                    }
                }
                if (bindsTo != null) {
                    ClassLoader classLoader = this.getClass().getClassLoader();
                    Class<?> clazz = classLoader.loadClass(bindsTo);
                    SafLoadable safLoadable = (SafLoadable) clazz.newInstance();
                    log.info("# loading data from=" + file.getName());
                    safLoadable.execute(configurationContext);
                } else
                    log.warning("# bindsTo does not exists. File=" + file.getAbsolutePath());

            } else
                log.warning("# bioAssayType does not exists. File=" + file.getAbsolutePath());

        }
        ontModel.close();
    }
}
