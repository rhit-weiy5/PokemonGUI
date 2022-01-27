package Panel;

import Database.DatabaseConnection;
import Listener.*;
import javax.swing.*;
import java.awt.*;

public class DeletePokemonPanel extends JPanel {

    private JTextField pidTextField = null;
    private JButton deleteButton = null;
    private DatabaseConnection db;

    public DeletePokemonPanel(DatabaseConnection db){
        this.db = db;
        this.setLayout(new BorderLayout());

        this.pidTextField = new JTextField();
        this.deleteButton = new JButton("Delete");

        this.pidTextField.setPreferredSize(new Dimension(75, 25));
        this.deleteButton.setPreferredSize(new Dimension(80,25));

        FlowLayout layout = new FlowLayout();
        layout.setHgap(15);
        this.setLayout(layout);

        deleteButton.addActionListener(new DeleteListener(db, pidTextField));

        this.add(new JLabel("Delete a Pokemon:          "));
        this.add(new JLabel("PID"));
        this.add(pidTextField);
        this.add(deleteButton);
    }

}
