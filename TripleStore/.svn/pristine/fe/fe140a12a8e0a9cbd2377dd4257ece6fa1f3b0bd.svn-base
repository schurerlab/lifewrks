package edu.miami.ccs.life.gui;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 *
 */

public class CellRenderer extends DefaultTreeCellRenderer {

    private ImageIcon[] levels;


    public CellRenderer() {
        // difficulty levels only three
        levels = new ImageIcon[3];
        levels[0] = createImageIcon("1.gif");
        levels[1] = createImageIcon("2.gif");
        levels[2] = createImageIcon("3.gif");
    }

    public Component getTreeCellRendererComponent(JTree tree,
                                                  Object value,
                                                  boolean selected,
                                                  boolean expanded,
                                                  boolean leaf,
                                                  int row,
                                                  boolean hasFocus) {
        DefaultTreeCellRenderer component =
                (DefaultTreeCellRenderer) super
                        .getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row,
                                hasFocus);
        Object userObject = ((DefaultMutableTreeNode) value).getUserObject();
        if (leaf && (userObject instanceof Index)) {
            Index index = (Index) userObject;
            int level = index.getLevel();
            switch (level) {
                case 1:
                    component.setIcon(levels[0]);
                    break;
                case 2:
                    component.setIcon(levels[1]);
                    break;
                case 3:
                    component.setIcon(levels[2]);
                    break;
            }
            component.setText(index.toString());
        }
        return component;
    }


    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = Thread.currentThread().getContextClassLoader().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

}
