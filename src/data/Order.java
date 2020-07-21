package data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Order {
    private int orderId;
    private ObservableList<OrderedItem> orderedItemList;
    private int customerId;
    private double grossTotal;
    private double netTotal;
    private double vat;
    private String orderDate;

    public Order(){
        this.orderedItemList = FXCollections.observableArrayList();
        this.grossTotal = 0;
    }
    

}
