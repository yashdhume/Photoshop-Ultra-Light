package Main;
import Global.DragandDrop;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class EditingView {
    public static ImageView imageViewEditView = new ImageView();
    public static AnchorPane anchorPaneEditView = new AnchorPane();
    public static ImageView imgSetByNewDrag = new ImageView();


    public ToolbarView toolbarView;
    public PropertiesView propertiesView;


    public EditingView(){}

    public void InitializeToolbar(AnchorPane pane) {
    	toolbarView = new ToolbarView(pane);
    }

    public void InitializeProperties(AnchorPane pane) {
        propertiesView = new PropertiesView(pane);
    }

    //Edit View
    public AnchorPane EditView(){
        DragandDrop dragandDrop = new DragandDrop();
        // When the drag drop completed / image updated, set the imageRest = true
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
