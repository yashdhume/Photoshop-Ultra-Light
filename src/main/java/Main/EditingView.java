package Main;
import Global.DragandDrop;
import Global.MouseState;
import Layers.LayerView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;


public class EditingView {
    public static LayerView layerView;
    public  static MouseState mouseState;

    //public static ImageView imageViewEditView = new ImageView();
    public static AnchorPane anchorPaneEditView = new AnchorPane();
    public static ImageView imgSetByNewDrag = new ImageView();

    // Constructor
    public EditingView(){}

    public void Initialize(AnchorPane layerPane){
        layerView = new LayerView(layerPane);
        mouseState = MouseState.MOVE;
    }

    //Edit View
    public AnchorPane EditView(){
        DragandDrop dragandDrop = new DragandDrop();
        dragandDrop.external(anchorPaneEditView,layerView);
        anchorPaneEditView.getChildren().add(layerView.getEditableStack());
        StackPane app = (StackPane)  anchorPaneEditView.getChildren().get(0);
        app.setLayoutX(anchorPaneEditView.getWidth()/2);
        app.setLayoutY(anchorPaneEditView.getHeight()/2);
        return anchorPaneEditView;
    }
}
