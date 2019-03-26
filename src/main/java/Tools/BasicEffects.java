package Tools;
//Sliders for contrast saturation brightness and hue

import javafx.scene.control.Slider;

public class BasicEffects {
    private double[] basicEffectDoubles;

    public BasicEffects() {
        basicEffectDoubles = new double[4];
    }

    public Slider getSlider(int which) {
        Slider slider = new Slider();
        slider.setValue(0);
        slider.setMax(1);
        slider.setMin(-1);
        slider.setMajorTickUnit(0.01);
        // slider.setMinorTickCount(0.01);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            basicEffectDoubles[which] = newValue.doubleValue();
            Effects.BasicEffects basicEffects = new Effects.BasicEffects(basicEffectDoubles[0], basicEffectDoubles[1], basicEffectDoubles[2], basicEffectDoubles[3]);
            basicEffects.changeEffect();
            System.out.println("h");
        });

        return slider;
    }
}
