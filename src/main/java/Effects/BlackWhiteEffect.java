package Effects;

import Global.OpenCVMat;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import javafx.scene.image.Image;

public class BlackWhiteEffect {
    private Mat originalImage, bwEffect;
    OpenCVMat openCVMat;
 /*   public BlackWhiteEffect(File input) {
        if (input != null) {
            try {
                Image image = new Image(new FileInputStream(input));
                originalImage = new Mat();
                bwEffect = new Mat();
                originalImage = openCVMat.imageToMatrix(image);

                Imgproc.cvtColor(originalImage, bwEffect, Imgproc.COLOR_RGB2GRAY);
            }catch (Exception e){System.out.println("An Error Occured");}


        }
    }
    public BlackWhiteEffect(String url) {

        if (url != null) {
            try {
                System.out.println("Running");
                originalImage = new Mat();
                bwEffect = new Mat();
                originalImage = openCVMat.imageToMatrix(new Image(url));
                Imgproc.cvtColor(originalImage, bwEffect, Imgproc.COLOR_BGR2GRAY);
            }catch (Exception e){System.out.println(e);}


        }
    }*/
    public BlackWhiteEffect(Image image) {
        if (image != null) {
            try {
                openCVMat = new OpenCVMat();
                System.out.println("Running");
                originalImage = new Mat();
                bwEffect = new Mat();
                originalImage = openCVMat.imageToMatrix(image);
                Imgproc.cvtColor(originalImage, bwEffect, Imgproc.COLOR_BGR2GRAY);
                openCVMat.setEffect(bwEffect);
                openCVMat.setOriginalImage(originalImage);
            }catch (Exception e){System.out.println(e);}


        }
    }
    public Image getEffect(){
        return openCVMat.getImagePost();
    }

}

