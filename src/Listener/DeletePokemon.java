package Listener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeletePokemon implements ActionListener {

    private JTable PokemonTable = null;
    private DefaultTableModel model = null;
    public DeletePokemon(JTable PokemonTable, DefaultTableModel model){
        this.PokemonTable = PokemonTable;
        this.model = model;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        int i = this.PokemonTable.getSelectedRow();
        System.out.println(i);
        model.removeRow(i);
    }
}
