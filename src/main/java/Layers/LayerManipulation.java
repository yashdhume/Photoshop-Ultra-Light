package Layers;

import Global.MouseState;
import Main.EditingView;
import org.opencv.core.Point;

public class LayerManipulation {
    private Point previousLocation;
    public LayerManipulation(){
        EditingView.layerView.getEditableStack().setOnMousePressed(e->{
            previousLocation = new Point(e.getSceneX(), e.getSceneY());
            if (EditingView.mouseState == MouseState.DRAW){
                if (EditingView.layerView.getSelected().layerType == LayerType.PAINT){
                    ((PaintLayer)EditingView.layerView.getSelected()).onPaint(new Point(e.getX(), e.getY()));
                }
            }
        });
        EditingView.layerView.getEditableStack().setOnMouseDragged(e->{
            if (EditingView.mouseState == MouseState.MOVE){
                EditingView.layerView.getSelected().setLocation(new Point(e.getSceneX(), e.getSceneY()));
            }
            else if (EditingView.mouseState == MouseState.RESIZE){
                EditingView.layerView.getSelected().resize(previousLocation.y - e.getSceneY());
            }
            else if(EditingView.mouseState == MouseState.ROTATE){
                double deltaX = e.getSceneX() - previousLocation.x;
                EditingView.layerView.getSelected().rotate(deltaX/100);
            }
            else if (EditingView.mouseState == MouseState.DRAW){
                if (EditingView.layerView.getSelected().layerType == LayerType.PAINT){
                    ((PaintLayer)EditingView.layerView.getSelected()).onFinishedPaint(new Point(e.getX(), e.getY()));
                }
            }
        });
        EditingView.layerView.getEditableStack().setOnMouseReleased(e->{
            if (EditingView.mouseState == MouseState.CROP){
                EditingView.layerView.getSelected().crop(previousLocation, new Point(e.getX(), e.getY()));
            }
            previousLocation = new Point(0,0);
        });
    }
}
