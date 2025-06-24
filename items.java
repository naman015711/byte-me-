package byteme.bytemeap4;
import java.util.ArrayList;
import java.util.*;
import java.io.Serializable;
public class items implements Serializable{
    String item_name;
    int item_cost;
    int avalaibility;
    String category;
    static ArrayList<items> menu= new ArrayList<items>();
    static ArrayList<Order> order_list = new ArrayList<>();

    public items(String item_name,int item_cost,int availability, String category){
        this.item_cost=item_cost;
        this.item_name=item_name;
        this.avalaibility=availability;
        this.category=category;
    }

    public static ArrayList<Order> getOrderList() {
        return order_list;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public items() {
    }

    public int getAvalaibility() {
        return avalaibility;
    }

    public void setAvalaibility(int avalaibility) {
        this.avalaibility = avalaibility;
    }

    public int getItem_cost() {
        return item_cost;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }
    public void setItem_cost(int item_cost) {
        this.item_cost = item_cost;
    }
    public void add_item_to_menu(items item){
        menu.add(item);//cost is in rupee.
    }
    public void remove_item(items item){
        menu.remove(item);
    }

}
