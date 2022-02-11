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
        JPanel borderLayoutPanel = new JPanel(new BorderLayout());
        this.db = db;
        this.sPane = Pokedextable();
        borderLayoutPanel.add(this.sPane, BorderLayout.CENTER);
        JFrame frame = new JFrame("Ability");
        frame.setUndecorated(true);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.WARNING_DIALOG);
        frame.getContentPane().add(borderLayoutPanel);
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
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
            this.dextable.getColumnModel().getColumn(0).setPreferredWidth(50);
            this.dextable.getColumnModel().getColumn(1).setPreferredWidth(100);
            this.dextable.getColumnModel().getColumn(2).setPreferredWidth(50);
            this.dextable.getColumnModel().getColumn(3).setPreferredWidth(550);
            this.dextable.getColumnModel().getColumn(4).setPreferredWidth(50);
            return scrollPane;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sPane;
    }

}
