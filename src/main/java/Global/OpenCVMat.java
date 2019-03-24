//This Class handles creating Matrix out a Image and visa versa for Open CV
//This class can be used for any open cv code
package Global;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritablePixelFormat;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;

public class OpenCVMat {
    private Mat originalImage, effect;
    // Constructor
    public OpenCVMat(){}

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

    //Get the Original Image before the Effect
    public Image getImageOriginal(){
        try{
            return matToImage(originalImage);
        }catch (Exception e){
            return null;
        }
    }

    //Get the Buffered Image After the Effect
    public Image getImagePost(){
        try {
            // getImageOriginal();
            return matToImage(effect);
        }catch(Exception e){
            return  null;
        }
    }

    //JavaFX image to a Matrix
    public Mat imageToMatrix(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        byte[] buffer = new byte[width * height * 4];
        //Reads every pixel from image and converts to matrix
        PixelReader reader = image.getPixelReader();
        WritablePixelFormat<ByteBuffer> format = WritablePixelFormat.getByteBgraInstance();
        reader.getPixels(0, 0, width, height, format, buffer, 0, width * 4);
        //CV 4 channel
        Mat mat = new Mat(height, width, CvType.CV_8UC4);
        mat.put(0, 0, buffer);
        return mat;

    }

    public Image mat2Image(Mat frame) {
        try {
            return matToImage(frame);
        } catch (Exception e) {
            System.err.println("Cannot convert the Mat object:" + e);
            return null;
        }
    }

    public Mat imageToMatrix(Image image, int t) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        byte[] buffer = new byte[width * height * 4];

        //Reads every pixel from image and converts to matrix
        PixelReader reader = image.getPixelReader();
        WritablePixelFormat<ByteBuffer> format = WritablePixelFormat.getByteBgraInstance();
        reader.getPixels(0, 0, width, height, format, buffer, 0, width * 4);

        //CV 4 channel
        Mat mat = new Mat(height, width, t);
        mat.put(0, 0, buffer);
        return mat;
    }

    //Matrix to JavaFX image
    public Image matToImage(Mat matrix){
        try {
            //converts to .bmp for fast conversion
            MatOfByte byteMat = new MatOfByte();
            Imgcodecs.imencode(".bmp", matrix, byteMat);
            return new Image(new ByteArrayInputStream(byteMat.toArray()));

        } catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    //Matrix to Buffered Image
    public BufferedImage matToBufferedImage(Mat original) {
        java.awt.image.BufferedImage image = null;
        int width = original.width(); int height = original.height(); int channels = original.channels();
        byte[] sourcePixels = new byte[width * height * channels];
        original.get(0,0, sourcePixels);

        if (original.channels() > 1){
            image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        }
        else {
            image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        }

        byte[] targetPixels =  ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(sourcePixels, 0, targetPixels, 0, sourcePixels.length);
        return image;
    }

    //Buffered Image to Matrix
    private Mat bufferedImageToMat(BufferedImage bi) {
        Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);
        System.out.println(bi.getType());
        byte[] data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
        mat.put(0, 0, data);
        return mat;

    }
}
