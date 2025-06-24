package byteme.bytemeap4;
import java.io.*;
import java.util.*;

public class customer extends items implements college_canteen {
    String username;
    int password;
    String type; // R-Regular, VIP-Very Important Person
    HashMap<items, Integer> cart = new HashMap<>(); // Integer will contain the quantity for each item
    ArrayList<Order> order_history = new ArrayList<>();

    public customer(String username, int password, String type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public customer() {
    }

    @Override
    public void login() {
        System.out.println("Welcome " + username + ", you have logged in as a customer.");
    }

    public void add_to_cart() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter category to browse: ");
        String cat = scan.nextLine();
        brows_menu(cat);

        System.out.print("Select the item to add to cart: ");
        int select = scan.nextInt();
        scan.nextLine(); // Clear the buffer after nextInt()

        System.out.print("Select quantity: ");
        int q = scan.nextInt();
        scan.nextLine(); // Clear the buffer after nextInt()

        cart.put(menu.get(select - 1), q);
        saveCartToFile();
    }

    public void total_cost() {
        int cost = 0;
        for (Map.Entry<items, Integer> entry : cart.entrySet()) {
            items item = entry.getKey();
            int quantity = entry.getValue();
            cost += (item.getItem_cost()) * quantity;
        }
        System.out.println("Total cost of your cart items is " + cost);
    }

    public void view_cart() {
        int i = 0;
        for (Map.Entry<items, Integer> entry : cart.entrySet()) {
            items item = entry.getKey();
            int quantity = entry.getValue();
            System.out.println((i + 1) + "." + item.getItem_name() + " cost - " + item.getItem_cost() + " - Quantity: " + quantity);
            i++;
        }
    }
    public void cancel_order(items cancelItem) {
        if (cart.containsKey(cancelItem)) {
            cart.remove(cancelItem);
            System.out.println(cancelItem.getItem_name() + " has been removed from your cart.");
            saveCartToFile(); // Update cart file after cancellation
        } else {
            System.out.println("Item not found in cart.");
        }
    }


    public boolean checkout() {
        total_cost();
        System.out.print("Do you want to proceed (yes or no): ");
        Scanner scan = new Scanner(System.in);

        if (scan.hasNextLine()) {
            String choice = scan.nextLine();

            if (choice.equalsIgnoreCase("no")) {
                System.out.println("Keep visiting your cart :)");
                return false;
            } else if (choice.equalsIgnoreCase("yes")) {
                System.out.println("Pay the amount... Payment done.");
                return true;
            }
        }

        return false;
    }


    public void place_order() {
        String customerType = this.type; // Assuming this.type is 'VIP' or 'Regular'
        boolean val = checkout();
        if (val) {
            for (HashMap.Entry<items, Integer> entry : cart.entrySet()) {
                items item = entry.getKey();
                int quantity = entry.getValue();

                if (item.getAvalaibility() == 1) {
                    Order newOrder = new Order(item, quantity, customerType);
                    order_list.add(newOrder);
                    order_history.add(newOrder);
                    System.out.println("Your order has been placed successfully!");
                } else {
                    System.out.println(item.getItem_name() + " is not available.");
                }
            }
            saveOrderHistory(); // Save order history to file
            cart.clear();
            saveCartToFile(); // Clear cart file after placing the order
        }
    }

    public void search_and_order(String name) {
        boolean in_menu = false;
        for (items i : menu) {
            if (i.getItem_name().equals(name)) {
                in_menu = true;
                break;
            }
        }
        if (in_menu) {
            add_to_cart();
        } else {
            System.out.println("Item not in the menu. Try browsing the menu first...");
        }
    }

    public void veiw_order_history() {
        int i = 0;
        for (Order o : order_history) {
            o.setStatus("null");
            System.out.println((i + 1) + ". Order: " + o.item.getItem_name() +"cost"+o.item.getItem_cost()+ ", Status: " + o.getStatus());
            i++;
        }
    }

    public void reorder() {
        int i = 0;
        for (Order o : order_history) {
            o.setStatus("null");
            System.out.println((i + 1) + ". Order: " + o.toString() + ", Status: " + o.getStatus());
            i++;
        }
        System.out.print("Select which order to reorder: ");
        Scanner scan = new Scanner(System.in);
        int s = scan.nextInt();
        scan.nextLine();
        order_list.add(order_history.get(s - 1));
    }

    public void modify_cart_item() {
        // Display current items in the cart
        view_cart();
        Scanner scan = new Scanner(System.in);
        System.out.print("Which item to update (enter the item number): ");
        int s = scan.nextInt();
        scan.nextLine(); // Clear the newline left over

        System.out.print("Enter new quantity: ");
        int newQuantity = scan.nextInt();
        scan.nextLine(); // Consume newline

        // Check if the selected item number is valid
        if (s > 0 && s <= cart.size()) {
            // Get the item from the cart using the index (s - 1)
            items selectedItem = (items) cart.keySet().toArray()[s - 1];

            // Validate new quantity
            if (newQuantity >= 0) {
                // Update the quantity in the cart
                cart.put(selectedItem, newQuantity);
                System.out.println("Updated " + selectedItem.getItem_name() + " quantity to " + newQuantity);
            } else {
                System.out.println("Invalid quantity. Quantity must be non-negative.");
            }
        } else {
            System.out.println("Invalid item selection. Please try again.");
        }
        saveCartToFile(); // Update cart storage after modification
    }

    // File Handling for Cart
    private void saveCartToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(username + "_cart.txt"))) {
            for (Map.Entry<items, Integer> entry : cart.entrySet()) {
                items item = entry.getKey();
                int quantity = entry.getValue();
                writer.println(item.getItem_name() + "," + quantity + "," + item.getItem_cost());
            }
        } catch (IOException e) {
            System.out.println("Error saving cart: " + e.getMessage());
        }
    }

    // File Handling for Order History
    private void saveOrderHistory() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(username + "_order_history.txt", true))) {
            for (Order order : order_history) {
                items item = order.getItem();
                writer.println(item.getItem_name() + "," + order.getQuantity() + "," + item.getItem_cost() + "," + order.getCustomerType());
            }
        } catch (IOException e) {
            System.out.println("Error saving order history: " + e.getMessage());
        }
    }

    public static void brows_menu(String category) {
        if (menu.size() != 0) {
            System.out.println("This is the menu for selected category:");
            int i = 0;
            for (items x : menu) {
                if (!category.equals("all")) {
                    if (x.getCategory().equals(category)) {
                        System.out.println((i + 1) + ". Name-" + x.getItem_name() +
                                " Cost-" + x.getItem_cost() +
                                " Availability-" + x.getAvalaibility() +
                                " Category- " + x.getCategory());
                        i++;
                    }
                } else {
                    System.out.println((i + 1) + ". Name-" + x.getItem_name() +
                            " Cost-" + x.getItem_cost() +
                            " Availability-" + x.getAvalaibility() +
                            " Category- " + x.getCategory());
                    i++;
                }
            }
        } else {
            System.out.println("No items in the menu.");
        }
    }
}
