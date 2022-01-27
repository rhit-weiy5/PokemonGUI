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

public class DeleteListener implements ActionListener {

    private DatabaseConnection db;
    private JTextField pidTextField;

    public DeleteListener(DatabaseConnection db, JTextField pid){
        this.db = db;
        this.pidTextField = pid;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        delete();
    }

    public boolean delete(){
        CallableStatement stmt = null;
        try {
            stmt = db.getConnection().prepareCall("{call delete_pokemon (@PID = ?)}");
            int x = 0;

            try {
                x = Integer.parseInt(pidTextField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,"PID should be an int");
                //e.printStackTrace();
                return false;
            }
            stmt.setInt(1, x);
            stmt.execute();
            return true;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "PID not exists");
            return false;
        }
    }
}
