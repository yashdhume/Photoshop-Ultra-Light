package UI;

import Global.MouseState;
import Main.EditingView;
import Tools.PaintDrawUI;
import Tools.BasicEffects;
import Tools.BlackandWhiteEffect;
import Tools.ChangeColorSpace;
import Tools.GaussianBlur;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
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
        gp.setPadding(new Insets(30, 10, 30, 30));
        gp.setHgap(15);
        gp.setVgap(10);

        // Gaussian Blur
        GaussianBlur gaussianBlur = new GaussianBlur(editingView);
        Button btnGaussianBlur = gaussianBlur.getButton();
        Slider sliderGaussian = gaussianBlur.getSlider();

        // Black and White Effect
        BlackandWhiteEffect blackandWhiteEffect = new BlackandWhiteEffect(editingView);
        Button btnBlackAndWhite = blackandWhiteEffect.getButton();

        // RGB
        ChangeColorSpace changeColorSpace = new ChangeColorSpace();
        Button btnEffects = changeColorSpace.getButton();

        // Declaring label for contrast, hue, brightness, and saturation
        Label lblBrightness = new Label();
        lblBrightness.setGraphic(new ImageView(new Image("brightnessIcon.png", 25, 25, false, false)));
        lblBrightness.setTooltip(new Tooltip("Brightness"));

        Label lblHue = new Label();
        lblHue.setGraphic(new ImageView(new Image("hueIcon.png", 25, 25, false, false)));
        lblHue.setTooltip(new Tooltip("Hue"));

        Label lblContrast = new Label();
        lblContrast.setGraphic(new ImageView(new Image("contrastIcon.png", 25, 25, false, false)));
        lblContrast.setTooltip(new Tooltip("Contrast"));

        Label lblSaturation = new Label();
        lblSaturation.setGraphic(new ImageView(new Image("saturationIcon.png", 25, 25, false, false)));
        lblSaturation.setTooltip(new Tooltip("Saturation"));

        // Contrast, hue, brightness, saturation
        BasicEffects basicEffects = new BasicEffects();
        Slider sliderContrast = basicEffects.getSlider(0);
        Slider sliderHue = basicEffects.getSlider(1);
        Slider sliderBrightness = basicEffects.getSlider(2);
        Slider sliderSaturation = basicEffects.getSlider(3);

        // Colorpicker label and Button
        Label lblColorPicker = new Label();
        lblColorPicker.setGraphic(new ImageView(new Image("colorPaletteIcon.png", 25, 25, false, false)));
        lblColorPicker.setTooltip(new Tooltip("Color Picker"));

        // Paint Draw Properties
        PaintDrawUI paintDrawUI = new PaintDrawUI(editingView);

        // Move Button
        Button Move = new Button();
        Move.setGraphic(new ImageView(new Image("moveIcon.png", 25, 25, false, false)));
        Move.setTooltip(new Tooltip("Move"));
        Move.setOnAction(e -> editingView.mouseState = MouseState.MOVE);

        //Rotate Button
        Button Rotate = new Button();
        Rotate.setGraphic(new ImageView(new Image("rotateIcon.png", 25, 25, false, false)));
        Rotate.setTooltip(new Tooltip("Rotate"));
        Rotate.setOnAction(e -> editingView.mouseState = MouseState.ROTATE);

        // Crop Button
        Button Crop = new Button();
        Crop.setGraphic(new ImageView(new Image("cropIcon.png", 25, 25, false, false)));
        Crop.setTooltip(new Tooltip("Crop"));
        Crop.setOnAction(e -> editingView.mouseState = MouseState.CROP);

        // Resize Button
        Button Resize = new Button();
        Resize.setGraphic(new ImageView(new Image("resizeIcon.png", 25, 25, false, false)));
        Resize.setTooltip(new Tooltip("Resize"));
        Resize.setOnAction(e -> editingView.mouseState = MouseState.RESIZE);

        // Add Buttons to the grid pane
        gp.add(Move, 0, 0);
        gp.add(Rotate, 1, 0);
        gp.add(Crop, 0, 1);
        gp.add(Resize, 1, 1);
        gp.add(paintDrawUI.getColorPickerButton(), 0, 2);
        gp.add(paintDrawUI.getPaintDrawUI(), 1, 2);
        gp.add(btnBlackAndWhite, 0, 3);
        gp.add(btnEffects, 1, 3);
        gp.add(btnGaussianBlur, 0, 4);
        gp.add(sliderGaussian, 1, 4);
        gp.add(lblContrast, 0, 5);
        gp.add(sliderContrast, 1, 5);
        gp.add(lblHue, 0, 6);
        gp.add(sliderHue, 1, 6);
        gp.add(lblBrightness, 0, 7);
        gp.add(sliderBrightness, 1, 7);
        gp.add(lblSaturation, 0, 8);
        gp.add(sliderSaturation, 1, 8);

        toolbarPane.getChildren().addAll(gp/*, paintDrawUI.getPaintDrawUI()*/);
    }

}