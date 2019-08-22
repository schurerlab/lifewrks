package edu.miami.ccs.life.gui;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

/**
 *
 */

public class SparqlQueryInterface extends JFrame
        implements ActionListener, TreeSelectionListener, KeyListener {

    protected JTextPane queryField;

    protected JTextPane resultArea;

    private JTree tree;

    DefaultMutableTreeNode rootNode =
            new DefaultMutableTreeNode("Sparql::Larq");

    private DefaultTreeModel treeModel;

    private JenaBasedReasonerConnection connection;

    private SparqlRenderer sparqlRenderer = new SparqlRenderer();

    private AutoCompleter ac;

    private DerbyIndexManager dbManager;

    public SparqlQueryInterface(JenaBasedReasonerConnection connection,
                                DerbyIndexManager dbManager) {
        super("BAO SPARQL Interface (OWL-DL)");
        this.connection = connection;
        this.dbManager = dbManager;
        setSize(800, 600);
        Container contentPane = getContentPane();
        createView(contentPane);
    }

    public AutoCompleter getAc() {
        return ac;
    }

    private void createView(Container contentPane) {
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        // constrains
        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        mainSplitPane.setLeftComponent(westPanel);
        mainSplitPane.setRightComponent(eastPanel);
        contentPane.add(mainSplitPane);
        createBorder("Examples", westPanel);
        createBorder("Sparql", eastPanel);

        treeModel = new DefaultTreeModel(rootNode);
        treeModel.addTreeModelListener(new SparqlTreeModelListener());
        tree = new JTree(treeModel);
        tree.setEditable(true);
        tree.setShowsRootHandles(true);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addTreeSelectionListener(this);
        tree.setCellRenderer(new CellRenderer());
        setWithDefaultQueries();
        JScrollPane treeView = new JScrollPane(tree);
        westPanel.add(treeView);
        JPanel buttonPanel = new JPanel();
        JButton runButton = new JButton("Exec");
        runButton.setActionCommand("#exec");
        JButton saveButton = new JButton("Add");
        saveButton.setActionCommand("#add");
        JButton removeButton = new JButton("Remove");
        removeButton.setActionCommand("#remove");
        runButton.addActionListener(this);
        saveButton.addActionListener(this);
        removeButton.addActionListener(this);
        buttonPanel.add(runButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(removeButton);
        westPanel.add(buttonPanel);

        queryField = new JTextPane();
        JScrollPane queryScrollPane = new JScrollPane(queryField);
        ac = new OwlAc(queryField);
        queryField.addKeyListener(this);

        resultArea = new JTextPane();
        JScrollPane htmlView = new JScrollPane(resultArea);
        //Add the scroll panes to a split pane.
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(queryScrollPane);
        splitPane.setBottomComponent(htmlView);
        eastPanel.add(splitPane);

    }

    private void createBorder(String title, JPanel panel) {
        panel.setBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),
                        title));

    }

    private void setWithDefaultQueries() {
        List<Index> indexes = dbManager.selectAll();
        DefaultMutableTreeNode category;
        for (Index index : indexes) {
            category = new DefaultMutableTreeNode(index);
            rootNode.add(category);
        }
    }

    /**
     * Remove the currently selected node.
     */
    public void removeCurrentNode() {
        TreePath currentSelection = tree.getSelectionPath();
        if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
                    (currentSelection.getLastPathComponent());
            MutableTreeNode parent = (MutableTreeNode) (currentNode.getParent());
            if (parent != null) {
                treeModel.removeNodeFromParent(currentNode);
            }
            // commit to data base
            Object o = currentNode.getUserObject();
            if (o instanceof Index) {
                Index io = (Index) o;
                dbManager.delete(io);
            }
        }
    }


    /**
     * Add child to the currently selected node.
     */
    public void addIndex(Index child) {
        DefaultMutableTreeNode parentNode;
        TreePath parentPath = tree.getSelectionPath();

        if (parentPath != null) {
            //this is the bug
            /*parentNode = (DefaultMutableTreeNode)
                    (parentPath.getLastPathComponent());*/

            addObject(rootNode, child, true);
            // commit to database
            dbManager.insert(child);
        } else {
            System.err.println("child node can not be added");
        }
    }


    private void addObject(DefaultMutableTreeNode parent,
                           Object child,
                           boolean shouldBeVisible) {
        DefaultMutableTreeNode childNode =
                new DefaultMutableTreeNode(child);
        //It is key to invoke this on the TreeModel, and NOT DefaultMutableTreeNode
        treeModel.insertNodeInto(childNode, parent,
                parent.getChildCount());

        //Make sure the user can see the lovely new node.
        if (shouldBeVisible) {
            tree.scrollPathToVisible(new TreePath(childNode.getPath()));
        }
    }


    public void keyTyped(KeyEvent e) {
        // not used
    }

    public void keyPressed(KeyEvent e) {
        //not used
    }

    public void keyReleased(KeyEvent e) {
        if (!ac.isPopupVisible()) {
            sparqlRenderer.toColor(queryField);
        }
    }


    public class Result implements ResultCallback {

        boolean errorState = false;


        public void result(ResultSet rs) {
            StringBuilder sb = new StringBuilder();
            int cnt = 1;
            for (; rs.hasNext();) {
                QuerySolution soln = rs.nextSolution();
                sb.append("(").append(cnt).append(") ").append(soln.toString()).append("\n");
                cnt++;
            }
            resultArea.setText(sb.toString());
            //Make sure the new text is visible, even if there
            //was a selection in the text area.
            resultArea.setCaretPosition(resultArea.getDocument().getLength());
            sparqlRenderer.toColor(resultArea);
        }

        public boolean isError() {
            return errorState;
        }

        public void setErrorState(boolean errorState) {
            this.errorState = errorState;
        }

        public void error(String error) {
            if (isError()) {
                resultArea.setText(error);
            }
        }

    }

    class CustomPanel extends JPanel {

        private JTextField label = new JTextField("label ?", 20);

        private String[] difficulty = {"1", "2", "3"};

        private JComboBox difficultyBox = new JComboBox(difficulty);


        public CustomPanel() {
            JPanel p = new JPanel();
            p.setLayout(new GridLayout(2, 2));
            p.add(new JLabel("Label", JLabel.RIGHT));
            p.add(label);
            p.add(new JLabel("Difficulty", JLabel.RIGHT));
            p.add(difficultyBox);
            add(p, BorderLayout.NORTH);
        }

        public JTextField getLabel() {
            return label;
        }

        public JComboBox getDifficultyBox() {
            return difficultyBox;
        }
    }

    public void actionPerformed(ActionEvent e) {
        String actionCmd = e.getActionCommand();
        if (actionCmd.equalsIgnoreCase("#exec")) {
            String text = queryField.getText();
            //resultArea.append(text + newline);
            queryField.selectAll();
            Result callback = new Result();

            TreePath currentSelection = tree.getSelectionPath();
            boolean isLark = false;
            if (currentSelection != null) {
                DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
                        (currentSelection.getLastPathComponent());
                Object o = currentNode.getUserObject();
                if (o instanceof Index) {
                    Index io = (Index) o;
                    if (io.getLabel().startsWith("LARQ")) {
                        connection.larqSql(text, callback);
                        isLark = true;
                    }
                }
            }
            if (!isLark) {
                connection.sql(text, callback);
            }
            //Make sure the new text is visible, even if there
            //was a selection in the text area.
            resultArea.setText("Loading ... ");
            resultArea.setCaretPosition(resultArea.getDocument().getLength());
            sparqlRenderer.toColor(resultArea);
        } else if (actionCmd.equals("#add")) {
            CustomPanel panel = new CustomPanel();
            int selection = JOptionPane
                    .showConfirmDialog(this, panel, "Save SPARQL query",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (selection == JOptionPane.OK_OPTION) {
                System.out.println("label=" + panel.getLabel().getText());
                System.out.println("diff=" + panel.getDifficultyBox().getSelectedItem());
                addIndex(new Index(panel.getLabel().getText(), queryField.getText(),
                        Integer.parseInt((String) panel.getDifficultyBox().getSelectedItem())));
            } else {
                System.err.println("not saved");
            }


        } else if (actionCmd.equals("#remove")) {
            Object[] options = {"Yes", "No"};
            int n = JOptionPane.showOptionDialog(this, "Do you want this query to be deleted ?",
                    "SPARQL status", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, null, null);
            if (n == 0) {
                removeCurrentNode();
            } else {
                System.err.println("not removed");
            }
        }
    }


    public void valueChanged(TreeSelectionEvent e) {
//Returns the last path element of the selection.
//This method is useful only when the selection model allows a single selection.
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                tree.getLastSelectedPathComponent();

        if (node == null)
            //Nothing is selected.
            return;

        Object nodeInfo = node.getUserObject();
        if (node.isLeaf()) {
            if (nodeInfo instanceof Index) {
                Index index = (Index) nodeInfo;
                queryField.setText(index.getQuery());
//                sparqlRenderer.toHighight(queryField);
                sparqlRenderer.toColor(queryField);
                queryField.repaint();
            }
        }
    }
}
