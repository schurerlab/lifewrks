package edu.miami.ccs.life.gui;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.larq.IndexBuilderSubject;
import com.hp.hpl.jena.query.larq.IndexLARQ;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.tdb.TDB;
import com.hp.hpl.jena.tdb.TDBFactory;
import edu.miami.ccs.life.LifeException;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.mindswap.pellet.jena.PelletReasonerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 */
public class JenaTdbConnectionManager implements ConnectionManager {

    private OntModel tBoxModel;

    private Model aBoxModel;

    private IndexLARQ indexLARQ;

    private Reasoner reasoner;


    private Map<String, String> altEntriesMap = new ConcurrentHashMap<String, String>();

    public JenaTdbConnectionManager() throws LifeException {
        TDB.getContext().set(TDB.symLogExec, true);
    }


    public void connect(String tdbDirName, File kb) {
        //System.out.println("@@@ load TBox");
        //tBoxModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
        //for (Map.Entry<String, String> entry : altEntriesMap.entrySet()) {
        //    tBoxModel.getDocumentManager().addAltEntry(entry.getKey(), entry.getValue());
        ///}
        //tBoxModel.read(kb.toURI().toString());
        //reasoner = PelletReasonerFactory.theInstance().create();
        reasoner = SafReasonerFactory.theInstance().create();
        //OntModelSpec spec = new OntModelSpec(OntModelSpec.OWL_DL_MEM);
        //spec.setReasoner(reasoner);
        //reasoner = reasoner.bindSchema(tBoxModel);

        System.out.println("@@@@ load ABox");
        aBoxModel = TDBFactory.createModel(tdbDirName);
    }

    public void connect(String directoryName, String larqDir, String tBoxUrl) throws LifeException {
        if (!(directoryName != null && directoryName.length() > 0)) {
            throw new LifeException("Directory name is not available");
        }
        // -- Read and index all literal strings.
        IndexBuilderSubject larqBuilder = null;
        if (larqDir != null) {
            IndexWriter writer;
            try {
                writer = new IndexWriter(larqDir, new StandardAnalyzer(), true);
            } catch (IOException e) {
                throw new LifeException(e);
            }
//            writer.setWriteLockTimeout(2000);
            larqBuilder = new IndexBuilderSubject(writer);
        }

        tBoxModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
        if (larqBuilder != null) {
            // Index statements as they are added to the model.
            System.out.println("register model");
            tBoxModel.register(larqBuilder);
        }
        for (Map.Entry<String, String> entry : altEntriesMap.entrySet()) {
            tBoxModel.getDocumentManager().addAltEntry(entry.getKey(), entry.getValue());
        }
        System.out.println("@@@@ load TBox");
        tBoxModel.read(tBoxUrl);
        System.out.println("@@@@ load ABox");
        aBoxModel = TDBFactory.createModel(directoryName);
        if (larqBuilder != null) {
            Dataset dataset = TDBFactory.createDataset(directoryName);
            Model defaultModel = dataset.getDefaultModel();
            System.out.println("index default model");
            larqBuilder.indexStatements(defaultModel.listStatements());
            for (Iterator names = dataset.listNames(); names.hasNext(); ) {
                String name = names.next().toString();
                System.out.println("index strings and literals in named model: " + name);
                Model model = dataset.getNamedModel(name);
                larqBuilder.indexStatements(model.listStatements());
            }
            // -- Finish indexing
            larqBuilder.closeWriter();
            System.out.println("unregister model");
            tBoxModel.unregister(larqBuilder);
            indexLARQ = larqBuilder.getIndex();
        }

    }

    public void registerAltEntry(String key, String value) {
        altEntriesMap.put(key, value);
    }


    public void close() {
        System.out.println("closing the models");
        if (tBoxModel != null) {
            tBoxModel.close();
        }
        if (aBoxModel != null) {
            aBoxModel.close();
        }
    }

    public void truncate() throws LifeException {
        throw new RuntimeException("Not implemented yet");
    }

    public void format(boolean flag) throws LifeException {
        throw new RuntimeException("Not implemented yet");
    }


    public Model getTBoxModel() {
        return tBoxModel;
    }

    public Model getABoxModel() {
        return aBoxModel;
    }

    public IndexLARQ getIndexLarq() {
        return indexLARQ;
    }

    public Reasoner getReasoner() {
        return reasoner;
    }
}
