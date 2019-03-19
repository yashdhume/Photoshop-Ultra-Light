package UI;

import Effects.ColorEffect;
import Effects.GaussianBlurEffect;
import Global.MouseState;
import Layers.ImageLayer;
import Tools.PaintDraw;
import Main.EditingView;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Random;

public class ToolbarView {
    AnchorPane toolbarPane;
    EditingView editingView = new EditingView();
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

        Button btnGaussianBlur = new Button();
        btnGaussianBlur.setGraphic(new ImageView(new Image("gaussianBlurIcon.png", 25, 25, false, false)));
        btnGaussianBlur.setTooltip(new Tooltip("Gaussian Blur"));
        btnGaussianBlur.setOnAction((event) -> {
            GaussianBlurEffect gaussianBlurEffect = new GaussianBlurEffect(editingView.layerView.getSelectedAsImage().getImage(), 45, 0);
            ImageLayer layer = (ImageLayer)editingView.layerView.getSelected();
            layer.setImage(gaussianBlurEffect.getEffect());
            EditingView.layerView.updateSelected(layer);
        });

        Slider sliderGaussian = new Slider(0, 100, 100);
        sliderGaussian.setMin(0);
        sliderGaussian.setValue(0);
        sliderGaussian.setMax(100);
        sliderGaussian.setMajorTickUnit(5);
        sliderGaussian.setMinorTickCount(0);
        sliderGaussian.setShowTickMarks(true);
        sliderGaussian.setShowTickLabels(true);
        sliderGaussian.setSnapToTicks(true);
        Label gaussianScale = new Label();

        PauseTransition pause = new PauseTransition(Duration.millis(50));

        // Gaussian Bar event. The Gaussian filter listen to the bar value change.
        sliderGaussian.valueProperty().addListener((observable, oldValue, newValue) -> {
            pause.setOnFinished(event -> {
                Runnable runGaussian = () -> {
                    int kernel = newValue.intValue();
                    if (kernel % 2 == 0) {
                        kernel += 1;
                    }

                    GaussianBlurEffect gaussianBlurEffect = new GaussianBlurEffect(((ImageLayer)editingView.layerView.getSelected()).getOriginalImage().getImage(), kernel, 0);
                    editingView.layerView.applyEffectToSelected(gaussianBlurEffect.getEffect());
                };
                new Thread(runGaussian).start();
            });
            pause.playFromStart();
        });

        // Gaussian Bar event - when mouse on release
        sliderGaussian.setOnMouseReleased(event -> {
            int kernel = (int) sliderGaussian.getValue();
            if (kernel % 2 == 0) {
                kernel += 1;
            }
            GaussianBlurEffect gaussianBlurEffect = new GaussianBlurEffect(((ImageLayer)editingView.layerView.getSelected()).getOriginalImage().getImage(), kernel, 0);
            gaussianBlurEffect.setGaussianEffect(kernel);
            EditingView.layerView.applyEffectToSelected(gaussianBlurEffect.getEffect());
        });

        // Black and White Effect
        Button btnBlackAndWhite = new Button();
        btnBlackAndWhite.setOnAction((event) -> {
            ColorEffect colorEffect = new ColorEffect(editingView.layerView.getSelectedAsImage().getImage(),6);
            ImageLayer layer = (ImageLayer)editingView.layerView.getSelected();
            layer.setImage(colorEffect.getEffect());
            editingView.layerView.updateSelected(layer);
        });
        btnBlackAndWhite.setGraphic(new ImageView(new Image("/blackAndWhiteIcon.png", 25, 25, false, false)));
        btnBlackAndWhite.setTooltip(new Tooltip("Black and White"));

        // Black and White Effect
        Button btnRandomEffect = new Button();
        btnRandomEffect.setOnAction((event) -> {
            Random rnd = new Random();
            ColorEffect colorEffect = new ColorEffect(editingView.layerView.getSelectedAsImage().getImage(),rnd.nextInt(125));
            ImageLayer layer = (ImageLayer)editingView.layerView.getSelected();
            layer.setImage(colorEffect.getEffect());
            editingView.layerView.updateSelected(layer);
        });
        btnRandomEffect.setGraphic(new ImageView(new Image("/blackAndWhiteIcon.png", 25, 25, false, false)));
        btnRandomEffect.setTooltip(new Tooltip("Random Effect"));

        /* Paint/Draw properties */

        // Colorpicker label and Button
        Label lblColorPicker = new Label();
        lblColorPicker.setGraphic(new ImageView(new Image("colorPaletteIcon.png", 25, 25, false, false)));
        lblColorPicker.setTooltip(new Tooltip("Color Picker"));

        ColorPicker colorPicker = new ColorPicker(GlobalColor);
        PaintDraw draw = new PaintDraw(colorPicker.getValue(), 5);
        colorPicker.setMaxSize(45, 35);
        colorPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            draw.setColor(newValue);
            GlobalColor = newValue;
        });

        // Stroke Textfield and Slider
        TextField textFieldStroke = new TextField();
        textFieldStroke.setPrefColumnCount(3);
        Slider strokeSlider = new Slider(0, 100, 100);
        strokeSlider.setMin(0);
        strokeSlider.setValue(10);
        strokeSlider.setMax(100);
        strokeSlider.setMajorTickUnit(5);
        strokeSlider.setMinorTickCount(0);
        strokeSlider.setShowTickMarks(true);
        strokeSlider.setShowTickLabels(true);

        strokeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            draw.setStroke(newValue.intValue());
            textFieldStroke.setText(newValue.toString());
        });

        textFieldStroke.setOnKeyPressed((KeyEvent event)->{
            if(event.getCode()==KeyCode.ENTER){
                draw.setStroke(Integer.parseInt(textFieldStroke.getText()));
                strokeSlider.setValue(Integer.parseInt(textFieldStroke.getText()));
            }
        });

        strokeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            draw.setStroke(newValue.intValue());
            textFieldStroke.setText(newValue.toString());
        });
        colorPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            draw.setColor(newValue);
        });

        // Brush Button
        Button Brush = new Button();
        Brush.setGraphic(new ImageView(new Image("brushIcon.png", 25, 25, false, false)));
        Brush.setTooltip(new Tooltip("Brush"));

        Brush.setOnAction((event) -> {
            draw.drawOnAnchor(editingView.anchorPaneEditView);
        });
        textFieldStroke.setOnKeyPressed((KeyEvent event)->{
            if(event.getCode()==KeyCode.ENTER){
                draw.setStroke(Integer.parseInt(textFieldStroke.getText()));
                strokeSlider.setValue(Integer.parseInt(textFieldStroke.getText()));
            }
        });

        // Pencil Button
        Button Pencil = new Button();
        Pencil.setGraphic(new ImageView(new Image("pencilIcon.png", 25, 25, false, false)));
        Pencil.setTooltip(new Tooltip("Pencil"));
        Pencil.setOnAction((event) -> {
            editingView.mouseState = MouseState.DRAW;
            draw.drawOnImage(editingView.anchorPaneEditView, editingView.layerView.getSelectedAsImage());
        });

        // Draw Circle Button
        Button Circle = new Button();
        Circle.setGraphic(new ImageView(new Image("circleIcon.png", 25, 25, false, false)));
        Circle.setTooltip(new Tooltip("Draw Circle"));

        // Draw Rectangle Button
        Button Rectangle = new Button();
        Rectangle.setGraphic(new ImageView(new Image("rectangleIcon.png", 25, 25, false, false)));
        Rectangle.setTooltip(new Tooltip("Draw Rectangle"));

        // Draw Triangle Button
        Button Triangle = new Button();
        Triangle.setGraphic(new ImageView(new Image("triangleIcon.png", 25, 25, false, false)));
        Triangle.setTooltip(new Tooltip("Draw Triangle"));

        // Move Button
        Button Move = new Button();
        Move.setGraphic(new ImageView(new Image("moveIcon.png", 25, 25, false, false)));
        Move.setTooltip(new Tooltip("Move"));
        Move.setOnAction(e->{
            editingView.mouseState = MouseState.MOVE;
        });

        // Add Buttons to the grid pane
        gp.add(btnGaussianBlur, 1, 0);
        gp.add(sliderGaussian, 2, 0);
        gp.add(gaussianScale, 3, 0);
        gp.add(btnBlackAndWhite, 0, 0);
        gp.add(btnRandomEffect, 0, 10);
        gp.add(Circle, 0, 1);
        gp.add(Rectangle, 1, 1);
        gp.add(Triangle, 0, 2);
        gp.add(Move, 1, 2);
        gp.add(Pencil, 0, 3);
        gp.add(Brush, 1, 3);
        gp.add(strokeSlider, 2, 3);
        gp.add(textFieldStroke,3,3);
        gp.add(lblColorPicker, 0, 4);
        gp.add(colorPicker, 1, 4);

        toolbarPane.getChildren().addAll(gp);
    }
}