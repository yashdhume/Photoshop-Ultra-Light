package Layers;

import Global.MouseState;
import Main.EditingView;
import javafx.scene.layout.StackPane;
import org.opencv.core.Point;

public class LayerManipulation {
    private Point previousLocation;
    public LayerManipulation(){
        EditingView.layerView.getEditableStack().setOnMousePressed(e->{
            previousLocation = new Point(e.getSceneX(), e.getSceneY());
        });
        EditingView.layerView.getEditableStack().setOnMouseDragged(e->{
            if (EditingView.mouseState == MouseState.MOVE){
                EditingView.layerView.getSelected().setLocation(new Point(e.getSceneX(), e.getSceneY()));
            }
            else if(EditingView.mouseState == MouseState.ROTATE){
                double deltaX = e.getSceneX() - previousLocation.x;
                EditingView.layerView.getSelected().rotate(deltaX/100);
            }
        });
        EditingView.layerView.getEditableStack().setOnMouseReleased(e->{
            previousLocation = new Point(0,0);
        });
    }

}
