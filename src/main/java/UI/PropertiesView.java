package UI;

import CamCapture.CamCaptureDemo;
import ImageScraper.ImageScraperView;
import Main.EditingView;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class PropertiesView {
    AnchorPane propertiesPane;
    EditingView editingView = new EditingView();

    public PropertiesView(AnchorPane pane) {
        propertiesPane = pane;
        GetPropertiesView();
    }

    public void GetPropertiesView() {
        GridPane gp = new GridPane();
        gp.setHgap(10);
        gp.setVgap(10);

        Button picGoogleBtn = new Button("Get Picture from Google");
        picGoogleBtn.setOnAction((event) -> {
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
        });

        Button cameraBtn = new Button("Take Picture from your Camera");
        cameraBtn.setOnAction((event) -> {
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
        });



        gp.add(picGoogleBtn, 0, 0);
        gp.add(cameraBtn, 0, 1);
        propertiesPane.getChildren().addAll(gp);
    }

}
