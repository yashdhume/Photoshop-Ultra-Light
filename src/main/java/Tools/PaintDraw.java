package Tools;

import Global.MouseState;
import Layers.PaintLayer;
import Main.EditingView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

public class PaintDraw {
    private double initX, initY;
    private Color color;
    private int stroke;

    public PaintDraw(Color color, int stroke){
        this.color=color;
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
    public void drawOnImage(AnchorPane anchorPane, ImageView imageView){
        Image[] image={null};
        image[0]=imageView.getImage();
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(700);
        imageView.setFitHeight(700);
        imageView.setImage(image[0]);
        EditingView editingView = new EditingView();
        anchorPane.setOnMouseDragged(event -> {
            double x=event.getX();
            double y=event.getY();

            WritableImage wi=new WritableImage(image[0].getPixelReader(),(int)image[0].getWidth(),(int)image[0].getHeight());
            PixelWriter pw=wi.getPixelWriter();
            pw.setColor((int)x,(int)y,color);
            image[0]=wi;
            editingView.layerView.getSelectedAsImage().setImage(image[0]);
        });
    }

    public void setColor(Color color){
        this.color = color;
    }

    public void setStroke(int stroke){
        this.stroke = stroke;
    }

}