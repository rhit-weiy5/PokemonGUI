package Panel;

import Listener.*;
import javax.swing.*;
import java.awt.*;

public class CreatePokemonPanel extends JPanel {

    private JTextField name = null;
    private JTextField TrainerIDBox = new JTextField();
    private JTextField NameBox = new JTextField();
    private JTextField AbilityIDBox = new JTextField();
    private JTextField levelTextField = null;
    private JComboBox<String> specieComboBox = null;
    private JComboBox<String> GenderBox = new JComboBox<>();
    private JButton createButton = null;

    public CreatePokemonPanel(){
        this.setLayout(new BorderLayout());
        this.specieComboBox = new JComboBox<>();
        this.levelTextField = new JTextField();
        this.createButton = new JButton("Create");

        this.specieComboBox.setPreferredSize(new Dimension(100,25));
        this.GenderBox.setPreferredSize(new Dimension(100,25));
        this.levelTextField.setPreferredSize(new Dimension(30, 25));
        this.TrainerIDBox.setPreferredSize(new Dimension(30, 25));
        this.AbilityIDBox.setPreferredSize(new Dimension(30, 25));
        this.NameBox.setPreferredSize(new Dimension(30, 25));
        this.levelTextField.setPreferredSize(new Dimension(30, 25));
        this.createButton.setPreferredSize(new Dimension(80,25));

        FlowLayout layout = new FlowLayout();
        layout.setHgap(15);
        this.setLayout(layout);

        this.add(new JLabel("Create a Pokemon:"));

    	this.add(new JLabel("TrainerID"));
        this.add(TrainerIDBox);
        
        this.add(new JLabel("Name"));
        this.add(NameBox);
        
        this.add(new JLabel("Gender"));
        this.add(GenderBox);
        
        createButton.addActionListener(new CreateListener());
        this.add(createButton);
    }
}
