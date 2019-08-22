package edu.miami.ccs.life.gui;

import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.query.larq.LARQ;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.Reasoner;
import edu.miami.ccs.life.LifeException;
import org.mindswap.pellet.jena.PelletInfGraph;
import org.mindswap.pellet.jena.PelletReasonerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Jena based reasoner. Reasoning is done in the memory mode. So if this is enabled
 * make sure that the system has enough memory. We keep only one Inferred model in the memory at a
 * given time.
 */
public class JenaBasedReasonerConnection
{

    private ConnectionManager connectionManager;

    private InfModel infModel;

    private Executor exec = Executors.newFixedThreadPool(10);

    public JenaBasedReasonerConnection(ConnectionManager connectionManager)
    {
        this.connectionManager = connectionManager;
    }

    public void init(boolean isOne)
    {
        if (isOne)
        {
            initReasonerWithOneModel();
        } //else {
        //initReasonerWithTwoModel();
        //}
    }

    private void initReasonerWithOneModel()
    {
        // create Pellet reasoner
        System.out.println("### Pellet reasoner starts with ONE model");
        Reasoner reasoner = PelletReasonerFactory.theInstance().create();
        // get the empty model
        System.out.println("### triple store size before inference : " +
                connectionManager.getABoxModel().size());
        infModel = ModelFactory.createInfModel(reasoner, connectionManager.getABoxModel());
        //------ lets comment this
        ((PelletInfGraph) infModel.getGraph()).classify();
        /*//------lets comment this
        // print validation report
        ValidityReport report = infModel.validate();
        printIterator(report.getReports(), "Validation Results");*/
        startReasoner(infModel);
        System.out.println("### Pellet reasoner ends ");
        System.out.println(
                "### ontology size before infernece: " + connectionManager.getABoxModel().size());
        System.out.println("###the triple store size after inference: " + infModel.size());

    }


    private void initReasonerWithTwoModel()
    {
        // create Pellet reasoner
        System.out.println("### Pellet reasoner starts with TWO models");
        //Reasoner reasoner = PelletReasonerFactory.theInstance().create();
        // get the empty model
        System.out.println("### triple store size before inference : " +
                connectionManager.getABoxModel().size());
        infModel = ModelFactory.createInfModel(connectionManager.getReasoner(),
                connectionManager.getTBoxModel(),
                connectionManager.getABoxModel());
        /*//------ lets comment this
        ((PelletInfGraph) infModel.getGraph()).classify();
        //------lets comment this
        // print validation report
        ValidityReport report = infModel.validate();
        printIterator(report.getReports(), "Validation Results");*/
        startReasoner(infModel);
        System.out.println("### Pellet reasoner ends ");
        System.out.println(
                "### ontology size before infernece: " + connectionManager.getTBoxModel().size());
        System.out.println("###the triple store size after inference: " + infModel.size());
    }


    public void startReasoner(InfModel model)
    {
        PelletInfGraph pellet = (PelletInfGraph) model.getGraph();
        //load data to pellet
        model.rebind();
        //check for consistency
        boolean consistent = pellet.isConsistent();
        // trigger classification
        // compute subClassOf and equivalentClass inferences between
        // all named classes
        pellet.classify();
        // trigger realization
        // find the most specific type for each instance
        pellet.realize();

        // create an inferencing model using Pellet reasoner
        if (!consistent)
        {
            // create an inferencing model using Pellet reasoner
            Model explanation = pellet.explainInconsistency();
            // print the explanation
            explanation.write(System.out);
        }
    }

    public void serialiseOntology(OutputStream out) throws IOException
    {


    }


    public void printIterator(Iterator<?> i, String header)
    {
        System.out.println(header);
        for (int c = 0; c < header.length(); c++)
        {
            System.out.print("=");
        }
        System.out.println();

        if (i.hasNext())
        {
            while (i.hasNext())
            {
                System.out.println(i.next());
            }
        } else
        {
            System.out.println("<EMPTY>");
        }
        System.out.println();
    }

    public void sql(final String sql, final ResultCallback callback)
    {
        if (infModel != null)
        {
            // use thread pool to execute this
            Runnable producer = new Runnable()
            {
                QueryExecution qe = null;

                public void run()
                {
                    try
                    {
                        Query query = QueryFactory.create(sql, Syntax.syntaxARQ);
                        qe = QueryExecutionFactory.create(query, infModel);
                        ResultSet rs = qe.execSelect();
                        if (callback != null)
                        {
                            try
                            {
                                callback.result(rs);
                            } catch (LifeException e)
                            {
                                System.out.println("## iterator fails");
                            }
                        }
                    } catch (Exception e)
                    {
                        if (callback != null)
                        {
                            callback.setErrorState(true);
                            callback.error(e.getMessage());
                        }
                    } finally
                    {
                        if (qe != null)
                        {
                            qe.close();
                        }
                    }

                }
            };
            exec.execute(producer);
        }
    }


    public void larqSql(String sql, ResultCallback callback)
    {
        if (connectionManager.getIndexLarq() != null)
        {
            QueryExecution qe = null;
            try
            {
                Query query = QueryFactory.create(sql);
                qe = QueryExecutionFactory.create(query, infModel);
                // -- Make available to this query execution only
                LARQ.setDefaultIndex(qe.getContext(), connectionManager.getIndexLarq());
                ResultSet rs = qe.execSelect();
//                ResultSetFormatter.out(rs);
                if (callback != null)
                {
                    try
                    {
                        callback.result(rs);
                    } catch (LifeException e)
                    {
                        System.out.println("## iterator fails");
                    }
                }
            } catch (Exception e)
            {
                if (callback != null)
                {
                    callback.setErrorState(true);
                    callback.error(e.getMessage());
                }
            } finally
            {
                if (qe != null)
                {
                    qe.close();
                }
            }

        }
    }

}
