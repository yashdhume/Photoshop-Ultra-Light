package Layers;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import org.opencv.core.Point;

public class TextLayer extends Layer{
    String text;
    Label label;
    double fontSize;

    public TextLayer(String name, String text){
        super(name);
        this.text = text;
        fontSize = 20;
        label = new Label(this.text);
        label.setFont(new Font(fontSize));
    }

    @Override
    public Pane getLayer() {
        label.setLayoutX(location.x);
        label.setLayoutY(location.y);
        Pane pane = new Pane();
        pane.getChildren().add(label);
        return pane;
    }

    public String getText() {
        return text;
    }

    @Override
    public void setLocation(Point p) {
        location.x = p.x - text.length()*(fontSize/3);
        location.y = p.y + fontSize/2;
        label.setTranslateX(location.x);
        label.setTranslateY(location.y);
    }
}
