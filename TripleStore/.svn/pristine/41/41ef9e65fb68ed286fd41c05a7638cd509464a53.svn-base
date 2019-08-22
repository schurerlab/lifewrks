package edu.miami.ccs.life.gui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This base code  is borrowed from
 * http://www.jroller.com/santhosh/date/20050620#file_path_autocompletion
 *
 * @author: Saminda Abeyruwan has modified the code a lot to work with JTextPane
 */

public abstract class AutoCompleter {
//    JList list = new JList();
    protected JListControl listControl = new JListControl();

    protected JPopupMenu popup = new JPopupMenu();

    protected JTextPane textComp;

    private static final String AUTOCOMPLETER = "AUTOCOMPLETER"; //NOI18N

    // save the last location
    private static int x = 0;

    private static int y = 0;

    protected boolean isDocListenerEnabled;

    public AutoCompleter(JTextPane comp) {
        textComp = comp;
        textComp.putClientProperty(AUTOCOMPLETER, this);
        JScrollPane scroll = new JScrollPane(listControl.getList());
        scroll.setBorder(null);

        listControl.getList().setFocusable(false);
        scroll.getVerticalScrollBar().setFocusable(false);
        scroll.getHorizontalScrollBar().setFocusable(false);

        popup.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        popup.add(scroll);

        textComp.registerKeyboardAction(showAction,
                KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, KeyEvent.CTRL_MASK),
                JComponent.WHEN_FOCUSED);

        textComp.registerKeyboardAction(upAction, KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0),
                JComponent.WHEN_FOCUSED);
        textComp.registerKeyboardAction(hidePopupAction,
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_FOCUSED);

        popup.addPopupMenuListener(new PopupMenuListener() {
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            }

            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                textComp.unregisterKeyboardAction(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
            }

            public void popupMenuCanceled(PopupMenuEvent e) {
            }
        });
        listControl.getList().setRequestFocusEnabled(false);

        listControl.getList().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent arg0) {
                popup.setVisible(false);
                acceptedListItem((String) listControl.getList().getSelectedValue(), listControl);
            }
        });
    }

    protected Action acceptAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            JComponent tf = (JComponent) e.getSource();
            AutoCompleter completer = (AutoCompleter) tf.getClientProperty(AUTOCOMPLETER);
            completer.popup.setVisible(false);
            completer.acceptedListItem((String) completer.listControl.getList().getSelectedValue(),
                    listControl);
        }
    };

    protected final DocumentListener documentListener = new DocumentListener() {
        public void insertUpdate(DocumentEvent e) {
            showPopup();
            System.out.println("document listener insert update event");
        }

        public void removeUpdate(DocumentEvent e) {
            showPopup();
            System.out.println("document listener remove update event");
        }

        public void changedUpdate(DocumentEvent e) {
            showPopup();
            System.out.println("document listener change update event");
        }
    };

    public void showPopup() {
        popup.setVisible(false);
        if (textComp.isEnabled() && updateListData() &&
                listControl.getList().getModel().getSize() != 0) {
            if (!isDocListenerEnabled) {
                textComp.getDocument().addDocumentListener(documentListener);
                isDocListenerEnabled = true;
            }
            textComp.registerKeyboardAction(acceptAction,
                    KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_FOCUSED);
            int size = listControl.getList().getModel().getSize();
            listControl.getList().setVisibleRowCount(size < 10 ? size : 10);

            Point p = textComp.getCaret().getMagicCaretPosition();
            if (p != null) {
                x = p.x;
                y = p.y;
            }
            popup.show(textComp, x, y + 15);
        } else {
            popup.setVisible(false);
        }
        textComp.requestFocus();
    }

    static Action showAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            JComponent tf = (JComponent) e.getSource();
            AutoCompleter completer = (AutoCompleter) tf.getClientProperty(AUTOCOMPLETER);
            if (tf.isEnabled()) {
                if (completer.popup.isVisible())
                    completer.selectNextPossibleValue();
                else
                    completer.showPopup();
            }
        }
    };

    static Action upAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            JComponent tf = (JComponent) e.getSource();
            AutoCompleter completer = (AutoCompleter) tf.getClientProperty(AUTOCOMPLETER);
            if (tf.isEnabled()) {
                if (completer.popup.isVisible())
                    completer.selectPreviousPossibleValue();
            }
        }
    };

    static Action hidePopupAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            JComponent tf = (JComponent) e.getSource();
            AutoCompleter completer = (AutoCompleter) tf.getClientProperty(AUTOCOMPLETER);
            if (tf.isEnabled())
                completer.popup.setVisible(false);
        }
    };

    /**
     * Selects the next item in the list.  It won't change the selection if the
     * currently selected item is already the last item.
     */
    protected void selectNextPossibleValue() {
        int si = listControl.getList().getSelectedIndex();

        if (si < listControl.getList().getModel().getSize() - 1) {
            listControl.getList().setSelectedIndex(si + 1);
            listControl.getList().ensureIndexIsVisible(si + 1);
        }
    }

    /**
     * Selects the previous item in the list.  It won't change the selection if the
     * currently selected item is already the first item.
     */
    protected void selectPreviousPossibleValue() {
        int si = listControl.getList().getSelectedIndex();

        if (si > 0) {
            listControl.getList().setSelectedIndex(si - 1);
            listControl.getList().ensureIndexIsVisible(si - 1);
        }
    }

    public boolean isPopupVisible() {
        return popup.isVisible();
    }

    // update list model depending on the data in textfield 

    protected abstract boolean updateListData();

    // user has selected some item in the list. update textfield accordingly...

    protected abstract void acceptedListItem(String selected, JListControl listControl);

}
