package Panel;

import Database.DatabaseConnection;
import Listener.*;
import javax.swing.*;
import java.awt.*;

public class AddRandomPanel extends JPanel {

    private JTextField pidTextField = null;
    private JButton randomButton = null;
    private DatabaseConnection db;

    public AddRandomPanel(DatabaseConnection db){
        this.db = db;
        this.setLayout(new BorderLayout());

        this.pidTextField = new JTextField();
        this.randomButton = new JButton("AddRandom");

        this.pidTextField.setPreferredSize(new Dimension(75, 25));
        this.randomButton.setPreferredSize(new Dimension(125,25));

        FlowLayout layout = new FlowLayout();
        layout.setHgap(15);
        this.setLayout(layout);

        randomButton.addActionListener(new RandomListener(db, pidTextField));
        repaint();
        this.add(new JLabel("Add some random Pokemon:          "));
        this.add(new JLabel("Number of random Pokemon"));
        this.add(pidTextField);
        this.add(randomButton);
    }

}
