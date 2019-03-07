package Effects;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.nio.Buffer;

public class BlackWhiteEffect {
    private Mat originalImage, bwEffect;

    public BlackWhiteEffect(File input) {
        if (input != null) {
            try {
                BufferedImage image = ImageIO.read(input);
                originalImage = new Mat();
                bwEffect = new Mat();
                originalImage = bufferedImageToMat(image);

                Imgproc.cvtColor(originalImage, bwEffect, Imgproc.COLOR_RGB2GRAY);
            }catch (Exception e){System.out.println("An Error Occured");}


        }
    }
    public BlackWhiteEffect(URL url) {

        if (url != null) {
            try {
                System.out.println("Running");
                originalImage = new Mat();
                bwEffect = new Mat();
                originalImage = bufferedImageToMat(ImageIO.read(url));
                Imgproc.cvtColor(originalImage, bwEffect, Imgproc.COLOR_BGR2GRAY);
            }catch (Exception e){System.out.println(e);}


        }
    }
    private Mat bufferedImageToMat(BufferedImage bi) {
        Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);
        System.out.println(bi.getType());
        byte[] data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
        mat.put(0, 0, data);
        return mat;

    }

    private BufferedImage matToBufferedImage(Mat matrix){
        try {
            MatOfByte mob = new MatOfByte();
            Imgcodecs.imencode(".jpg", matrix, mob);
            byte ba[] = mob.toArray();
            BufferedImage bi = ImageIO.read(new ByteArrayInputStream(ba));
            return bi;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
    //Return the original Matrix of the Image
    public Mat getOriginalImage(){
        return originalImage;
    }

    //Return the Matrix after the effect
    public Mat getBwEffect(){
        return bwEffect;
    }


    //Get the Original Image before the Effect
    public BufferedImage getImageOriginal(){
        try{
            return matToBufferedImage(originalImage);
        }catch (Exception e){
            return null;
        }
    }

    //Get the Buffered Image After the Effect
    public BufferedImage getImagePost(){
        try {
            return matToBufferedImage(bwEffect);
        }catch(Exception e){
            return  null;
        }
    }
}

