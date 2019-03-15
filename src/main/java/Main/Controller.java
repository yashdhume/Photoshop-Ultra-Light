package Main;

import CamCapture.CamCaptureDemo;
import Effects.BlackWhiteEffect;
import ImageScraper.ImageScraperView;
import Layers.ImageLayer;
import Layers.LayerView;
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
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.stage.FileChooser;

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
    private AnchorPane toolbar;

    @FXML
    private AnchorPane layers;

    @FXML
    private  Button drawBtn;

    @FXML
    private Slider strokeSlide;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private TextField strokeTextBox;
    EditingView editingView = new EditingView();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*toolbar = new AnchorPane();
        toolbar.getChildren().add(new Label("Hello"));
        System.out.println(toolbar.getLayoutX());
        System.out.println(toolbar.getLayoutY());
        Button btn = new Button("df");
        toolbar.getChildren().add(btn);*/
        //LayerView lv = new LayerView(layers);
        editingView.InitializeLayers(layers);
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
                ImageIO.write(SwingFXUtils.fromFXImage(editingView.layerView.getCompositeImageView().getImage(), null), "jpg", file);
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
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(screenBounds.getWidth()-scene.getWidth());
        stage.setY(screenBounds.getHeight()/2-scene.getHeight()/2);
        stage.getIcons().add(new Image("googleIcon.png"));
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();


    }

    @FXML
    void cameraBtnAction(ActionEvent event) {
        Stage stage = new Stage();
        CamCaptureDemo camCaptureDemo = new CamCaptureDemo();
        Scene scene = new Scene(camCaptureDemo.start(), 800, 600);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(screenBounds.getWidth()-scene.getWidth());
        stage.setY(screenBounds.getHeight()/2-scene.getHeight()/2);
        stage.getIcons().add(new Image("cameraIcon.png"));
        stage.setAlwaysOnTop(true);
        stage.setScene(scene);
        stage.setOnCloseRequest((WindowEvent e) -> camCaptureDemo.setClosed());
        stage.show();

    }

    @FXML
    void bWEffectAction(ActionEvent event){
    System.out.println("Running Black and White");
        BlackWhiteEffect blackWhiteEffect = new BlackWhiteEffect(editingView.layerView.getSelectedAsImage().getImage());
        editingView.layerView.updateSelected(new ImageLayer("Black And White Layer", blackWhiteEffect.getEffect()));

    }
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
    }

}