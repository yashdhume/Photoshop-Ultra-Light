package sample;

import ImageScraper.ImageScraperView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import static ImageScraper.ImageScraperView.scroll;


public class Controller {
    @FXML
    private Button picGoogleBtn;

    @FXML
    private Button cameraBtn;
    @FXML
    void picGoogleBtnAction(ActionEvent event)throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(scroll, 500, 500);
        stage.setScene(scene);
        stage.show();


    }
    @FXML
    void cameraBtnAction(ActionEvent event) {

    }

}