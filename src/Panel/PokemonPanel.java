package Panel;

import Listener.*;
import javax.swing.*;
import java.awt.*;

public class PokemonPanel extends JPanel {

    private JTextField pidTextField = null;
    private JComboBox<String> specieComboBox = null;
    private JComboBox<String> genderComboBox = null;
    private JTextField levelTextField = null;
    private JComboBox<String> trainerTextField = null;
    private JPanel filterPanel = null;
    private JButton filterButton = null;
    private JTable pokemonTable = null;
    private JScrollPane sPane;


    public PokemonPanel(){
        this.filterPanel = generateFilterUiItems();
        this.sPane = generatePokemonTable();
        this.setLayout(new BorderLayout());
        this.add(filterPanel, "North");
        this.add(sPane, "Center");
    }

    private JScrollPane generatePokemonTable() {
        this.pokemonTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(this.pokemonTable);
        this.pokemonTable.setFillsViewportHeight(true);
        return scrollPane;
    }

    private JPanel generateFilterUiItems() {
        JPanel fPanel = new JPanel();
        this.pidTextField = new JTextField();
        this.specieComboBox = new JComboBox<>();
        this.genderComboBox = new JComboBox<>();
        this.levelTextField = new JTextField();
        this.trainerTextField = new JComboBox<>();

        //Set dimension
        this.pidTextField.setPreferredSize(new Dimension(75, 25));
        this.specieComboBox.setPreferredSize(new Dimension(100, 25));
        this.genderComboBox.setPreferredSize(new Dimension(50, 25));
        this.levelTextField.setPreferredSize(new Dimension(30, 25));
        this.trainerTextField.setPreferredSize(new Dimension(80,25));

        //Set layout
        FlowLayout layout = new FlowLayout();
        layout.setHgap(15);
        fPanel.setLayout(layout);

        //add stuff
        fPanel.add(new JLabel("PID"));
        fPanel.add(this.pidTextField);
        fPanel.add(new JLabel("Specie"));
        fPanel.add(this.specieComboBox);
        fPanel.add(new JLabel("Gender"));
        fPanel.add(this.genderComboBox);
        fPanel.add(new JLabel("Level"));
        fPanel.add(this.levelTextField);
        fPanel.add(new JLabel("Trainer"));
        fPanel.add(this.trainerTextField);
        this.filterButton = new JButton("Search");
        filterButton.addActionListener(new SearchListener());
        fPanel.add(this.filterButton);
        return fPanel;
    }
}
