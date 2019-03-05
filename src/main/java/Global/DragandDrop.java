package Global;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class DragandDrop {
    public static void external(StackPane stackPane, ImageView imageView){
        stackPane.setOnDragOver((DragEvent e)->{
            final Dragboard db = e.getDragboard();

            final boolean filesAccepted= db.getFiles().get(0).getName().toLowerCase().endsWith(".png")
                    || db.getFiles().get(0).getName().toLowerCase().endsWith(".jpeg")
                    || db.getFiles().get(0).getName().toLowerCase().endsWith(".jpg");

            if (db.hasFiles()) {
                if (filesAccepted) e.acceptTransferModes(TransferMode.COPY);
                else e.consume();
            }

        });
        stackPane.setOnDragDropped((DragEvent e)->{
            final Dragboard db = e.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                success = true;
                final File file = db.getFiles().get(0);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(file.getAbsolutePath());
                        try {
                            //if(!stackPane.getChildren().isEmpty()) stackPane.getChildren().remove(0);
                            Image img = new Image(new FileInputStream(file.getAbsolutePath()));
                            imageView.setImage(img);
                        } catch (FileNotFoundException ex) {
                            System.out.println("error");
                        }
                    }
                });
            }
            e.setDropCompleted(success);
            e.consume();
        });
    }
    public static void localArray(ArrayList<ImageView> imageViewArr,int index, ImageView imageView){
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
            e.consume();


        });
        imageViewArr.get(index).setOnDragDone((DragEvent e)-> {

            Dragboard db = e.getDragboard();

            if (db.hasImage()) {
                imageView.setImage(db.getImage());
            }
            e.consume();


        });
    }
    public static void local(ImageView imageView, ImageView imageView2){
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
            e.consume();
        });
        imageView.setOnDragDone((DragEvent e)-> {
            Dragboard db = e.getDragboard();

            if (db.hasImage()) {
                imageView2.setImage(db.getImage());
            }
            e.consume();


        });
    }
}
