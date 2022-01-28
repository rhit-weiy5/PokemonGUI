package Listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Database.DatabaseConnection;

public class UpdateListener implements ActionListener {

	private DatabaseConnection db;
	private JTextField pidTextField;
	private JTextField NameField;

	public UpdateListener(DatabaseConnection db, JTextField pid, JTextField Name) {
		this.db = db;
		this.pidTextField = pid;
		this.NameField = Name;
	}

	@Override
    public void actionPerformed(ActionEvent e) {
    	update();
    }

	 public boolean update(){
	        CallableStatement stmt = null;
	        try {
	            stmt = db.getConnection().prepareCall("{call update_pokemon (@PID = ?, @Name = ?)}");
	            stmt.setInt(1, Integer.parseInt(pidTextField.getText()));
	            stmt.setString(2, NameField.getText());
	            
	            stmt.execute();
	            return true;
	        } catch (SQLException ex) {
	            JOptionPane.showMessageDialog(null, "PID not exists");
	            return false;
	        }
	    }
	
	

}
