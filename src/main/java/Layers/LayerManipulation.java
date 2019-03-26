package Layers;

import Global.MouseState;
import Main.EditingView;
import org.opencv.core.Point;

/*
    The Layer Manipulation Class handles mouse movement over the canvas.

    The class simply utilizes three mouse events and keeps track of which state the mouse is currently
    in, to call the correct function. Any manipulation of the image which requires mouse movement to adjust
    should be implemented here.
 */

public class LayerManipulation {
    //Store the previous locaation of the mouse, this could mean multiple things
    //depending on the state's use case.
    private Point previousLocation;
    public LayerManipulation(){

        //When the Mouse first presses onto the canvas:
        EditingView.layerView.getEditableStack().setOnMousePressed(e->{
            previousLocation = new Point(e.getSceneX(), e.getSceneY());

            if (EditingView.mouseState == MouseState.DRAW){
                if (EditingView.layerView.getSelected().layerType == LayerType.PAINT){
                    ((PaintLayer)EditingView.layerView.getSelected()).onPaint(new Point(e.getX(), e.getY()));
                }
            }
        });

        //When the mouse is dragging over the canvas:
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

        //When the mouse releases from the Canvas:
        EditingView.layerView.getEditableStack().setOnMouseReleased(e->{
            if (EditingView.mouseState == MouseState.CROP){
                EditingView.layerView.getSelected().crop(previousLocation, new Point(e.getX(), e.getY()));
            }
            previousLocation = new Point(0,0);
        });
    }
}
