package edu.miami.ccs.life.gui;

import javax.swing.*;
import java.util.Vector;

/**
 *
 */

public class JListControl {

    private JList list = new JList();

    private int start;

    private int end;

    private String prefix;

    private boolean shouldReplace;

    public void updateIndex(int start, int end, String prefix, boolean shouldReplace) {
        this.start = start;
        this.end = end;
        this.prefix = prefix;
        this.shouldReplace = shouldReplace;
    }

    public void setListData(final Object[] listData) {
        list.setListData(listData);
    }

    public void setListData(final Vector<?> listData) {
        list.setListData(listData);
    }

    public JList getList() {
        return list;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean isShouldReplace() {
        return shouldReplace;
    }
}
