package edu.miami.ccs.life.gui;

import com.hp.hpl.jena.query.larq.IndexLARQ;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.reasoner.Reasoner;
import edu.miami.ccs.life.LifeException;

import java.io.File;

/**
 * This connects to the persistence store of the underlying
 * implementation.
 * <p/>
 * M is the required connection object
 */
public interface ConnectionManager {

    void connect(String tdbDirName, File kb);

    void connect(String tdbDirName, String larqDir, String tBoxUrl) throws LifeException;

    void close();

    void truncate() throws LifeException;

    /**
     * format the underlying store
     *
     * @param flag set the underlying store to be formatted or not
     * @throws LifeException if the operation should failed
     */
    void format(boolean flag) throws LifeException;

    Model getTBoxModel();

    Model getABoxModel();

    IndexLARQ getIndexLarq();

    Reasoner getReasoner();


}
