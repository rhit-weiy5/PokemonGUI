package Listener;

import Database.DatabaseConnection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;

public class SearchListener implements ActionListener {

    private DatabaseConnection db = null;

    public SearchListener(DatabaseConnection db){
        this.db = db;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    	System.out.println("I am clicked");
    }
}
