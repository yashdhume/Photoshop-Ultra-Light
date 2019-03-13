package Tools;

import Main.EditingView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
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
    public void drawOnAnchor(AnchorPane anchorPane){
        EditingView editingView = new EditingView();
        if(editingView.layerView.getCompositeImageView().getImage()!=null) {
            System.out.println(editingView.layerView.getCompositeImageView().getImage());
            final double maxX = editingView.layerView.getCompositeImageView().getImage().getWidth();
            final double maxY = editingView.layerView.getCompositeImageView().getImage().getHeight();


            editingView.layerView.getCompositeImageView().setOnMousePressed((MouseEvent e) -> {
                initX = e.getSceneX();
                initY = e.getSceneY();
                e.consume();
            });
            editingView.layerView.getCompositeImageView().setOnMouseDragged((MouseEvent e) -> {
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
    public void drawOnImage(ImageView imageView){
        Image[] image={null};
        image[0]=imageView.getImage();
        imageView.setFitHeight(image[0].getHeight());
        imageView.setFitWidth(image[0].getWidth());
        imageView.setImage(image[0]);
        EditingView editingView = new EditingView();
        imageView.setOnMouseDragged(event -> {
            double x=event.getX();
            double y=event.getY();
            WritableImage wi=new WritableImage(image[0].getPixelReader(),(int)image[0].getWidth(),(int)image[0].getHeight());
            PixelWriter pw=wi.getPixelWriter();
            pw.setColor((int)x,(int)y,color);
            image[0]=wi;
            editingView.layerView.getCompositeImageView().setImage(image[0]);

        });
    }

}
