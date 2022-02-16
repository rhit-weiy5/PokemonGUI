package Panel;

import Database.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Pokedex extends JPanel{
    private DatabaseConnection db = null;
    private JTable dextable = null;
    private JScrollPane sPane;

    public Pokedex(DatabaseConnection db) {
        // TODO Auto-generated constructor stub
        JPanel borderLayoutPanel = new JPanel(new BorderLayout());
        this.db = db;
        this.sPane = Pokedextable();
        borderLayoutPanel.add(this.sPane, BorderLayout.CENTER);
        JFrame frame = new JFrame("Pokedex");
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
            ResultSet countrs = stmt.executeQuery("SELECT ID From Pokedex");
            int count=0;
            while(countrs.next()) {
                count++;
            }

            ResultSet rs = stmt.executeQuery("Select * From Pokedex");
            String[][] rec = new String[count][12];
            String[] header = { "ID", "No.", "Name", "Type1", "Type2", "Total", "HP", "Attack", "Defense", "Sp_Atk", "Sp_Def", "Speed"};
            int index =0;
            while(rs.next()) {
                rec[index][0]=rs.getString("ID");
                rec[index][1]=rs.getString("SpeciesID");
                rec[index][2]=rs.getString("Name");
                rec[index][3]=rs.getString("Type1");
                rec[index][4]=rs.getString("Type2");
                rec[index][5]=rs.getString("Total");
                rec[index][6]=rs.getString("HP");
                rec[index][7]=rs.getString("ATK");
                rec[index][8]=rs.getString("DEF");
                rec[index][9]=rs.getString("SPA");
                rec[index][10]=rs.getString("SPD");
                rec[index][11]=rs.getString("SPE");
                index++;
            }
            this.dextable = new JTable(rec,header){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }};
            JScrollPane scrollPane = new JScrollPane(this.dextable);
            this.dextable.setFillsViewportHeight(true);
            this.dextable.getColumnModel().getColumn(0).setPreferredWidth(60);
            this.dextable.getColumnModel().getColumn(1).setPreferredWidth(60);
            this.dextable.getColumnModel().getColumn(2).setPreferredWidth(330);
            this.dextable.getColumnModel().getColumn(3).setPreferredWidth(80);
            this.dextable.getColumnModel().getColumn(4).setPreferredWidth(80);
            this.dextable.getColumnModel().getColumn(5).setPreferredWidth(80);
            this.dextable.getColumnModel().getColumn(6).setPreferredWidth(80);
            this.dextable.getColumnModel().getColumn(7).setPreferredWidth(80);
            this.dextable.getColumnModel().getColumn(8).setPreferredWidth(80);
            this.dextable.getColumnModel().getColumn(9).setPreferredWidth(80);
            this.dextable.getColumnModel().getColumn(10).setPreferredWidth(80);
            this.dextable.getColumnModel().getColumn(11).setPreferredWidth(80);
            return scrollPane;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sPane;
    }

}
