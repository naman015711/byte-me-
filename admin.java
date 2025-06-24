package byteme.bytemeap4;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.*;
public class admin extends customer implements college_canteen {
    String username;
    int password;
    int total_sales;
    public admin(String username , int password ){
        this.password=password;
        this.username=username;
    }

    @Override
    public void login() {
        System.out.print("welcome " + username + " you have loged in as admin");
    }

    //managing menu:-
    public void add_item(items item){

        add_item_to_menu(item);
        menu.sort(new costcomparator());
    }
    public void update_item() {
        brows_menu("all");

        Scanner scan = new Scanner(System.in);
        System.out.print("Select item to update: \n");
        int s = scan.nextInt();
        scan.nextLine();  // Clear the newline left over by nextInt()

        items item = menu.get(s - 1);

        System.out.print("What do you want to update? i-item name, c for item cost, a for availability: \n");
        String choice = scan.nextLine();  // Now it will properly wait for input
        if (choice.equals("c")) {
            System.out.print("Enter the updated cost: ");
            int updated_cost = scan.nextInt();
            item.setItem_cost(updated_cost);

        } else if (choice.equals("i")) {
            System.out.print("Enter the updated name: ");
            String updated_name = scan.nextLine();
            item.setItem_name(updated_name);

        } else if (choice.equals("a")) {
            System.out.print("Enter the updated availability (only 0 and 1): ");
            int updated_status = scan.nextInt();
            item.setAvalaibility(updated_status);
        }
    }


    public void item_remove(items item ){
            int count=0;
            for (items x : menu) {
                count++;
                if (x.getItem_name()==item.getItem_name()){
                    remove_item(item);
                    break;
                }
            }
            if(count==menu.size()-1){
                System.out.print("item dosent exist in the menu, try adding it to the menu first ");
            }
    }
    public void view_order() {
        // Sort orders by customer type (VIP first)
        order_list.sort((order1, order2) -> {
            boolean isVip1 = order1.getCustomerType().equals("VIP");
            boolean isVip2 = order2.getCustomerType().equals("VIP");
            return Boolean.compare(isVip2, isVip1); // VIP customers first
        });

        // Display the orders with status
        int i = 0 ;
        for (Order order : order_list) {
            System.out.println(i+1 + ".Order: " + order.item.getItem_name()+" cost - " + order.item.getItem_cost() + ", Status: " + order.getStatus());
            i++;
        }
    }
    public void generate_report(){
        System.out.print(" today's(current) sales are " + total_sales + "rupees");
    }
    public void updateOrderStatus() {
        view_order(); // Display current orders with status

        if (order_list.isEmpty()) {
            System.out.println("No orders available to update.");
            return;
        }

        Scanner scan = new Scanner(System.in);
        System.out.print("Select order to update status (enter order number): ");
        int s = scan.nextInt();
        scan.nextLine();

        // Check if the selected index is valid
        if (s > 0 && s <= order_list.size()) {
            Order order = order_list.get(s - 1);

            System.out.print("Enter new status (Pending, In Progress, Completed, Canceled): ");
            String newStatus = scan.nextLine();

            // Validate the status
            List<String> validStatuses = Arrays.asList("Pending", "In Progress", "Completed", "Canceled");
            if (validStatuses.contains(newStatus)) {
                order.setStatus(newStatus);
                System.out.println("Order status updated to: " + newStatus);

                if (order.getStatus().equals("Completed")) {
                    total_sales += order.item.getItem_cost()*order.getQuantity();
                    order_list.remove(order);
                }
            } else {
                System.out.println("Invalid status. Please enter a valid status.");
            }
        } else {
            System.out.println("Invalid order selection. Please try again.");
        }
    }
    }




