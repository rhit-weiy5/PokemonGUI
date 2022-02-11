package Panel;

import Listener.*;
import javax.swing.*;

import Database.DatabaseConnection;

import java.awt.*;

public class UpdatePokemonPanel extends JPanel {
	private JTextField pid = null;
	private JTextField NameTextField = null;
	private JTextField LevelTextField = null;
	private JButton updateButton = null;
	private DatabaseConnection db;

	public UpdatePokemonPanel(DatabaseConnection db) {
		this.setLayout(new BorderLayout());
		this.db = db;
		this.pid = new JTextField();
		this.NameTextField = new JTextField();
		this.LevelTextField = new JTextField();
		this.updateButton = new JButton("Update");

		this.pid.setPreferredSize(new Dimension(60, 25));
		this.NameTextField.setPreferredSize(new Dimension(80, 25));
		this.LevelTextField.setPreferredSize(new Dimension(30, 25));
		this.updateButton.setPreferredSize(new Dimension(80, 25));

		FlowLayout layout = new FlowLayout();
		layout.setHgap(15);
		this.setLayout(layout);


		this.add(new JLabel("Update a Pokemon:          "));
		this.add(new JLabel("pid"));
		this.add(pid);
		this.add(new JLabel("Name"));
		this.add(NameTextField);
		this.add(new JLabel("Level"));
		this.add(LevelTextField);
		this.add(updateButton);
		updateButton.addActionListener(new UpdateListener(db, pid, NameTextField, LevelTextField));
		repaint();
	}
}
