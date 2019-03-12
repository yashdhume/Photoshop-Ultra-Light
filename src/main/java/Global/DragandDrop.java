package Global;

import Main.EditingView;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class DragandDrop {
    public  DragandDrop(){}
    public void visual(boolean start){
        EditingView editingView = new EditingView();
        if(start)
            editingView.anchorPaneEditView.setStyle("-fx-border-color: red;"
                    + "-fx-border-width: 5;"
                    + "-fx-background-color: gray;"
                    + "-fx-border-style: solid;");
        else
            editingView.anchorPaneEditView.setStyle("-fx-border-color: #C6C6C6");

    }
    public void external(AnchorPane stackPane, ImageView imageView){

        stackPane.setOnDragOver((DragEvent e)->{
            final Dragboard db = e.getDragboard();

            final boolean filesAccepted;

            if (db.hasFiles()) {
                filesAccepted= db.getFiles().get(0).getName().toLowerCase().endsWith(".png")
                        || db.getFiles().get(0).getName().toLowerCase().endsWith(".jpeg")
                        || db.getFiles().get(0).getName().toLowerCase().endsWith(".jpg");
                if (filesAccepted){
                    visual(true);
                    e.acceptTransferModes(TransferMode.COPY);
                }
                else e.consume();
            }

        });

        stackPane.setOnDragDropped((DragEvent e)->{
            final Dragboard db = e.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                success = true;
                final File file = db.getFiles().get(0);
                Platform.runLater(() -> {
                    System.out.println(file.getAbsolutePath());
                    try {
                        //if(!stackPane.getChildren().isEmpty()) stackPane.getChildren().remove(0);
                        Image img = new Image(new FileInputStream(file.getAbsolutePath()));
                        imageView.setImage(img);
                    } catch (FileNotFoundException ex) {
                        System.out.println("error");
                    }
                });
            }
            visual(false);
            e.setDropCompleted(success);
            e.consume();
        });
    }
    public void localArray(ArrayList<ImageView> imageViewArr,int index, ImageView imageView){
        imageViewArr.get(index).setOnDragDetected((MouseEvent e)->{
            Dragboard db = imageViewArr.get(index).startDragAndDrop(TransferMode.MOVE);
            db.setDragView(imageViewArr.get(index).snapshot(null,null));
            ClipboardContent content = new ClipboardContent();
            content.putImage(imageViewArr.get(index).getImage());
            db.setContent(content);
            e.consume();
        });
        imageViewArr.get(index).setOnDragOver((DragEvent e)-> {
            if (e.getGestureSource() != imageViewArr.get(index) &&
                    e.getDragboard().hasImage()) {
                e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            visual(true);
            e.consume();


        });
        imageViewArr.get(index).setOnDragDone((DragEvent e)-> {
            Dragboard db = e.getDragboard();
            if (db.hasImage()) {
                imageView.setImage(db.getImage());
            }
            visual(false);
            e.consume();
        });

    }
    public void local(ImageView imageView, ImageView imageView2){
        imageView.setOnDragDetected((MouseEvent event) -> {
            Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putImage(imageView.getImage());
            db.setContent(content);
            event.consume();
        });

        imageView.setOnDragOver((DragEvent e)-> {
            if (e.getGestureSource() != imageView &&
                    e.getDragboard().hasImage()) {
                e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            visual(true);
            e.consume();
        });
        imageView.setOnDragDone((DragEvent e)-> {
            Dragboard db = e.getDragboard();

            if (db.hasImage()) {
                imageView2.setImage(db.getImage());
            }
            visual(false);
            e.consume();


        });
    }
}