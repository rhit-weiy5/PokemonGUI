package Panel;

import Database.DatabaseConnection;
import Listener.DeletePokemon;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;

public class PokemonPanel extends JPanel {

	private JTextField pidTextField = null;
	private JComboBox<String> specieComboBox = null;
	private JComboBox<String> genderComboBox = null;
	private JTextField levelTextField = null;
	private JComboBox<String> trainerTextField = null;
	private JPanel filterPanel = null;
	private JButton filterButton = null;
	private JButton deleteButton = null;
	private JTable pokemonTable = null;
	private JScrollPane sPane;
	private DatabaseConnection db = null;
	private Frame fr;
	private DefaultTableModel model;
	private ArrayList<Integer> pid;

	public PokemonPanel(DatabaseConnection db, Frame fr) {
		this.db = db;
		this.filterPanel = generateFilterUiItems();
		this.sPane = generatePokemonTable();

		this.deleteButton = new JButton("Delete");
		this.deleteButton.addActionListener(new DeletePokemon(this.pokemonTable, this.model, this.pid, this.db));
		this.filterPanel.add(deleteButton);

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
			Object[] header = { "Name", "Gender", "Level", "Nature", "Friendship", "SpecieName", "AbilityName", "ItemName",
					"TrainerName", "HP", "ATK", "DEF", "SPA", "SPD", "SPE" };
			this.pid = new ArrayList<Integer>();
			int index = 0;
			while (rs.next()) {
				pid.add(rs.getInt("PID"));
				//System.out.println(pid[index]);
				rec[index][0] = rs.getString("Pname");
				int iVal = rs.getInt("Pgender");
				if (rs.wasNull()) {
					rec[index][1] = "NULL";
				} else if (iVal == 1) {
					rec[index][1] = "FEMALE";
				} else {
					rec[index][1] = "MALE";
				}
				rec[index][2] = parse(rs.getString("Level"));
				rec[index][3] = rs.getString("Nature");
				rec[index][4] = parse(rs.getString("Friendship"));
				rec[index][5] = rs.getString("SName");
				rec[index][6] = rs.getString("AName");
				rec[index][7] = rs.getString("IName");
				rec[index][8] = rs.getString("TName");
				rec[index][9] = parse(rs.getString("HP"));
				rec[index][10] = parse(rs.getString("ATK"));
				rec[index][11] = parse(rs.getString("DEF"));
				rec[index][12] = parse(rs.getString("SPA"));
				rec[index][13] = parse(rs.getString("SPD"));
				rec[index][14] = parse(rs.getString("SPE"));
				index++;
			}
			this.model = new DefaultTableModel(rec, header);
			this.pokemonTable = new JTable(model){
				@Override
				public boolean isCellEditable(int row, int column) {
					return column == 0 || column == 2 || column == 4 ? true : false;
				}
			};
//			this.pokemonTable = new JTable(rec, header) {
//				@Override
//				public boolean isCellEditable(int row, int column) {
//					return column == 0 || column == 2 || column == 4 ? true : false;
//				}
//			};
			JScrollPane scrollPane = new JScrollPane(this.pokemonTable);

			this.pokemonTable.setFillsViewportHeight(true);
			this.pokemonTable.getColumnModel().getColumn(0).setPreferredWidth(80);
			this.pokemonTable.getColumnModel().getColumn(1).setPreferredWidth(80);
			this.pokemonTable.getColumnModel().getColumn(2).setPreferredWidth(50);
			this.pokemonTable.getColumnModel().getColumn(3).setPreferredWidth(55);
			this.pokemonTable.getColumnModel().getColumn(4).setPreferredWidth(55);
			this.pokemonTable.getColumnModel().getColumn(5).setPreferredWidth(250);
			this.pokemonTable.getColumnModel().getColumn(6).setPreferredWidth(100);
			this.pokemonTable.getColumnModel().getColumn(7).setPreferredWidth(100);
			this.pokemonTable.getColumnModel().getColumn(8).setPreferredWidth(100);
			pokemonTable.setAutoCreateRowSorter(true);
			TableRowSorter<TableModel> sort = new TableRowSorter<>(pokemonTable.getModel());

			TableColumnModel columnModel = pokemonTable.getColumnModel();
			pokemonTable.setRowSorter(sort);
			TableModel tm = this.pokemonTable.getModel();
			this.pokemonTable.getModel().addTableModelListener(new TableModelListener() {
				@Override
				public void tableChanged(TableModelEvent e) {
					int r = e.getFirstRow();
					int pid1 = pid.get(r);
//					System.out.println("Current:" + pid1);
					int c = e.getColumn();
//					System.out.println(c);
//					System.out.println(tm.getValueAt(pid, c));

					CallableStatement stmt = null;
					String s = "{call update_pokemon (@PID = ?, @Name = ?, @LEVEL = ?, @Friendship = ?)}";
					try {
						stmt = db.getConnection().prepareCall(s);
						stmt.setInt(1, pid1);

						if (c == 0) {
							stmt.setString(2, (String) tm.getValueAt(r, c));
							stmt.setString(3, null);
							stmt.setString(4, null);
						} else if (c == 2) {
							stmt.setString(2, null);
							stmt.setString(3, (String) tm.getValueAt(r, c));
							stmt.setString(4, null);
						} else if (c == 4) {
							stmt.setString(2, null);
							stmt.setString(3, null);
							stmt.setString(4, (String) tm.getValueAt(r, c));
						}
//						System.out.println("pid: " + pid);
//						System.out.println(s);
						stmt.execute();
						JOptionPane.showMessageDialog(null, "The specific PID has been successfully update.");
					} catch (SQLException ex) {
						JOptionPane.showMessageDialog(null, "PID not exists");
					}

				}

			});

			this.levelTextField.getDocument().addDocumentListener(new DocumentListener() {
				public void insertUpdate(DocumentEvent e) {
					String str = levelTextField.getText();
					if (str.trim().length() == 0) {
						sort.setRowFilter(null);
					} else {
						// (?i) means case insensitive search
						sort.setRowFilter(RowFilter.regexFilter(".*" + str +".*", 3));
					}
				}

				public void removeUpdate(DocumentEvent e) {
					String str = levelTextField.getText();
					if (str.trim().length() == 0) {
						sort.setRowFilter(null);
					} else {
						sort.setRowFilter(RowFilter.regexFilter(".*" + str + ".*", 3));
					}
				}

				public void changedUpdate(DocumentEvent e) {
				}
			});


			this.pidTextField.getDocument().addDocumentListener(new DocumentListener() {
				public void insertUpdate(DocumentEvent e) {
					String str = pidTextField.getText();
					if (str.trim().length() == 0) {
						sort.setRowFilter(null);
					} else {
						// (?i) means case insensitive search
						sort.setRowFilter(RowFilter.regexFilter(".*" + str + ".*", 0));
					}
				}

				public void removeUpdate(DocumentEvent e) {
					String str = pidTextField.getText();
					if (str.trim().length() == 0) {
						sort.setRowFilter(null);
					} else {
						sort.setRowFilter(RowFilter.regexFilter(".*" + str + ".*", 0));
					}
				}

				public void changedUpdate(DocumentEvent e) {
				}
			});

			return scrollPane;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sPane;
	}

	private static String parse(String s){
		if (s.length() == 1){
			s = "00" + s;
		}
		if (s.length() == 2){
			s = "0" + s;
		}
		return s;
	}

	private JPanel generateFilterUiItems() {
		JPanel fPanel = new JPanel();
		this.pidTextField = new JTextField();
		this.specieComboBox = new JComboBox<>();
		this.genderComboBox = new JComboBox<>();
		this.levelTextField = new JTextField();
		this.trainerTextField = new JComboBox<>();
		this.pokemonTable = new JTable();

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