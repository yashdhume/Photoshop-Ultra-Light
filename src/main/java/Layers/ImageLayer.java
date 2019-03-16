package Layers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.opencv.core.Point;

public class ImageLayer extends Layer{
    ImageView imageView;
    public ImageLayer(String name, Image image){
        super(name);
        imageView = new ImageView(image);
        layerType = LayerType.IMAGE;
    }
    public void setImage(Image img){
        imageView.setImage(img);
        layerType = LayerType.IMAGE;
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

    @Override
    public void setLocation(Point p) {
        location.x = p.x - imageView.getImage().getWidth()/2;
        location.y = p.y - imageView.getImage().getHeight()/2;
        imageView.setTranslateX(location.x);
        imageView.setTranslateY(location.y);
    }

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
