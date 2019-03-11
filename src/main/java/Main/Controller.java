package Main;

import CamCapture.CamCaptureDemo;
import Effects.BlackWhiteEffect;
import ImageScraper.ImageScraper;
import ImageScraper.ImageScraperView;
import Tools.PaintDraw;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;




public class Controller {
    @FXML
    private Button picGoogleBtn;

    @FXML
    private Button cameraBtn;
    @FXML
    private  Button tempBtn;
    @FXML
    private MenuItem newMI;

    @FXML
    private AnchorPane toolbar;


    @FXML
    void newMI(ActionEvent event) {
        Stage stage = new Stage();
        EditingView editingView = new EditingView();
        Scene scene = new Scene(editingView.EditView(),700,700);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();
    }
    @FXML
    void picGoogleBtnAction(ActionEvent event){

        Stage stage = new Stage();
        ImageScraperView imageScraperView = new ImageScraperView("");
        Scene scene = new Scene(imageScraperView.googleImageView(), 500, 500);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();


    }

    @FXML
    void cameraBtnAction(ActionEvent event) {
        Stage stage = new Stage();
        CamCaptureDemo camCaptureDemo = new CamCaptureDemo();
        Scene scene = new Scene(camCaptureDemo.start(), 800, 600);
        stage.setAlwaysOnTop(true);
        stage.setScene(scene);
        stage.setOnCloseRequest((WindowEvent e) -> camCaptureDemo.setClosed());
        stage.show();

    }
    @FXML
    void tempBtnAction(ActionEvent event){
        EditingView editingView = new EditingView();
        PaintDraw paintDraw = new PaintDraw(Color.RED,2);
        paintDraw.draw(editingView.anchorPaneEditView);
    }

}