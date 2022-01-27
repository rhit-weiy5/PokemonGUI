package Listener;

import Database.DatabaseConnection;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteListener implements ActionListener {

    private DatabaseConnection db;
    private JTextField pidTextField;

    public DeleteListener(DatabaseConnection db, JTextField pid){
        this.db = db;
        this.pidTextField = pid;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PreparedStatement stmt = null;
        try {
            stmt = db.getConnection().prepareStatement("EXEC delete_pokemon @PID = ?");
            int x = Integer.parseInt(pidTextField.getText());
            stmt.setInt(1, x);
            stmt.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
