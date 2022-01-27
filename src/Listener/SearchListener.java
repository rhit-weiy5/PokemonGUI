package Listener;

import Database.DatabaseConnection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchListener implements ActionListener {

    private DatabaseConnection db = null;

    public SearchListener(DatabaseConnection db){
        this.db = db;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
