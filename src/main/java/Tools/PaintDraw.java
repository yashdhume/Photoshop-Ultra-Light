package Tools;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import static Main.EditingView.imageViewEditView;

public class PaintDraw {
    private double initX, initY;
    private Color color;
    private int stroke;
    public PaintDraw(Color color, int stroke){
        this.color=color;
        this.stroke = stroke;
    }
    public void draw(AnchorPane anchorPane){
        if(imageViewEditView.getImage()!=null) {
            System.out.println(imageViewEditView.getImage());
            final double maxX = imageViewEditView.getImage().getWidth();
            final double maxY = imageViewEditView.getImage().getHeight();


            imageViewEditView.setOnMousePressed((MouseEvent e) -> {
                initX = e.getSceneX();
                initY = e.getSceneY();
                e.consume();
            });
            imageViewEditView.setOnMouseDragged((MouseEvent e) -> {
                if (e.getSceneX() < maxX && e.getSceneY() < maxY) {
                    Line line = new Line(initX, initY, e.getSceneX(), e.getSceneY());
                    line.setFill(null);
                    line.setStroke(color);
                    line.setStrokeWidth(stroke);
                    anchorPane.getChildren().add(line);
                }

                initX = e.getSceneX() > maxX ? maxX : e.getSceneX();
                initY = e.getSceneY() > maxY ? maxY : e.getSceneY();
            });
        }
    }
}
