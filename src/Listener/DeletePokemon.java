package Listener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeletePokemon implements ActionListener {

    private JTable PokemonTable = null;
    public DeletePokemon(JTable PokemonTable){
        this.PokemonTable = PokemonTable;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        int i = this.PokemonTable.getSelectedRow();
        ((DefaultTableModel)PokemonTable.getModel()).removeRow(i);
    }
}
