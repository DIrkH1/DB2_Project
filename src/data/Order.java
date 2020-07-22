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

    public Order( int orderId, int customerId, String orderDate){
        this.orderedItemList = FXCollections.observableArrayList();
        this.grossTotal = 0;
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
    }

    public Order(int orderId, double netTotal, double grossTotal, double vat, int customerId , String orderDate ){
        this.orderedItemList = FXCollections.observableArrayList();
        this.orderId = orderId;
        this.grossTotal = grossTotal;
        this.netTotal = netTotal;
        this.vat = vat;
        this.customerId = customerId;
        this.orderDate = orderDate;
    }

    public void addProduct(Product product){
        boolean itemExists = false;
        for (OrderedItem ordItem : orderedItemList){
            if(ordItem.equals(product)){
                ordItem.incOrder();
                itemExists = true;
            }
        }
        if(!itemExists){
            orderedItemList.add(new OrderedItem(product));
        }
        grossTotal = grossTotal + product.getPrice();
        netTotal = this.getNetTotal();
    }

    public void removeOrderedItem(OrderedItem orderedItem){
        if(orderedItemList.contains(orderedItem)){
            if(orderedItem.getOrderAmount() > 1){
                orderedItem.decOrder();
            } else {
                orderedItemList.remove(orderedItem);
            }
            grossTotal = grossTotal - orderedItem.getPrice();
            netTotal = this.getNetTotal();
        }
    }

    public ObservableList<TotalEntry> getTotalCost(){
        ObservableList<TotalEntry> totalCostList = FXCollections.observableArrayList();
        totalCostList.add(new TotalEntry(netTotal, "netTotal: "));
        String vat = "grossTotal (16% tax): ";
        totalCostList.add(new TotalEntry(grossTotal, vat));
        return totalCostList;
    }

    public String toSql(){
        return "(" + grossTotal + ", " + netTotal + ", " + customerId + ")";
    }

    public double getGrossTotal() {
        return grossTotal;
    }

    public ObservableList<OrderedItem> getOrderedItemList() {
        return orderedItemList;
    }

    public double getNetTotal(){
        return netTotal = Math.round(grossTotal * 84) / 100;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getOrderDate() {
        return orderDate;
    }
}
