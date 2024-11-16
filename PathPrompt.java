/*
Brent Thompson
CEN 3024C 15339 Software Development 1
Professor Ashley Evans
November 12th, 2024

Module 10 - Integrate Database

This class is used to prompt the user for a filepath to either load or create a database. The information is needed to
run the solar database app.
 */
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Brent Thompson
 * @version 1.0
 */
public class PathPrompt extends JFrame {
    public JTextField filePath;
    public JPanel PathPrompt;
    public JButton addDatabase;

    /**
     * @see SolarDatabaseMenu for more details on how this class is used.
     */
    public PathPrompt() {
        setContentPane(PathPrompt);
        setTitle("Solar Database Filepath");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        addDatabase.addActionListener(new ActionListener() {
            /**
             * @param e Collect a filepath to be used in the solar panel database
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String filename = "";

                try {
                    filename = filePath.getText();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }

                while (filename.equals("")) {
                    JOptionPane.showMessageDialog(PathPrompt, "Please enter a file name");
                    break;}
                filename = filePath.getText();
                SolarDatabaseMenu frame = new SolarDatabaseMenu(filename);
            }
        });
    }
}
