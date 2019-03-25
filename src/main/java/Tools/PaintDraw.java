package Tools;

import Global.MouseState;
import Main.EditingView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class PaintDraw {
    private double initX, initY;
    private Color color;
    private int stroke;

    public PaintDraw(Color color, int stroke){
        this.color=color;
        this.stroke = stroke;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public void setStroke(int stroke){
        this.stroke = stroke;
    }

    public void drawOnAnchor(AnchorPane anchorPane){
        if(EditingView.layerView.getSelectedAsImage().getImage()!=null && EditingView.mouseState == MouseState.DRAW) {
            System.out.println(EditingView.layerView.getSelectedAsImage().getImage());
            final double maxX = EditingView.layerView.getSelectedAsImage().getImage().getWidth();
            final double maxY = EditingView.layerView.getSelectedAsImage().getImage().getHeight();


            EditingView.layerView.getSelectedAsImage().setOnMousePressed((MouseEvent e) -> {
                initX = e.getSceneX();
                initY = e.getSceneY();
                e.consume();
            });
            EditingView.layerView.getSelectedAsImage().setOnMouseDragged((MouseEvent e) -> {
                if (e.getSceneX() < maxX && e.getSceneY() < maxY) {
                    Line line = new Line(initX, initY, e.getSceneX(), e.getSceneY());
                    line.setStroke(color);
                    line.setSmooth(true);
                    line.setStrokeWidth(stroke);
                    anchorPane.getChildren().add(line);
                }

                initX = e.getSceneX() > maxX ? maxX : e.getSceneX();
                initY = e.getSceneY() > maxY ? maxY : e.getSceneY();
            });
        }
    }
    public void drawOnCanvas(){
        Canvas canvas = new Canvas(700, 700);
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.strokeRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(50);
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            gc.beginPath();
            gc.moveTo(event.getX(), event.getY());
            gc.stroke();
        });
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            gc.lineTo(event.getX(), event.getY());
            gc.stroke();
        });
    }
}