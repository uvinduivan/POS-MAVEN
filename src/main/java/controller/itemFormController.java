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
    private TableColumn<itemTm, String> colCode;
    @FXML
    private TableColumn<itemTm, String> colDesc;
    @FXML
    private TableColumn<itemTm, Double> colPrice;
    @FXML
    private TableColumn<itemTm, Integer> colQTY;
    @FXML
    private TableColumn<itemTm, Button> colOption;

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

    public void initialize() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        loadItemTable();

        tblItem.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> setData(newValue));
    }

    private void setData(itemTm newValue) {
        if (newValue != null) {
            txtCode.setEditable(false);
            txtCode.setText(newValue.getCode());
            txtDesc.setText(newValue.getDescription());
            txtUprice.setText(String.valueOf(newValue.getUnitPrice()));
            txtQTY.setText(String.valueOf(newValue.getQtyOnHand()));
        }
    }

    private void loadItemTable() {
        ObservableList<itemTm> tmList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM item";

        try (Statement stm = DBConnection.getInstance().getConnection().createStatement();
             ResultSet result = stm.executeQuery(sql)) {

            while (result.next()) {
                Button btn = new Button("Delete");
                itemTm c = new itemTm(
                        result.getString(1),
                        result.getString(2),
                        result.getDouble(3),
                        result.getInt(4),
                        btn
                );

                btn.setOnAction(actionEvent -> deleteItem(c.getCode()));
                tmList.add(c);
            }

            tblItem.setItems(tmList);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteItem(String code) {
        String sql = "DELETE FROM item WHERE code = ?";

        try (PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
            stm.setString(1, code);
            int result = stm.executeUpdate();
            if (result > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Item Deleted!").show();
                loadItemTable();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete item!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void saveButtonOnAction(ActionEvent event) {
        ItemDto item = new ItemDto(txtCode.getText(),
                txtDesc.getText(),
                Double.parseDouble(txtUprice.getText()),
                Integer.parseInt(txtQTY.getText())
        );
        String sql = "INSERT INTO item (code, description, unitPrice, qtyOnHand) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
            stm.setString(1, item.getCode());
            stm.setString(2, item.getDescription());
            stm.setDouble(3, item.getUnitPrice());
            stm.setInt(4, item.getQtyOnHand());

            int result = stm.executeUpdate();
            if (result > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Item Saved!").show();
                loadItemTable();
                clearFields();
            }
        } catch (SQLIntegrityConstraintViolationException ex) {
            new Alert(Alert.AlertType.ERROR, "Duplicate Entry").show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void updateButtonOnAction(ActionEvent event) {
        ItemDto item = new ItemDto(txtCode.getText(),
                txtDesc.getText(),
                Double.parseDouble(txtUprice.getText()),
                Integer.parseInt(txtQTY.getText())
        );
        String sql = "UPDATE item SET description = ?, unitPrice = ? WHERE code = ?";

        try (PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
            stm.setString(1, item.getDescription());
            stm.setDouble(2, item.getUnitPrice());
            stm.setString(3, item.getCode());

            int result = stm.executeUpdate();
            if (result > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Item Updated!").show();
                loadItemTable();
                clearFields();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        txtCode.setEditable(true);
        txtCode.clear();
        txtDesc.clear();
        txtUprice.clear();
        txtQTY.clear();
    }

    @FXML
    void backButtonOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) tblItem.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/dashBoardForm.fxml"))));
        stage.show();
    }
}
