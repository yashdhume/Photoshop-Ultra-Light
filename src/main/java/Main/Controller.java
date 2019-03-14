package Main;

import CamCapture.CamCaptureDemo;
import Effects.BlackWhiteEffect;
import ImageScraper.ImageScraperView;
import Tools.PaintDraw;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.stage.FileChooser;
import javafx.scene.canvas.Canvas;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRenderedImage;
import javafx.scene.image.WritableImage;
import java.io.File;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller extends AnchorPane implements Initializable{
    @FXML
    private Button picGoogleBtn;

    @FXML
    private Button cameraBtn;
    @FXML
    private  Button bWEffectBtn;
    @FXML
    private MenuItem newMI;

    @FXML
    private AnchorPane toolBar;
    @FXML
    private AnchorPane view;
    @FXML
    private AnchorPane properties;

    @FXML
    private  Button drawBtn;

    @FXML
    private Slider strokeSlide;
    
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private TextField strokeTextBox;
    EditingView editingView = new EditingView();
    
    Canvas canvas = new Canvas(1080, 790);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	editingView.InitializeToolbar(toolBar);
    	editingView.InitializeProperties(properties);
    }

    @FXML
    void newMI(ActionEvent event) {
        Stage stage = new Stage();
        Scene scene = new Scene(editingView.EditView(),700,700);
        stage.getIcons().add(new Image("logo.png"));
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
        Stage stage = new Stage();
        FileChooser savefile = new FileChooser();
        savefile.setTitle("Save File");
        File file = savefile.showSaveDialog(stage);
        if (file != null) {
            try {
                WritableImage writableImage = new WritableImage(1080, 790);
                EditingView.anchorPaneEditView.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", file);
            } catch (IOException ex) {
                System.out.println("Error!");
            }
        }
    }

    @FXML
    void saveAs(ActionEvent event) {
        Stage stage = new Stage();
        FileChooser savefile = new FileChooser();
        savefile.setTitle("Save File");
        File file = savefile.showSaveDialog(stage);
        if (file != null) {
            try {
                WritableImage writableImage = new WritableImage(1080, 790);
                EditingView.anchorPaneEditView.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", file);
            } catch (IOException ex) {
                System.out.println("Error!");
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

   /*
    Color color = Color.WHITE;
    int stroke=2;

    private void drawUpdate(){
        PaintDraw paintDraw = new PaintDraw(color, stroke);
        paintDraw.drawOnAnchor(editingView.anchorPaneEditView);
        //paintDraw.drawOnImage(editingView.imageViewEditView);
    }
    @FXML
    void strokeAction(MouseEvent event){
       stroke = (int)strokeSlide.getValue();
      //  System.out.println(stroke);
        strokeTextAction();
        drawUpdate();
    }
    @FXML
    void drawAction(ActionEvent event){
        strokeTextBox.setText(String.valueOf(stroke));
        drawUpdate();
    }
    @FXML
    void colorPickerAction(ActionEvent event){
        color = colorPicker.getValue();
        drawUpdate();
    }

    @FXML
    void strokeTextAction(){
        strokeTextBox.setText(String.valueOf(stroke));

    }
    @FXML
    void strokeTextBoxEnter(KeyEvent event){
        if(event.getCode()==KeyCode.ENTER){
            stroke = Integer.parseInt(strokeTextBox.getText());
            strokeSlide.setValue(stroke);
            drawUpdate();

        }
    }*/

}