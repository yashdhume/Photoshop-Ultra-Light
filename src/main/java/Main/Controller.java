package Main;

import CamCapture.CamCaptureDemo;
import ImageScraper.ImageScraperView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.stage.FileChooser;
import java.io.File;
import java.awt.Image;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class Controller extends AnchorPane {
    @FXML
    private Button picGoogleBtn;

    @FXML
    private Button cameraBtn;
    @FXML
    private MenuItem newMI;

    @FXML
    private AnchorPane toolbar;


    public void initialize() {
        toolbar = new AnchorPane();
        toolbar.getChildren().add(new Label("Hello"));
        System.out.println(toolbar.getLayoutX());
        System.out.println(toolbar.getLayoutY());
    }

    @FXML
    void newMI(ActionEvent event) {
        Stage stage = new Stage();
        Scene scene = new Scene(EditingView.EditView(),500,500);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    @FXML
    void open(ActionEvent event) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
        }
    }

    @FXML
    void openRecent(ActionEvent event) {
        // To be implemented, list last few projects/images opened
    }

    @FXML
    void save(ActionEvent event) {
        /*try {
            ImageIO.write(SwingFXUtils.fromFXImage(EditingView.imageViewEditView.getImage(), null), "jpg", file);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }*/
    }

    @FXML
    void saveAs(ActionEvent event) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save As");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(EditingView.imageViewEditView.getImage(), null), "jpg", file);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @FXML
    void revert(ActionEvent event) {
        // To be implemented revert changes
    }

    @FXML
    void preference(ActionEvent event) {
    }

    @FXML
    void quit(ActionEvent event) {
        System.exit(0);
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