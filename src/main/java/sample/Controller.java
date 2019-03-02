package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Controller {
    int counter = 0;

    @FXML
    private Button btn1;

    @FXML
    private Label lab1;

    @FXML
    private Button btn2;

    @FXML
    private Button btn21;

    @FXML
    void display(ActionEvent event) {
        counter++;
        lab1.setText("Button Clicked "+ counter);
    }


}
