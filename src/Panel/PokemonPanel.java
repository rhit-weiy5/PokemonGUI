package Panel;

import Database.DatabaseConnection;
import Listener.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PokemonPanel extends JPanel {

	private JTextField pidTextField = null;
	private JComboBox<String> specieComboBox = null;
	private JComboBox<String> genderComboBox = null;
	private JTextField levelTextField = null;
	private JComboBox<String> trainerTextField = null;
	private JPanel filterPanel = null;
	private JButton filterButton = null;
	private JTable pokemonTable = null;
	private JScrollPane sPane;
	private DatabaseConnection db = null;
	private Frame fr;

	public PokemonPanel(DatabaseConnection db, Frame fr) {
		this.db = db;
		this.filterPanel = generateFilterUiItems();
		this.sPane = generatePokemonTable();
		this.setLayout(new BorderLayout());
		this.add(filterPanel, "North");
		this.add(sPane, "Center");
		this.fr = fr;
	}

	private JScrollPane generatePokemonTable() {
		Statement stmt;
		try {
			String temp = "Select * From search_pokemon()";
			if (!this.pidTextField.getText().equals("") && this.pidTextField.getText() != null) {
				temp += (" WHERE PID=" + this.pidTextField.getText());
			}

			if (!this.specieComboBox.getSelectedItem().equals("None")
					&& this.specieComboBox.getSelectedItem() != null) {
				if (temp.indexOf("WHERE") == -1) {
					temp += (" WHERE SName='" + this.specieComboBox.getSelectedItem() + "'");
				} else {
					temp += (" AND SName='" + this.specieComboBox.getSelectedItem() + "'");
				}
			}

			if (!this.genderComboBox.getSelectedItem().equals("None")
					&& this.genderComboBox.getSelectedItem() != null) {
				if (temp.indexOf("WHERE") == -1) {
					if (this.genderComboBox.getSelectedItem().equals("NULL")) {
						temp += (" WHERE PGender is null");
					} else if (this.genderComboBox.getSelectedItem().equals("Male")) {
						temp += (" WHERE PGender=0");
					} else if (this.genderComboBox.getSelectedItem().equals("Female")) {
						temp += (" WHERE PGender=1");
					}
				} else {
					if (this.genderComboBox.getSelectedItem().equals("NULL")) {
						temp += (" AND PGender is null");
					} else if (this.genderComboBox.getSelectedItem().equals("Male")) {
						temp += (" AND PGender=0");
					} else if (this.genderComboBox.getSelectedItem().equals("Female")) {
						temp += (" AND PGender=1");
					}
				}
			}

			if (!this.levelTextField.getText().equals("") && this.levelTextField.getText() != null) {
				if (temp.indexOf("WHERE") == -1) {
					temp += (" WHERE Level=" + this.levelTextField.getText());
				} else {
					temp += (" AND Level>=" + this.levelTextField.getText());
				}
			}

			if (!this.trainerTextField.getSelectedItem().equals("None")
					&& this.trainerTextField.getSelectedItem() != null) {
				if (temp.indexOf("WHERE") == -1) {
					temp += (" WHERE TName='" + this.trainerTextField.getSelectedItem() + "'");
				} else {
					temp += (" AND TName='" + this.trainerTextField.getSelectedItem() + "'");
				}
			}

			stmt = this.db.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(temp);
			int count = 0;
			while (rs.next()) {
				count++;
			}

			// ResultSet rs = stmt.executeQuery("SELECT PID, Pokemon1.Name as Pname,
			// Pokemon1.Gender as Pgender, Level, Friendship, Pokedex.Name as SName,
			// Ability1.Name as AName, Trainer.Name as TName From Pokemon1 JOIN Trainer on
			// Trainer.ID = Pokemon1.TrainerID JOIN Ability1 on Pokemon1.AbilityID =
			// Ability1.ID Join Pokedex on Pokemon1.SpeciesID = Pokedex.ID");

			rs = stmt.executeQuery(temp);

			String[][] rec = new String[count][14];
			String[] header = { "PID", "Name", "Gender", "Level", "Friendship", "SpecieName", "AbilityName",
					"TrainerName", "BaseHP", "BaseATK", "BaseDEF", "BaseSPA", "BaseSPD", "BaseSPE" };
			int index = 0;
			while (rs.next()) {
				rec[index][0] = rs.getString("PID");
				rec[index][1] = rs.getString("Pname");
				int iVal = rs.getInt("Pgender");
				if (rs.wasNull()) {
					rec[index][2] = "NULL";
				} else if (iVal == 1){
					rec[index][2] = "FEMALE";
				}
				else{
					rec[index][2] = "MALE";
				}
				rec[index][3] = rs.getString("Level");
				rec[index][4] = rs.getString("Friendship");
				rec[index][5] = rs.getString("SName");
				rec[index][6] = rs.getString("AName");
				rec[index][7] = rs.getString("TName");
				rec[index][8] = rs.getString("BaseHP");
				rec[index][9] = rs.getString("BaseATK");
				rec[index][10] = rs.getString("BaseDEF");
				rec[index][11] = rs.getString("BaseSPA");
				rec[index][12] = rs.getString("BaseSPD");
				rec[index][13] = rs.getString("BaseSPE");
				index++;
			}
			this.pokemonTable = new JTable(rec, header);
			JScrollPane scrollPane = new JScrollPane(this.pokemonTable);
			this.pokemonTable.setFillsViewportHeight(true);
			this.pokemonTable.getColumnModel().getColumn(0).setPreferredWidth(50);
			this.pokemonTable.getColumnModel().getColumn(1).setPreferredWidth(80);
			this.pokemonTable.getColumnModel().getColumn(2).setPreferredWidth(50);
			this.pokemonTable.getColumnModel().getColumn(3).setPreferredWidth(50);
			this.pokemonTable.getColumnModel().getColumn(4).setPreferredWidth(55);
			this.pokemonTable.getColumnModel().getColumn(5).setPreferredWidth(200);
			this.pokemonTable.getColumnModel().getColumn(6).setPreferredWidth(150);
			this.pokemonTable.getColumnModel().getColumn(7).setPreferredWidth(100);

			return scrollPane;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sPane;
	}

	private JPanel generateFilterUiItems() {
		JPanel fPanel = new JPanel();
		this.pidTextField = new JTextField();
		this.specieComboBox = new JComboBox<>();
		this.genderComboBox = new JComboBox<>();
		this.levelTextField = new JTextField();
		this.trainerTextField = new JComboBox<>();
		getSpecie();
		getTrainer();
		this.genderComboBox.addItem("None");
		this.genderComboBox.addItem("Male");
		this.genderComboBox.addItem("Female");
		this.genderComboBox.addItem("NULL");

		// Set dimension
		this.pidTextField.setPreferredSize(new Dimension(75, 25));
		this.specieComboBox.setPreferredSize(new Dimension(100, 25));
		this.genderComboBox.setPreferredSize(new Dimension(60, 25));
		this.levelTextField.setPreferredSize(new Dimension(30, 25));
		this.trainerTextField.setPreferredSize(new Dimension(80, 25));

		// Set layout
		FlowLayout layout = new FlowLayout();
		layout.setHgap(15);
		fPanel.setLayout(layout);

		// add stuff
		fPanel.add(new JLabel("PID"));
		fPanel.add(this.pidTextField);
		fPanel.add(new JLabel("Specie"));
		fPanel.add(this.specieComboBox);
		fPanel.add(new JLabel("Gender"));
		fPanel.add(this.genderComboBox);
		fPanel.add(new JLabel("Level"));
		fPanel.add(this.levelTextField);
		fPanel.add(new JLabel("Trainer"));
		fPanel.add(this.trainerTextField);
		this.filterButton = new JButton("Search");
		filterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				this.switchToPokedex();
			}

			private void switchToPokedex() {
				// TODO Auto-generated method stub
				JScrollPane tmp = sPane;
				if (true) {
					new PokedexWithFilter(db, pidTextField.getText(), (String)specieComboBox.getSelectedItem(),
							(String)genderComboBox.getSelectedItem(), levelTextField.getText(),
							(String)trainerTextField.getSelectedItem());
					tmp.setVisible(false);
					sPane.setVisible(true);
				}
			}
		});

//		ActionListener filterListener = new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				sPane = generatePokemonTable();
//				System.out.println("I was clicked");
//				fr.invalidate();
//				fr.validate();
//				fr.repaint();
//				System.out.println(fr);
//			}
//		};
//		filterButton.addActionListener(filterListener);
		fPanel.add(this.filterButton);
		return fPanel;
	}

	public void getSpecie() {
		this.specieComboBox.addItem("None");
		try {
			Statement stmt = this.db.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(
					" SELECT DISTINCT Pokedex.Name From Pokemon1 JOIN Pokedex on Pokedex.ID = Pokemon1.SpeciesID");
			while (rs.next()) {
				this.specieComboBox.addItem(rs.getString("Name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void getTrainer() {
		this.trainerTextField.addItem("None");
		try {
			Statement stmt = this.db.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(
					" SELECT DISTINCT Trainer.Name as trainername From Pokemon1 JOIN Trainer on Trainer.ID = Pokemon1.TrainerID");
			while (rs.next()) {
				this.trainerTextField.addItem(rs.getString("trainername"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
