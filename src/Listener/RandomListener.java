package Listener;

import Database.DatabaseConnection;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class RandomListener implements ActionListener {

    private DatabaseConnection db;
    private JTextField pidTextField;

    public RandomListener(DatabaseConnection db, JTextField pid){
        this.db = db;
        this.pidTextField = pid;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        random();
    }

    public boolean random(){
        CallableStatement stmt = null;
        try {
            stmt = db.getConnection().prepareCall("{call addSomeRandomPokemon (@Number = ?)}");
            int x = 0;
            try {
                x = Integer.parseInt(pidTextField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,"Should offer an int of number");
                return false;
            }
            stmt.setInt(1, x);
            stmt.execute();
            pidTextField.setText("");
            JOptionPane.showMessageDialog(null, "Random Pokemon have been added.");
            return true;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Please enter an integer");
            return false;
        }
    }
}
