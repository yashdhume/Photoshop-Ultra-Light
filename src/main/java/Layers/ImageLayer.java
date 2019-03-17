package Layers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.opencv.core.Point;

import java.util.ArrayList;

public class ImageLayer extends Layer{
    ImageView imageView, originalImage;
    ArrayList<ImageView> versions;
    public ImageLayer(String name, Image image){
        super(name);
        imageView = new ImageView(image);
        originalImage = new ImageView(image);
        layerType = LayerType.IMAGE;
        versions = new ArrayList<>();
    }
    public void setImage(Image img){
        imageView.setImage(img);
        originalImage = new ImageView(img);
        layerType = LayerType.IMAGE;
        versions = new ArrayList<>();
    }

    @Override
    public Pane getLayer() {
        Pane pane = new Pane();
        pane.setOnMouseDragged(e->{
            if (this.isSelected) {
                pane.setLayoutX(e.getX());
                pane.setLayoutY(e.getY());
            }
        });
        pane.getChildren().add(imageView);
        return pane;
    }
    public void applyEffect(ImageView image){
        versions.add(imageView);
        imageView = image;
    }
    @Override
    public void setLocation(Point p) {
        location.x = p.x - imageView.getImage().getWidth()/2;
        location.y = p.y - imageView.getImage().getHeight()/2;
        imageView.setTranslateX(location.x);
        imageView.setTranslateY(location.y);
    }
    public ImageView getOriginalImage(){return originalImage;}
    public Image getImage(){
        return imageView.getImage();
    }
    public ImageView getImageView(){
        return imageView;
    }
    public void setImageView(ImageView iv){
        imageView = iv;
    }
}
