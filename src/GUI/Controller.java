package GUI;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import logic.SQLConnection;

import javax.swing.table.TableColumn;
import javax.swing.text.TableView;
import java.awt.*;

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
    public TableView logGrossCol;
    @FXML
    public TableView logNetCol;
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

    private logic.SQLConnection sql = new SQLConnection();
    private ObservableList<Menu> menus = FXCollections.observableArrayList();

}
