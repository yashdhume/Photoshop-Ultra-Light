package UI;

import Effects.BlackWhiteEffect;
import Effects.GaussianBlurEffect;
import Tools.PaintDraw;
import Main.EditingView;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.w3c.dom.css.Rect;

public class ToolbarView {
    AnchorPane toolbarPane;
    EditingView editingView = new EditingView();

    public ToolbarView(AnchorPane pane) {
        toolbarPane = pane;
        GetToolbarView();
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
            GaussianBlurEffect gaussianBlurEffect = new GaussianBlurEffect(editingView.imageViewEditView.getImage(), 45, 0);
            editingView.imageViewEditView.setImage(gaussianBlurEffect.getEffect());
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

                    GaussianBlurEffect gaussianBlurEffect = new GaussianBlurEffect(editingView.imgSetByNewDrag.getImage(), kernel, 0);
                    editingView.imageViewEditView.setImage(gaussianBlurEffect.getEffect());
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
            GaussianBlurEffect gaussianBlurEffect = new GaussianBlurEffect(editingView.imgSetByNewDrag.getImage(), kernel, 0);
            gaussianBlurEffect.setGaussianEffect(kernel);
            editingView.imageViewEditView.setImage(gaussianBlurEffect.getEffect());
        });

        Button btnBlackAndWhite = new Button();
        btnBlackAndWhite.setOnAction((event) -> {
            BlackWhiteEffect blackWhiteEffect = new BlackWhiteEffect(editingView.imageViewEditView.getImage());
            editingView.imageViewEditView.setImage(blackWhiteEffect.getEffect());
        });
        btnBlackAndWhite.setGraphic(new ImageView(new Image("blackAndWhiteIcon.png", 25, 25, false, false)));
        btnBlackAndWhite.setTooltip(new Tooltip("Black and White"));


        ///////////////////////////////////////////////////////////////////////////////////////////////////////
        // Painting / Draw
        ///////////////////////////////////////////////////////////////////////////////////////////////////////
        Color color = Color.BLACK;
        int stroke = 5;

        Label lblColorPicker = new Label();
        lblColorPicker.setGraphic(new ImageView(new Image("colorPaletteIcon.png", 25, 25, false, false)));
        lblColorPicker.setTooltip(new Tooltip("Color Picker"));

        ColorPicker colorPicker = new ColorPicker(color);
        colorPicker.setMaxSize(45, 35);
        colorPicker.setOnAction((event) -> {
            drawUpdate(colorPicker.getValue(), stroke);
        });

        PaintDraw draw = new PaintDraw(colorPicker.getValue(), stroke);

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

        strokeSlider.setOnMouseMoved((event) -> {
            textFieldStroke.setText("" + (int)strokeSlider.getValue() + "");
            drawUpdate(color, (int)strokeSlider.getValue());
        });

        textFieldStroke.setOnKeyPressed((KeyEvent event)->{
            if(event.getCode()==KeyCode.ENTER){
                draw.setStroke(Integer.parseInt(textFieldStroke.getText()));
                strokeSlider.setValue(Integer.parseInt(textFieldStroke.getText()));
            }
        });

        Button Brush = new Button();
        Brush.setGraphic(new ImageView(new Image("brushIcon.png", 25, 25, false, false)));
        Brush.setTooltip(new Tooltip("Brush"));
        Brush.setOnAction((event) -> {
            drawUpdate(color, stroke);
        });

        Button Pencil = new Button();
        Pencil.setGraphic(new ImageView(new Image("pencilIcon.png", 25, 25, false, false)));
        Pencil.setTooltip(new Tooltip("Pencil"));
        Pencil.setOnAction((event) -> {
            PaintDraw paintDraw = new PaintDraw(color, stroke);
            paintDraw.drawOnImage(editingView.imageViewEditView);
        });

        Button Circle = new Button();
        Circle.setGraphic(new ImageView(new Image("circleIcon.png", 25, 25, false, false)));
        Circle.setTooltip(new Tooltip("Draw Circle"));

        Button Rectangle = new Button();
        Rectangle.setGraphic(new ImageView(new Image("rectangleIcon.png", 25, 25, false, false)));
        Rectangle.setTooltip(new Tooltip("Draw Rectangle"));

        Button Triangle = new Button();
        Triangle.setGraphic(new ImageView(new Image("triangleIcon.png", 25, 25, false, false)));
        Triangle.setTooltip(new Tooltip("Draw Triangle"));

        Button Layer = new Button();
        Layer.setGraphic(new ImageView(new Image("layersIcon.png", 25, 25, false, false)));
        Layer.setTooltip(new Tooltip("Add Layer"));

        gp.add(btnGaussianBlur, 1, 0);
        gp.add(sliderGaussian, 2, 0);
        gp.add(gaussianScale, 3, 0);
        gp.add(btnBlackAndWhite, 0, 0);
        gp.add(Circle, 0, 1);
        gp.add(Rectangle, 1, 1);
        gp.add(Triangle, 0, 2);
        gp.add(Pencil, 0, 3);
        gp.add(Brush, 1, 3);
        gp.add(strokeSlider, 2, 3);
        gp.add(textFieldStroke,3,3);
        gp.add(lblColorPicker, 0, 4);
        gp.add(colorPicker, 1, 4);
        gp.add(Layer, 0, 5);

        toolbarPane.getChildren().addAll(gp);
    }

    private void drawUpdate(Color color, int stroke){
        PaintDraw paintDraw = new PaintDraw(color, stroke);
        paintDraw.drawOnAnchor(editingView.anchorPaneEditView);
    }
}
