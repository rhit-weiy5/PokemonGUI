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
	private JTextField LevelField;

	public UpdateListener(DatabaseConnection db, JTextField pid, JTextField Name, JTextField Level) {
		this.db = db;
		this.pidTextField = pid;
		this.NameField = Name;
		this.LevelField = Level;
	}

	@Override
    public void actionPerformed(ActionEvent e) {
    	update();
    }

	 public boolean update(){
	        CallableStatement stmt = null;
	        try {
	            stmt = db.getConnection().prepareCall("{call update_pokemon (@PID = ?, @Name = ?, @LEVEL = ?)}");
	            stmt.setInt(1, Integer.parseInt(pidTextField.getText()));
	            stmt.setString(2, NameField.getText());
				stmt.setInt(3, Integer.parseInt(LevelField.getText()));

	            pidTextField.setText("");
	            JOptionPane.showMessageDialog(null, "The specific PID has been successfully update.");
	            stmt.execute();
	            return true;
	        } catch (SQLException ex) {
	            JOptionPane.showMessageDialog(null, "PID not exists");
	            return false;
	        }
	    }
	
	

}
