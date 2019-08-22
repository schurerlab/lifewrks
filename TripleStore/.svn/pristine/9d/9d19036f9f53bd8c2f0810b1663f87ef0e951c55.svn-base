package edu.miami.ccs.life.gui;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */

public class SparqlRenderer {

    public static final String SELECT = "SELECT";
    public static final String PREFIX = "PREFIX";
    public static final String FILTER = "FILTER";
    public static final String UNION = "UNION";
    public static final String LIMIT = "LIMIT";
    public static final String WHERE = "WHERE";
    public static final String DISTINCT = "DISTINCT";
    public static final String FROM = "FROM";
    public static final String NAMED = "NAMED";
    public static final String GRAPH = "GRAPH";
    public static final String ORDER_BY = "ORDER BY";
    public static final String DESC = "DESC";
    public static final String CONSTRUCT = "CONSTRUCT";
    public static final String DESCRIBE = "DESCRIBE";
    public static final String OPTIONAL = "OPTIONAL";
    public static final String REGEX = "REGEX";
    public static final String ASK = "ASK";

    public static final String GROUP_BY = "GROUP BY";
    public static final String AS = "AS";
    public static final String HAVING = "HAVING";

    private static final String VAR_REGEX = "\\?[a-zA-Z_0-9]+";
    private static final String NS_REGEX = "<[a-zA-Z_0-9:/\\.\\-]+#?[a-zA-Z_0-9:/\\.\\-]*>";

    private Color keywordColor = new Color(0, 0, 129);
    private Color keywordExtColr = new Color(0, 255, 255);
    private Color varColor = new Color(128, 0, 128);
    private Color nsColor = new Color(0, 128, 0);


    public void toColor(JTextPane textComp) {
        all(textComp);
        keyWords(textComp);
        ns(textComp);
        var(textComp);
    }

    public void all(JTextPane textComp) {
        color0(textComp, 0, textComp.getText().length(), Color.black);
    }


    private void ns(JTextPane textComp) {
        Pattern pattern = Pattern.compile(NS_REGEX);
        Matcher m = pattern.matcher(textComp.getText());
        while (m.find()) {
            // this is fun
            color0(textComp, m.start(), (m.end() - m.start()), nsColor);
        }
    }

    private void var(JTextPane textComp) {
        Pattern pattern = Pattern.compile(VAR_REGEX);
        Matcher m = pattern.matcher(textComp.getText());
        while (m.find()) {
            // this is fun
            color0(textComp, m.start(), (m.end() - m.start()), varColor);
        }
    }

    private void keyWords(JTextPane textComp) {
        color0(textComp, PREFIX, keywordColor);
        color0(textComp, SELECT, keywordColor);
        color0(textComp, WHERE, keywordColor);
        color0(textComp, FILTER, keywordColor);
        color0(textComp, UNION, keywordColor);
        color0(textComp, LIMIT, keywordColor);

        color0(textComp, FROM, keywordColor);
        color0(textComp, NAMED, keywordColor);
        color0(textComp, ORDER_BY, keywordColor);
        color0(textComp, DESC, keywordColor);
        color0(textComp, CONSTRUCT, keywordColor);
        color0(textComp, DESCRIBE, keywordColor);
        color0(textComp, DISTINCT, keywordColor);
        color0(textComp, GRAPH, keywordColor);
        color0(textComp, OPTIONAL, keywordColor);
        color0(textComp, REGEX, keywordColor);
        color0(textComp, ASK, keywordColor);

        color0(textComp, GROUP_BY, keywordExtColr);
        color0(textComp, AS, keywordExtColr);
        color0(textComp, HAVING, keywordExtColr);
    }

    private void color0(JTextPane textPane, String pattern, Color color) {
        try {
//            StyledDocument doc = textPane.getStyledDocument();
            StyledDocument doc = textPane.getStyledDocument();
            String text = doc.getText(0, doc.getLength());
            MutableAttributeSet attr = attr(color);
            int pos = 0;
            // Search for pattern
            while ((pos = text.indexOf(pattern, pos)) >= 0) {
                // set the char attribute
//                doc.insertString(pos, pattern, attr);
                doc.setCharacterAttributes(pos, pattern.length(), attr, true);
                pos += pattern.length();
            }
        } catch (BadLocationException e) {
            System.err.println("bad location");
        }
    }

    private MutableAttributeSet attr(Color color) {
        MutableAttributeSet attr = new SimpleAttributeSet();
        //write some text in red
        StyleConstants.setForeground(attr, color);
        StyleConstants.setBold(attr, true);
        StyleConstants.setFontFamily(attr, "Monospaced");
        StyleConstants.setFontSize(attr, 12);
        return attr;
    }

    private void color0(JTextPane textPane, int offset, int length, Color color) {

        StyledDocument doc = textPane.getStyledDocument();
        MutableAttributeSet attr = attr(color);
        // Search for pattern
        doc.setCharacterAttributes(offset, length, attr, true);
    }

}
