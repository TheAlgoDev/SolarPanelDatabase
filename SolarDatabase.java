/*
Brent Thompson
Software Development 1 COP 3024C
Professor Ashley Evans
October 12th, 2024
Module 7 | DMS Project Phase 1: Logic and Input Validation

This Solar Database class is an abstraction of a database that holds solar panel objects. Key functionality includes
adding new modules by batch or individually, removing modules, and generating reports on data.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// A Solar Database holds Solar Panel objects as a list
public class SolarDatabase {
    public String name;
    public List<SolarPanel> items;
    public int size;

// New SolarDatabase Objects start with size zero and only require a name
    public SolarDatabase(String name) {
        int size = 0;
        this.name = name;
        this.items = new ArrayList<>();
    }

// Allows user to import a text file, reads file line by line
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

// Return all items in the database
    public List<SolarPanel> getItems() {
        return items;
    }

// Add a panel to the database
    public void addPanel(SolarPanel panel) {
        items.add(panel);
        this.size += 1;
    }

// Remove a panel from the database based on two attributes, module_ID and serial number
    public void removePanel() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Module ID of the panel: ");
        String moduleID = scanner.nextLine();
        System.out.println("Enter the Serial Number of the panel: ");
        String serialNumber = scanner.nextLine();

// Check if ModuleID and Serial Number match, and determine print message from result
        boolean removed = items.removeIf(panel -> panel.getModuleID().equals(moduleID) &&
                panel.getSerialNumber().equals(serialNumber));

        if (removed) {
            System.out.println("Module with ID " + moduleID + " has been removed.");
            this.size -= 1;
        } else {
            System.out.println("No module with ID " + moduleID + " is in the list.");
        }

    }
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
            String fieldName = scanner.nextLine();

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

// Method to find the panel by Module ID
    public SolarPanel findPanelByModuleID(String moduleID) {
        for (SolarPanel panel : items) {
            if (panel.getModuleID().equals(moduleID)) {
                return panel;
            }
        }
        return null;
    }
// Generate a custom report that calculates values and displays module information
    public void generateReport() {
        System.out.println("Solar Panel Report for Database: " + name);
        System.out.println("------------------------------------");

        if (items.isEmpty()) {
            System.out.println("No solar panels have been added to the database yet.");
        } else {
            for (SolarPanel panel : items) {
                System.out.println("Module ID: " + panel.getModuleID());
                System.out.println("Serial Number: " + panel.getSerialNumber());
                System.out.println("Make: " + panel.getMake());
                System.out.println("VOC: " + panel.getVOC());
                System.out.println("Number of Cells (X): " + panel.getNumberCellsX());
                System.out.println("Number of Cells (Y): " + panel.getNumberCellsY());
                System.out.println("Total Number of Cells: " + panel.calculateNumberCells());
                System.out.println("Power Produced per Cell:" + panel.calculatePowerPerCell());
                System.out.println("------------------------------------");
            }
        }
    }
}
