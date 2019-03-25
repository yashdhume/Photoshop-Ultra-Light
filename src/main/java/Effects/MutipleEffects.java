package Effects;

import Layers.ImageLayer;
import Main.EditingView;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import java.util.ArrayList;

public class MutipleEffects {
    public MutipleEffects(){}
    private ArrayList<Button> getButtonArr(){
        ArrayList<Button> effectButtons = new ArrayList<>();
        effectButtons.add(getEffectButton("RGB to Grey", 7));
        effectButtons.add(getEffectButton("RGB to BGR", 4));
        effectButtons.add(getEffectButton("RGB to HLS", 53));
        effectButtons.add(getEffectButton("RGB to HSV", 41));
        effectButtons.add(getEffectButton("RGB to XYZ", 33));
        effectButtons.add(getEffectButton("RGB to YCrCb", 37));

        return effectButtons;
    }
    public VBox getMutipleEffects(){
        ArrayList<Button> effectButtons = getButtonArr();
        VBox vBox = new VBox();
        for(int i =0; i<effectButtons.size();i++)
            vBox.getChildren().add(effectButtons.get(i));
        return vBox;
    }
    private Button getEffectButton(String effectName,int whichEffect){
        Button btn = new Button(effectName);
        EditingView editingView = new EditingView();
        btn.setOnAction((event) -> {
            ColorEffect colorEffect = new ColorEffect(editingView.layerView.getSelectedAsImage().getImage(), whichEffect);
            editingView.layerView.applyEffectToSelected(colorEffect.getEffect());
            /*
            ImageLayer layer = (ImageLayer) editingView.layerView.getSelected();
            layer.setImage(colorEffect.getEffect());
            editingView.layerView.updateSelected(layer);
            */
        });
        return btn;
    }
}
