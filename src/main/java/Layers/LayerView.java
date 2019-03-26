package Layers;

import Global.MouseState;
import Global.OpenCVMat;
import Main.EditingView;
import UI.ToolbarView;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
        layers.add(new ImageLayer("middle", new Image("googleIcon.png")));
        //layers.add(new ImageLayer("foreground", new Image("cameraIcon.png")));

        controlPane = pane;
        composite = new ImageView();
        indexofSelected = 0;
        editable = new StackPane();
        InitializeButtons();
        renderLayers();
        layers.get(indexofSelected).selectLayer();
    }

    private void InitializeButtons(){
        Button solidButton = new Button("Create new Solid");
        solidButton.setOnAction(e->{
            this.addSolid(width, height, ToolbarView.GlobalColor);
        });
        solidButton.setLayoutX(20);
        solidButton.setLayoutY(80);

        Button textButton = new Button("Create new Text");
        textButton.setOnAction(e->{
            Stage stage = new Stage();
            GridPane gp = new GridPane();

            TextField name = new TextField();
            name.setOnKeyReleased(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    if (name.getText().length() >0){
                        addText(name.getText());
                        stage.close();
                    }
                }
            });

            Button submit = new Button("Done");
            submit.setOnAction(event->{
                if (name.getText().length() >0){
                    addText(name.getText());
                    stage.close();
                }
            });

            gp.add(name, 0, 0);
            gp.add(submit, 0, 1);
            Scene scene = new Scene(gp);
            scene.getStylesheets().add("style.css");
            stage.getIcons().add(new Image("logo.png"));
            stage.setScene(scene);
            stage.setAlwaysOnTop(true);
            stage.show();
        });
        textButton.setLayoutX(20);
        textButton.setLayoutY(100);

        Button paintButton = new Button("Create new Paint");
        paintButton.setOnAction(e->addPaint());
        paintButton.setLayoutX(20);
        paintButton.setLayoutY(120);

        Button fitButton = new Button("Fit Image");
        fitButton.setOnAction(e->{
            if (getSelected().getType() == LayerType.IMAGE){
                ((ImageLayer)getSelected()).fitImage(width, height);
            }
        });
        fitButton.setLayoutY(140);
        fitButton.setLayoutX(20);

        HBox hbox = new HBox(20);
        hbox.setPadding(new Insets(50));
        hbox.getChildren().addAll(solidButton, paintButton, textButton, fitButton);
        controlPane.getChildren().add(hbox);
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
                if (a != indexofSelected){
                    layers.get(a).selectLayer();
                    layers.get(indexofSelected).unselectLayer();
                    indexofSelected = a;
                }
                if (e.getClickCount() >= 2){
                    layerOptions(layers.get(a), a);
                }
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

    private void layerOptions(Layer layer, int index){
        GridPane gp = new GridPane();
        gp.setPadding(new Insets(20));
        gp.add(new Label(layer.name + " Options"), 0,0);

        TextField layerName = new TextField();
        layerName.setText(layer.name);
        Label lblLayerName = new Label("Layer Name: ");
        gp.add(lblLayerName, 0,1);
        gp.add(layerName,1,1);

        Label lblLayerNumber = new Label("Layer Number: ");
        ObservableList<String> options = FXCollections.observableArrayList();
        Observable placeholder;
        for (int i = 0; i < layers.size(); i++){
            options.add(layers.get(i).name + " (" + (i+1) + ")");
        }
        ComboBox comboBox = new ComboBox(options);
        comboBox.setValue(options.get(index));
        gp.add(lblLayerNumber, 0, 2);
        gp.add(comboBox, 1,2);

        Button apply = new Button("Apply");
        Button done = new Button("Done");
        Label lblVerification = new Label("");
        Stage stage = new Stage();
        apply.setOnAction(e->{
            String text = "";
            int count = 0;
            System.out.println(layer.name + " (" + (index+1) + ")");
            System.out.println(comboBox.getValue().toString());
            if (comboBox.getValue() != layer.name + " (" + (index+1) + ")"){
                Matcher matcher = Pattern.compile("\\(([^)]+)\\)").matcher(comboBox.getValue().toString());
                matcher.find();
                int value = Integer.valueOf(matcher.group(1)).intValue()-1;

                if (value < layers.size() && value>= 0){
                    Layer temp = layers.get(value);
                    layers.set(value, layer);
                    layers.set(index, temp);
                    count++;
                }
                else {
                    text += " Failed to Change Layer Order.";
                }
            }

            if (layerName.getText() != layer.name){
                boolean isUnique = true;
                if (layerName.getText() == "" || !isUnique)
                    text +=  "Failed to change Layer Name";
                else {
                    layer.name = layerName.getText();
                    count++;
                }
            }

            text+= " " + count + " successful changes.";
            lblVerification.setText(text);
            renderLayers();
            stage.close();
        });

        done.setOnAction(e-> stage.close());
        HBox hbox = new HBox(20);
        hbox.getChildren().addAll(apply, done);
        gp.add(hbox,1,3);
        gp.add(lblVerification, 0, 4);
        Scene scene = new Scene(gp);
        scene.getStylesheets().add("style.css");
        stage.getIcons().add(new Image("logo.png"));
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    //Compiles the Editable View
    public void renderEditables(){
        if (editable.getChildren().size() > 0)
            editable.getChildren().remove(0,editable.getChildren().size()-1);
        for (int i = 0; i < layers.size(); i++){
            if (layers.get(i).isVisible)
                editable.getChildren().add(layers.get(i).getLayer());
        }
    }
    private void selectLayer(int index){
        if (index < layers.size()){
            layers.get(indexofSelected).unselectLayer();
            indexofSelected = index;
            layers.get(indexofSelected).selectLayer();
        }
    }
    public void addLayer(){
        String name = "New Layer " + layers.size();
        layers.add(new Layer(name));
        renderEditables();
    }
    public void addPaint(){
        EditingView.mouseState = MouseState.DRAW;
        String name = "New Layer " + layers.size();
        layers.add(new PaintLayer(name, width, height));
        renderLayers();
        selectLayer(layers.size()-1);
    }
    public void addSolid(double width, double height, Color color){
        String name = "New Layer " + layers.size();
        layers.add(new SolidLayer(name, width, height, color));
        renderLayers();
        selectLayer(layers.size()-1);
    }
    //add an Image to the layer Stack
    public void addImage(Image image){
        String name = "New Layer " + layers.size();
        layers.add(new ImageLayer(name, image));
        renderLayers();
        selectLayer(layers.size()-1);
    }
    public void addText(String text){
        layers.add(new TextLayer(text, text, ToolbarView.GlobalColor));
        renderLayers();
        selectLayer(layers.size()-1);
    }
    public void removeSelectedLayer(){
        layers.remove(indexofSelected.intValue());
        indexofSelected = layers.size()-1;
        layers.get(indexofSelected).selectLayer();
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
    public void moveSelect(boolean isUp){
        System.out.println("ENTERS");
        if (isUp){
            if (indexofSelected + 1 < layers.size()){
                System.out.println(indexofSelected);
                layers.get(indexofSelected.intValue()).unselectLayer();
                indexofSelected++;
                layers.get(indexofSelected.intValue()).selectLayer();
                System.out.println(indexofSelected);
            }
        }
        else{
            if (indexofSelected -1 >= 0){
                layers.get(indexofSelected).unselectLayer();
                indexofSelected = indexofSelected-1;
                layers.get(indexofSelected).selectLayer();
            }
        }
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
