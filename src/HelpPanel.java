import javax.swing.*;
import java.awt.*;

public class HelpPanel extends JPanel {

    private JLabel titleLabel;
    private JLabel textLabel;

    public HelpPanel(){
        this.titleLabel = new JLabel("How to Use the Pokemon Database");
        titleLabel.setFont(new Font("Calibri", Font.BOLD, 30));
        this.textLabel = new JLabel("Help to the database");
        GridLayout layout = new GridLayout(2,1);
        layout.setHgap(15);
        this.setLayout(layout);
        this.add(titleLabel);
        this.add(textLabel);
    }
}
