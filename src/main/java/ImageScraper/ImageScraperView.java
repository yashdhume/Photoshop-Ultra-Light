package ImageScraper;

import Global.DragandDrop;
import Main.EditingView;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;


public class ImageScraperView implements Runnable {
    private ArrayList<Image> images ;
    private ArrayList<ImageView> imageView;
    private int numOfPicturesDisplayed;
    private String text;
    private final FlowPane flowPane = new FlowPane();
    public ImageScraperView(String text) {
        this.text = text;
        images = new ArrayList<>();
        imageView = new ArrayList<>();
        numOfPicturesDisplayed = 10;
    }
    public void run() {
        int numOfSearchResults = 100;
        ImageScraper imageScraper = new ImageScraper();
        ArrayList<String> googleImagesLinks = imageScraper.getImageArray(text, numOfSearchResults);
        for (int i = 0; i < numOfPicturesDisplayed; i++) {
            images.add(new Image(googleImagesLinks.get(i)));
            System.out.println(images.get(i).errorProperty());
        }
        int numErrors = 0;
        for (int j = 0; j < images.size(); j++) {
            if (images.get(j).isError()) {
                images.set(j, new Image(googleImagesLinks.get(j + images.size())));
                numErrors++;
            }
        }
        System.out.println(numErrors + " Broken Pics");
        Platform.runLater(this::loadImages);
    }
    private void loadImages(){
        for (int i = 0; i < images.size(); i++) {
            EditingView editingView = new EditingView();
            imageView.add(new ImageView(images.get(i)));
            imageView.get(i).setFitHeight(1000);
            imageView.get(i).setFitWidth(500);
            imageView.get(i).setPreserveRatio(true);
            imageView.get(i).setCache(true);
            final int index = i;
            DragandDrop dragandDrop = new DragandDrop();
            dragandDrop.localArray(imageView, index, editingView.imageViewEditView);
        }
        for (int i = 0; i < imageView.size(); i++) {
            flowPane.getChildren().add(imageView.get(i));
        }
        flowPane.getChildren().remove(flowPane.getChildren().get(1));
    }
    private void btnPress(VBox vBox, TextField textField, String  extraText){
        images.clear();
        imageView.clear();
        flowPane.getChildren().clear();
        flowPane.getChildren().add(vBox);
        this.text = textField.getText();
        this.text = this.text+extraText;
        ProgressBar progressBar = new ProgressBar();
        flowPane.getChildren().add(progressBar);
        new Thread(this).start();
    }
    public ScrollPane googleImageView() {
        final TextField textField = new TextField();
        Button searchBtn = new Button("Search");
        Button funnyBtn = new Button("Funny");
        Button cuteBtn = new Button("Cute");
        ScrollPane scroll = new ScrollPane();
        VBox vBox = new VBox();
        flowPane.setPadding(new Insets(5, 5, 5, 5));
        flowPane.setVgap(5);
        flowPane.setHgap(5);
        flowPane.setPrefWrapLength(5);
        flowPane.setAlignment(Pos.CENTER);
        vBox.getChildren().add(textField);
        HBox hBox = new HBox();
        hBox.getChildren().addAll(searchBtn,funnyBtn, cuteBtn);
        vBox.getChildren().add(hBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(5,5,5,5));
        flowPane.getChildren().add(vBox);
        searchBtn.setOnAction(e -> btnPress(vBox, textField, ""));
        funnyBtn.setOnAction(e -> btnPress(vBox, textField, " funny"));
        cuteBtn.setOnAction(e -> btnPress(vBox, textField, " cute"));
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setContent(flowPane);
        return scroll;


    }
}
