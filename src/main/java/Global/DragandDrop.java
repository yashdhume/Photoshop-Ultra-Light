//This Class handles all drag and drop events
package Global;

import Layers.LayerView;
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

import static Main.EditingView.imgSetByNewDrag;


public class DragandDrop {

    //Contructor
    public  DragandDrop(){}

    //adding a red border for where to drag and drop
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

    //Drag and from an file on hard drive
    public void external(AnchorPane stackPane, LayerView layerView){


        //drag over event
        stackPane.setOnDragOver((DragEvent e)->{
            final Dragboard db = e.getDragboard();

            final boolean filesAccepted;

            if (db.hasFiles()) {
                // only allow png, jpeg, jpg
                filesAccepted= db.getFiles().get(0).getName().toLowerCase().endsWith(".png")
                        || db.getFiles().get(0).getName().toLowerCase().endsWith(".jpeg")
                        || db.getFiles().get(0).getName().toLowerCase().endsWith(".jpg");
                if (filesAccepted){
                    visual(true);
                    e.acceptTransferModes(TransferMode.COPY);
                }
                else {
                    e.consume();
                }
            }

        });
        //drag dropped event
        stackPane.setOnDragDropped((DragEvent e)->{
            final Dragboard db = e.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                success = true;
                final File file = db.getFiles().get(0);
                //Multi-threading waiting for drag board to have a file
                Platform.runLater(() -> {
                    System.out.println(file.getAbsolutePath());
                    try {
                        //if(!stackPane.getChildren().isEmpty()) stackPane.getChildren().remove(0);
                        Image img = new Image(new FileInputStream(file.getAbsolutePath()));
                        layerView.addImage(img);
                        imgSetByNewDrag.setImage(img);
                    } catch (FileNotFoundException ex) {
                        System.out.println("error");
                        AlertDialogue alertDialogue = new AlertDialogue();
                        alertDialogue.getAlert(ex);
                    }
                });
            }
            visual(false);
            e.setDropCompleted(success);
            e.consume();
      });
    }




    //drag and drop within a program that is using an array list
    public void localArray(ArrayList<ImageView> imageViewArr,int index){
        //Drag Detected event
        imageViewArr.get(index).setOnDragDetected((MouseEvent e)->{
            Dragboard db = imageViewArr.get(index).startDragAndDrop(TransferMode.MOVE);
            db.setDragView(imageViewArr.get(index).snapshot(null,null));
            //copy to clipboard to copy the file
            ClipboardContent content = new ClipboardContent();
            content.putImage(imageViewArr.get(index).getImage());
            db.setContent(content);
            e.consume();
        });
        //Drag over event
        imageViewArr.get(index).setOnDragOver((DragEvent e)-> {
            if (e.getGestureSource() != imageViewArr.get(index) &&
                    e.getDragboard().hasImage()) {
                e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            visual(true);
            e.consume();


        });
        // Drag Done event
        imageViewArr.get(index).setOnDragDone((DragEvent e)-> {
            Dragboard db = e.getDragboard();
            if (db.hasImage()) {
                EditingView.layerView.addImage(db.getImage());
                imgSetByNewDrag.setImage(db.getImage());
            }
            visual(false);
            e.consume();

        });


    }

    //drag and drop within a program that is using a single image
    public void local(ImageView imageView){
        // Drag Detected event
        imageView.setOnDragDetected((MouseEvent event) -> {
            Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putImage(imageView.getImage());
            db.setContent(content);
            event.consume();
        });
        //Drag over event
        imageView.setOnDragOver((DragEvent e)-> {
            if (e.getGestureSource() != imageView &&
                    e.getDragboard().hasImage()) {
                e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            visual(true);
            e.consume();
        });
        //Drag Done event
        imageView.setOnDragDone((DragEvent e)-> {
            Dragboard db = e.getDragboard();

            if (db.hasImage()) {
                EditingView.layerView.addImage(db.getImage());
                imgSetByNewDrag.setImage(db.getImage());
            }
            visual(false);
            e.consume();

        });
    }
}