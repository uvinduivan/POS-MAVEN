package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashBoardFormController {

    public AnchorPane pane;

    public void customerButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage=(Stage) pane.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/customerForm.fxml"))));
            stage.show();
    }
}
