package Effects;

import Layers.ImageLayer;
import Main.EditingView;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;

public class BasicEffects {
    private double contrast, hue, brightness, saturation;

    public BasicEffects(double contrast, double hue, double brightness, double saturation) {
        this.contrast = contrast;
        this.hue = hue;
        this.brightness = brightness;
        this.saturation = saturation;
    }
    public void changeEffect(){
        ColorAdjust colorAdjust = new ColorAdjust();
        EditingView editingView = new EditingView();
        colorAdjust.setContrast(contrast);
        colorAdjust.setHue(hue);
        colorAdjust.setBrightness(brightness);
        colorAdjust.setSaturation(saturation);
        ImageView imageView = editingView.layerView.getSelectedAsImage();
        ImageLayer layer = (ImageLayer) editingView.layerView.getSelected();
        imageView.setEffect(colorAdjust);
        editingView.layerView.updateSelected(layer);
    }
}
