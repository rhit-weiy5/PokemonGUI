package Panel;

import javax.swing.*;
import java.awt.*;

public class HelpPanel extends JPanel {

    private JLabel titleLabel;
    private JLabel textLabel1;
    private JTextArea textContent1;
    private JLabel textLabel2;
    private JTextArea textContent2;
    private JLabel textLabel3;
    private JTextArea textContent3;

    public HelpPanel(){
        this.titleLabel = new JLabel("How to Use the Pokemon Database");
        titleLabel.setFont(new Font("Calibri", Font.BOLD, 30));
        this.textLabel1 = new JLabel("Help to Find the Pokemon You Want");
        this.textContent1 = new JTextArea("Go the pokemon list window. Initially this window will show you all the Pokemon.\n" +
                "To get the Pokemon you want, use the filters and only Pokemon that contain these keywords will be shown.");
        this.textLabel2 = new JLabel("About the Wiki");
        this.textContent2 = new JTextArea("Our wiki provides the statistics of all the species and forms, all the items, all the moves and all the abilities.\n" +
                "To look at the information you want, go to the drop down menu wiki and select the page you want."
                );
        this.textLabel3 = new JLabel("How to Update or Delete a Pokemon");
        this.textContent3 = new JTextArea("To update a Pokemon you want, select the corresponding rows and columns in the table and make changes\n" +
                "Note: only some values can be updated.\n" + "To delete a Pokemon, select that row and press delete.\n");

        GridLayout layout = new GridLayout(7,1);
        layout.setHgap(15);
        this.setLayout(layout);
        this.add(titleLabel);
        this.add(textLabel1);
        this.add(textContent1);
        this.add(textLabel2);
        this.add(textContent2);
        this.add(textLabel3);
        this.add(textContent3);
    }
}
