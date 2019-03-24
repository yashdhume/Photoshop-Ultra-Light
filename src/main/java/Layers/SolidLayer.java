package Layers;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.opencv.core.Point;

public class SolidLayer extends Layer {
    private Rectangle rectangle;

    public SolidLayer(String name, double width, double height, Color color){
        super(name);
        rectangle = new Rectangle((int)width, (int)height, color);
    }

    @Override
    public void setLocation(Point p) {
        location.x = p.x - rectangle.getWidth()/2;
        location.y = p.y - rectangle.getHeight()/2;
        rectangle.setX(location.x);
        rectangle.setY(location.y);
    }

    @Override
    public void rotate(double degrees) {
        rectangle.setRotate(rectangle.getRotate()+degrees);
    }

    @Override
    public void crop(Point start, Point end) {
        rectangle.setX(start.x);
        rectangle.setY(start.y);
        rectangle.setWidth(end.x - start.x);
        rectangle.setHeight(end.y - start.y);
    }

    @Override
    public Pane getLayer() {
        Pane pane = new Pane();
        pane.getChildren().add(rectangle);
        return pane;
    }
}
