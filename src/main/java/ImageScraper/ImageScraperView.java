package ImageScraper;

import Main.EditingView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import java.io.IOException;
import java.util.ArrayList;

public class ImageScraperView {
    public  static ArrayList<Image> images = new ArrayList<>();
    public  static ArrayList<ImageView> imageView = new ArrayList<>();
    public  static int numOfSearchResults = 100;
    public  static int numOfPicturesDiplayed =10;

    public static void loadImage(String textField)throws IOException {
        ArrayList<String> googleImagesLinks = ImageScraper.getImageArray(textField, numOfSearchResults);
        for (int i = 0; i<numOfPicturesDiplayed; i++) {
            images.add(new Image(googleImagesLinks.get(i)));
            System.out.println(images.get(i).errorProperty());
        }
        int numErros=0;
        for (int j = 0; j < images.size(); j++) {
            if(images.get(j).isError()) {
                //images.remove(j);
                images.set(j,new Image(googleImagesLinks.get(j+images.size())));
                numErros++;
                System.out.println("test");
            }
        }
        System.out.println(numErros+ " Broken Pics");
        for (int i = 0; i < images.size(); i++) {
            imageView.add(new ImageView(images.get(i)));
            imageView.get(i).setFitHeight(1000);
            imageView.get(i).setFitWidth(500);
            imageView.get(i).setPreserveRatio(true);
            imageView.get(i).setCache(true);
            final int index = i;
            Global.DragandDrop.localArray(imageView, index, EditingView.imageViewEditView);
        }
    }
    public static ScrollPane googleImageView(){
        final TextField textField = new TextField();
        Button btn = new Button("Search");
        ScrollPane scroll = new ScrollPane();


        final FlowPane flowPane = new FlowPane();
        flowPane.setPadding(new Insets(5, 5, 5, 5));
        flowPane.setVgap(5);
        flowPane.setHgap(5);
        flowPane.setPrefWrapLength(5);
        flowPane.setAlignment(Pos.CENTER);
        flowPane.getChildren().add(textField);
        flowPane.getChildren().add(btn);
        btn.setOnAction(e-> {

                try {
                    images.clear();
                    imageView.clear();
                    loadImage(textField.getText());
                    for (int i = 0; i < imageView.size(); i++) {
                        flowPane.getChildren().add(imageView.get(i));
                    }
                }
                catch (IOException ex){

                }

        });

        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setContent(flowPane);
        return scroll;


    }
}
