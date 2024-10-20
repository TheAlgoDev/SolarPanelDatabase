/*
Brent Thompson
Software Development 1 COP 3024C
Professor Ashley Evans
October 20th, 2024
Module 8 | DMS Project Phase 2: Software Testing

This SolarDatabaseClass is used to verify the functionality of the Solar Database class.
*/

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class SolarDatabaseTest {
    // Three new solar panel objects and a database instantiated to test with
    SolarPanel newPanel1;
    SolarPanel newPanel2;
    SolarPanel newPanel3;
    SolarDatabase database;
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        newPanel1 = new SolarPanel("Test101", "Ty993ss2","Molar", 8.1F, 6, 10);
        newPanel2 = new SolarPanel("Test102", "A344843u","Solar", 5.6F, 8, 10);
        newPanel3 = new SolarPanel("Test103", "455gshs2","Rolar", 3.9F, 6, 10);
        database = new SolarDatabase("Test");

    }
    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.DisplayName("Adding solar modules Test ")
    void AddPanelTest() {
        // Test adding a solar panel
        database.addPanel(newPanel1);
        database.addPanel(newPanel2);

        List<SolarPanel> items = database.getItems();
        assertEquals(2, items.size());
        assertEquals(newPanel1, items.get(0));
        assertEquals(newPanel2, items.get(1));
    }

    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.DisplayName("Removing Module by ID Test")
    void removePanelTest() {
        // Add panels to the database
        database.addPanel(newPanel1);
        database.addPanel(newPanel2);

        // Check initial size
        assertEquals(2, database.getItems().size());

        // Simulate removing panel1 by Module ID and Serial Number
        database.items.removeIf(panel -> panel.getModuleID().equals("Test101") && panel.getSerialNumber().equals("Ty993ss2"));

        // After removal, the size should be reduced
        assertEquals(1, database.getItems().size());
        assertFalse(database.getItems().contains(newPanel1), "Error: Panel was not removed");
        assertTrue(database.getItems().contains(newPanel2), "Error: Incorrect panel removed");
    }

    @org.junit.jupiter.api.Test
    @org.junit.jupiter.api.DisplayName("Find Module by ID Test")
    void findPanelByModuleIDTest() {
        // Remove the module that matches the ID and serial number provided
        database.addPanel(newPanel1);
        SolarPanel foundPanel = database.findPanelByModuleID("Test101");
        assertEquals(newPanel1, foundPanel, "Error: Panel was not found");
    }
}