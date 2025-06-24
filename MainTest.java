package byteme.bytemeap4;

import static org.junit.jupiter.api.Assertions.*;



import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class MainTest {

    static admin adminUser;
    static customer customer1;
    static customer customer2;
    static customer customer3;
    static items beverage1;
    static items beverage2;
    static items snack1;
    static items snack2;
    static items meal1;
    static items meal2;


    @BeforeEach
    void setUp() {
        // Setup dummy objects for testing
        adminUser = new admin("adminUser", 1234);
        customer1 = new customer("Alice", 1111, "Regular");
        customer2 = new customer("Bob", 2222, "VIP");
        customer3 = new customer("Charlie", 3333, "VIP");
        beverage1 = new items("Coke", 30, 1, "beverages");
        beverage2 = new items("Pepsi", 30, 0, "beverages");// Available
        snack1 = new items("Chips", 20, 0, "snacks");
        snack2 = new items("Sandwich", 50, 0, "snacks");// Out of stock
        meal1 = new items("Burger", 100, 1, "meal");
        meal2 = new items("Pasta", 150, 1, "meal");// Available

        adminUser.add_item(beverage1);
        adminUser.add_item(snack1);
        adminUser.add_item(meal1);
        adminUser.add_item(beverage2);
        adminUser.add_item(snack2);
        adminUser.add_item(meal2);
    }

    // Test for invalid login attempts (Customer)
    @Test
    void testInvalidCustomerLogin() {

        customer1.login();
        customer2.login();
        customer3.login();

        assertEquals("Alice", customer1.username, "Wrong username , Customer username should be Alice");
        assertEquals("Bob", customer2.username, "Wrong username , Customer username should be Bob");
        assertEquals("Charlie", customer3.username, "Wrong username , customer username should be Charlie ");
    }

    // Test for invalid login attempts (Admin)
    @Test
    void testInvalidAdminLogin() {
        // Simulate incorrect login credentials
        adminUser.login();  // Login should be successful based on internal state
        assertEquals("adminUser", adminUser.username, "Admin username should be adminUser");
    }

    // Test ordering out-of-stock item (Customer)
    @Test
    void testOrderOutOfStockItem() {
        String simulatedInput = "snacks\n1\n2\nyes\n"; // Simulated user input
        InputStream originalIn = System.in; // Save original System.in

        try {
            // Redirect System.in to the simulated input
            ByteArrayInputStream testInput = new ByteArrayInputStream(simulatedInput.getBytes());
            System.setIn(testInput);

            // Execute the methods
            customer1.add_to_cart();
            customer1.place_order();

            // Assertions
            assertTrue(customer1.order_history.isEmpty(), "Order history should be empty for out-of-stock items.");
        } finally {
            // Restore original System.in
            System.setIn(originalIn);
        }
    }


}