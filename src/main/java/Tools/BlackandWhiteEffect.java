package Tools;

//Button for the black and white

import Effects.ColorEffect;
import Main.EditingView;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BlackandWhiteEffect {
    private EditingView editingView;
    private Button btnBlackAndWhite;

    public BlackandWhiteEffect(EditingView editingView) {
        this.editingView = editingView;
        btnBlackAndWhite = new Button();
    }

    public Button getButton() {
        btnBlackAndWhite.setOnAction((event) -> {
            ColorEffect colorEffect = new ColorEffect(editingView.layerView.getSelectedAsImage().getImage(), 6);
            editingView.layerView.applyEffectToSelected(colorEffect.getEffect());
        });
        btnBlackAndWhite.setGraphic(new ImageView(new Image("blackAndWhiteIcon.png", 25, 25, false, false)));
        btnBlackAndWhite.setTooltip(new Tooltip("Black and White"));
        return btnBlackAndWhite;

    }
}
