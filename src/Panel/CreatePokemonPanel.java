package Panel;

import Listener.*;
import javax.swing.*;

import Database.DatabaseConnection;

import java.awt.*;

public class CreatePokemonPanel extends JPanel {

	private JTextField name = null;
	private JTextField TrainerIDBox = new JTextField();
	private JTextField NameBox = new JTextField();
	private JTextField AbilityIDBox = new JTextField();
	private JTextField levelTextField = null;
	private JTextField specieComboBox = null;
	private JComboBox<String> GenderBox = null;
	private JButton createButton = null;
	private DatabaseConnection db;

	public CreatePokemonPanel(DatabaseConnection db) {
		this.setLayout(new BorderLayout());
		this.specieComboBox = new JTextField();
		this.levelTextField = new JTextField();
		this.TrainerIDBox = new JTextField();
		this.NameBox = new JTextField();
		this.AbilityIDBox = new JTextField();
		this.GenderBox = new JComboBox<String>();
		this.db = db;
		this.createButton = new JButton("Create");

		this.specieComboBox.setPreferredSize(new Dimension(100, 25));
		this.GenderBox.setPreferredSize(new Dimension(100, 25));
		this.levelTextField.setPreferredSize(new Dimension(30, 25));
		this.TrainerIDBox.setPreferredSize(new Dimension(30, 25));
		this.AbilityIDBox.setPreferredSize(new Dimension(30, 25));
		this.NameBox.setPreferredSize(new Dimension(30, 25));
		this.levelTextField.setPreferredSize(new Dimension(30, 25));
		this.createButton.setPreferredSize(new Dimension(80, 25));

		this.GenderBox.addItem("NULL");
		this.GenderBox.addItem("Male");
		this.GenderBox.addItem("Female");
		
		GridLayout layout = new GridLayout(3, 4);
		layout.setHgap(5);
		layout.setVgap(5);
		this.setLayout(layout);

		this.add(new JLabel("Create a Pokemon:"));
		JLabel a = new JLabel();
		a.setPreferredSize(new Dimension(30, 25));
		JLabel b = new JLabel();
		b.setPreferredSize(new Dimension(30, 25));
		JLabel c = new JLabel();
		c.setPreferredSize(new Dimension(30, 25));
		JLabel d = new JLabel();
		d.setPreferredSize(new Dimension(30, 25));
		JLabel e = new JLabel();
		e.setPreferredSize(new Dimension(30, 25));

		this.add(a);
		this.add(b);
		this.add(c);
		this.add(d);
		this.add(e);

		this.add(new JLabel("TrainerID"));
		this.add(TrainerIDBox);

		this.add(new JLabel("Name"));
		this.add(NameBox);

		this.add(new JLabel("Gender"));
		this.add(GenderBox);

		this.AbilityIDBox.setPreferredSize(new Dimension(30, 25));
		this.specieComboBox.setPreferredSize(new Dimension(100, 25));

		layout.setHgap(15);
		this.setLayout(layout);

		this.add(new JLabel("AbilityID"));
		this.add(AbilityIDBox);

		this.add(new JLabel("Specie"));
		this.add(specieComboBox);

		this.add(createButton);

		createButton.addActionListener(
				new CreateListener(db, TrainerIDBox, NameBox, GenderBox, AbilityIDBox, specieComboBox));
	}
}
