/*
Brent Thompson
Software Development 1 COP 3024C
Professor Ashley Evans
October 12th, 2024
Module 7 | DMS Project Phase 1: Logic and Input Validation

This Main Application uses Solar Panel Objects and Solar Databases to represent the processing of solar panels. A menu
allows the user to choose options that preform operations on the database. Basic CRUD functionality exists along with
custom report generation.
 */

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SolarDatabaseApp {
    public static void main(String[] args) throws IOException {

// Instantiate the Database that will be added to and interacted with
        SolarDatabase FSEC = new SolarDatabase("FSEC");
        Scanner scanner = new Scanner(System.in);

// While loop that uses choice to determine what the program does. Loops until user selects 0.
        int choice = 7;
        while (choice != 0) {
            System.out.println("\nMenu:");
            System.out.println("1. Add new modules");
            System.out.println("2. Remove a module");
            System.out.println("3. Update module information");
            System.out.println("4. Generate report from database");
            System.out.println("5. Print list of module in database");
            System.out.println("0. Exit the program\n ");
            System.out.print("Enter your choice: ");
// Consume any choice values that will not work with if else structure
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException ie) {
                scanner.nextLine();
            }
// Adding new modules, with two sub options to chose from. Manual Add and Batch Add
            if (choice == 1) {
                System.out.println("Adding new modules, will this be a file upload or manual input?");
                System.out.println("1. Add an individual module");
                System.out.println("2. Bulk add with text file");
                choice = scanner.nextInt();
// Manually add a module, prompting the user for each attribute.
                if (choice == 1) {
                    System.out.println("Adding an individual module to the database");
                    System.out.println("Please enter the information for the module: ");
                    SolarPanel panel = SolarPanel.createModuleFromInput();
                    FSEC.addPanel(panel);
                    System.out.println("Added module to the database");
                }
// Import text file and batch process modules.
                else if (choice == 2) {
                    System.out.println("Bulk adding modules from text file");
                    System.out.println("Enter with format 'ModuleID,SerialNumber,Make,VOC,NumberCellsX,NumberCellsY'");
                    System.out.print("Enter the filepath of the text file to import:");
                    List<SolarPanel> panels = SolarDatabase.importPanelList();
                    for (SolarPanel panel : panels) {
                        FSEC.addPanel(panel);
                    }
                }
            }
// Removes a module based off the module ID and the serial number.
            else if (choice == 2) {
                System.out.println("Removing a module from the database");
                System.out.println("Please enter the information for the module: ");
                //FSEC.removePanel();
            }
// Starts an update process that uses module ID to update a value associated to it.
            else if (choice == 3) {
                System.out.println("Updating module information");
                FSEC.mapPanelUpdate();
            }
// Generates a report that includes calculated values on each module stored in the database.
            else if (choice == 4) {
                System.out.println("Generating report from database");
                FSEC.generateReport();
            }
// Prints a simpler summary of the modules currently in the database.
            else if (choice == 5) {
                System.out.println("Printing list of module in database");
                for (SolarPanel panel : FSEC.getItems()) {
                    System.out.println(panel.toString());
                }
            }
// Option that allows the user to exit the program from the menu
            else if (choice == 0) {
                System.out.println("User has chosen to exit the program");
                break; // Exit the loop
            }
            else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
        System.out.println("Exiting...");
        scanner.close();
    }
}