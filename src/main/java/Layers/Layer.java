package Layers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import org.opencv.core.Point;

public class Layer {
    private ImageView image;
    private String name;
    public Boolean isVisible, isSelected;
    private LayerType layerType;
    private Point location;
    public Layer(String name, Image img){
        isVisible = true;
        isSelected = false;
        this.name = name;
        this.image = new ImageView(img);
        layerType = LayerType.OBJECT;
        location = new Point(0,0);

    }
    public Layer(String name){
        this.name = name;
        layerType = LayerType.ADJUST;
        location = new Point(0,0);
    }

    public GridPane getLayerView(){
        isVisible =true;
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10));
        Rectangle thumbnail = new Rectangle(20,20);
        CheckBox checkBox = new CheckBox();
        checkBox.setSelected(true);
        checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                isVisible = newValue;
            }
        });
        pane.add(checkBox, 0, 0);
        pane.add(thumbnail, 1, 0);
        pane.add(new Label(this.name), 2, 0);

        return pane;
    }
    public void setLocation(Point p){
        location = p;
    }
    public Point getLocation(){
        return location;
    }
    public LayerType getType(){
        return layerType;
    }
    public void setImage(Image img){
        image.setImage(img);
        layerType = LayerType.OBJECT;
    }
    public Image getImage(){
        return image.getImage();
    }
    public ImageView getImageView(){
        return image;
    }
    public void setImageView(ImageView iv){
        image = iv;
    }
}
