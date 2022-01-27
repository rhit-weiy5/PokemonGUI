package Listener;

import Database.DatabaseConnection;

import javax.xml.crypto.Data;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteListener implements ActionListener {

    DatabaseConnection db;
    int x;

    @Override
    public void actionPerformed(ActionEvent e) {
        PreparedStatement stmt = null;
        try {
            stmt = db.getConnection().prepareStatement("EXEC delete_pokemon @PID = ?");
            stmt.setInt(1, x);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
