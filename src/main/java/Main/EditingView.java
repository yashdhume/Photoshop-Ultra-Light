package Main;
import Global.DragandDrop;
import Layers.LayerView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


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
        dragandDrop.external(anchorPaneEditView,layerView.getCompositeImageView());
        layerView.getCompositeImageView().setPreserveRatio(true);
        layerView.getCompositeImageView().setFitWidth(700);
        layerView.getCompositeImageView().setFitHeight(700);
        anchorPaneEditView.getChildren().add(layerView.getCompositeImageView());
        ImageView app = (ImageView)  anchorPaneEditView.getChildren().get(0);
        app.setX(anchorPaneEditView.getWidth()/2);
        app.setY(anchorPaneEditView.getHeight()/2);
        return anchorPaneEditView;
    }

}
