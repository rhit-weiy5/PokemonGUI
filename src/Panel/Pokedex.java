package Panel;

import Database.DatabaseConnection;
import Listener.*;
import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Pokedex extends JPanel{
    private DatabaseConnection db = null;
    private JTable dextable = null;
    private JScrollPane sPane;

    public Pokedex(DatabaseConnection db) {
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
            ResultSet countrs = stmt.executeQuery("SELECT ID From Pokedex");
            int count=0;
            while(countrs.next()) {
                count++;
            }

            ResultSet rs = stmt.executeQuery("Select * From Pokedex");
            String[][] rec = new String[count][12];
            String[] header = { "ID", "SpeciesID", "Name", "Type1", "Type2", "Total", "HP", "Attack", "Defense", "Sp_Atk", "Sp_Def", "Speed"};
            int index =0;
            while(rs.next()) {
                rec[index][0]=rs.getString("ID");
                rec[index][1]=rs.getString("SpeciesID");
                rec[index][2]=rs.getString("Name");
                rec[index][3]=rs.getString("Type1");
                rec[index][4]=rs.getString("Type2");
                rec[index][5]=rs.getString("Total");
                rec[index][6]=rs.getString("HP");
                rec[index][7]=rs.getString("Attack");
                rec[index][8]=rs.getString("Defense");
                rec[index][9]=rs.getString("Sp_Atk");
                rec[index][10]=rs.getString("Sp_Def");
                rec[index][11]=rs.getString("Speed");
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
