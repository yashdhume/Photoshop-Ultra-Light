package Layers;

import Global.MouseState;
import Global.OpenCVMat;
import Main.EditingView;
import UI.ToolbarView;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
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


/*
       Welcome to the Layer View I hope you enjoy your stay:

       This Class Contains 3 Key Components:

       1. Layer Pane : The section of the program where you can see the order of your layers. RenderLayers
       will not only update layers in this section, but also rerender the Editable View

       2. Editable View: Rendering the Layers for editing purposes. All layers have a ".getLayer()" method
       which will return a pane estimating the look of the image. This is all compiled in renderEditables.
       This function is called when RenderLayers is called so there is no need to call this function with
       very few exceptions.

       3. Composite View: Renders the layers into a single ImageView for exporting. This should rarely be used
       and usually for the final copy. We may implement Adjustment Layers and merging layers in the future
       using this feature.

 */

public class LayerView {
    // Housing Pane of the Layer Blocks
    private AnchorPane controlPane;
    // Layout of the Layers
    private GridPane layerPane; //Note: Must be updated to table view

    // List of the Layers in the Scene
    private ArrayList<Layer> layers = new ArrayList<>();

    //Keeps track of the index of the selected layer
    private Integer indexofSelected, layerPaneIndex;

    //Composite Image View of the layers
    private ImageView composite;

    //Editable view of the Layers
    private StackPane editable;

    // Customized width and height
    double height, width;

    public LayerView(AnchorPane pane, double width, double height){
        //Recommended to Initialize with a solid layer
        layers.add(new SolidLayer("Background", width, height, Color.WHITE));

        // Assign customized height and width for layers
        this.width = width;
        this.height = height;

        //For testing I recommend adding some files by default
        layers.add(new ImageLayer("middle", new Image("file:C:/Users/Kashif/IdeaProjects/Photoshop-Ultra-Light/src/main/resources/cameraIcon.png")));
        layers.add(new ImageLayer("foreground", new Image("file:C:/Users/Kashif/IdeaProjects/Photoshop-Ultra-Light/src/main/resources/googleIcon.png")));


        controlPane = pane;
        composite = new ImageView();
        indexofSelected = 1;
        editable = new StackPane();
        InitializeButtons();
        renderLayers();
    }

    private void InitializeButtons(){

        Button solidButoon = new Button("Create new Solid");
        solidButoon.setOnAction(e->{
            this.addSolid(700, 700, ToolbarView.GlobalColor);
        });
        solidButoon.setLayoutX(20);
        solidButoon.setLayoutY(80);
        controlPane.getChildren().addAll(solidButoon);
    }

    //Renders Layers in the Layer pane and the Editable View
    //Use this when there are any major changes.
    public void renderLayers(){

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
        layerPane.setLayoutY(200);
        if (layerPaneIndex == null) {
            controlPane.getChildren().add(layerPane);
            controlPane.getChildren().indexOf(layerPane);
        }
        else controlPane.getChildren().set(layerPaneIndex, layerPane);

        renderEditables();
    }
    //Compiles the Editable View
    public void renderEditables(){
        for (int i = 0; i < layers.size(); i++){
            if (layers.get(i).getType() == LayerType.IMAGE && layers.get(i).isVisible){
                ImageView imageView = ((ImageLayer)layers.get(i)).getImageView();
                imageView.setPreserveRatio(true);
                imageView.setFitWidth(width);
                imageView.setFitHeight(height);

                //Set bounds on images to try to meet the requirements.
                editable.getChildren().add(new Pane(imageView));
            } else if (layers.get(i).isVisible)
                editable.getChildren().add(layers.get(i).getLayer());
        }
    }
    public void addLayer(){
        String name = "New Layer " + layers.size();
        layers.add(new Layer(name));
        renderEditables();
    }
    public void addPaint(){
        String name = "New Layer " + layers.size();
        layers.add(new PaintLayer(name, 700, 700));
        renderLayers();
    }
    public void addSolid(double width, double height, Color color){
        String name = "New Layer " + layers.size();
        layers.add(new SolidLayer(name, width, height, color));
        renderLayers();
    }
    //add an Image to the layer Stack
    public void addImage(Image image){
        String name = "New Layer " + layers.size();
        layers.add(new ImageLayer(name, image));
        renderLayers();
    }
    //get the Layer object of the currently selected layer.
    public  Layer getSelected(){
        if (indexofSelected == null)
            return null;
        return layers.get(indexofSelected);
    }
    //Get an Imageview Interpretation of the Selected Layer
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
    //Use this for applying effects. This will allow the layer to keep a history of the changes
    // allowing you to revert back to the original Image.
    public void applyEffectToSelected(Image image){
        ((ImageLayer) this.getSelected()).applyEffect(image);
        renderLayers();
    }
    //Use this if you plan on replacing the layer with another one.
    public void updateSelected(Layer layer){
        if (indexofSelected == null)
            return;
        layers.set(indexofSelected, layer);
        renderLayers();
    }
    //Return the Composiite Imageview.
    public ImageView getCompositeImageView(){
        renderToImage();
        return composite;
    }
    //Return the Editable View
    public StackPane getEditableStack(){
        if (editable == null){
            editable = new StackPane();
            renderEditables();
        }
        return editable;
    }
    //Compiles all the Layers into an Image for use.
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

    //OpenCV Algorithm for burning images onto each other.
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
