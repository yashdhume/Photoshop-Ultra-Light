package Tools;

import Global.MouseState;
import Main.EditingView;
import UI.ToolbarView;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import javax.tools.Tool;


public class PaintDrawUI {
    public static int GlobalStroke = 5;
    private EditingView editingView;
    //private Color GlobalColor = Color.HOTPINK;
    private PaintDraw draw;
    private TextField textFieldStroke;
    private Slider strokeSlider;

    public PaintDrawUI(EditingView editingView) {
        this.editingView = editingView;
        draw = new PaintDraw(ToolbarView.GlobalColor, GlobalStroke);
        strokeSlider = getStrokeSlider();
        textFieldStroke = getTextFieldStroke();
    }

    private Button getButtonButton() {
        Button Brush = new Button();
        Brush.setGraphic(new ImageView(new Image("brushIcon.png", 25, 25, false, false)));
        Brush.setTooltip(new Tooltip("Brush"));

        Brush.setOnAction((event) -> {
            editingView.mouseState = MouseState.DRAW;
            //draw.drawOnAnchor(editingView.anchorPaneEditView);
        });

        textFieldStroke.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                draw.setStroke(Integer.parseInt(textFieldStroke.getText()));
                strokeSlider.setValue(Integer.parseInt(textFieldStroke.getText()));
            }
        });
        return Brush;
    }

    public ColorPicker getColorPickerButton() {
        ColorPicker colorPicker = new ColorPicker(ToolbarView.GlobalColor);

        colorPicker.setMaxSize(45, 35);
        colorPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            draw.setColor(newValue);
            ToolbarView.GlobalColor = newValue;
        });
        colorPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            draw.setColor(newValue);
        });
        return colorPicker;
    }

    private Slider getStrokeSlider() {
        Slider strokeSlider = new Slider(0, 100, 100);
        strokeSlider.setMin(0);
        strokeSlider.setValue(10);
        strokeSlider.setMax(100);
        strokeSlider.setMajorTickUnit(5);
        strokeSlider.setMinorTickCount(0);
        strokeSlider.setShowTickMarks(true);
        strokeSlider.setShowTickLabels(true);

        strokeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            GlobalStroke = newValue.intValue();
            draw.setStroke(newValue.intValue());
            textFieldStroke.setText(newValue.toString());
        });
        return strokeSlider;
    }

    private TextField getTextFieldStroke() {
        TextField textFieldStroke = new TextField();
        textFieldStroke.setPrefColumnCount(3);

        textFieldStroke.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                draw.setStroke(Integer.parseInt(textFieldStroke.getText()));
                strokeSlider.setValue(Integer.parseInt(textFieldStroke.getText()));
            }
        });
        return textFieldStroke;
    }

    public GridPane getPaintDrawUI() {
        GridPane gp = new GridPane();
        gp.setHgap(10);
        gp.add(getButtonButton(), 1, 0);
        gp.add(getStrokeSlider(), 2, 0);
        gp.add(getTextFieldStroke(), 3, 0);
        return gp;
    }
}
