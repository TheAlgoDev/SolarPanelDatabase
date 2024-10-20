/*
Brent Thompson
Software Development 1 COP 3024C
Professor Ashley Evans
October 20th, 2024
Module 8 | DMS Project Phase 2: Software Testing

This SolarPanelTest Class is used to test the functionality of the Solar Panel class
*/

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SolarPanelTest {

    SolarPanel newPanel;
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        // New solar panel object used for testing
        newPanel = new SolarPanel("Test101", "F443ss2","Solar", 8.1F, 6, 10);
    }

    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.DisplayName("Calculate Number of Cells Test")
    void calculateNumberCellsTest() {
        // Using our own test data, calculate the number of cells
        int NumberCells = newPanel.getNumberCellsX() * newPanel.getNumberCellsY();
        assertEquals(60, NumberCells, "Error. Number of Cells is incorrect");
    }

    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.DisplayName("Calculate Power per Cell Test")
    void calculatePowerPerCellTest() {
        // Calculate the power for each cell
        int NumberCells = newPanel.getNumberCellsX() * newPanel.getNumberCellsY();
        assertEquals(0.135F, 8.1F/NumberCells, "Error. Power per Cell is incorrect");
    }
}