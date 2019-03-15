package Main;
import Global.DragandDrop;
import Layers.LayerView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;


public class EditingView {
    public static LayerView layerView;
    //public static ImageView imageViewEditView = new ImageView();
    public static AnchorPane anchorPaneEditView = new AnchorPane();
    public EditingView(){}
    public void InitializeLayers(AnchorPane pane){
        layerView = new LayerView(pane);
    }
    public AnchorPane EditView(){
        DragandDrop dragandDrop = new DragandDrop();
        dragandDrop.external(anchorPaneEditView);
        anchorPaneEditView.getChildren().add(layerView.getEditableStack());
        StackPane app = (StackPane)anchorPaneEditView.getChildren().get(0);
        app.setLayoutX(anchorPaneEditView.getWidth()/2);
        app.setLayoutY(anchorPaneEditView.getHeight()/2);
        return anchorPaneEditView;
    }

}
