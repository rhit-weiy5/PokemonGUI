package Panel;

import javax.swing.*;
import java.awt.*;

public class HelpPanel extends JPanel {

    private JLabel titleLabel;
    private JLabel textLabel1;
    private JTextArea textContent1;
    private JLabel textLabel2;
    private JTextArea textContent2;

    public HelpPanel(){
        this.titleLabel = new JLabel("How to Use the Pokemon Database");
        titleLabel.setFont(new Font("Calibri", Font.BOLD, 30));
        this.textLabel1 = new JLabel("Help to Find the Pokemon you want");
        this.textContent1 = new JTextArea("Go the pokemon list window. Initially this window will show you all the Pokemon.\n" +
                "To get the Pokemon you want, use the filters and click search.");
        this.textLabel2 = new JLabel("About the Wiki");
        this.textContent2 = new JTextArea("Our wiki provides the statistics of all the species and forms, all the items, all the moves and all the abilities.\n" +
                "To look at the information you want, go to the drop down menu wiki and select the page you want."
                );

        GridLayout layout = new GridLayout(5,1);
        layout.setHgap(15);
        this.setLayout(layout);
        this.add(titleLabel);
        this.add(textLabel1);
        this.add(textContent1);
        this.add(textLabel2);
        this.add(textContent2);
    }
}
