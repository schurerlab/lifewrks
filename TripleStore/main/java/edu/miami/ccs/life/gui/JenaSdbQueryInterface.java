package edu.miami.ccs.life.gui;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;

/**
 *
 */
public class JenaSdbQueryInterface extends JFrame implements ActionListener {

    protected JTextArea queryField;

    protected JTextArea resultArea;

    private final static String newline = "\n";

    private JenaBasedReasonerConnection connection;

    public JenaSdbQueryInterface(JenaBasedReasonerConnection connection) {
        super("Simple::Sam OWL-RDF(S) Query Interface");
        this.connection = connection;
        setSize(400, 800);
        Container mainPane = getContentPane();
        createUserView(mainPane);
    }

    public class Result implements ResultCallback {

        boolean errorState = false;



        public void result(ResultSet rs) {
            ByteArrayOutputStream resultOut = new ByteArrayOutputStream();
            StringBuilder sb = new StringBuilder();
            for (; rs.hasNext();) {
                QuerySolution soln = rs.nextSolution();
                sb.append(soln.toString()).append("\n");
            }
            //ResultSetFormatter.out(resultOut, rs);
            //String outString = new String(resultOut.toByteArray());
            resultArea.setText(sb.toString());
        }

        public boolean isError() {
            return errorState;
        }

        public void setErrorState(boolean errorState) {
            this.errorState =errorState;
        }

        public void error(String error) {
            if (isError()) {
                resultArea.setText(error);
            }
        }

    }


    public void createUserView(Container mainPane) {
        mainPane.setLayout(new GridBagLayout());

        queryField = new JTextArea(5, 20);
        JScrollPane queryScrollPane = new JScrollPane(queryField);
        queryField.setText("PREFIX rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX xsd:   <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX owl:   <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX swrl:  <http://www.w3.org/2003/11/swrl#>\n" +
                "PREFIX swrlb: <http://www.w3.org/2003/11/swrlb#>\n" +
                "PREFIX ns0: <http://cohse.semanticweb.org/ontologies/people#>\n" +
                "PREFIX bao: <http://www.bioassayontology.org/bao#>\n" +
                "\n" +
                "SELECT ?s ?aid\n" +
                "WHERE\n" +
                "{\n" +
                "  ?s rdf:type bao:Assay .\n" +
                "  ?s bao:hasAid ?aid .\n" +
                "}\n" +
                "LIMIT 20");

        resultArea = new JTextArea(5, 20);
        resultArea.setEditable(false);
        JScrollPane resultScrollPane = new JScrollPane(resultArea);

//Lay out the buttons from left to right.
        JButton sparqlButton = new JButton("Sparql");
        sparqlButton.addActionListener(this);
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        buttonPane.add(Box.createHorizontalGlue());
        buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPane.add(sparqlButton);


        //Add Components to this panel.
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        c.weighty = 1;

        c.fill = GridBagConstraints.BOTH;
        add(queryScrollPane, c);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        add(resultScrollPane, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 2;
        add(buttonPane, c);
    }

    public void actionPerformed(ActionEvent evt) {
        String text = queryField.getText();
        //resultArea.append(text + newline);
        queryField.selectAll();
        Result callback = new Result();
        connection.sql(text, callback);
        //Make sure the new text is visible, even if there
        //was a selection in the text area.
        resultArea.setCaretPosition(resultArea.getDocument().getLength());

    }
}
