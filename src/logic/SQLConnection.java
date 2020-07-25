package logic;

import data.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class SQLConnection {
    Connection con ;
    PreparedStatement p_stmt;
    ResultSet rs;

    public SQLConnection(){
        this.con = null;
        this.p_stmt = null;
        this.rs = null;
    }

    public void getConnection () {
        String dbHost = "134.108.190.89";
        String dbPort = "1433";
        String dbName = "SWB_DB2_Projekt";
        String dbUser = "wkb4";
        String dbPass = "wkb4";
        String connectionUrl = "jdbc:sqlserver://" +  dbHost
                + ":" + dbPort + ";" + "databaseName=" + dbName + ";"
                + "user=" + dbUser + ";" + "password=" + dbPass + ";";
        try {
            //get database connection
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void closeConnection() {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (p_stmt != null) {
                try {
                    p_stmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }

    public ObservableList<Product> getProduct(){
        ObservableList<Product> dbProduct = FXCollections.observableArrayList();
        String sqlString = "select * from Adel_product";
        try{
            getConnection();
            p_stmt = con.prepareStatement(sqlString);
            rs = p_stmt.executeQuery();
            while(rs.next()){
                int id = rs.getInt("productId");
                String name = rs.getString("name");
                String material = rs.getString("material");
                String description = rs.getString("description");
                double price = rs.getDouble("price");

                dbProduct.add(new Product(id, name, material, description, price));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return dbProduct;
    }

    public ObservableList<Customer> getCustomer() {
        ObservableList<Customer> dbCustomer = FXCollections.observableArrayList();
        String sqlString = "select * from Adel_customer";
        try{
            getConnection();
            p_stmt = con.prepareStatement(sqlString);
            rs = p_stmt.executeQuery();
            while(rs.next()){
             int customerId = rs.getInt("customerId");
             String surname = rs.getString("surname");
             String name = rs.getString("name");
             String address = rs.getString("address");
             String city = rs.getString("city");
             String country = rs.getString("country");
             String company = rs.getString("company");
             dbCustomer.add(new Customer(customerId,surname,name,address,city,country,company));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return dbCustomer;
    }

    public void sendOrder(Order order){
        String sqlString = "insert into Adel_order (grossTotal, netTotal, customerId) values" + order.toSql();
        try{
            getConnection();
            p_stmt = con.prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS);
            p_stmt.executeUpdate();
            rs = p_stmt.getGeneratedKeys();
            if(rs.next()){
                int orderId = rs.getInt(1);
                sendOrderItems(order.getOrderedItemList(),orderId);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void sendOrderItems(ObservableList<OrderedItem> orderItemList, int orderId){
        String values = "";
        for(OrderedItem ordItem : orderItemList){
            values = values + " (" + orderId + ", " + ordItem.getSQL() + "),";
        }
        values = values.substring(0, values.length() - 1);
        String sqlString = "insert into Adel_orderedItems values" + values;
        try{
            p_stmt = con.prepareStatement(sqlString, Statement.NO_GENERATED_KEYS);
            p_stmt.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public ObservableList<Order> getOrder(){
        ObservableList<Order> dbOrder = FXCollections.observableArrayList();
        String sqlString = "select * from Adel_order as o" + "join Adel_orderedItems as oI on o.orderId = oI.orderId"
                + "join Adel_product p on oI.productId = p.productId" ;
        try{
          getConnection();
          p_stmt = con.prepareStatement(sqlString);
          rs = p_stmt.executeQuery();
          int orderId = 0;
          int customerId;
          String orderDate;
          Order dbOrderTF = new Order();
          while (rs.next()){
              if(orderId != rs.getInt(1)){
                  orderId = rs.getInt(1);
                  customerId = rs.getInt("customerId");
                  orderDate = rs.getString("orderDate");
                  dbOrderTF = new Order(orderId, customerId, orderDate);
                  dbOrder.add(dbOrderTF);
              }
              int productId = rs.getInt("productId");
              String productName = rs.getString("productName");
              String material = rs.getString("material");
              String descr = rs.getString("description");
              Double price = rs.getDouble("price");
              Product dbProduct = new Product(productId, productName, material, descr, price );
              int amount = rs.getInt("orderAmount");
              for(int i=0; i<amount; i++){
                  dbOrder.get(dbOrder.size() - 1).addProduct(dbProduct);
              }
          }
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return dbOrder;
    }


    public void sendOrderFinish(int currentTFOrder) {
    String sqlString = "exec Adel_finishedOrder " + currentTFOrder;
        try {
        getConnection();
        p_stmt = con.prepareStatement(sqlString);
        p_stmt.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        closeConnection();
    }
    }


    public ObservableList<Order> updateLog(){
        ObservableList<Order> dbOrderLog = FXCollections.observableArrayList();
        String sqlString = "exec Adel_checkprices";
        try{
            getConnection();
            p_stmt = con.prepareStatement(sqlString);
            rs = p_stmt.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                double gross = rs.getDouble("grossTotal");
                double net = rs.getDouble("netTotal");
                double vat = rs.getInt("vat");
                int customerId = rs.getInt("customerId");
                String date = rs.getString("orderDate");
                dbOrderLog.add(new Order(id, gross, net, vat, customerId, date));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return dbOrderLog;
    }

     public void postCustomer(int customerId, String surname, String name, String address, String city, String country, String company){
        String sqlString = "set IDENTITY_INSERT Adel_Customer ON insert into Adel_Customer(customerId,surname,name,address,city,country,company) values (?,?,?,?,?,?,?);";
        try {
            getConnection();
            p_stmt = con.prepareStatement(sqlString);
            p_stmt.setInt(1, customerId);
            p_stmt.setString(2, surname);
            p_stmt.setString(3, name);
            p_stmt.setString(4, address);
            p_stmt.setString(5, city);
            p_stmt.setString(6, country);
            p_stmt.setString(7, company);
            p_stmt.executeQuery();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            closeConnection();
        }
     }

     public void deleteCustomer(int id){
        String sqlString = "delete from Adel_Customer where customerId=" + id;
        try{
            getConnection();
            p_stmt = con.prepareStatement(sqlString);
            p_stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void postProduct(int id, String name, String material, String descr, double price){
        String sqlString = "set IDENTITY_INSERT Adel_Product ON insert into Adel_Product(productId, name, material, description, price) values(?,?,?,?,?)";
        try {
            getConnection();
            p_stmt = con.prepareStatement(sqlString);
            p_stmt.setInt(1, id);
            p_stmt.setString(2,name);
            p_stmt.setString(3,material);
            p_stmt.setString(4, descr);
            p_stmt.setDouble(5, price);
            p_stmt.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            closeConnection();
        }
    }

    public void deleteProduct(int id){
        String sqlString = "delete from Adel_Product where productId=" + id;
        try{
            getConnection();
            p_stmt = con.prepareStatement(sqlString);
            p_stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }
}


