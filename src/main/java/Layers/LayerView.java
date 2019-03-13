package Layers;

import Global.OpenCVMat;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;

import java.util.ArrayList;


public class LayerView {

    private AnchorPane controlPane;
    private GridPane layerPane;
    private ArrayList<Layer> layers = new ArrayList<>();
    private Integer indexofSelected;
    private ImageView composite;
    private  StackPane editable;


    public LayerView(AnchorPane pane){
        layers.add(new Layer("background", new Image("file:/C:/Users/Kashif/IdeaProjects/Photoshop-Ultra-Light/src/main/resources/logo.png")));
        layers.add(new Layer("middle", new Image("file:/C:/Users/Kashif/IdeaProjects/Photoshop-Ultra-Light/src/main/resources/googleIcon.png")));
        layers.add(new Layer("forground", new Image("file:/C:/Users/Kashif/IdeaProjects/Photoshop-Ultra-Light/src/main/resources/cameraIcon.png")));
        controlPane = pane;
        composite = new ImageView();
        indexofSelected = 1;
        composite.setOnMouseDragged(e->{
            if (indexofSelected != null){
                layers.get(indexofSelected).setLocation(new Point(e.getX(), e.getY()));
                renderToImage();
            }
        });
        editable = new StackPane();
        renderLayers();
    }
    public void renderLayers(){
        controlPane.getChildren().remove(layerPane);
        Button button = new Button("Create new Layer");
        button.setOnAction(e->{
            addLayer();
            renderLayers();
        });
        button.setLayoutX(20);
        button.setLayoutY(200);
        controlPane.getChildren().add(button);
        if (layers.size() == 0){
            return;
        }
        layerPane = new GridPane();
        for (int i = layers.size()-1; i >= 0; i--){
            GridPane temp = layers.get(i).getLayerView();
            temp.setOnMouseClicked(e->{
                int a = layerPane.getRowIndex(temp);
                layers.get(a).isSelected = true;
                layers.get(indexofSelected).isSelected =false;
                indexofSelected = a;
            });
            layerPane.add(temp, 0, Math.abs(i-layers.size()));

        }
        layerPane.setLayoutX(20);
        layerPane.setLayoutY(300);
        controlPane.getChildren().add(layerPane);
        renderToImage();

    }
    public void addLayer(){
        String name = "New Layer " + layers.size();
        layers.add(new Layer(name));
        renderLayers();
    }
    public void addImage(Image image){
        String name = "New Layer " + layers.size();
        layers.add(new Layer(name, image));
        renderLayers();
    }
    public ImageView getSelectedImageView(){
        if (indexofSelected == null) {
            if (layers.size() == 0)
                return new ImageView();
            return layers.get(indexofSelected).getImageView();
        }
        return layers.get(indexofSelected).getImageView();
    }
    public void updateSelectedImageView(Image img){
        if (indexofSelected == null)
            return;
        layers.get(indexofSelected).setImageView(new ImageView(img));
        renderToImage();
    }
    public ImageView getCompositeImageView(){
        renderToImage();
        return composite;
    }
    public StackPane getEditableStack(){
        return editable;
    }

    public Image renderToImage(){
        OpenCVMat mat = new OpenCVMat();
        Mat core = mat.imageToMatrix(layers.get(0).getImage());
        for (int i = 1; i < layers.size(); i++){
            if (layers.get(i).getType() == LayerType.OBJECT && layers.get(i).isVisible)
            overlayImage(core, mat.imageToMatrix(layers.get(i).getImage()), core, layers.get(i).getLocation());
        }
        composite.setImage(mat.mat2Image(core));
        return composite.getImage();
    }
    public void overlayImage(Mat background,Mat foreground,Mat output, Point location){

        background.copyTo(output);

        for(int y = (int) Math.max(location.y , 0); y < background.rows(); ++y){

            int fY = (int) (y - location.y);

            if(fY >= foreground.rows())
                break;

            for(int x = (int) Math.max(location.x, 0); x < background.cols(); ++x){
                int fX = (int) (x - location.x);
                if(fX >= foreground.cols()){
                    break;
                }

                double opacity;
                double[] finalPixelValue = new double[4];

                opacity = foreground.get(fY , fX)[3];

                finalPixelValue[0] = background.get(y, x)[0];
                finalPixelValue[1] = background.get(y, x)[1];
                finalPixelValue[2] = background.get(y, x)[2];
                finalPixelValue[3] = background.get(y, x)[3];

                for(int c = 0;  c < output.channels(); ++c){
                    if(opacity > 0){
                        double foregroundPx =  foreground.get(fY, fX)[c];
                        double backgroundPx =  background.get(y, x)[c];

                        float fOpacity = (float) (opacity / 255);
                        finalPixelValue[c] = ((backgroundPx * ( 1.0 - fOpacity)) + (foregroundPx * fOpacity));
                        if(c==3){
                            finalPixelValue[c] = foreground.get(fY,fX)[3];
                        }
                    }
                }
                output.put(y, x,finalPixelValue);
            }
        }
    }
}
