package Global;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritablePixelFormat;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;

public class OpenCVMat {
    private Mat originalImage, effect;
    public OpenCVMat(){}
    //Get the Original Image before the Effect
    public Image getImageOriginal(){
        try{
            return matToBufferedImage(originalImage);
        }catch (Exception e){
            return null;
        }
    }
    public void setEffect(Mat effect){ this.effect = effect; }
    public  void setOriginalImage(Mat originalImage){ this.originalImage = originalImage; }
    //Return the original Matrix of the Image
    public Mat getOriginalImage(){
        return originalImage;
    }
    //Return the Matrix after the effect
    public Mat getEffect(){
        return effect;
    }
    //Get the Buffered Image After the Effect
    public Image getImagePost(){
        try {
           // getImageOriginal();
            return matToBufferedImage(effect);
        }catch(Exception e){
            return  null;
        }
    }
    public Mat bufferedImageToMat(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        byte[] buffer = new byte[width * height * 4];
        System.out.println(image);
        PixelReader reader = image.getPixelReader();
        WritablePixelFormat<ByteBuffer> format = WritablePixelFormat.getByteBgraInstance();
        reader.getPixels(0, 0, width, height, format, buffer, 0, width * 4);

        Mat mat = new Mat(height, width, CvType.CV_8UC4);
        mat.put(0, 0, buffer);
        return mat;

    }

    public Image matToBufferedImage(Mat matrix){
        try {
            MatOfByte byteMat = new MatOfByte();
            Imgcodecs.imencode(".bmp", matrix, byteMat);
            return new Image(new ByteArrayInputStream(byteMat.toArray()));

        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }


}
