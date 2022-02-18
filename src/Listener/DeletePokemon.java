package Listener;

import Database.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class DeletePokemon implements ActionListener {

    private JTable PokemonTable = null;
    private DefaultTableModel model = null;
    private ArrayList<Integer> pid = null;
    private DatabaseConnection db;
    public DeletePokemon(JTable PokemonTable, DefaultTableModel model, ArrayList<Integer> pid, DatabaseConnection db){
        this.PokemonTable = PokemonTable;
        this.model = model;
        this.pid = pid;
        this.db = db;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int i = this.PokemonTable.getSelectedRow();
        //System.out.println(i);
        //System.out.println("pid:" + model.getDataVector().get(0));
        int pidToRemove = (pid.get(i));
        delete(pidToRemove, i);
        model.removeRow(i);
        JOptionPane.showMessageDialog(null,"Sucessful Deleted");
    }

    public boolean delete(int pidToRemove, int i){
        CallableStatement stmt = null;
        try {
            stmt = db.getConnection().prepareCall("{call delete_pokemon (@PID = ?)}");
            stmt.setInt(1, pidToRemove);
            stmt.execute();
            JOptionPane.showMessageDialog(null, "The specific PID has been deleted.");
            //System.out.println(i);
            pid.remove(i);

            return true;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "PID not exists");
            return false;
        }
    }
}
