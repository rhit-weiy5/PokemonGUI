import Database.DatabaseConnection;
import Panel.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame {

	private DatabaseConnection db = null;

	private JMenu mainMenu;
	private JMenuItem pokemonList;
	private JMenuItem changePokemon;
	private JMenu wikiMenu;
	private JMenuItem pokedex;
	private JMenuItem items;
	private JMenuItem moves;
	private JMenuItem abilities;

	private JMenu helpMenu;
	private JMenuItem help;
	public JPanel containerPanel;
	private PokemonPanel pPanel;
	private JPanel cPanel;
	private HelpPanel hPanel;
	public JPanel curPanel;

	public Frame(DatabaseConnection db) {

		// initialization
		this.db = db;
		this.setSize(1920, 1080);
		this.setUndecorated(true);
		this.getRootPane().setWindowDecorationStyle(JRootPane.WARNING_DIALOG);
		this.setTitle("Pokemon");
		this.containerPanel = new JPanel();
		this.pPanel = new PokemonPanel(db, this);
		this.cPanel = getChangePanel();
		this.cPanel.setVisible(false);
		this.hPanel = new HelpPanel();
		this.hPanel.setVisible(false);
		this.curPanel = pPanel;

		// main menu
		this.mainMenu = new JMenu("Screens");
		this.pokemonList = new JMenuItem("Pokemon List");
		this.pokemonList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Frame.this.switchToPokemonList();
			}
		});
		this.changePokemon = new JMenuItem("Change Pokemon");
		this.changePokemon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Frame.this.switchToChangePokemon();
			}
		});
		this.mainMenu.add(this.pokemonList);
		this.mainMenu.add(this.changePokemon);

		// wiki menu
		this.wikiMenu = new JMenu("Wiki");
		this.pokedex = new JMenuItem("Pokedex");
		this.pokedex.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Frame.this.switchToPokedex();
			}
		});
		this.items = new JMenuItem("Items");
		this.items.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Frame.this.switchToItems();
			}
		});
		this.moves = new JMenuItem("Moves");
		this.moves.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Frame.this.switchToMoves();
			}
		});
		this.abilities = new JMenuItem("Abilities");
		this.abilities.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Frame.this.switchToAbility();
			}
		});
		this.wikiMenu.add(pokedex);
		this.wikiMenu.add(items);
		this.wikiMenu.add(moves);
		this.wikiMenu.add(abilities);

		// help menu
		this.helpMenu = new JMenu("Help");
		this.help = new JMenuItem("How to Use");
		this.help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Frame.this.switchToHelp();
			}
		});
		this.helpMenu.add(help);

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(this.mainMenu);
		menuBar.add(this.wikiMenu);
		menuBar.add(this.helpMenu);
		this.rootPane.setPreferredSize(new Dimension(1260, 800));
		this.pPanel.setPreferredSize(new Dimension(1230, 720));
		this.containerPanel.add(this.pPanel);
		this.containerPanel.add(this.cPanel);
		this.containerPanel.add(this.hPanel);
		System.out.println(this);
		this.add(menuBar, "North");
		this.add(this.containerPanel, "Center");
		this.add(new JLabel("  This GUI is made by Qijun Jiang, James Li and Yunzhe Wei"), "South");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.pack();
	}

	private void switchToPokemonList() {
		JPanel tmp = this.curPanel;
		this.curPanel = new PokemonPanel(db, this);
		this.curPanel.setPreferredSize(new Dimension(1230, 720));
		this.containerPanel.add(this.curPanel);
		this.switchPanel(tmp);
	}

	private void switchToChangePokemon() {
		JPanel tmp = this.curPanel;
		if (this.cPanel != this.curPanel) {
			this.curPanel = this.cPanel;
			this.switchPanel(tmp);
		}
	}

	private void switchToPokedex() {
		JPanel tmp = this.curPanel;
		new Pokedex(db);
		this.switchPanel(tmp);
	}

	private void switchToItems() {
		JPanel tmp = this.curPanel;
		new Items(db);
		this.switchPanel(tmp);
	}

	private void switchToMoves() {
		JPanel tmp = this.curPanel;
		new Moves(db);
		this.switchPanel(tmp);
	}

	private void switchToAbility() {
		JPanel tmp = this.curPanel;
		new Ability(db);
		this.switchPanel(tmp);
	}

	private void switchToHelp() {
		JPanel tmp = this.curPanel;
		if (this.hPanel != this.curPanel) {
			this.curPanel = this.hPanel;
			this.switchPanel(tmp);
		}
	}

	private void switchPanel(Component toRemove) {
		toRemove.setVisible(false);
		this.curPanel.setVisible(true);
	}

	public JPanel getChangePanel() {
		JPanel cPanel = new JPanel();
		GridLayout layout = new GridLayout(5, 1);
		layout.setVgap(30);
		cPanel.setLayout(layout);
		JPanel tilePanel = new JPanel();
		JLabel titleLabel = new JLabel("Here you can create, update or delete a Pokemon");
		titleLabel.setFont(new Font("Calibri", Font.BOLD, 20));
		tilePanel.add(titleLabel);
		JPanel createPanel = new CreatePokemonPanel(db);
		JPanel updatePanel = new UpdatePokemonPanel(db);
		JPanel deletePanel = new DeletePokemonPanel(db);
		JPanel AddRandomPanel = new AddRandomPanel(db);
		cPanel.add(tilePanel);

		cPanel.add(createPanel);
		cPanel.add(updatePanel);
		cPanel.add(deletePanel);
		cPanel.add(AddRandomPanel);
		return cPanel;
	}
}
