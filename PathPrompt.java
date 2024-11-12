import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PathPrompt extends JFrame {
    public JTextField filePath;
    public JPanel PathPrompt;
    public JButton addDatabase;

    public PathPrompt() {
        setContentPane(PathPrompt);
        setTitle("Solar Database Filepath");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        addDatabase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filename = filePath.getText();
                SolarDatabaseMenu frame = new SolarDatabaseMenu(filename);
            }
        });
    }
}
