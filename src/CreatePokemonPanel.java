import javax.swing.*;
import java.awt.*;

public class CreatePokemonPanel extends JPanel {

    private JTextField name = null;
    private JComboBox<String> specieComboBox = null;
    private JTextField levelTextField = null;
    private JButton createButton = null;

    public CreatePokemonPanel(){
        this.setLayout(new BorderLayout());
        this.specieComboBox = new JComboBox<>();
        this.levelTextField = new JTextField();
        this.createButton = new JButton("Create");

        this.specieComboBox.setPreferredSize(new Dimension(100,25));
        this.levelTextField.setPreferredSize(new Dimension(30, 25));
        this.createButton.setPreferredSize(new Dimension(80,25));

        FlowLayout layout = new FlowLayout();
        layout.setHgap(15);
        this.setLayout(layout);

        createButton.addActionListener(new CreateListener());

        this.add(new JLabel("Create a Pokemon:          "));
        this.add(new JLabel("Specie"));
        this.add(specieComboBox);
        this.add(new JLabel("Level"));
        this.add(levelTextField);
        this.add(createButton);
    }
}
