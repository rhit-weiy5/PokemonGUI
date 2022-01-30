package Panel;

import Database.DatabaseConnection;
import Listener.*;
import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PokemonPanel extends JPanel {


    private JTextField pidTextField = null;
    private JComboBox<String> specieComboBox = null;
    private JComboBox<String> genderComboBox = null;
    private JTextField levelTextField = null;
    private JComboBox<String> trainerTextField = null;
    private JPanel filterPanel = null;
    private JButton filterButton = null;
    private JTable pokemonTable = null;
    private JScrollPane sPane;
    private DatabaseConnection db = null;


    public PokemonPanel(DatabaseConnection db){
        this.db = db;
        this.filterPanel = generateFilterUiItems();
        this.sPane = generatePokemonTable();
        this.setLayout(new BorderLayout());
        this.add(filterPanel, "North");
        this.add(sPane, "Center");
    }

    private JScrollPane generatePokemonTable() {
    	Statement stmt;
		try {		
			stmt = this.db.getConnection().createStatement();
			ResultSet countrs = stmt.executeQuery("SELECT PID From Pokemon");
			int count=0;
			while(countrs.next()) {
				count++;
			}
			
			ResultSet rs = stmt.executeQuery("SELECT PID, Pokemon.Name as Pname, Pokemon.Gender as Pgender, Level, Friendship, Species.SpecieName as SName, Ability.Name as AName, Trainer.Name as TName From Pokemon JOIN Trainer on Trainer.ID = Pokemon.TrainerID JOIN Ability on Pokemon.AbilityID = Ability.ID Join Species on Pokemon.SpecieID = Species.ID");
			String[][] rec = new String[count][8];
			String[] header = { "PID", "Name", "Gender", "Level", "Friendship", "SpecieName", "AbilityName", "TrainerName"};
			int index =0;
	    	while(rs.next()) {
	    		rec[index][0]=rs.getString("PID");
	    		rec[index][1]=rs.getString("Pname");
	    		if(rs.getInt("Pgender")==1) {
	    			rec[index][2]="Female";
	    		}else {
	    			rec[index][2]="Male";
	    		}
	    		rec[index][3]=rs.getString("Level");
	    		rec[index][4]=rs.getString("Friendship");
	    		rec[index][5]=rs.getString("SName");
	    		rec[index][6]=rs.getString("AName");
	    		rec[index][7]=rs.getString("TName");
	    		index++;	    		
	    	}
	        this.pokemonTable = new JTable(rec,header);
	        JScrollPane scrollPane = new JScrollPane(this.pokemonTable);
	        this.pokemonTable.setFillsViewportHeight(true);
	        return scrollPane;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sPane;
    }

    private JPanel generateFilterUiItems() {
        JPanel fPanel = new JPanel();
        this.pidTextField = new JTextField();
        this.specieComboBox = new JComboBox<>();
        this.genderComboBox = new JComboBox<>();
        this.levelTextField = new JTextField();
        this.trainerTextField = new JComboBox<>();
        getSpecie();
        getTrainer();
        this.genderComboBox.addItem("None");
        this.genderComboBox.addItem("Male");
        this.genderComboBox.addItem("Female");
        this.genderComboBox.addItem("NULL");

        //Set dimension
        this.pidTextField.setPreferredSize(new Dimension(75, 25));
        this.specieComboBox.setPreferredSize(new Dimension(100, 25));
        this.genderComboBox.setPreferredSize(new Dimension(60, 25));
        this.levelTextField.setPreferredSize(new Dimension(30, 25));
        this.trainerTextField.setPreferredSize(new Dimension(80,25));

        //Set layout
        FlowLayout layout = new FlowLayout();
        layout.setHgap(15);
        fPanel.setLayout(layout);

        //add stuff
        fPanel.add(new JLabel("PID"));
        fPanel.add(this.pidTextField);
        fPanel.add(new JLabel("Specie"));
        fPanel.add(this.specieComboBox);
        fPanel.add(new JLabel("Gender"));
        fPanel.add(this.genderComboBox);
        fPanel.add(new JLabel("Level"));
        fPanel.add(this.levelTextField);
        fPanel.add(new JLabel("Trainer"));
        fPanel.add(this.trainerTextField);
        this.filterButton = new JButton("Search");
        filterButton.addActionListener(new SearchListener(db));
        fPanel.add(this.filterButton);
        return fPanel;
    }
    public void getSpecie() {	
    	this.specieComboBox.addItem("None");
		try {
			Statement stmt = this.db.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(" SELECT DISTINCT SpecieName From Pokemon JOIN Species on Species.ID = Pokemon.SpecieID");
			while(rs.next()) {
				this.specieComboBox.addItem(rs.getString("SpecieName"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
    public void getTrainer() {	
    	this.trainerTextField.addItem("None");
		try {
			Statement stmt = this.db.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(" SELECT DISTINCT Trainer.Name as trainername From Pokemon JOIN Trainer on Trainer.ID = Pokemon.TrainerID");
			while(rs.next()) {
				this.trainerTextField.addItem(rs.getString("trainername"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
}
