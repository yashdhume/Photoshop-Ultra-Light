package Layers;

import Global.OpenCVMat;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;

import java.util.ArrayList;

public class ImageLayer extends Layer{
    ImageView imageView, originalImage;
    ArrayList<ImageView> versions;
    public ImageLayer(String name, Image image){
        super(name);
        imageView = new ImageView(image);
        originalImage = new ImageView(image);
        layerType = LayerType.IMAGE;
        versions = new ArrayList<>();
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(image.getHeight());
        imageView.setFitWidth(image.getWidth());
    }
    public void setImage(Image img){
        imageView.setImage(img);
        originalImage = new ImageView(img);
        layerType = LayerType.IMAGE;
        versions = new ArrayList<>();
    }

    @Override
    public void undo() {
        System.out.println(versions.size());
        if (versions.size() > 0){
            imageView.setImage(versions.get(versions.size()-1).getImage());
            versions.remove(versions.size()- 1);
        }
        else super.undo();
    }

    @Override
    public Pane getLayer() {
        Pane pane = new Pane();
        pane.getChildren().add(imageView);
        return pane;
    }
    public void applyEffect(Image image){
        System.out.println("EFFECT APPLIED");;
        versions.add(new ImageView(imageView.getImage()));
        imageView.setImage(image);
    }
    public void fitImage(double width, double height){
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
    }
    @Override
    public void setLocation(Point p) {
        location.x = p.x - imageView.getImage().getWidth()/2;
        location.y = p.y - imageView.getImage().getHeight()/2;
        imageView.setTranslateX(location.x);
        imageView.setTranslateY(location.y);

    }

    @Override
    protected Pane getThumbnail() {
        Pane p = new Pane();
        ImageView iv = new ImageView(originalImage.getImage());
        iv.setFitWidth(20);
        iv.setFitHeight(20);
        p.getChildren().add(iv);
        return p ;
    }

    @Override
    public void rotate(double degrees) {
        imageView.setRotate(imageView.getRotate()+degrees);
    }
    @Override
    public void resize(double size) {
        if (imageView.getFitHeight() - size < 20 || imageView.getFitWidth() < 20){
            return; 
        }
        imageView.setFitWidth(imageView.getFitWidth()-size);
        imageView.setFitHeight(imageView.getFitHeight()-size);
    }

    @Override
    public void crop(Point start, Point end) {
        double width = end.x- start.x;
        double height = end.y - start.y;
        System.out.println(width + " " + height);
        System.out.println(imageView.getImage().getWidth()+ " " + imageView.getImage().getHeight());
        int adjStartX = (int)(start.x -location.x);
        int adjStartY = (int)(start.y -location.y);
        Point adj = new Point((int)start.x, (int)start.y);
        Point endAdj = new Point((int)end.x - (int)location.x, (int)end.y - (int)location.y);

        //System.out.println(start.toString() + " " + end.toString());
        imageView.setViewport(new Rectangle2D(start.x-location.x, start.y-location.y, width, height));
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        imageView.setLayoutX(start.x);
        imageView.setLayoutY(start.y);
        imageView.setSmooth(true);

        /*
        OpenCVMat openCVMat = new OpenCVMat();
        Rect subImage = new Rect(adj, endAdj);
        System.out.println(subImage);
        Mat a = new Mat(openCVMat.imageToMatrix(imageView.getImage()), subImage);
        //a.submat(new Rect((int)start.x, (int)start.y,(int)end.x, (int)end.y));

        imageView.setImage(openCVMat.mat2Image(a));
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        imageView.setLayoutY(start.y);
        imageView.setLayoutX(start.x);
        */



    }

    public ImageView getOriginalImage(){return originalImage;}
    public Image getImage(){
        return imageView.getImage();
    }
    public ImageView getImageView(){
        return imageView;
    }
    public void setImageView(ImageView iv){
        imageView = iv;
    }
}
