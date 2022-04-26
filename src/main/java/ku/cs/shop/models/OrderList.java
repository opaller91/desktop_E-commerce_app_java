package ku.cs.shop.models;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class OrderList {

    private ArrayList<Order> orders;

    public OrderList() {
        orders = new ArrayList<>();
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void removeOrder(String orderID) {
        Order order = findOrder(orderID);
        orders.remove(order);
    }

    public ArrayList<Order> getAllOrders() {
        return orders;
    }

    public Order findOrder(String orderID) {
        Order order = null;
        for (Order temp : orders) {
            if (orderID.equals(temp.getOrderID())) {
                order = temp;
            }
        }
        return order;
    }

    public int findNextOrderID() {
        int i = 1;
        boolean idFound = false;
        if (orders.size() == 0) return i;
        for (; i < orders.size(); i++) { // loop ids
            idFound = false; // set to false
            for (Order temp : orders) { // search all items
                if (Integer.parseInt(temp.getOrderID()) == i) { // if id exists
                    idFound = true; // found id
                    break; // go to next id
                }
            }
            if (!idFound) { // if there's no id
                return i; // return the id
            }
        }
        return ++i; // if 1-n ids are taken, send the next one
    }

    public OrderList checkShipped(boolean isShipped) { // problematic
        OrderList checkShippedOrder = new OrderList();
        for (Order temp : orders) {
            if (isShipped == temp.isShipped()) {
                checkShippedOrder.addOrder(temp);
            }
        }
        return checkShippedOrder;
    }
}
