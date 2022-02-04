package Panel;

import Database.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Items extends JPanel{
    private DatabaseConnection db = null;
    private JTable dextable = null;
    private JScrollPane sPane;

    public Items(DatabaseConnection db) {
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
            ResultSet countrs = stmt.executeQuery("SELECT ID From Item1");
            int count=0;
            while(countrs.next()) {
                count++;
            }

            ResultSet rs = stmt.executeQuery("Select * From Item1");
            String[][] rec = new String[count][4];
            String[] header = { "ID", "Name", "Category", "Effect"};
            int index =0;
            while(rs.next()) {
                rec[index][0]=rs.getString("ID");
                rec[index][1]=rs.getString("Name");
                rec[index][2]=rs.getString("Category");
                rec[index][3]=rs.getString("Effect");
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