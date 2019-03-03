package sample;

import ImageScraper.ImageScraper;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {
    ArrayList<Image> images = new ArrayList<Image>();
    ArrayList<ImageView> imageView = new ArrayList<ImageView>();
    int numOfSearchResults = 10;
    public void loadImage(String textField)throws IOException{
        ArrayList<String> googleImagesLinks = ImageScraper.getImageArray(textField, numOfSearchResults);
        // Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        for (int i = 0; i < googleImagesLinks.size(); i++) {
            images.add(new Image(googleImagesLinks.get(i)));
        }
        for (int i = 0; i < images.size(); i++) {
            imageView.add(new ImageView(images.get(i)));
            imageView.get(i).setFitHeight(1000);
            imageView.get(i).setFitWidth(500);
            imageView.get(i).setPreserveRatio(true);
        }
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        final TextField textField = new TextField();
        Button btn = new Button("Search");



        final FlowPane flowPane = new FlowPane();
        flowPane.setPadding(new Insets(5, 5, 5, 5));
        flowPane.setVgap(5);
        flowPane.setHgap(5);
        flowPane.setPrefWrapLength(5);
        flowPane.setAlignment(Pos.CENTER);
        flowPane.getChildren().add(textField);
        flowPane.getChildren().add(btn);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                try {
                    loadImage(textField.getText());
                    for (int i = 0; i < imageView.size(); i++) {
                        flowPane.getChildren().add(imageView.get(i));
                    }
                }
                catch (IOException ex){

                }
            }
        });
        final ScrollPane scroll = new ScrollPane();
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);    // Horizontal scroll bar
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);    // Vertical scroll bar
        scroll.setContent(flowPane);

        Scene scene = new Scene(scroll, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        Application.launch(args);
    }
}