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
        JPanel borderLayoutPanel = new JPanel(new BorderLayout());
        this.db = db;
        this.sPane = Pokedextable();
        borderLayoutPanel.add(this.sPane, BorderLayout.CENTER);
        JFrame frame = new JFrame("Moves");
        frame.getContentPane().add(borderLayoutPanel);
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
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
            this.dextable.getColumnModel().getColumn(0).setPreferredWidth(40);
            this.dextable.getColumnModel().getColumn(1).setPreferredWidth(150);
            this.dextable.getColumnModel().getColumn(2).setPreferredWidth(50);
            this.dextable.getColumnModel().getColumn(3).setPreferredWidth(40);
            this.dextable.getColumnModel().getColumn(4).setPreferredWidth(40);
            this.dextable.getColumnModel().getColumn(5).setPreferredWidth(40);
            this.dextable.getColumnModel().getColumn(6).setPreferredWidth(40);
            this.dextable.getColumnModel().getColumn(7).setPreferredWidth(40);
            this.dextable.getColumnModel().getColumn(8).setPreferredWidth(500);
            this.dextable.getColumnModel().getColumn(9).setPreferredWidth(40);
            return scrollPane;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sPane;
    }

}
