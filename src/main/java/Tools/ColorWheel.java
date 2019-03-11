package Tools;

        import javafx.scene.control.ColorPicker;
        import javafx.scene.paint.Color;

public class ColorWheel {
    public ColorWheel(){}
    public final ColorPicker colorPicker= new ColorPicker();
    public ColorPicker colorWheel(){
        colorPicker.setValue(Color.RED);
        return  colorPicker;


    }

}
