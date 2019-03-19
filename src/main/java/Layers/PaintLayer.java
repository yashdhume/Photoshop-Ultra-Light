package Layers;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class PaintLayer extends Layer {
    ImageView current;
    ArrayList<ImageView> versions;
    public PaintLayer(String name, int width, int height){
        super(name);
        current = new ImageView();
        current.setFitWidth(width);
        current.setFitHeight(height);
        versions = new ArrayList<>();
    }

    @Override
    public Pane getLayer() {
        Pane pane = new Pane();
        pane.getChildren().add(current);
        return pane;
    }

    public ImageView getImageView() {
        return current;
    }
    public void setImageView(ImageView imageView){
        versions.add(current);
        current = imageView;
    }

    @Override
    public void undo() {
        if (versions.size() >0) {
            current = versions.get(versions.size() - 1);
            versions.remove(versions.size() - 1);
        }
        else super.undo();
    }
}
