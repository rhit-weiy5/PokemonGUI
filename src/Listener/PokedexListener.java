package Listener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class PokedexListener implements DocumentListener {

        private JTextField searchTextField = null;
        private TableRowSorter<TableModel> sort = null;

        public PokedexListener(JTextField searchTextField, TableRowSorter<TableModel> sort){
            this.searchTextField = searchTextField;
            this.sort = sort;
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
        String str = searchTextField.getText();
        if (str.trim().length() == 0) {
            sort.setRowFilter(null);
        } else {
            sort.setRowFilter(RowFilter.regexFilter("(?i)" + str, 2));
        }
    }
        @Override
        public void removeUpdate(DocumentEvent e) {
        String str = searchTextField.getText();
        if (str.trim().length() == 0) {
            sort.setRowFilter(null);
        } else {
            sort.setRowFilter(RowFilter.regexFilter("(?i)" + str, 2));
        }
    }
        @Override
        public void changedUpdate(DocumentEvent e) {}
}
