package Main;

import CamCapture.CamCaptureDemo;
import ImageScraper.ImageScraperView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;




public class Controller {
    @FXML
    private Button picGoogleBtn;

    @FXML
    private Button cameraBtn;
    @FXML
    private MenuItem newMI;

    @FXML
    void newMI(ActionEvent event) {
        Stage stage = new Stage();
        Scene scene = new Scene(EditingView.EditView(),500,500);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();
    }
    @FXML
    void picGoogleBtnAction(ActionEvent event){

        Stage stage = new Stage();
        Scene scene = new Scene(ImageScraperView.googleImageView(), 500, 500);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();


    }
    @FXML
    void cameraBtnAction(ActionEvent event) {
        Stage stage = new Stage();
        Scene scene = new Scene(CamCaptureDemo.start(), 800, 600);
        stage.setAlwaysOnTop(true);
        stage.setScene(scene);
        stage.setOnCloseRequest((WindowEvent e) -> CamCaptureDemo.setClosed());
        stage.show();

    }

}