package Layers;

import Global.OpenCVMat;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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
    private StackPane editable;


    public LayerView(AnchorPane pane){
        layers.add(new SolidLayer("Background", 700, 700, Color.WHITE));
        layers.add(new ImageLayer("middle", new Image("file:/C:/Users/Kashif/IdeaProjects/Photoshop-Ultra-Light/src/main/resources/googleIcon.png")));
        layers.add(new ImageLayer("forground", new Image("file:/C:/Users/Kashif/IdeaProjects/Photoshop-Ultra-Light/src/main/resources/cameraIcon.png")));
        controlPane = pane;
        composite = new ImageView();
        indexofSelected = 1;
        /*
        composite.setOnMouseDragged(e->{
            if (indexofSelected != null){
                layers.get(indexofSelected).setLocation(new Point(e.getX(), e.getY()));
                renderToImage();
            }
        });
        */
        editable = new StackPane();
        editable.setOnMouseDragged(e->{
            layers.get(indexofSelected).setLocation(new Point(e.getSceneX(), e.getSceneY()));
        });
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
                int a = Math.abs(layers.size()-layerPane.getRowIndex(temp));
                layers.get(a).selectLayer();
                layers.get(indexofSelected).unselectLayer();
                indexofSelected = a;
            });
            layerPane.add(temp, 0, Math.abs(i-layers.size()));

        }
        layerPane.setLayoutX(20);
        layerPane.setLayoutY(300);
        controlPane.getChildren().add(layerPane);

        renderEditables();
    }

    public void renderEditables(){
        for (int i = 0; i < layers.size(); i++){
            if (layers.get(i).getType() == LayerType.IMAGE){
                ImageView imageView = ((ImageLayer)layers.get(i)).getImageView();
                imageView.setPreserveRatio(true);
                imageView.setFitWidth(700);
                imageView.setFitHeight(700);
                editable.getChildren().add(new Pane(imageView));
            }
            else editable.getChildren().add(layers.get(i).getLayer());
        }
    }
    public void addLayer(){
        String name = "New Layer " + layers.size();
        //layers.add(new Layer(name));
        renderLayers();
    }
    public void addImage(Image image){
        String name = "New Layer " + layers.size();
        layers.add(new ImageLayer(name, image));
        renderLayers();
    }
    public  Layer getSelected(){
        if (indexofSelected == null)
            return null;
        return layers.get(indexofSelected);
    }
    public ImageView getSelectedAsImage(){
        if (layers.get(indexofSelected).getType() == LayerType.IMAGE){
            return ((ImageLayer)layers.get(indexofSelected)).getImageView();
        }
        else if (layers.get(indexofSelected).getType() == LayerType.SOLID) {
            return new ImageView();
        }
        else if (layers.get(indexofSelected).getType() == LayerType.ADJUST){
            OpenCVMat mat = new OpenCVMat();
            Mat core = new Mat();
            for (int i = 0; i < indexofSelected; i++){
                if (layers.get(i).getType() == LayerType.IMAGE && layers.get(i).isVisible)
                    overlayImage(core, mat.imageToMatrix(((ImageLayer)layers.get(indexofSelected)).getImage()), core, layers.get(i).getLocation());
            }
            return new ImageView(mat.mat2Image(core));
        }
        return null;
    }
    public void updateSelected(Layer layer){
        if (indexofSelected == null)
            return;
        layers.set(indexofSelected, layer);
        renderLayers();
    }

    public ImageView getCompositeImageView(){
        renderToImage();
        return composite;
    }

    public StackPane getEditableStack(){
        if (editable == null){
            editable = new StackPane();
            renderEditables();
        }
        return editable;
    }

    public Image renderToImage(){
        OpenCVMat mat = new OpenCVMat();
        Mat core = mat.imageToMatrix(((ImageLayer)layers.get(indexofSelected)).getImage());
        for (int i = 1; i < layers.size(); i++){
            if (layers.get(i).getType() == LayerType.IMAGE && layers.get(i).isVisible)
                overlayImage(core, mat.imageToMatrix(((ImageLayer)layers.get(indexofSelected)).getImage()), core, layers.get(i).getLocation());
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
