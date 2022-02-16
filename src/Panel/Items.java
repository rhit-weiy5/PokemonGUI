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
        JPanel borderLayoutPanel = new JPanel(new BorderLayout());
        this.db = db;
        this.sPane = Pokedextable();
        borderLayoutPanel.add(this.sPane, BorderLayout.CENTER);
        JFrame frame = new JFrame("Items");
        frame.setUndecorated(true);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.WARNING_DIALOG);
        frame.getContentPane().add(borderLayoutPanel);
        frame.setSize(1100, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
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
            this.dextable = new JTable(rec,header){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }};
            JScrollPane scrollPane = new JScrollPane(this.dextable);
            this.dextable.setFillsViewportHeight(true);
            this.dextable.setFillsViewportHeight(true);
            this.dextable.getColumnModel().getColumn(0).setPreferredWidth(40);
            this.dextable.getColumnModel().getColumn(1).setPreferredWidth(130);
            this.dextable.getColumnModel().getColumn(2).setPreferredWidth(130);
            this.dextable.getColumnModel().getColumn(3).setPreferredWidth(800);
            return scrollPane;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sPane;
    }

}
