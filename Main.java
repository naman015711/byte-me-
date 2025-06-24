package byteme.bytemeap4;
import javafx.application.Application;


import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main extends Application {

    static admin adminUser = new admin("adminUser", 1234);
    static customer customer1 = new customer("Alice", 1111, "Regular");
    static customer customer2 = new customer("Bob", 2222, "VIP");
    static customer customer3 = new customer("Charlie", 3333, "VIP");

    static items beverage1 = new items("Coke", 30, 1, "beverages");
    static items snack1 = new items("Chips", 20, 1, "snacks");
    static items meal1 = new items("Burger", 100, 1, "meal");
    static items beverage2 = new items("Pepsi", 30, 0, "beverages");
    static items snack2 = new items("Sandwich", 50, 0, "snacks");
    static items meal2 = new items("Pasta", 150, 1, "meal");

    static ArrayList<Order> orders = new ArrayList<>();

    public static void main(String[] args) {
        // Run tests before starting the main menu flow
        runTests();

        adminUser.add_item(beverage1);
        adminUser.add_item(snack1);
        adminUser.add_item(meal1);
        adminUser.add_item(beverage2);
        adminUser.add_item(snack2);
        adminUser.add_item(meal2);

        Scanner scan = new Scanner(System.in);
        boolean exit = false;

        System.out.println("Welcome to the College Canteen System!");

        while (!exit) {
            System.out.println("\nPlease select an option:");
            System.out.println("1. Login");
            System.out.println("2. Exit");

            int choice = scan.nextInt();
            scan.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    loginUser(); // Centralized login handling
                    break;

                case 2:
                    exit = true;
                    System.out.println("Thank you for using the College Canteen System. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
        scan.close();
    }

    static void loginUser() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Are you (1) Customer or (2) Admin?");
        int userType = scan.nextInt();
        scan.nextLine(); // Consume newline

        System.out.print("Enter username: ");
        String username = scan.nextLine();
        System.out.print("Enter password: ");
        int password = scan.nextInt();

        if (userType == 1) {
            customer cust = findCustomer(username, password);
            if (cust != null) {
                System.out.println("Welcome " + cust.username + "!");
                customerMenu(cust);
            } else {
                System.out.println("Invalid customer credentials.");
            }
        } else if (userType == 2) {
            if (username.equals(adminUser.username) && password == adminUser.password) {
                System.out.println("Welcome Admin!");
                adminMenu(adminUser);
            } else {
                System.out.println("Invalid admin credentials.");
            }
        } else {
            System.out.println("Invalid selection.");
        }
    }

    static customer findCustomer(String username, int password) {
        for (customer cust : Arrays.asList(customer1, customer2, customer3)) {
            if (cust.username.equals(username) && cust.password == password) {
                return cust;
            }
        }
        return null;
    }

    private static void customerMenu(customer cust) {
        Scanner scan = new Scanner(System.in);
        boolean back = false;

        while (!back) {
            System.out.println("\nCustomer Menu:");
            System.out.println("1. Browse Menu");
            System.out.println("2. Add to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Place Order");
            System.out.println("5. Cancel Order");
            System.out.println("6. Re-order ");
            System.out.println("7. View Order History ");
            System.out.println("8. Modify Cart Item Quantity");
            System.out.println("9. Search Item by Name ");
            System.out.println("10. Logout");

            int choice = scan.nextInt();
            scan.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Launch GUI to browse menu
                    Platform.runLater(() -> browseMenuGUI());
                    break;

                case 2:
                    cust.add_to_cart();
                    break;

                case 3:
                    cust.view_cart();
                    break;

                case 4:
                    cust.place_order();
                    break;

                case 5:
                    System.out.print("Enter the item name to cancel: ");
                    String cancelItemName = scan.nextLine();
                    items cancelItem = findItemByName(cancelItemName);
                    if (cancelItem != null) {
                        cust.cancel_order(cancelItem);
                    } else {
                        System.out.println("Item not found in cart.");
                    }
                    break;

                case 6:
                    cust.reorder();
                    break;

                case 7:
                    cust.veiw_order_history();
                    break;

                case 8:
                    System.out.print("Enter new quantity: ");
                    int newQuantity = scan.nextInt();
                    scan.nextLine();
                    cust.modify_cart_item();
                    break;

                case 9:
                    System.out.print("Enter item name to search: \n");
                    String name = scan.nextLine();
                    cust.search_and_order(name);
                    break;

                case 10:
                    back = true;
                    System.out.println("Logging out...");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private static void adminMenu(admin adm) {
        Scanner scan = new Scanner(System.in);
        boolean back = false;

        while (!back) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. View Orders");
            System.out.println("2. Update Order Status");
            System.out.println("3. Add Item to Menu");
            System.out.println("4. Update Item in Menu");
            System.out.println("5. Remove Item from Menu");
            System.out.println("6. Generate Report");
            System.out.println("7. Logout");

            int choice = scan.nextInt();
            scan.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Launch GUI to view orders
                    adm.view_order();
                    Platform.runLater(() -> viewOrdersGUI());
                    break;

                case 2:
                    adm.updateOrderStatus();
                    break;

                case 3:
                    System.out.print("Enter item name: ");
                    String itemName = scan.nextLine();
                    System.out.print("Enter item cost: ");
                    int itemCost = scan.nextInt();
                    System.out.print("Enter item availability (1 for available, 0 for unavailable): ");
                    int availability = scan.nextInt();
                    scan.nextLine(); // Consume newline
                    System.out.print("Enter item category: ");
                    String itemCategory = scan.nextLine();
                    items newItem = new items(itemName, itemCost, availability, itemCategory);
                    adm.add_item(newItem);
                    break;

                case 4:
                    adm.update_item();
                    break;

                case 5:
                    System.out.print("Enter item name to remove: ");
                    String removeItemName = scan.nextLine();
                    items removeItem = findItemByName(removeItemName);
                    if (removeItem != null) {
                        adm.item_remove(removeItem);
                    } else {
                        System.out.println("Item not found in menu.");
                    }
                    break;

                case 6:
                    adm.generate_report();
                    break;

                case 7:
                    back = true;
                    System.out.println("Logging out...");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private static void browseMenuGUI() {
        Stage stage = new Stage();
        VBox vbox = new VBox();

        ObservableList<String> menuItems = FXCollections.observableArrayList();
        for (items item : adminUser.menu) {
            menuItems.add(item.getItem_name() + " - Price: " + item.getItem_cost() + " (Category: " + item.getCategory() + ")");
        }

        ListView<String> listView = new ListView<>(menuItems);
        vbox.getChildren().add(listView);

        Scene scene = new Scene(vbox, 400, 400);
        stage.setScene(scene);
        stage.setTitle("Browse Menu");
        stage.show();
    }

    private static void viewOrdersGUI() {
        Stage stage = new Stage();
        VBox vbox = new VBox();

        ObservableList<String> orderItems = FXCollections.observableArrayList();
        for (Order order : items.order_list) {
            orderItems.add(order.item.getItem_name()+","+order.item.getItem_cost()+","+order.getQuantity());
        }

        ListView<String> listView = new ListView<>(orderItems);
        vbox.getChildren().add(listView);

        Scene scene = new Scene(vbox, 400, 400);
        stage.setScene(scene);
        stage.setTitle("View Orders");
        stage.show();
    }

    // Helper method to find an item by name
    private static items findItemByName(String itemName) {
        for (items item : adminUser.menu) {
            if (item.getItem_name().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    // Mock test method to run tests
    private static void runTests() {
        System.out.println("Running tests...");
        // Add test code if necessary
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
