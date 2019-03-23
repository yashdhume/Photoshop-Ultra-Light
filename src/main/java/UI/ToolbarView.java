package UI;

import Global.MouseState;
import Tools.*;
import Main.EditingView;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

public class ToolbarView {
    private AnchorPane toolbarPane;
    private EditingView editingView = new EditingView();
    public static Color GlobalColor;

    public ToolbarView(AnchorPane pane) {
        toolbarPane = pane;
        GetToolbarView();
        GlobalColor = Color.HOTPINK;
    }

    public void GetToolbarView() {
        GridPane gp = new GridPane();
        gp.setPadding(new Insets(30, 30, 30, 30));
        gp.setHgap(15);
        gp.setVgap(10);
        GaussianBlur gaussianBlur = new GaussianBlur(editingView);
        Button btnGaussianBlur = gaussianBlur.getButton();
        Slider sliderGaussian = gaussianBlur.getSlider();
        // Black and White Effect
        BlackandWhiteEffect blackandWhiteEffect = new BlackandWhiteEffect(editingView);
        Button btnBlackAndWhite = blackandWhiteEffect.getButton();
                // RGB to somthing
        MutipleEffects mutipleEffects = new MutipleEffects();
        Button btnEffects =mutipleEffects.getButton();

        //contrast, hue, brighness, saturation
        BasicEffects basicEffects = new BasicEffects();
        Slider sliderContarst = basicEffects.getSlider(0);
        Slider sliderHue = basicEffects.getSlider(1);
        Slider sliderBrightness =  basicEffects.getSlider(2);
        Slider sliderSaturation =  basicEffects.getSlider(3);

        /* Paint/Draw properties */

        // Colorpicker label and Button
        Label lblColorPicker = new Label();
        lblColorPicker.setGraphic(new ImageView(new Image("colorPaletteIcon.png", 25, 25, false, false)));
        lblColorPicker.setTooltip(new Tooltip("Color Picker"));


        PaintDrawUI paintDrawUI = new PaintDrawUI(editingView);


        Button Move = new Button();
        Move.setGraphic(new ImageView(new Image("moveIcon.png", 25, 25, false, false)));
        Move.setTooltip(new Tooltip("Move"));
        Move.setOnAction(e -> editingView.mouseState = MouseState.MOVE);

        //Rotate Button
        Button Rotate = new Button("Rotate");
        Rotate.setOnAction(e -> editingView.mouseState = MouseState.ROTATE);
        Button Crop = new Button("Crop");
        Crop.setOnAction(e -> editingView.mouseState = MouseState.CROP);
        Button btnResize = new Button("Resize");
        btnResize.setOnAction(e -> editingView.mouseState = MouseState.RESIZE);
        // Add Buttons to the grid pane
        gp.add(btnGaussianBlur, 1, 0);
        gp.add(sliderGaussian, 2, 0);
        gp.add(btnBlackAndWhite, 0, 0);
        gp.add(btnEffects, 0, 10);
        gp.add(btnResize, 0, 5);
        gp.add(sliderContarst, 0, 6);
        gp.add(sliderHue, 0, 7);
        gp.add(sliderBrightness, 0, 8);
        gp.add(sliderSaturation, 0, 9);

        gp.add(Crop, 2, 1);
        gp.add(Move, 1, 2);
        gp.add(Rotate, 2, 2);


        toolbarPane.getChildren().addAll(gp, paintDrawUI.getPaintDrawUI());
    }


}