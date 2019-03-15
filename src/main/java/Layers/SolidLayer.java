package Layers;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class SolidLayer extends Layer {
    private Rectangle rectangle;
    public SolidLayer(String name, double width, double height){
        super(name);
        rectangle = new Rectangle((int)width, (int)height);
        layerType = LayerType.SOLID;
    }
    public SolidLayer(String name, double width, double height, Color color){
        super(name);
        rectangle = new Rectangle((int)width, (int)height, color);
    }

    @Override
    public Pane getLayer() {
        Pane pane = new Pane();
        pane.getChildren().add(rectangle);
        return pane;
    }
}
