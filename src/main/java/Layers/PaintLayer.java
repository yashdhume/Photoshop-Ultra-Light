package Layers;

import Main.EditingView;
import Tools.PaintDrawUI;
import UI.ToolbarView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.opencv.core.Point;

import java.util.ArrayList;

//Implement with Canvas
public class PaintLayer extends Layer {
    Pane current;
    Canvas canvas;
    ArrayList<Pane> versions;
    GraphicsContext gc;
    public PaintLayer(String name, double width, double height){
        super(name);
        current = new Pane();
        layerType = LayerType.PAINT;
        canvas = new Canvas(width, height);
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
    public void onPaint(Point point){
         gc = canvas.getGraphicsContext2D();
         gc.setStroke(ToolbarView.GlobalColor);
         gc.setLineWidth(PaintDrawUI.GlobalStroke);
         gc.beginPath();
         gc.moveTo(point.x, point.y);
         gc.stroke();

    }
    public void onFinishedPaint(Point point){
        versions.add(new Pane(current));
        gc.lineTo(point.x, point.y);
        gc.stroke();
        EditingView.Render();
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
