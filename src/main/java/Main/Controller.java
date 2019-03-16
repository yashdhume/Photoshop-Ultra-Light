package Main;

import UI.UIInitializer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.awt.image.RenderedImage;
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
    private Menu openRecentMenu;
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
    File file;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new UIInitializer(toolBar, properties);
    }
    // File
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
        file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            Image image = new Image(file.toURI().toString());
            editingView.imageViewEditView.setImage(image);
            //recents.add(file.toURI().toString());
        }
    }

    @FXML
    void openRecent(ActionEvent event) {
        // To be Implemented
    }

    @FXML
    void save(ActionEvent event) {
        FileChooser savefile = new FileChooser();
        savefile.setTitle("Save");
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
        savefile.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));
        savefile.setTitle("Save As");
        file = savefile.showSaveDialog(stage);
        try {
            WritableImage writableImage = new WritableImage(1080, 790);
            EditingView.anchorPaneEditView.snapshot(null, writableImage);
            RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
            ImageIO.write(renderedImage, "png", file);
        } catch (IOException ex) {
            System.out.println("Error!");
        }
    }

    @FXML
    void preference(ActionEvent event) {
    }

    @FXML
    void quit(ActionEvent event) {
        System.exit(0);
    }

    // Help
    @FXML
    void about(ActionEvent event) {
        Stage stage = new Stage();
        stage.setTitle("About");
        Label label = new Label("Photoshop-Ultra-Light\n" +
                "Developing by GND not Developers \n" +
                "Yash Dhume; Kashif Hussain; Kathryn, Mei-Yu, Chen; Jessica;\n" +
                "CSCI 2020U Course Project\n" +
                "\nAbout the Project\n" +
                "Language: Java 1.8 (dependencies: JavaFX)\n" +
                "Build tool: Maven 3.1.5\n" +
                "APIs: OpenCV");
        label.setPadding(new Insets(30));
        Scene scene = new Scene(label,500,350);
        stage.getIcons().add(new Image("logo.png"));
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

}