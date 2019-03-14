package Effects;

import Global.OpenCVMat;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import javafx.scene.image.Image;

public class GaussianBlurEffect {
    private Mat originalImage, gaussianEffect;
    OpenCVMat openCVMat;

    public GaussianBlurEffect(Image image) {
        if (image != null) {
            try {
                openCVMat = new OpenCVMat();
                System.out.println("Running");
                originalImage = new Mat();
                gaussianEffect = new Mat();
                originalImage = openCVMat.imageToMatrix(image);

                Imgproc.GaussianBlur(originalImage, gaussianEffect, new Size(45,45), 0);

                openCVMat.setEffect(gaussianEffect);
                openCVMat.setOriginalImage(originalImage);
            }catch (Exception e){System.out.println(e);}


        }
    }
    public Image getEffect(){
        return openCVMat.getImagePost();
    }

}

