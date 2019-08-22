package edu.miami.ccs.life.gui;

import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.RDFS;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */

public class OwlAc extends AutoCompleter {

    private Map<String, OntInfo> prefixedInfo;

    private final String[] empty = new String[]{};

    private static final String REGEX = "(bao\\:|rdf\\:|rdfs\\:|owl\\:)[a-zA-Z_0-9]*";

    public OwlAc(JTextPane comp) {
        super(comp);
    }


    // update classes model depending on the data in textfield

    protected boolean updateListData() {

        Pattern pattern = Pattern.compile(REGEX);
        String text = textComp.getText();
        Matcher m = pattern.matcher(text);
        int offset = textComp.getCaretPosition();
        listControl.getList().setListData(empty);
        while (m.find()) {
            System.out.println("group :: " + m.group());
            if (m.end() == offset) {
                // correct location
                String extract = text.substring(m.start() + 4, m.end());
                System.out.println("extract " + extract);
                String group = m.group();
                String prefix = group.substring(0, group.indexOf(":"));
                if (prefixedInfo.containsKey(prefix)) {
                    if (prefix.equals("bao")) {
                        listControl.updateIndex(m.start(), m.end(), prefix, true);
                    } else {
                        listControl.updateIndex(m.start(), m.end(), prefix, false);
                    }
                    List<String> possibleStrings = new ArrayList<String>();
                    for (String show : prefixedInfo.get(prefix).displayList()) {
                        if (show.startsWith(extract.trim())) {
                            possibleStrings.add(show);
                        }
                    }
                    Collections.sort(possibleStrings);
                    listControl.getList().setListData(possibleStrings.toArray());
                }
                break;
            }
        }
        return true;
    }

    // user has selected some item in the classes. update textfield accordingly...

    protected void acceptedListItem(String selected, JListControl listControl) {
        if (selected == null) {
            return;
        }
        // first set the document listener off
        if (isDocListenerEnabled) {
            isDocListenerEnabled = false;
            textComp.getDocument().removeDocumentListener(documentListener);
        }
        System.out.println("selected " + selected);
        selected = selected.substring(selected.indexOf("::") + 2, selected.length());
        System.out.println("id= " + selected);
        try {
            int offset = Math.min(listControl.getStart(), textComp.getCaretPosition());
            System.out.println("offset=" + offset);
            String insert = listControl.getPrefix() + ":" + selected;
            textComp.getDocument().remove(listControl.getStart(),
                    (listControl.getEnd() - listControl.getStart()));
            textComp.getStyledDocument().insertString(offset, insert, null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        popup.setVisible(false);
    }

    public void setPrefixedInfo(Map<String, OntInfo> prefixedInfo) {
        this.prefixedInfo = prefixedInfo;
    }
}
