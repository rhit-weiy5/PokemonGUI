package Panel;

import Database.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Moves extends JPanel{
    private DatabaseConnection db = null;
    private JTable dextable = null;
    private JScrollPane sPane;

    public Moves(DatabaseConnection db) {
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
            ResultSet countrs = stmt.executeQuery("SELECT ID From Move1");
            int count=0;
            while(countrs.next()) {
                count++;
            }

            ResultSet rs = stmt.executeQuery("Select * From Move1");
            String[][] rec = new String[count][10];
            String[] header = { "ID", "Name", "Type", "Cat", "Power", "Acc", "PP", "TM", "Effect", "Prob"};
            int index =0;
            while(rs.next()) {
                rec[index][0]=rs.getString("ID");
                rec[index][1]=rs.getString("Name");
                rec[index][2]=rs.getString("Type");
                rec[index][3]=rs.getString("Cat");
                rec[index][4]=rs.getString("Power");
                rec[index][5]=rs.getString("Acc");
                rec[index][6]=rs.getString("PP");
                rec[index][7]=rs.getString("TM");
                rec[index][8]=rs.getString("Effect");
                rec[index][9]=rs.getString("Prob");
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
