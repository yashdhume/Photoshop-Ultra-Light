package Main;

import UI.UIInitializer;
import VIPLogin.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.awt.image.RenderedImage;
import javafx.scene.image.WritableImage;
import java.io.File;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller extends AnchorPane implements Initializable{
    @FXML
    private Button picGoogleBtn;
    @FXML
    private Button cameraBtn;
    @FXML
    private Button bWEffectBtn;
    @FXML
    private MenuItem newMI;
    @FXML
    private MenuItem adminMI;
    @FXML
    private Menu openRecentMenu;
    @FXML
    private AnchorPane toolBar;
    @FXML
    private AnchorPane view;
    @FXML
    private AnchorPane properties, layers;
    @FXML
    private  Button drawBtn;
    @FXML
    private Slider strokeSlide;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private TextField strokeTextBox;

    private File file;
    private double width, height;
    private EditingView editingView = new EditingView();
    private List<String> recents = new ArrayList<String>();
    private Stage layout_stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new UIInitializer(toolBar, properties);
    }

    // File
    @FXML
    void newMI(ActionEvent event) {
        layout_stage = new Stage();
        layout_stage.setTitle("Set Layout");
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(30));
        pane.setHgap(20);
        pane.setVgap(20);

        TextField widthTextField = new TextField("700");
        TextField heightTextField = new TextField("700");
        Button apply = new Button("Apply");

        apply.setOnAction((e) -> {
            width = Double.parseDouble(widthTextField.getText());
            height = Double.parseDouble(heightTextField.getText());
            layout_stage.close();
            openNew(width, height);
        });

        pane.add(new Label("Set Layout Size"), 0,0);
        pane.add(new Label("Width: "), 0, 1);
        pane.add(widthTextField, 1, 1);
        pane.add(new Label("Height: "), 0, 2);
        pane.add(heightTextField, 1, 2);
        pane.add(apply, 1, 3);

        Scene scene = new Scene(pane, 500, 500);
        scene.getStylesheets().add("style.css");
        layout_stage.getIcons().add(new Image("logo.png"));
        layout_stage.setScene(scene);
        layout_stage.setAlwaysOnTop(true);
        layout_stage.show();
    }

    @FXML
    void loginAction(){
        Stage stage = new Stage();
        stage.setTitle("Sign In");

        // Start Server; running in the background
        Server server = new Server();
        server.start();

        // Start Client
        Client client = new Client();

        Scene scene = new Scene(client.start(), 450, 200);
        scene.getStylesheets().add("style.css");
        stage.getIcons().add(new Image("logo.png"));
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    @FXML
    void adminMIAction(){
        Stage stage = new Stage();
        stage.setTitle("Admin");

        // Start Server; running in the background
        Server server = new Server();
        server.start();

        // Start Admin
        Admin admin = new Admin();

        Scene scene = new Scene(admin.start(), 450, 400);
        scene.getStylesheets().add("style.css");
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    void openNew(double width, double height) {
        Stage stage = new Stage();
        Pane pane = new Pane();

        editingView.Initialize(layers, width, height);
        pane.getChildren().add(editingView.EditView(width, height));

        Scene scene = new Scene(pane, width, height);
        scene.getStylesheets().add("style.css");
        scene.setOnKeyPressed(e->{
            if(e.getCode() == KeyCode.Z && e.isControlDown()){
                editingView.layerView.getSelected().undo();
            }
        });

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
            editingView.layerView.addImage(image);
            recents.add(file.toURI().toString());
        }
    }

    @FXML
    void openRecent(ActionEvent event) {
        openRecentMenu.getItems().clear();
        for(String s : recents) {
            MenuItem mi = new MenuItem(s);
            mi.setOnAction((e) -> {
                Image image = new Image(s);
                editingView.layerView.addImage(image);
            });
            openRecentMenu.getItems().add(mi);
        }
    }

    @FXML
    void save(ActionEvent event) {
        FileChooser savefile = new FileChooser();
        savefile.setTitle("Save");
        if (file != null) {
            try {
                WritableImage writableImage = new WritableImage((int)width, (int)height);
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
            WritableImage writableImage = new WritableImage((int)width, (int)height);
            EditingView.anchorPaneEditView.snapshot(null, writableImage);
            RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
            ImageIO.write(renderedImage, "png", file);
        } catch (IOException ex) {
            System.out.println("Error!");
        }
    }

    @FXML
    void quit(ActionEvent event) {
        Stage stage = new Stage();
        stage.setTitle("Quit");

        // Declare VBox and Hbox
        VBox vbox = new VBox();
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(30));
        HBox hbox = new HBox();
        hbox.setSpacing(20);
        hbox.setPadding(new Insets(10, 30, 10, 50));

        // Set up label and buttons
        Label prompt = new Label("Are you sure you want to quit?");
        Button yes = new Button("Yes");
        yes.setOnAction((e) -> { System.exit(0); });
        Button no = new Button("No");
        no.setOnAction((e) -> {stage.close();});

        // Add all buttons and label
        hbox.getChildren().addAll(yes, no);
        vbox.getChildren().addAll(prompt, hbox);

        Scene scene = new Scene(vbox,300,150);
        scene.getStylesheets().add("style.css");
        stage.getIcons().add(new Image("logo.png"));
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();
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
        scene.getStylesheets().add("style.css");
        stage.getIcons().add(new Image("logo.png"));
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

}