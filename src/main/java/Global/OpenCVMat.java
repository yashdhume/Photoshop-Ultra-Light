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
    public OpenCVMat(){}
    //Get the Original Image before the Effect
    public Image getImageOriginal(){
        try{
            return matToMatrix(originalImage);
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
            return matToMatrix(effect);
        }catch(Exception e){
            return  null;
        }
    }
    public Mat imageToMatrix(Image image) {
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
    public Image mat2Image(Mat frame)
    {
        try
        {
            return matToMatrix(frame);
        }
        catch (Exception e)
        {
            System.err.println("Cannot convert the Mat object:" + e);
            return null;
        }
    }

    public Image matToMatrix(Mat matrix){
        try {
            MatOfByte byteMat = new MatOfByte();
            Imgcodecs.imencode(".bmp", matrix, byteMat);
            return new Image(new ByteArrayInputStream(byteMat.toArray()));

        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
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
    private Mat bufferedImageToMat(BufferedImage bi) {
        Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);
        System.out.println(bi.getType());
        byte[] data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
        mat.put(0, 0, data);
        return mat;

    }


}
