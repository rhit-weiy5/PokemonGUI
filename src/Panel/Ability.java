package Panel;

import Database.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Ability extends JPanel{
    private DatabaseConnection db = null;
    private JTable dextable = null;
    private JScrollPane sPane;

    public Ability(DatabaseConnection db) {
        // TODO Auto-generated constructor stub
        this.db = db;
        this.sPane = Pokedextable();
        this.setLayout(new BorderLayout());
        this.add(sPane, "Center");
    }

    private JScrollPane Pokedextable() {
        Statement stmt;
        try {
            stmt = this.db.getConnection().createStatement();
            ResultSet countrs = stmt.executeQuery("SELECT ID From Ability1");
            int count=0;
            while(countrs.next()) {
                count++;
            }

            ResultSet rs = stmt.executeQuery("Select * From Ability1");
            String[][] rec = new String[count][5];
            String[] header = { "ID", "Name", "Pok_mon", "Description", "Gen"};
            int index =0;
            while(rs.next()) {
                rec[index][0]=rs.getString("ID");
                rec[index][1]=rs.getString("Name");
                rec[index][2]=rs.getString("Pok_mon");
                rec[index][3]=rs.getString("Description");
                rec[index][4]=rs.getString("Gen");
                index++;
            }
            this.dextable = new JTable(rec,header);
            JScrollPane scrollPane = new JScrollPane(this.dextable);
            this.dextable.setFillsViewportHeight(true);
            return scrollPane;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sPane;
    }

}
