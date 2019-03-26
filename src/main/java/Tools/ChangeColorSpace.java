package Tools;
//Change Color Space

import Effects.ColorSpace;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ChangeColorSpace {
    public ChangeColorSpace() {
    }

    public Button getButton() {
        Button btnEffects = new Button();
        btnEffects.setOnAction((event) -> {
            Stage stage = new Stage();
            ColorSpace colorSpace = new ColorSpace();
            Scene scene = new Scene(colorSpace.getMutipleEffects(), 150, 200);
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX(0);
            stage.setY(screenBounds.getHeight() - 500);
            stage.setScene(scene);
            stage.setAlwaysOnTop(true);
            stage.show();
        });
        btnEffects.setGraphic(new ImageView(new Image("randomIcon.png", 25, 25, false, false)));
        btnEffects.setTooltip(new Tooltip("Change Colour Space"));
        return btnEffects;
    }
}
