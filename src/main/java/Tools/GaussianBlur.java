package Tools;

import Effects.GaussianBlurEffect;
import Layers.ImageLayer;
import Main.EditingView;
import javafx.animation.PauseTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class GaussianBlur {
    private EditingView editingView;

    public GaussianBlur(EditingView editingView) {
        this.editingView = editingView;
    }

    public Button getButton() {
        Button btnGaussianBlur = new Button();
        btnGaussianBlur.setGraphic(new ImageView(new Image("gaussianBlurIcon.png", 25, 25, false, false)));
        btnGaussianBlur.setTooltip(new Tooltip("Gaussian Blur"));
        btnGaussianBlur.setOnAction((event) -> {
            GaussianBlurEffect gaussianBlurEffect = new GaussianBlurEffect(editingView.layerView.getSelectedAsImage().getImage(), 45, 0);
            ImageLayer layer = (ImageLayer) editingView.layerView.getSelected();
            layer.setImage(gaussianBlurEffect.getEffect());
            EditingView.layerView.updateSelected(layer);
        });
        return btnGaussianBlur;

    }

    public Slider getSlider() {
        Slider sliderGaussian = new Slider(0, 100, 100);
        sliderGaussian.setMin(0);
        sliderGaussian.setValue(0);
        sliderGaussian.setMax(100);
        sliderGaussian.setMajorTickUnit(5);
        sliderGaussian.setMinorTickCount(0);
        sliderGaussian.setShowTickMarks(true);
        sliderGaussian.setShowTickLabels(true);
        sliderGaussian.setSnapToTicks(true);

        PauseTransition pause = new PauseTransition(Duration.millis(50));

        // Gaussian Bar event. The Gaussian filter listen to the bar value change.
        sliderGaussian.valueProperty().addListener((observable, oldValue, newValue) -> {
            pause.setOnFinished(event -> {
                Runnable runGaussian = () -> {
                    int kernel = newValue.intValue();
                    if (kernel % 2 == 0) {
                        kernel += 1;
                    }

                    GaussianBlurEffect gaussianBlurEffect = new GaussianBlurEffect(((ImageLayer) editingView.layerView.getSelected()).getOriginalImage().getImage(), kernel, 0);
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
            GaussianBlurEffect gaussianBlurEffect = new GaussianBlurEffect(((ImageLayer) editingView.layerView.getSelected()).getOriginalImage().getImage(), kernel, 0);
            gaussianBlurEffect.setGaussianEffect(kernel);
            EditingView.layerView.applyEffectToSelected(gaussianBlurEffect.getEffect());
        });
        return sliderGaussian;
    }
}
