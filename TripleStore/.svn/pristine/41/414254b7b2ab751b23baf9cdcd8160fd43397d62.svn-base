package edu.miami.ccs.life.gui;

import com.hp.hpl.jena.query.ResultSet;
import edu.miami.ccs.life.LifeException;

/**
 * This is the interface that load the data to the callback
 */
public interface ResultCallback {

    void result(ResultSet rs) throws LifeException;

    boolean isError();

    void setErrorState(boolean errorState);

    void error(String error);


}
