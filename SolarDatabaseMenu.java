import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

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

    public SolarDatabaseMenu(String filename) {
        setContentPane(Menu);
        setTitle("Solar Database Menu");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        SolarDatabase FSEC = new SolarDatabase(filename);


        btnClear.addActionListener(new ActionListener() {
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
        exitProgramButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        addIndividualModule.addActionListener(new ActionListener() {
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
        importFromCSV.addActionListener(new ActionListener() {
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
        removeModule.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String modID = removeModID.getText();
                String serialNumber = removeSerialNumber.getText();
                String returnMessage = FSEC.removePanelWithJpanel(modID, serialNumber);
                removeModuleSuccess.setText(returnMessage);
            }
        });
        updateModule.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String modID = updateModID.getText();
                String key = (String) updateKey.getSelectedItem();
                String value = updateValue.getText();
                String updateReturnMessage = FSEC.mapPanelUpdateFromJpanel(modID, key, value);
                updateSuccessMessage.setText(updateReturnMessage);

            }
        });
        generateReport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String modID = generateReportID.getText();
                SolarPanel panel = FSEC.findPanelByModuleID(modID);
                generateReportMessage.setText("This System is used to view details on modules.\n" +
                        "Here are some stats on the module you have selected...");
                generateReportMessage.setText(generateReportMessage.getText() + panel.toString());

            }
        });

        printModules.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (SolarPanel panel : FSEC.getItems())
                    printModuleView.setText(printModuleView.getText() + panel.toString());

            }
        });


        addDatabase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FSEC.filepath = filePath.getText();
            }
        });

    }
    public static void main(String[] args) {
        PathPrompt prompt = new PathPrompt();
    }
}