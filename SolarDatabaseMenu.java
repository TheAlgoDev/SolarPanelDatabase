/*

Brent Thompson
CEN 3024C 15339 Software Development 1
Professor Ashley Evans
November 12th, 2024

Module 10 - Integrate Database

The Menu is used to interface with a sqlite database using basic crud functions. There 5 tabs of options
for the used to interact with.

 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

/**
 * @author Brent Thompson
 * @version 1.0
 */
public class SolarDatabaseMenu extends JFrame {

    private JPanel Menu;
    private JPanel PathPrompt;
    private JTabbedPane tabbedPane1;
    private JButton addIndividualModule;
    private JButton importFromCSV;
    private JButton removeModule;
    private JButton updateModule;
    private JButton generateReport;
    private JButton printModules;
    private JButton exitProgramButton;
    private JTextField addModID;
    private JTextField addVOC;
    private JTextField addSerialNumber;
    private JTextField addMake;
    private JTextField numberCellsX;
    private JTextField numberCellsY;
    private JTextField filepath;
    private JTextField removeModID;
    private JTextField removeSerialNumber;
    private JTextField updateModID;
    private JComboBox updateKey;
    private JTextField updateValue;
    private JButton btnClear;
    private JTextPane addModuleSuccess;
    private JTextPane printModuleView;
    private JTextPane removeModuleSuccess;
    private JTextPane importModulesSuccess;
    private JTextPane generateReportMessage;
    private JTextField generateReportID;
    private JTextPane updateSuccessMessage;
    private JTextField filePath;
    private JButton addDatabase;

    /**
     * @param filename Location of the sqlite database
     */
    public SolarDatabaseMenu(String filename) {
        setContentPane(Menu);
        setTitle("Solar Database Menu");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        SolarDatabase FSEC = new SolarDatabase(filename);


        btnClear.addActionListener(new ActionListener() {
            /**
             * @param e Clear the form
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                addModID.setText("");
                addVOC.setText("");
                addSerialNumber.setText("");
                addMake.setText("");
                numberCellsX.setText("");
                numberCellsY.setText("");
                filepath.setText("");
                removeModID.setText("");
                removeSerialNumber.setText("");
                updateModID.setText("");
                addModuleSuccess.setText("");
                printModuleView.setText("");
                removeModuleSuccess.setText("");
                importModulesSuccess.setText("");
                generateReportMessage.setText("");
                generateReportID.setText("");
                updateSuccessMessage.setText("");
            }
        });
        // Exit the program by using Exit button
        exitProgramButton.addActionListener(new ActionListener() {
            /**
             * @param e Exit the program
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        // Adding an individual module using data entered in text fields
        addIndividualModule.addActionListener(new ActionListener() {
            /**
             * @param e Add a solar panel
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String modID = addModID.getText();
                String serialNumber = addSerialNumber.getText();
                float voc = Float.parseFloat(addVOC.getText());
                String make = addMake.getText();
                int cellsX = Integer.parseInt(numberCellsX.getText());
                int cellsY = Integer.parseInt(numberCellsY.getText());
                SolarPanel newPanel = new SolarPanel(modID, serialNumber, make, voc, cellsX, cellsY);
                FSEC.addPanel(newPanel);
                addModuleSuccess.setText("Module with ID " + modID + " added successfully.");
            }
        });
        // Importing modules to database using CSV
        importFromCSV.addActionListener(new ActionListener() {
            /**
             * @param e Import solar panel modules using a CSV
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String file = filepath.getText();
                    List<SolarPanel> importedPanels = SolarDatabase.importPanelListFromJpanel(file);
                    for (SolarPanel panel : importedPanels) {
                        FSEC.addPanel(panel);
                    }
                    String size = Integer.toString(FSEC.getSize());
                    importModulesSuccess.setText("Imported Panel Details: " + size);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        // Remove module with the use of ModuleID and Serial Number
        removeModule.addActionListener(new ActionListener() {
            /**
             * @param e Remove a module from the database
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String modID = removeModID.getText();
                String serialNumber = removeSerialNumber.getText();
                String returnMessage = FSEC.removePanelWithJpanel(modID, serialNumber);
                removeModuleSuccess.setText(returnMessage);
            }
        });
        // Update a module in database using ModuleID
        updateModule.addActionListener(new ActionListener() {
            /**
             * @param e Update the records of a specific module
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String modID = updateModID.getText();
                String key = (String) updateKey.getSelectedItem();
                String value = updateValue.getText();
                String updateReturnMessage = FSEC.mapPanelUpdateFromJpanel(modID, key, value);
                updateSuccessMessage.setText(updateReturnMessage);

            }
        });
        // Generate report of specific module in the database
        generateReport.addActionListener(new ActionListener() {
            /**
             * @param e Generate a report of the solar panel
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String modID = generateReportID.getText();
                SolarPanel panel = FSEC.findPanelByModuleID(modID);
                generateReportMessage.setText("This System is used to view details on modules.\n" +
                        "Here are some stats on the module you have selected...");
                generateReportMessage.setText(generateReportMessage.getText() + panel.toString());

            }
        });
        // Print all modules with their details
        printModules.addActionListener(new ActionListener() {
            /**
             * @param e Print the contents of the solar panel database
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                for (SolarPanel panel : FSEC.getItems())
                    printModuleView.setText(printModuleView.getText() + panel.toString());

            }
        });

        // Add filepath to database object
        addDatabase.addActionListener(new ActionListener() {
            /**
             * @param e Add a database to the program and at the filepath specified
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                FSEC.filepath = filePath.getText();
            }
        });

    }

    /**
     * @param args Main Program
     */
    public static void main(String[] args) {
        PathPrompt prompt = new PathPrompt();
    }
}