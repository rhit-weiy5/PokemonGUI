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
    private JPanel containerPanel;
    private PokemonPanel pPanel;
    private JPanel cPanel;
    private HelpPanel hPanel;
    private JPanel curPanel;

    public Frame(DatabaseConnection db){

        //initialization
        this.db = db;

        this.setSize(960, 540);
        this.setResizable(false);
        this.setTitle("Pokemon");
        this.containerPanel = new JPanel();
        this.pPanel = new PokemonPanel(db);
        this.cPanel = getChangePanel();
        this.cPanel.setVisible(false);
        this.hPanel = new HelpPanel();
        this.hPanel.setVisible(false);
        this.curPanel = pPanel;

        //main menu
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

        //wiki menu
        this.wikiMenu = new JMenu("Wiki");
        this.pokedex = new JMenuItem("Pokédex");
        this.items = new JMenuItem("Items");
        this.moves = new JMenuItem("Moves");
        this.abilities = new JMenuItem("Abilities");
        this.wikiMenu.add(pokedex);
        this.wikiMenu.add(items);
        this.wikiMenu.add(moves);
        this.wikiMenu.add(abilities);

        //help menu
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

        this.containerPanel.add(this.pPanel);
        this.containerPanel.add(this.cPanel);
        this.containerPanel.add(this.hPanel);
        this.add(menuBar, "North");
        this.add(this.containerPanel, "Center");
        this.add(new JLabel("  This GUI is made by Qijun Jiang, James Li and Yunzhe Wei"), "South");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
    }

    private void switchToPokemonList() {
        JPanel tmp = this.curPanel;
        if (this.pPanel != this.curPanel) {
            this.curPanel = this.pPanel;
            this.switchPanel(tmp);
        }
    }

    private void switchToChangePokemon() {
        JPanel tmp = this.curPanel;
        if (this.cPanel != this.curPanel) {
            this.curPanel = this.cPanel;
            this.switchPanel(tmp);
        }
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

    public JPanel getChangePanel(){
        JPanel cPanel = new JPanel();
        GridLayout layout = new GridLayout(4, 1);
        layout.setVgap(70);
        cPanel.setLayout(layout);
        JPanel tilePanel = new JPanel();
        JLabel titleLabel = new JLabel("Here you can create, update or delete a Pokemon");
        titleLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        tilePanel.add(titleLabel);
        JPanel createPanel = new CreatePokemonPanel();
        JPanel updatePanel = new UpdatePokemonPanel();
        JPanel deletePanel = new DeletePokemonPanel(db);
        cPanel.add(tilePanel);
        cPanel.add(createPanel);
        cPanel.add(updatePanel);
        cPanel.add(deletePanel);
        return cPanel;
    }
}
