package Layers;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.opencv.core.Point;

import java.util.ArrayList;

public class PaintLayer extends Layer {
    Pane current;
    Canvas canvas;
    ArrayList<Pane> versions;
    public PaintLayer(String name){
        super(name);
        current = new Pane();
        canvas = new Canvas();
        current.getChildren().add(canvas);
        versions = new ArrayList<>();
    }

    @Override
    public Pane getLayer() {
        return current;
    }

    @Override
    public void crop(Point start, Point end) {
        versions.add(new Pane(current));

        current.setLayoutY(start.y);
        current.setLayoutX(start.x);

        current.setScaleX(end.x-start.x);
        current.setScaleY(end.y - start.y);
    }

    @Override
    public void setLocation(Point p) {
        versions.add(new Pane(current));
        location.x = p.x - current.getScaleX();
        location.y = p.y - current.getScaleY();

        current.setLayoutX(location.x);
        current.setLayoutY(location.y);
    }

    //Implement this Function
    public void paint(){
        versions.add(new Pane(current));
        return;
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
