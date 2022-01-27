package Panel;

import Listener.*;
import javax.swing.*;
import java.awt.*;

public class UpdatePokemonPanel extends JPanel {
    private JComboBox<String> specieComboBox = null;
    private JTextField levelTextField = null;
    private JButton updateButton = null;

    public UpdatePokemonPanel(){
        this.setLayout(new BorderLayout());
        this.specieComboBox = new JComboBox<>();
        this.levelTextField = new JTextField();
        this.updateButton = new JButton("Update");

        this.specieComboBox.setPreferredSize(new Dimension(100,25));
        this.levelTextField.setPreferredSize(new Dimension(30, 25));
        this.updateButton.setPreferredSize(new Dimension(80,25));

        FlowLayout layout = new FlowLayout();
        layout.setHgap(15);
        this.setLayout(layout);

        updateButton.addActionListener(new UpdateListener());

        this.add(new JLabel("Update a Pokemon:          "));
        this.add(new JLabel("Specie"));
        this.add(specieComboBox);
        this.add(new JLabel("Level"));
        this.add(levelTextField);
        this.add(updateButton);
    }
}
