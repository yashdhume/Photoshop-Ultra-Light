package Main;

import CamCapture.CamCaptureDemo;
import ImageScraper.ImageScraperView;
import Tools.PaintDraw;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;
import java.io.IOException;
public class Controller extends AnchorPane {
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


    public void initialize() {
        toolbar = new AnchorPane();
        toolbar.getChildren().add(new Label("Hello"));
        System.out.println(toolbar.getLayoutX());
        System.out.println(toolbar.getLayoutY());
    }

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