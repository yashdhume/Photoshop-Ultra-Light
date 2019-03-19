package Layers;

import Main.EditingView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.opencv.core.Point;

public class Layer {

    // Layer Information
    public String name;
    protected Boolean isVisible, isSelected;
    protected LayerType layerType;
    protected Point location;

    //Layer Display in Layer Panel
    GridPane pane;
    Rectangle thumbnail, backgroundColour;
    CheckBox visiblityCheckbox;
    Label layerName;


    public Layer(String name){
        this.name = name;
        layerType = LayerType.EMPTY;
        location = new Point(0,0);
        isSelected = false;
        isVisible = true;
    }

    //Create the Layer Box objects to be rendered in the Layers panel.
    private void generateLayer(){
        pane = new GridPane();
        pane.setPadding(new Insets(10));
        thumbnail = getThumbnail();
        visiblityCheckbox = new CheckBox();
        visiblityCheckbox.setSelected(true);
        visiblityCheckbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                isVisible = newValue;
                EditingView.Render();
            }
        });
        pane.add(visiblityCheckbox, 0, 0);
        pane.add(thumbnail, 1, 0);
        pane.add(new Label(this.name), 2, 0);
        pane.setStyle("-fx-background-color:#E8E5E8; -fx-opacity:1;");
    }

    private Rectangle getThumbnail(){
        if (layerType == LayerType.SOLID) return new Rectangle(20, 20, Color.BLACK);
        else if (layerType == LayerType.IMAGE) return new Rectangle(20, 20, Color.NAVY);
        else{
            Rectangle a= new Rectangle(20, 20);
            a.setFill(Color.RED);
            return a;
        }
    }

    //Get the LayerBox needed for the Layers Panel
    public GridPane getLayerView(){
        if (pane == null)
            generateLayer();
        return pane;
    }

    //Get the pane of the layer. Used primarily
    public Pane getLayer(){return null;}
    public void undo(){
        System.out.println("FAILED TO UNDO");
        return;
    }
    //Handle when the layer is selected or not
    public void selectLayer(){
        isSelected = true;
        pane.setStyle("-fx-background-color:#5AA4FF; -fx-opacity:1;");
    }

    public void unselectLayer(){
        isSelected = false;
        pane.setStyle("-fx-background-color:#E8E5E8; -fx-opacity:1;");
    }
    public void rotate(double degrees){
        return;
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
}
