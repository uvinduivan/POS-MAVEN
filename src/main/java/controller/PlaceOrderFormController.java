package controller;

import dto.CustomerDto;
import dto.ItemDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.CustomerModel;
import model.ItemModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderFormController {

    public ComboBox cmbCustId;
    public ComboBox cmbItemCode;
    public AnchorPane placeOrderFormPane;

    private List<CustomerDto> customers = new ArrayList<>();
    private List<ItemDto> items = new ArrayList<>();


    public void initialize(){
        loadCustomerIds();
        loadItemCodes();
    }

    private void loadItemCodes() {
        try {
            items = ItemModel.allItems();
            ObservableList list= FXCollections.observableArrayList();
            for (ItemDto dto:items){
                list.add(dto.getCode());
            }
            cmbItemCode.setItems(list);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCustomerIds() {
        try {
            customers = CustomerModel.allCustomers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void orderButtonOnAction(ActionEvent actionEvent) {

    }

    public void cartButtonOnAction(ActionEvent actionEvent) {

    }

    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage=(Stage) placeOrderFormPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/dashBoardForm.fxml"))));
        stage.show();
    }
}
