package GUI;

import data.*;
import logic.SQLConnection;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;


public class Controller {
    @FXML
    public TableView orderTable;
    @FXML
    public TableColumn productIdCol;
    @FXML
    public TableColumn productNameCol;
    @FXML
    public TableColumn materialCol;
    @FXML
    public TableColumn decIdCol;
    @FXML
    public TableColumn netPriceCol;
    @FXML
    public TableColumn productGrossPriceIdCol;
    @FXML
    public TableView allOrderTable;
    @FXML
    public TableColumn orderNameCol;
    @FXML
    public TableColumn orderAmountCol;
    @FXML
    public TableColumn orderPriceCol;
    @FXML
    public TableView totalCostTable;
    @FXML
    public TableColumn totalDescrCol;
    @FXML
    public TableColumn totalCostCol;
    @FXML
    public Button orderButton;
    @FXML
    public TableView orderToFinishCustomer;
    @FXML
    public TableColumn tf_orderIdCol;
    @FXML
    public TableColumn tf_customerIdCol;
    @FXML
    public Button getOrderButton;
    @FXML
    public TableView orderItemsToFinishCustomer;
    @FXML
    public TableColumn tf_productIdCol;
    @FXML
    public TableColumn tf_productNameCol;
    @FXML
    public TableColumn tf_orderAmountCol;
    @FXML
    public TableView logOrderTable;
    @FXML
    public TableColumn logIdCol;
    @FXML
    public TableColumn logGrossCol;
    @FXML
    public TableColumn logNetCol;
    @FXML
    public TableColumn logVatCol;
    @FXML
    public TableColumn logCustomerIdCol;
    @FXML
    public TableColumn logDateCol;
    @FXML
    public TableView uCustomerTable;
    @FXML
    public TableColumn uCustomerIdCol;
    @FXML
    public TableColumn uSurnameCol;
    @FXML
    public TableColumn uNameCol;
    @FXML
    public TableColumn uAddressCol;
    @FXML
    public TableColumn uCityCol;
    @FXML
    public TableColumn uCountryCol;
    @FXML
    public TableColumn uCompanyCol;
    @FXML
    public TextField customerIdTextField;
    @FXML
    public TextField surnameTextField;
    @FXML
    public TextField nameTextField1;
    @FXML
    public TextField addressTextField;
    @FXML
    public TextField cityTextField;
    @FXML
    public TextField countryTextField;
    @FXML
    public TextField companyTextField;
    @FXML
    public TableView uProductTable;
    @FXML
    public TableColumn uProductIdCol;
    @FXML
    public TableColumn uProductNameCol;
    @FXML
    public TableColumn uMaterialCol;
    @FXML
    public TableColumn uDescCol;
    @FXML
    public TableColumn uPriceCol;
    @FXML
    public TextField productIdTextField;
    @FXML
    public TextField nameTextField2;
    @FXML
    public TextField materialTextField;
    @FXML
    public TextField descTextField;
    @FXML
    public TextField priceTextField;
    @FXML
    public TextField customerNoText;


    private logic.SQLConnection sql = new SQLConnection();
    private ObservableList<Product> products = FXCollections.observableArrayList();
    private ObservableList<Customer> customers = FXCollections.observableArrayList();
    private Order order = new Order();
    private ObservableList<Order> ordersTF = FXCollections.observableArrayList();
    private int currentTFOrder;
    private ObservableList<Order> logOrders = FXCollections.observableArrayList();

    @FXML
    private void initialize(){
        initializeProductList();
        initializeCustomerList();
        setOrderEvent();
    }

    private void initializeCustomerList(){
        customers = sql.getCustomer();
        updateCustomerTable();
    }

    private void initializeProductList() {
        products = sql.getProduct();
        updateProductTable();
        updateUProductTable();
    }

    private void updateUProductTable() {
        uProductIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        uProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        uMaterialCol.setCellValueFactory(new PropertyValueFactory<>("material"));
        uDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        uPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        uProductTable.getItems().setAll(products);
    }

    private void updateCustomerTable(){
        uCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        uSurnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        uNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        uAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        uCityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        uCountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        uCompanyCol.setCellValueFactory(new PropertyValueFactory<>("company"));
        uCustomerTable.getItems().setAll(customers);
    }

    private void updateProductTable(){
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        materialCol.setCellValueFactory(new PropertyValueFactory<>("material"));
        decIdCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        netPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        orderTable.getItems().setAll(products);
    }

    private void updateAllOrderTable(){
        orderNameCol.setCellValueFactory(new PropertyValueFactory<>("product"));
        orderAmountCol.setCellValueFactory(new PropertyValueFactory<>("orderAmount"));
        orderPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        allOrderTable.getItems().setAll(order.getOrderedItemList());
    }

    private void updateTotalCostTable(){
        totalDescrCol.setCellValueFactory(new PropertyValueFactory<>("totalDescr"));
        totalCostCol.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        totalCostTable.getItems().setAll(order.getTotalCost());
    }

    private void setOrderEvent() {
        orderTable.setRowFactory(tv -> {
            TableRow<Product> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() > 1 && (!row.isEmpty())) {
                    order.addProduct(row.getItem());
                    updateAllOrderTable();
                    updateTotalCostTable();
                }
            });
            return row;
        });
        allOrderTable.setRowFactory(tv -> {
            TableRow<OrderedItem> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() > 1 && (!row.isEmpty())) {
                    order.removeOrderedItem(row.getItem());
                    updateAllOrderTable();
                    updateTotalCostTable();
                }
            });
            return row;
        });
    }

    @FXML
    public void setCustomerId() {
        String str = customerNoText.getText();
        if (str.matches("[0-9]+")) {
            int customerId = Integer.parseInt(str);
            order.setCustomerId(customerId);
            orderButton.disableProperty().set(false);
        } else customerNoText.setText(Integer.toString(order.getCustomerId()));
    }

    @FXML
    public void submitOrder() {
        if (order.getOrderedItemList().size() != 0) {
            sql.sendOrder(order);
            order = new Order();
            updateAllOrderTable();
            customerNoText.setText("0");
        }
    }

    @FXML
    public void initializeOrderList(){
        ordersTF = sql.getOrder();
        setOrderTFTableEvent();
        updateOrderTFTable();
    }

    private void updateOrderTFTable() {
        tf_orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        tf_customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        orderItemsToFinishCustomer.getItems().setAll(ordersTF);
    }

    private void setOrderTFTableEvent(){
        orderItemsToFinishCustomer.setRowFactory(tv -> {
            TableRow<Order> row = new TableRow<>();
            row.setOnMouseClicked(event ->{
                if(event.getClickCount() > 1 && (!row.isEmpty())){
                    updateOrderTFTDetails(row.getItem().getOrderedItemList());
                    currentTFOrder = row.getItem().getOrderId();
                }
            });
            return row;
                });
    }

    private void updateOrderTFTDetails(ObservableList<OrderedItem> itemList){
        tf_productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        tf_productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        tf_orderAmountCol.setCellValueFactory(new PropertyValueFactory<>("orderAmount"));
    }

    @FXML
    private void finishOrder(){
        sql.sendOrderFinish(currentTFOrder);
        initializeOrderList();
        orderItemsToFinishCustomer.getItems().clear();
    }

    @FXML
    private void logUpdate() {
        logOrders = sql.updateLog();
        updateLogList();
    }

    private void updateLogList(){
        logIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        logGrossCol.setCellValueFactory(new PropertyValueFactory<>("gross"));
        logNetCol.setCellValueFactory(new PropertyValueFactory<>("net"));
        logVatCol.setCellValueFactory(new PropertyValueFactory<>("vat"));
        logCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        logDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    @FXML
    public void postCustomer() {
        int id = Integer.parseInt(customerIdTextField.getText());
        String surname = surnameTextField.getText();
        String name = nameTextField1.getText();
        String address = addressTextField.getText();
        String city = cityTextField.getText();
        String country = countryTextField.getText();
        String company = companyTextField.getText();
        sql.postCustomer(id, surname, name, address, city, country, company);
        customers = sql.getCustomer();
        updateCustomerTable();
    }

    @FXML
    public void deleteCustomer(){
        int id = Integer.parseInt(customerIdTextField.getText());
        sql.deleteCustomer(id);
        customers = sql.getCustomer();
        updateCustomerTable();
    }

    @FXML
    public void getCustomer() {
        customers = sql.getCustomer();
        uCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        uSurnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        uNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        uAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        uCityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        uCountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        uCompanyCol.setCellValueFactory(new PropertyValueFactory<>("company"));
        uCustomerTable.getItems().setAll(customers);
        uCustomerTable.setRowFactory(tv -> {
            TableRow<Customer> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() > 1 && (!row.isEmpty())) {
                    customerIdTextField.setText(row.getItem().getId().toString());
                    surnameTextField.setText(row.getItem().getSurname());
                    nameTextField1.setText(row.getItem().getName());
                    addressTextField.setText(row.getItem().getAddress());
                    cityTextField.setText(row.getItem().getCity());
                    countryTextField.setText(row.getItem().getCountry());
                    companyTextField.setText(row.getItem().getCompany());
                }
            });
            return row;
        });
    }

    @FXML
    public void postProduct() {
        int id = Integer.parseInt(productIdTextField.getText());
        String name = nameTextField2.getText();
        String material = materialTextField.getText();
        String description = descTextField.getText();
        double price = Double.parseDouble(priceTextField.getText());
        sql.postProduct(id, name, material,description,price);
        products = sql.getProduct();
        updateProductTable();
        updateUProductTable();
    }

    @FXML
    public void deleteProduct(){
        int id = Integer.parseInt(productIdTextField.getText());
        sql.deleteProduct(id);
        products = sql.getProduct();
        updateProductTable();
    }

    @FXML
    public void getProduct() {
        products = sql.getProduct();
        uProductIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        uProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        uMaterialCol.setCellValueFactory(new PropertyValueFactory<>("material"));
        uDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        uPriceCol.setCellValueFactory(new PropertyValueFactory<>("netPrice"));
        uProductTable.getItems().setAll(products);
        uProductTable.setRowFactory(tv -> {
            TableRow<Product> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() > 1 && (!row.isEmpty())) {
                    productIdTextField.setText(row.getItem().getId().toString());
                    nameTextField2.setText(row.getItem().getName());
                    materialTextField.setText(row.getItem().getMaterial());
                    descTextField.setText(row.getItem().getDescription());
                    priceTextField.setText(row.getItem().getPrice().toString());
                }
            });
            return row;
        });
    }

}
