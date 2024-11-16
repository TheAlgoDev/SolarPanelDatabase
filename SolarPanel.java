/*
Brent Thompson
CEN 3024C 15339 Software Development 1
Professor Ashley Evans
November 12th, 2024

Module 10 - Integrate Database

The Solar Panel Class is an abstraction of a solar panel module, with functionality to update any field of the panels.
 */


import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Solar Panel class to represent a solar panel
 * @author Brent Thompson
 * @version 1.0
 */
// A few key characteristics of a solar panel like ID, serial-number, and number of cells.
public class SolarPanel {
    public String ModuleID;
    public String SerialNumber;
    public String Make;
    public Float VOC;
    public int NumberCellsX;
    public int NumberCellsY;

    /**
     * @param moduleID is a unique identifier for solar panels
     * @param serialNumber unique batch record to track makes
     * @param make brand or producer of solar panel
     * @param VOC Voltage open current is a common nameplate value
     * @param numberCellsX the number of cells along the shortest side of the module
     * @param numberCellsY the number of cells along the longest side of the module
     */
// Constructor to create new Solar Panel object
    public SolarPanel(String moduleID, String serialNumber, String make, float VOC, int numberCellsX, int numberCellsY) {
        this.ModuleID = moduleID;
        this.SerialNumber = serialNumber;
        this.Make = make;
        this.VOC = VOC;
        this.NumberCellsX = numberCellsX;
        this.NumberCellsY = numberCellsY;
    }

// Setters and Getters that allow the user to update values or retrieve them
    public String getModuleID() {return ModuleID;}

    public void setModuleID(String moduleID) {
        this.ModuleID = moduleID;
    }

    public String getSerialNumber() {
        return SerialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.SerialNumber = serialNumber;
    }

    public String getMake() {
        return Make;
    }

    public void setMake(String make) {
        this.Make = make;
    }

    public Float getVOC() {
        return VOC;
    }

    public void setVOC(Float VOC) {
        this.VOC = VOC;
    }

    public int getNumberCellsX() {
        return NumberCellsX;
    }

    public void setNumberCellsX(int numberCellsX) {
        this.NumberCellsX = numberCellsX;
    }

    public int getNumberCellsY() {
        return NumberCellsY;
    }

    public void setNumberCellsY(int numberCellsY) {
        this.NumberCellsY = numberCellsY;
    }

    /**
     * @return Calculates the number of cells by multipyling x and y
     */
// Custom methods to get total cell counts and per cell performance
    public int calculateNumberCells() {
        int total = getNumberCellsX() * getNumberCellsY();
        return total;}

    /**
     * @return Divides the voltage open current by number of cells to get a per cell value
     */
    public float calculatePowerPerCell() {
        float powerPerCell = (this.VOC / calculateNumberCells());
        return powerPerCell;
    }

    /**
     * @deprecated
     * @return Consule prototype of the solar panel database
     */
// Method that allows user to input values of a panel manually
    public static SolarPanel createModuleFromInput() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter module ID: ");
        String moduleID = scanner.nextLine();

        System.out.print("Enter serial number: ");
        String serialNumber = scanner.nextLine();

        System.out.print("Enter make: ");
        String make = scanner.nextLine();

        System.out.print("Enter VOC: ");

// Try catch blocks are required for the numeric entries
        float voc = 0.0F;
        try {voc = scanner.nextFloat();}
        catch (InputMismatchException ie) {
            System.out.println("Invalid Entry. Default set.");
            scanner.nextLine();;
        }
        System.out.print("Enter number of cells in X direction: ");
        int numberCellsX = 0;
        try {numberCellsX = scanner.nextInt();}
        catch (InputMismatchException ie) {
            System.out.println("Invalid Entry. Default set.");
            scanner.nextLine();;
        }
        System.out.print("Enter number of cells in Y direction: ");
        int numberCellsY = 0;
        try {numberCellsY = scanner.nextInt();}
        catch (InputMismatchException ie) {
            System.out.println("Invalid Entry. Default set.");
            scanner.nextLine();;
        }
// Instantiate a new SolarPanel object
        SolarPanel newPanel = new SolarPanel(moduleID, serialNumber, make, voc, numberCellsX, numberCellsY);
        return newPanel;
    }

    /**
     * @return Overridden to string method to print out details of module
     */
// Overridden toString method to highlight key details of a module
    @Override
    public String toString() {
        return "\n"+ "Module_ID = '" + ModuleID + '\'' +
                ", Serial Number = '" + SerialNumber + '\'' +
                ", Make = '" + Make + '\'' +
                ", VOC = " + VOC +
                ", Total Number of Cells = " + calculateNumberCells() +
                ", Power Produced per Cell = " + calculatePowerPerCell()
                ;
    }
}
