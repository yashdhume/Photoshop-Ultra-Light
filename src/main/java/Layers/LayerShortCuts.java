package Layers;

import Main.EditingView;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class LayerShortCuts {
    public LayerShortCuts(Scene scene){
        scene.setOnKeyPressed(e->{
            if (e.getCode() == KeyCode.DELETE || e.getCode() == KeyCode.BACK_SPACE ){
                EditingView.layerView.removeSelectedLayer();
            }
        });
    }
}
