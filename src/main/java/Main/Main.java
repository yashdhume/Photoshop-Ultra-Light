package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/MainUI.fxml"));
        primaryStage.setTitle("Photoshop Ultra Light");
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setMaximized(true);
        primaryStage.setScene(new Scene(root,((primaryScreenBounds.getWidth() - primaryStage.getWidth()) / 2),((primaryScreenBounds.getHeight() - primaryStage.getHeight()) / 4)));
        primaryStage.getIcons().add(new Image("logo.png"));
        primaryStage.show();
    }


    public static void main(String[] args) {
        nu.pattern.OpenCV.loadShared();
        Application.launch(args);
    }
}