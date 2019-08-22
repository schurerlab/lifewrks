package edu.miami.ccs.life.gui;

import edu.miami.ccs.life.LifeException;
import edu.miami.ccs.life.config.AltEntry;
import edu.miami.ccs.life.config.ConfigurationDescription;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Logger;

/**
 * @author Sam Abeyruwan
 * @version 0.1
 */
public class Gui {
    private ConfigurationDescription configurationDescription;
    private Logger                   log;

    public Gui(ConfigurationDescription configurationDescription, Logger log) {
        this.configurationDescription = configurationDescription;
        this.log = log;
    }

    public void run() throws LifeException, ClassNotFoundException, InstantiationException,
            IllegalAccessException {
        log.info("## @@>> GUI environment ...");
        final JenaTdbConnectionManager manager = new JenaTdbConnectionManager();


        for (AltEntry entry : configurationDescription.getAltEntries())
            manager.registerAltEntry(entry.getDocURI(), entry.getLocationURL());

        manager.connect(configurationDescription.getTripleStore(), null);

        LoadExternalOwlFiles externalOwlFiles = new LoadExternalOwlFiles();
        externalOwlFiles.register("bao", configurationDescription.getLifeOntology());
        externalOwlFiles.register("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns");
        externalOwlFiles.register("rdfs", "http://www.w3.org/2000/01/rdf-schema");
        externalOwlFiles.register("owl", "http://www.w3.org/2002/07/owl");

        final JenaBasedReasonerConnection connection =
                new JenaBasedReasonerConnection(manager);
        connection.init(true);

        SparqlExampleIndex sparqlExampleIndex = new SparqlExampleIndex();
        DerbyIndexManager dbManager =
                new DerbyIndexManager(sparqlExampleIndex, "localhost", 1527);
        dbManager.init();
        // meat
        final SparqlQueryInterface frame = new SparqlQueryInterface(connection, dbManager);
        ((OwlAc) frame.getAc()).setPrefixedInfo(externalOwlFiles.getPrefixedInfo());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                frame.addWindowListener(new WindowAdapter() {
                    /**
                     * Invoked when a window is in the process of being closed.
                     * The close operation can be overridden at this point.
                     */
                    public void windowClosing(WindowEvent e) {
                        try {
                            manager.close();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }


                });
                //Display the window.
//                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
