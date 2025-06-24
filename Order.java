package byteme.bytemeap4;
public class Order {
    items item;
    int quantity;
    String customerType;
    String status ;

    public Order(items item, int quantity, String customerType) {
        this.item = item;
        this.quantity = quantity;
        this.customerType = customerType;
    }

    public items getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCustomerType() {
        return customerType;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return item.toString() + " - Quantity: " + quantity + " (Ordered by: " + customerType + ")";
    }
}
