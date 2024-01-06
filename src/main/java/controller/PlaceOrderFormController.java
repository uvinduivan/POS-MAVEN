package controller;

import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dto.CustomerDto;
import dto.ItemDto;
import dto.OrderDetailDto;
import dto.OrderDto;
import dto.tm.OrderTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;
import dao.custom.CustomerDao;
import dao.custom.ItemDao;
import dao.custom.OrderDao;
import dao.custom.impl.CustomerDaoImpl;
import dao.custom.impl.ItemDaoImpl;
import dao.custom.impl.OrderDaoImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderFormController {

    public Label lblOrderId;
    public ComboBox cmbItemCode;
    public TextField txtDescription;
    public TableView placeOrderTable;
    @FXML
    private ComboBox cmbCustId;


    @FXML
    private TextField txtCustName;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private TextField txtQty;

    @FXML
    private TableColumn colCode;

    @FXML
    private TableColumn colDesc;

    @FXML
    private TableColumn colQty;

    @FXML
    private TableColumn colAmount;

    @FXML
    private TableColumn colOption;

    @FXML
    private Label lblTotal;

    private CustomerDao customerDao = new CustomerDaoImpl();
    private ItemDao itemDao = new ItemDaoImpl();
    private List<CustomerDto> customers;
    private List<ItemDto> items;
    private double total=0;

    private ObservableList<OrderTm> tmList = FXCollections.observableArrayList();
    private OrderDao orderDao = new OrderDaoImpl();

    public void initialize(){
        colCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));
        colDesc.setCellValueFactory(new TreeItemPropertyValueFactory<>("desc"));
        colQty.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
        colAmount.setCellValueFactory(new TreeItemPropertyValueFactory<>("amount"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));

        try {
            customers = CustomerDao.allCustomers();
            items = ItemDao.allItems();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        loadCustomerIds();
        loadItemCodes();

        cmbCustId.getSelectionModel().selectedItemProperty().addListener((observableValue, o, newValue) -> {
            for (CustomerDto dto:customers) {
                if (dto.getId().equals(newValue.toString())){
                    txtCustName.setText(dto.getName());
                }
            }
        });

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observableValue, o, newValue) -> {
            for (ItemDto dto:items) {
                if (dto.getCode().equals(newValue.toString())) {
                    txtDescription.setText(dto.getDescription());
                    txtUnitPrice.setText(String.format("%.2f", dto.getUnitPrice()));
                }
            }
        });

        setOrderId();
    }

    private void setOrderId() {
        try {
            String id = orderDao.getLastOrder().getOrderId();
            if (id!=null){
                int num = Integer.parseInt(id.split("[D]")[1]);
                num++;
                lblOrderId.setText(String.format("D%03d",num));
            }else{
                lblOrderId.setText("D001");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadItemCodes() {
        ObservableList list = FXCollections.observableArrayList();

        for (ItemDto dto:items) {
            list.add(dto.getCode());
        }

        cmbItemCode.setItems(list);
    }

    private void loadCustomerIds() {
        ObservableList list = FXCollections.observableArrayList();

        for (CustomerDto dto:customers) {
            list.add(dto.getId());
        }

        cmbCustId.setItems(list);
    }

    @FXML
    void backButtonOnAction(ActionEvent event) {
        Stage stage = (Stage)  placeOrderTable.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboardForm.fxml"))));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void cartButtonOnAction(ActionEvent actionEvent) {
        Button btn = new Button("Delete");

        OrderTm tm = new OrderTm(
                cmbItemCode.getValue().toString(),
                txtDescription.getText(),
                Integer.parseInt(txtQty.getText()),
                Double.parseDouble(txtUnitPrice.getText())*Integer.parseInt(txtQty.getText()),
                btn
        );
        /*btn.setOnAction(
            tmList.remove(tm);
            total -= tm.getAmount();
            lblTotal.setText(String.format("%.2f", total));
            placeOrderTable.refresh();
        });*/
        boolean isExist = false;
        for (OrderTm order:tmList) {
            if (order.getCode().equals(tm.getCode())){
                order.setQty(order.getQty()+tm.getQty());
                order.setAmount(order.getAmount()+tm.getAmount());
                isExist = true;
                total+= tm.getAmount();
            }
        }
        if (!isExist){
            tmList.add(tm);
            total+=tm.getAmount();
        }

        lblTotal.setText(String.format("%.2f",total));

        TreeItem treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
    }

    public void orderButtonOnAction(ActionEvent actionEvent) {
        List<OrderDetailDto> list = new ArrayList<>();
        for (OrderTm tm:tmList) {
            list.add(new OrderDetailDto(
                    lblOrderId.getText(),
                    tm.getCode(),
                    tm.getQty(),
                    tm.getAmount()/tm.getQty()
            ));
        }

        OrderDto dto = new OrderDto(
                lblOrderId.getText(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                cmbCustId.getValue().toString(),
                list
        );


        try {
            boolean isSaved = orderDao.saveOrder(dto);
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION, "Order Saved!").show();
                setOrderId();
            }else{
                new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}