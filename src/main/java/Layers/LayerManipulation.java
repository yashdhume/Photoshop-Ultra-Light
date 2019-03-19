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
            else if (EditingView.mouseState == MouseState.RESIZE && EditingView.layerView.getSelected().getType() == LayerType.IMAGE){
                ((ImageLayer) EditingView.layerView.getSelected()).resize(previousLocation.y - e.getSceneY());
            }
            else if(EditingView.mouseState == MouseState.ROTATE){
                double deltaX = e.getSceneX() - previousLocation.x;
                EditingView.layerView.getSelected().rotate(deltaX/100);
            }
        });
        EditingView.layerView.getEditableStack().setOnMouseReleased(e->{
            if (EditingView.mouseState == MouseState.CROP){
                EditingView.layerView.getSelected().crop(previousLocation, new Point(e.getSceneX(), e.getSceneY()));
            }
            previousLocation = new Point(0,0);
        });
    }

}
