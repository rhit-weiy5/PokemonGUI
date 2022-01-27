package Panel;

import Listener.*;
import javax.swing.*;
import java.awt.*;

public class CreatePokemonPanel2 extends JPanel {

    private JTextField AbilityIDBox = new JTextField();
    private JTextField levelTextField = null;
    private JComboBox<String> specieComboBox = null;

    public CreatePokemonPanel2(){
        this.setLayout(new BorderLayout());
        this.specieComboBox = new JComboBox<>();
        this.levelTextField = new JTextField();

        this.specieComboBox.setPreferredSize(new Dimension(100,25));
        this.levelTextField.setPreferredSize(new Dimension(30, 25));

        this.AbilityIDBox.setPreferredSize(new Dimension(30, 25));
        this.levelTextField.setPreferredSize(new Dimension(30, 25));

        FlowLayout layout = new FlowLayout();
        layout.setHgap(15);
        this.setLayout(layout);

        
        this.add(new JLabel("AbilityID"));
        this.add(AbilityIDBox);	
        
    	this.add(new JLabel("Specie"));
        this.add(specieComboBox);
        
        this.add(new JLabel("Level"));
        this.add(levelTextField);

    }
}
