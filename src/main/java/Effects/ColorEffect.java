package Effects;

import Global.AlertDialogue;
import Global.OpenCVMat;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import javafx.scene.image.Image;

public class ColorEffect {
    private Mat originalImage, bwEffect;
    OpenCVMat openCVMat;
    public ColorEffect(Image image, int whichEffect) {
        if (image != null) {
            try {
                openCVMat = new OpenCVMat();
                System.out.println("Running");
                originalImage = new Mat();
                bwEffect = new Mat();
                originalImage = openCVMat.imageToMatrix(image);
                Imgproc.cvtColor(originalImage, bwEffect, whichEffect);
                openCVMat.setEffect(bwEffect);
                openCVMat.setOriginalImage(originalImage);
            }catch (Exception e){
                AlertDialogue alertDialogue = new AlertDialogue();
                alertDialogue.getAlert(e);
            }
        }
    }
    public Image getEffect(){
        return openCVMat.getImagePost();
    }
}

