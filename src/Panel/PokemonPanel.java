package Panel;

import Database.DatabaseConnection;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
			String select = "Select * From search_pokemon()";
			if (!this.pidTextField.getText().equals("") && this.pidTextField.getText() != null) {
				select += (" WHERE PID=" + this.pidTextField.getText());
			}

			if (!this.specieComboBox.getSelectedItem().equals("None")
					&& this.specieComboBox.getSelectedItem() != null) {
				if (select.indexOf("WHERE") == -1) {
					select += (" WHERE SName='" + this.specieComboBox.getSelectedItem() + "'");
				} else {
					select += (" AND SName='" + this.specieComboBox.getSelectedItem() + "'");
				}
			}

			if (!this.genderComboBox.getSelectedItem().equals("None")
					&& this.genderComboBox.getSelectedItem() != null) {
				if (select.indexOf("WHERE") == -1) {
					if (this.genderComboBox.getSelectedItem().equals("NULL")) {
						select += (" WHERE PGender is null");
					} else if (this.genderComboBox.getSelectedItem().equals("Male")) {
						select += (" WHERE PGender=0");
					} else if (this.genderComboBox.getSelectedItem().equals("Female")) {
						select += (" WHERE PGender=1");
					}
				} else {
					if (this.genderComboBox.getSelectedItem().equals("NULL")) {
						select += (" AND PGender is null");
					} else if (this.genderComboBox.getSelectedItem().equals("Male")) {
						select += (" AND PGender=0");
					} else if (this.genderComboBox.getSelectedItem().equals("Female")) {
						select += (" AND PGender=1");
					}
				}
			}

			if (!this.levelTextField.getText().equals("") && this.levelTextField.getText() != null) {
				if (select.indexOf("WHERE") == -1) {
					select += (" WHERE Level=" + this.levelTextField.getText());
				} else {
					select += (" AND Level>=" + this.levelTextField.getText());
				}
			}

			if (!this.trainerTextField.getSelectedItem().equals("None")
					&& this.trainerTextField.getSelectedItem() != null) {
				if (select.indexOf("WHERE") == -1) {
					select += (" WHERE TName='" + this.trainerTextField.getSelectedItem() + "'");
				} else {
					select += (" AND TName='" + this.trainerTextField.getSelectedItem() + "'");
				}
			}

			stmt = this.db.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(select);
			int count = 0;
			while (rs.next()) {
				count++;
			}

			rs = stmt.executeQuery(select);

			Object[][] rec = new Object[count][15];
			Object[] header = { "PID", "Name", "Gender", "Level", "Nature", "Friendship", "SpecieName", "AbilityName",
					"TrainerName", "HP", "ATK", "DEF", "SPA", "SPD", "SPE" };
			int index = 0;
			while (rs.next()) {
				rec[index][0] = rs.getString("PID");
				rec[index][1] = rs.getString("Pname");
				int iVal = rs.getInt("Pgender");
				if (rs.wasNull()) {
					rec[index][2] = "NULL";
				} else if (iVal == 1) {
					rec[index][2] = "FEMALE";
				} else {
					rec[index][2] = "MALE";
				}
				rec[index][3] = rs.getInt("Level");
				rec[index][4] = rs.getString("Nature");
				rec[index][5] = rs.getInt("Friendship");
				rec[index][6] = rs.getString("SName");
				rec[index][7] = rs.getString("AName");
				rec[index][8] = rs.getString("TName");
				rec[index][9] = rs.getInt("HP");
				rec[index][10] = rs.getInt("ATK");
				rec[index][11] = rs.getInt("DEF");
				rec[index][12] = rs.getInt("SPA");
				rec[index][13] = rs.getInt("SPD");
				rec[index][14] = rs.getInt("SPE");
				index++;
			}
			this.pokemonTable = new JTable(rec, header) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return column == 1 || column == 3 || column == 5 ? true : false;
				}
			};
			JScrollPane scrollPane = new JScrollPane(this.pokemonTable);

			this.pokemonTable.setFillsViewportHeight(true);
			this.pokemonTable.getColumnModel().getColumn(0).setPreferredWidth(50);
			this.pokemonTable.getColumnModel().getColumn(1).setPreferredWidth(80);
			this.pokemonTable.getColumnModel().getColumn(2).setPreferredWidth(80);
			this.pokemonTable.getColumnModel().getColumn(3).setPreferredWidth(50);
			this.pokemonTable.getColumnModel().getColumn(4).setPreferredWidth(55);
			this.pokemonTable.getColumnModel().getColumn(5).setPreferredWidth(55);
			this.pokemonTable.getColumnModel().getColumn(6).setPreferredWidth(250);
			this.pokemonTable.getColumnModel().getColumn(7).setPreferredWidth(100);

//			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(pokemonTable.getModel());
//			pokemonTable.setRowSorter(sorter);

			TableRowSorter<TableModel> sort = new TableRowSorter<>(pokemonTable.getModel());

			TableColumnModel columnModel = pokemonTable.getColumnModel();
			pokemonTable.setRowSorter(sort);
			TableModel tm = this.pokemonTable.getModel();
			this.pokemonTable.getModel().addTableModelListener(new TableModelListener() {
				@Override
				public void tableChanged(TableModelEvent e) {
					int pid = e.getFirstRow();
					int c = e.getColumn();
					System.out.println(c);
					System.out.println(tm.getValueAt(pid, c));


				}

			});

//			this.levelTextField.getDocument().addDocumentListener(new DocumentListener() {
//				public void insertUpdate(DocumentEvent e) {
//					String str = levelTextField.getText();
//					if (str.trim().length() == 0) {
//						sort.setRowFilter(null);
//					} else {
//						// (?i) means case insensitive search
//						sort.setRowFilter(RowFilter.regexFilter(".*" + str +".*", 3));
//					}
//				}
//
//				public void removeUpdate(DocumentEvent e) {
//					String str = levelTextField.getText();
//					if (str.trim().length() == 0) {
//						sort.setRowFilter(null);
//					} else {
//						sort.setRowFilter(RowFilter.regexFilter(".*" + str + ".*", 3));
//					}
//				}
//
//				public void changedUpdate(DocumentEvent e) {
//				}
//			});
//
//
//			this.pidTextField.getDocument().addDocumentListener(new DocumentListener() {
//				public void insertUpdate(DocumentEvent e) {
//					String str = pidTextField.getText();
//					if (str.trim().length() == 0) {
//						sort.setRowFilter(null);
//					} else {
//						// (?i) means case insensitive search
//						sort.setRowFilter(RowFilter.regexFilter(".*" + str + ".*", 0));
//					}
//				}
//
//				public void removeUpdate(DocumentEvent e) {
//					String str = pidTextField.getText();
//					if (str.trim().length() == 0) {
//						sort.setRowFilter(null);
//					} else {
//						sort.setRowFilter(RowFilter.regexFilter(".*" + str + ".*", 0));
//					}
//				}
//
//				public void changedUpdate(DocumentEvent e) {
//				}
//			});

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
					new PokedexWithFilter(db, pidTextField.getText(), (String) specieComboBox.getSelectedItem(),
							(String) genderComboBox.getSelectedItem(), levelTextField.getText(),
							(String) trainerTextField.getSelectedItem());
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
