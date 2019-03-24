package Effects;

import Global.AlertDialogue;
import Global.OpenCVMat;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import javafx.scene.image.Image;

public class GaussianBlurEffect {
    private Mat originalImage, gaussianEffect;
    OpenCVMat openCVMat;

    public GaussianBlurEffect(Image image, int kernelSize, int sigma) {
        if (image != null) {

            try {
                openCVMat = new OpenCVMat();
                originalImage = new Mat();
                gaussianEffect = new Mat();
                originalImage = openCVMat.imageToMatrix(image);
                if (kernelSize == 0){
                    gaussianEffect = originalImage;
                } else {
                    Imgproc.GaussianBlur(originalImage, gaussianEffect, new Size(kernelSize,kernelSize), sigma);
                }

                openCVMat.setEffect(gaussianEffect);
                openCVMat.setOriginalImage(originalImage);
            }catch (Exception e){System.out.println(e); AlertDialogue alertDialogue = new AlertDialogue();
                alertDialogue.getAlert(e);}
        }
    }

    public void resetImage(Image image){
        this.originalImage = openCVMat.imageToMatrix(image);
    }

    public void setGaussianEffect(int kernelSize){
        if (originalImage != null){
            try {
                if (kernelSize == 0){
                    gaussianEffect = originalImage;
                } else {
                    Imgproc.GaussianBlur(originalImage, gaussianEffect, new Size(kernelSize,kernelSize), 0);
                }
                openCVMat.setEffect(gaussianEffect);
                openCVMat.setOriginalImage(originalImage);
            }
            catch (Exception e) {System.out.println(e); AlertDialogue alertDialogue = new AlertDialogue();
                alertDialogue.getAlert(e);}
        }
    }

    public Image getEffect(){
        return openCVMat.getImagePost();
    }

}
