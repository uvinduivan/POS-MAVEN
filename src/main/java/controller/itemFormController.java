package controller;

import db.DBConnection;
import dto.ItemDto;
import dto.tm.itemTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;


public class itemFormController {

    @FXML
    private TableColumn colCode;

    @FXML
    private TableColumn colDesc;

    @FXML
    private TableColumn colPrice;

    @FXML
    private TableColumn colOption;

    @FXML
    private TableColumn colQTY;

    @FXML
    private TableView<itemTm> tblItem;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtUprice;

    @FXML
    private TextField txtQTY;

    public void initialize(){
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        loadItemTable();

        tblItem.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setData(newValue);
        });
    }

    private void setData(itemTm newValue) {
        if (newValue != null) {
            txtCode.setEditable(false);
            txtCode.setText(newValue.getCode());
            txtDesc.setText(newValue.getDescription());
            txtUprice.setText(String.valueOf(Double.valueOf(newValue.getUnitPrice())));
            txtQTY.setText(String.valueOf(Integer.valueOf(newValue.getQtyOnHand())));
        }
    }

    private void loadItemTable() {
        ObservableList<itemTm> tmList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM item";

        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            ResultSet result = stm.executeQuery(sql);

            while (result.next()){
                Button btn = new Button("Delete");
                itemTm c = new itemTm(
                        result.getString(1),
                        result.getString(2),
                        result.getDouble(3),
                        result.getInt(4),
                        btn
                );

                btn.setOnAction(actionEvent -> {
                    deleteItem(c.getCode());
                });

                tmList.add(c);
            }

            tblItem.setItems(tmList);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() {
        Connection o = null;
        return o;
    }

    private void deleteItem(String id) {
        String sql = "DELETE from item WHERE id='"+id+"'";

        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            int result = stm.executeUpdate(sql);
            if (result>0){
                new Alert(Alert.AlertType.INFORMATION,"Item Deleted!").show();
                loadItemTable();
            }else{
                new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        tblItem.refresh();
        txtUprice.clear();
        txtQTY.clear();
        txtDesc.clear();
        txtCode.clear();
        txtCode.setEditable(true);
    }

    @FXML
    void saveButtonOnAction(ActionEvent event) {
        ItemDto c = new ItemDto(txtCode.getText(),
                txtDesc.getText(),
                Double.parseDouble(txtUprice.getText()),
                Integer.parseInt(txtQTY.getText())
        );
        String sql = "INSERT INTO item VALUES('"+c.getCode()+"','"+c.getDescription()+"','"+c.getUnitPrice()+"',"+c.getQtyOnHand()+")";

        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            int result = stm.executeUpdate(sql);
            if (result>0){
                new Alert(Alert.AlertType.INFORMATION,"Item Saved!").show();
                loadItemTable();
                clearFields();
            }
        } catch (SQLIntegrityConstraintViolationException ex){
            new Alert(Alert.AlertType.ERROR,"Duplicate Entry").show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void updateButtonOnAction(ActionEvent event) {
        ItemDto c = new ItemDto(txtCode.getText(),
                txtDesc.getText(),
                Double.parseDouble(txtUprice.getText()),
                Integer.parseInt(txtQTY.getText())
        );
        String sql = "UPDATE item SET code='"+c.getCode()+"', description='"+c.getDescription()+"', unitprice="+c.getUnitPrice()+" WHERE code='"+c.getCode()+"'";

        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            int result = stm.executeUpdate(sql);
            if (result>0){
                new Alert(Alert.AlertType.INFORMATION,"item "+c.getCode()+" Updated!").show();
                loadItemTable();
                clearFields();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage=(Stage) tblItem.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/dashBoardForm.fxml"))));
        stage.show();
    }
}