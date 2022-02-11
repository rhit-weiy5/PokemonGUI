package Listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.*;

import Database.DatabaseConnection;

public class CreateListener implements ActionListener {

	private DatabaseConnection db;
	private JTextField TrainerIDField;
	private JTextField NameField;
	private JComboBox<String> GenderField;
	private JTextField SpecieIDField;
	private JTextField AbilityIDField;

	public CreateListener(DatabaseConnection db, JTextField TrainerIDBox, JTextField NameBox, JComboBox<String> Gender,
			JTextField AbilityIDBox, JTextField specieComboBox) {
		this.db = db;
		this.TrainerIDField = TrainerIDBox;
		this.NameField = NameBox;
		this.GenderField = Gender;
	
		this.AbilityIDField = AbilityIDBox;
		this.SpecieIDField = specieComboBox;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		create();
	}

	public boolean create(){
		PreparedStatement stmt = null;
		try {
			stmt = db.getConnection().prepareStatement(
					"EXEC get_new_pokemon @TrainerID = ?, @Name = ?, @Gender = ?, @SpeciesID = ?,@AbilityID = ?");
			stmt.setInt(1, Integer.parseInt(TrainerIDField.getText()));
			stmt.setString(2, NameField.getText());
			if(GenderField.getSelectedItem().equals("Male")){
				stmt.setInt(3, 0);
			}else if(GenderField.getSelectedItem().equals("Female")) {
				stmt.setInt(3, 1);
			}else {
				stmt.setNull(3, java.sql.Types.NULL);
			}

			stmt.setInt(4, Integer.parseInt(SpecieIDField.getText()));
			stmt.setInt(5, Integer.parseInt(AbilityIDField.getText()));
			stmt.execute();
			TrainerIDField.setText("");
			NameField.setText("");
			GenderField.setSelectedItem(null);
			AbilityIDField.setText("");
			SpecieIDField.setText("");
			
			JOptionPane.showMessageDialog(null, "Create Successful");
			return true;

		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}
}
