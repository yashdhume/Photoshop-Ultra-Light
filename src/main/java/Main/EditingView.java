package Main;
import Global.DragandDrop;
import Global.MouseState;
import Layers.LayerView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class EditingView {

    public static LayerView layerView;

    public static ImageView imageViewEditView = new ImageView();
    public static AnchorPane anchorPaneEditView = new AnchorPane();
    public static ImageView imgSetByNewDrag = new ImageView();
    public static MouseState mouseState;
    public EditingView(){}
    public void Initialize(AnchorPane layerPane){
        mouseState = MouseState.NONE;
        layerView = new LayerView(layerPane);
    }

    //Edit View
    public AnchorPane EditView(){
        DragandDrop dragandDrop = new DragandDrop();
        dragandDrop.external(anchorPaneEditView,imageViewEditView);
        imageViewEditView.setPreserveRatio(true);
        imageViewEditView.setFitWidth(700);
        imageViewEditView.setFitHeight(700);
        anchorPaneEditView.getChildren().add(imageViewEditView);
        ImageView app = (ImageView)  anchorPaneEditView.getChildren().get(0);
        app.setX(anchorPaneEditView.getWidth()/2);
        app.setY(anchorPaneEditView.getHeight()/2);
        return anchorPaneEditView;
    }

}
