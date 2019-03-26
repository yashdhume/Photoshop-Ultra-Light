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
        EditingView.Render();
    }

    @Override
    public Pane getLayer() {
        return current;
    }

    @Override
    public void crop(Point start, Point end) {
        versions.add(new Pane(current));
        canvas.setWidth(end.x-start.x);
        canvas.setHeight(end.y - start.y);

        canvas.setLayoutX(start.x);
        canvas.setLayoutY(start.y);

        EditingView.Render();

    }

    @Override
    public void setLocation(Point p) {
        versions.add(new Pane(current));
        location.x = p.x - canvas.getWidth();
        location.y = p.y - canvas.getHeight();

        canvas.setLayoutX(location.x);
        canvas.setLayoutY(location.y);
        EditingView.Render();
    }

    //Initializes the stroke.
    public void onPaint(Point point){
         gc = canvas.getGraphicsContext2D();
         gc.setStroke(ToolbarView.GlobalColor);
         gc.setLineWidth(PaintDrawUI.GlobalStroke);
         gc.beginPath();
         gc.moveTo(point.x, point.y);
         gc.stroke();

    }
    //Completes the stroke.
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
