/*
Brent Thompson
CEN 3024C 15339 Software Development 1
Professor Ashley Evans
November 12th, 2024

Module 10 - Integrate Database
This Solar Database class is an abstraction of a database that interfaces with a sqlite database. Key functionality
includes adding new modules by batch or individually, removing modules, and generating reports on data.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Brent Thompson
 * @version 1.0
 */
// A Solar Database holds Solar Panel objects as a list
public class SolarDatabase {
    public String filepath;
    private Connection conn;
    //public int size;

    /**
     * @param filepath is the location of the sqlite database on disk
     */
// New SolarDatabase creates database if one does not exist already
    public SolarDatabase(String filepath) {
        //int size = getSize();
        filepath = filepath.replace("\\", "/");
        filepath = filepath.replace("\"", "");
        this.filepath = filepath;
        try {

            conn = DriverManager.getConnection("jdbc:sqlite:" + filepath);
            String createTableSQL = "CREATE TABLE IF NOT EXISTS Solar_Database ("
                    + "moduleID TEXT PRIMARY KEY,"
                    + "serialNumber TEXT,"
                    + "make TEXT,"
                    + "voc REAL,"
                    + "numberCellsX INTEGER,"
                    + "numberCellsY INTEGER"
                    + ");";
            Statement stmt = conn.createStatement();
            stmt.execute(createTableSQL);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @deprecated
     * @return Imports solar panel objects from the console
     * @throws IOException when a non-valid entry is entered
     */
// Allows user to import a text file, reads file line by line and imports panel to database
    public static List<SolarPanel> importPanelList() throws IOException {
        Scanner scanner = new Scanner(System.in);
        List<SolarPanel> importedPanels = new ArrayList<>();
        String filepath = scanner.nextLine();
        filepath = filepath.replace("\\", "/");
        filepath = filepath.replace("\"", "");
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 6) { // Ensure correct number of columns
                    try {
                        String moduleID = data[0];
                        String serialNumber = data[1];
                        String make = data[2];
                        float voc = Float.parseFloat(data[3]);
                        int numberCellsX = Integer.parseInt(data[4]);
                        int numberCellsY = Integer.parseInt(data[5]);
                        // Instantiate new SolarPanel and add to list of panels
                        SolarPanel panel = new SolarPanel(moduleID, serialNumber, make, voc, numberCellsX, numberCellsY);
                        System.out.println(panel);
                        importedPanels.add(panel);
                    } catch (NumberFormatException e) {
                        System.err.println("Error with line: " + line);
                    }
                }
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("Error with file: " + filepath);
        }
        return importedPanels;
    }

    /**
     * @param filepath location of the sqlite database
     * @return list of solar panel objects that will be added to the database
     * @throws IOException
     */
// Import a csv using the main menu, woks the same as import panel list
    public static List<SolarPanel> importPanelListFromJpanel(String filepath) throws IOException {
        List<SolarPanel> importedPanels = new ArrayList<>();
        filepath = filepath.replace("\\", "/");
        filepath = filepath.replace("\"", "");
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 6) { // Ensure correct number of columns
                    try {
                        String moduleID = data[0];
                        String serialNumber = data[1];
                        String make = data[2];
                        float voc = Float.parseFloat(data[3]);
                        int numberCellsX = Integer.parseInt(data[4]);
                        int numberCellsY = Integer.parseInt(data[5]);
                        // Instantiate new SolarPanel and add to list of panels
                        SolarPanel panel = new SolarPanel(moduleID, serialNumber, make, voc, numberCellsX, numberCellsY);
                        System.out.println(panel);
                        importedPanels.add(panel);
                    } catch (NumberFormatException e) {
                        System.err.println("Error with line: " + line);
                    }

                }
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("Error with file: " + filepath);
        }

        return importedPanels;
    }

    /**
     * @return Counts the number of rows in the solar panel database
     */
    // Uses count to get the size of the database
    public int getSize() {
        String countSQL = "SELECT COUNT(*) AS total FROM Solar_Database;";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(countSQL);
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * @param panel Solar panel object
     */
// Add a panel to the database
    public void addPanel(SolarPanel panel) {
        String insertSQL = "INSERT INTO Solar_Database (moduleID, serialNumber, make, voc, numberCellsX, numberCellsY) "
                + "VALUES (?, ?, ?, ?, ?, ?);";
        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, panel.getModuleID());
            pstmt.setString(2, panel.getSerialNumber());
            pstmt.setString(3, panel.getMake());
            pstmt.setFloat(4, panel.getVOC());
            pstmt.setInt(5, panel.getNumberCellsX());
            pstmt.setInt(6, panel.getNumberCellsY());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

// Remove a panel from the database based on two attributes, module_ID and serial number

    /**
     * @deprecated
     */
    public void removePanel() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Module ID of the panel: ");
        String moduleID = scanner.nextLine();
        System.out.println("Enter the Serial Number of the panel: ");
        String serialNumber = scanner.nextLine();

    }

    /**
     * @param moduleID Unique ID of a solar panel
     * @param serialNumber Serial number of the module, used to track batches
     * @return
     */
    public String removePanelWithJpanel(String moduleID, String serialNumber) {
        String deleteSQL = "DELETE FROM Solar_Database WHERE moduleID = ? AND serialNumber = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
            pstmt.setString(1, moduleID);
            pstmt.setString(2, serialNumber);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                return "Module with ID " + moduleID + " has been removed.";
            } else {
                return "No module with ID " + moduleID + " and Serial Number " + serialNumber + " is in the database.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error occurred while removing the module.";
        }
    }

    /**
     * @deprecated
     */
// Use an abbreviated name to select attribute for updating, select module using moduleID
    public void mapPanelUpdate() {
        Scanner scanner = new Scanner(System.in);

        // Ask for the Module ID
        System.out.println("Enter the Module ID of the panel: ");
        String moduleID = scanner.nextLine();

// Find the panel with the matching Module ID
        SolarPanel panelToUpdate = findPanelByModuleID(moduleID);

        if (panelToUpdate != null) {
            System.out.println("Panel found. Enter the shortened name of the field to update:");
            System.out.println("Serial Number(S), Make(M), VOC(V), Cells X(X), Cells Y(Y)");
            String fieldName = scanner.nextLine().toUpperCase();

// Update the chosen field based on the user input using setters of Solar Panel
            switch (fieldName) {
                case "S":
                    System.out.println("Enter the new Serial Number: ");
                    String newSerialNumber = scanner.nextLine();
                    panelToUpdate.setSerialNumber(newSerialNumber);
                    break;
                case "M":
                    System.out.println("Enter the new Make: ");
                    String newMake = scanner.nextLine();
                    panelToUpdate.setMake(newMake);
                    break;
                case "V":
                    System.out.println("Enter the new VOC (Float): ");
                    try {
                        Float newVOC = Float.parseFloat(scanner.nextLine());
                        panelToUpdate.setVOC(newVOC);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid float value.");
                    }
                    break;
                case "X":
                    System.out.println("Enter the new number of cells in the X direction: ");
                    try {
                        int newCellsX = Integer.parseInt(scanner.nextLine());
                        panelToUpdate.setNumberCellsX(newCellsX);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid integer.");
                    }
                    break;
                case "Y":
                    System.out.println("Enter the new number of cells in the Y direction: ");
                    try {
                        int newCellsY = Integer.parseInt(scanner.nextLine());
                        panelToUpdate.setNumberCellsY(newCellsY);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid integer.");
                    }
                    break;
                default:
                    System.out.println("Invalid field name. Please enter a valid option.");
                    break;
            }

            System.out.println("Update complete.");
        } else {
            System.out.println("Panel with Module ID " + moduleID + " not found.");
        }
    }

    /**
     * @param moduleID Unique ID of a solar panel
     * @param updateKey Which column in the database to update
     * @param updateValue What update value to add to the column
     * @return New entry in the database
     */
// Map update using values sent in from Jpanel
    public String mapPanelUpdateFromJpanel(String moduleID, String updateKey, String updateValue) {
        String column = "";
        switch (updateKey) {
            case "Serial Number":
                column = "serialNumber";
                break;
            case "Make":
                column = "make";
                break;
            case "Voltage Open Current":
                column = "voc";
                break;
            case "Number Cells X":
                column = "numberCellsX";
                break;
            case "Number Cells Y":
                column = "numberCellsY";
                break;
            default:
                return "Invalid field name. Please enter a valid option.";
        }

        String updateSQL = "UPDATE Solar_Database SET " + column + " = ? WHERE moduleID = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
            if (column.equals("voc")) {
                try {
                    float newVOC = Float.parseFloat(updateValue);
                    pstmt.setFloat(1, newVOC);
                } catch (NumberFormatException e) {
                    return "Invalid input. Please enter a valid float value.";
                }
            } else if (column.equals("numberCellsX") || column.equals("numberCellsY")) {
                try {
                    int newValue = Integer.parseInt(updateValue);
                    pstmt.setInt(1, newValue);
                } catch (NumberFormatException e) {
                    return "Invalid input. Please enter a valid integer.";
                }
            } else {
                pstmt.setString(1, updateValue);
            }
            pstmt.setString(2, moduleID);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                return updateKey + " Updated";
            } else {
                return "Panel with Module ID " + moduleID + " not found.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error occurred while updating the module.";
        }
    }

    /**
     * @param moduleID Unique ID of a solar panel
     * @return Solar panel object with the corresponding module ID
     */
// Use moduleID to find panel in database
    public SolarPanel findPanelByModuleID(String moduleID) {
        String selectSQL = "SELECT * FROM Solar_Database WHERE moduleID = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            pstmt.setString(1, moduleID);
            ResultSet results = pstmt.executeQuery();
            if (results.next()) {
                String serialNumber = results.getString("serialNumber");
                String make = results.getString("make");
                float voc = results.getFloat("voc");
                int numberCellsX = results.getInt("numberCellsX");
                int numberCellsY = results.getInt("numberCellsY");
                SolarPanel panel = new SolarPanel(moduleID, serialNumber, make, voc, numberCellsX, numberCellsY);
                return panel;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Return all items in the database

    /**
     * @return all solar panels from the database
     */
    public List<SolarPanel> getItems() {
        List<SolarPanel> panels = new ArrayList<>();
        String selectSQL = "SELECT * FROM Solar_Database;";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(selectSQL);
            while (rs.next()) {
                String moduleID = rs.getString("moduleID");
                String serialNumber = rs.getString("serialNumber");
                String make = rs.getString("make");
                float voc = rs.getFloat("voc");
                int numberCellsX = rs.getInt("numberCellsX");
                int numberCellsY = rs.getInt("numberCellsY");
                SolarPanel panel = new SolarPanel(moduleID, serialNumber, make, voc, numberCellsX, numberCellsY);
                panels.add(panel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return panels;
    }

    /**
     * @deprecated
     */
// Generate a custom report that calculates values and displays module information
public void generateReport() {
    System.out.println("Solar Panel Report for Database: " + filepath);
    System.out.println("------------------------------------");

    List<SolarPanel> panels = getItems();
    if (panels.isEmpty()) {
        System.out.println("No solar panels have been added to the database yet.");
    } else {
        for (SolarPanel panel : panels) {
            System.out.println("Module ID: " + panel.getModuleID());
            System.out.println("Serial Number: " + panel.getSerialNumber());
            System.out.println("Make: " + panel.getMake());
            System.out.println("VOC: " + panel.getVOC());
            System.out.println("Number of Cells (X): " + panel.getNumberCellsX());
            System.out.println("Number of Cells (Y): " + panel.getNumberCellsY());
            System.out.println("Total Number of Cells: " + panel.calculateNumberCells());
            System.out.println("Power Produced per Cell: " + panel.calculatePowerPerCell());
            System.out.println("------------------------------------");
        }
    }
}

    /**
     * @return Overridden toString method
     */
    @Override
    public String toString() {
        return "SolarDatabase{" +
                "name='" + filepath + '\'' +
                ", size=" + getSize() +
                '}';
    }
}
