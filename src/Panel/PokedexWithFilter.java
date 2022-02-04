package Panel;

import Database.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PokedexWithFilter extends JPanel {
	private DatabaseConnection db = null;
	private JTable dextable = null;
	private JScrollPane sPane;
	private String PID;
	private String Specie;
	private String Gender;
	private String Level;
	private String Trainer;

	public PokedexWithFilter(DatabaseConnection db, String PID, String Specie, String Gender, String Level,
			String Trainer) {
		// TODO Auto-generated constructor stub
		JPanel borderLayoutPanel = new JPanel(new BorderLayout());
		this.db = db;
		this.PID = PID;
		this.Specie = Specie;
		this.Gender = Gender;
		this.Level = Level;
		this.Trainer = Trainer;
		this.sPane = Pokedextable();
		borderLayoutPanel.add(this.sPane, BorderLayout.CENTER);
		JFrame frame = new JFrame("Pokedex");
		frame.getContentPane().add(borderLayoutPanel);
		frame.setSize(800, 800);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private JScrollPane Pokedextable() {
		Statement stmt;
		try {
			String temp = "Select * From search_pokemon()";
			if (this.PID == null || this.PID.equals("")) {
			} else {
				temp += (" WHERE PID=" + this.PID);
			}

			if (this.Specie == null || this.Specie.equals("None")) {
			} else {
				if (temp.indexOf("WHERE") == -1) {
					temp += (" WHERE SName='" + this.Specie + "'");
				} else {
					temp += (" AND SName='" + this.Specie + "'");
				}
			}

			if (this.Gender == null || this.Gender.equals("None")) {
			} else {
				if (temp.indexOf("WHERE") == -1) {
					if (this.Gender.equals("NULL")) {
						temp += (" WHERE PGender is null");
					} else if (this.Gender.equals("Male")) {
						temp += (" WHERE PGender=0");
					} else if (this.Gender.equals("Female")) {
						temp += (" WHERE PGender=1");
					}
				} else {
					if (this.Gender.equals("NULL")) {
						temp += (" AND PGender is null");
					} else if (this.Gender.equals("Male")) {
						temp += (" AND PGender=0");
					} else if (this.Gender.equals("Female")) {
						temp += (" AND PGender=1");
					}
				}
			}

			if (this.Level == null || this.Level.equals("")) {
			} else {
				if (temp.indexOf("WHERE") == -1) {
					temp += (" WHERE Level>=" + this.Level);
				} else {
					temp += (" AND Level>=" + this.Level);
				}
			}

			if (this.Trainer == null || this.Trainer.equals("None")) {
			} else {
				if (temp.indexOf("WHERE") == -1) {
					temp += (" WHERE TName='" + this.Trainer + "'");
				} else {
					temp += (" AND TName='" + this.Trainer + "'");
				}
			}

			stmt = this.db.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(temp);
			int count = 0;
			while (rs.next()) {
				count++;
			}

			rs = stmt.executeQuery(temp);

			String[][] rec = new String[count][8];
			String[] header = { "PID", "Name", "Gender", "Level", "Friendship", "SpecieName", "AbilityName",
					"TrainerName" };
			int index = 0;
			while (rs.next()) {
				System.out.println(rs.getString("Pname"));
				rec[index][0] = rs.getString("PID");
				rec[index][1] = rs.getString("Pname");
				if (rs.getInt("Pgender") == 1) {
					rec[index][2] = "Female";
				} else {
					rec[index][2] = "Male";
				}
				rec[index][3] = rs.getString("Level");
				rec[index][4] = rs.getString("Friendship");
				rec[index][5] = rs.getString("SName");
				rec[index][6] = rs.getString("AName");
				rec[index][7] = rs.getString("TName");
				index++;
			}
			this.dextable = new JTable(rec, header);
			JScrollPane scrollPane = new JScrollPane(this.dextable);
			this.dextable.setFillsViewportHeight(true);
			this.dextable.getColumnModel().getColumn(0).setPreferredWidth(50);
			this.dextable.getColumnModel().getColumn(1).setPreferredWidth(80);
			this.dextable.getColumnModel().getColumn(2).setPreferredWidth(50);
			this.dextable.getColumnModel().getColumn(3).setPreferredWidth(50);
			this.dextable.getColumnModel().getColumn(4).setPreferredWidth(55);
			this.dextable.getColumnModel().getColumn(5).setPreferredWidth(200);
			this.dextable.getColumnModel().getColumn(6).setPreferredWidth(150);
			this.dextable.getColumnModel().getColumn(7).setPreferredWidth(100);

			return scrollPane;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sPane;
	}

}
