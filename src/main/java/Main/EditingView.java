package Main;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;


public class EditingView {

    public static ImageView imageViewEditView = new ImageView();
    public static StackPane EditView(){
        StackPane stackPane= new StackPane();
        Global.DragandDrop.external(stackPane,imageViewEditView);
        stackPane.getChildren().add(imageViewEditView);
        return stackPane;
    }

}
