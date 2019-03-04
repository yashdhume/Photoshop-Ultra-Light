package sample;

import ImageScraper.ImageScraperView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;




public class Controller {
    @FXML
    private Button picGoogleBtn;

    @FXML
    private Button cameraBtn;
    @FXML
    void picGoogleBtnAction(ActionEvent event)throws IOException {

        Stage stage = new Stage();
        Scene scene = new Scene(ImageScraperView.googleImageView(), 500, 500);
        stage.setScene(scene);
        stage.show();


    }
    @FXML
    void cameraBtnAction(ActionEvent event) {

    }

}