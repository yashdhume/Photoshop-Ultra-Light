package CamCapture;

import Global.DragandDrop;
import Main.EditingView;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.ImageView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CamCaptureDemo{

    private ScheduledExecutorService timer;
    private VideoCapture capture = new VideoCapture();
    private boolean cameraActive = false;
    private int cameraId = 0;

    private ImageView cameraDisplay = new ImageView();
    private Button btCamera = new Button("Camera");
    private BorderPane borderPane = new BorderPane();
    private HBox btCameraBox = new HBox();
    public CamCaptureDemo(){
       // start();
    }


    private BufferedImage MatToBufferedImage(Mat original)
    {
        BufferedImage image = null;
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

    private <T> void onFXThread(final ObjectProperty<T> property, final T value)
    {
        Platform.runLater(()->{property.set(value);});
    }

    private void updateImageView(ImageView view, Image image)
    {
        onFXThread(view.imageProperty(), image);
    }



    private Image mat2Image(Mat frame)
    {
        try
        {
            return SwingFXUtils.toFXImage(MatToBufferedImage(frame), null);
        }
        catch (Exception e)
        {
            System.err.println("Cannot convert the Mat object:" + e);
            return null;
        }
    }

    private void stopAcquisition(){

        if (timer != null && !timer.isShutdown())
        {
            try
            {
                timer.shutdown();
                timer.awaitTermination(33, TimeUnit.MILLISECONDS);
            }
            catch (InterruptedException e)
            {
                System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
            }
        }
        if (capture.isOpened())
        {
            capture.release();
        }
    }

    public void setClosed(){
        stopAcquisition();
    }

    private void startCamera(ActionEvent e)
    {
        if (!cameraActive) {
            // start the video capture.
            capture.open(cameraId);

            // is the video stream available?
            if (capture.isOpened()) {
                cameraActive = true;


                // grab Global frame every 33 ms (30 frames/sec)
                Runnable framGrabber = new Runnable() {
                    @Override
                    public void run() {

                        // effectively grab and process Global single frame
                        Mat frame = grabFrame();

                        // convert and show the frame
                        Image imageToShow = mat2Image(frame);
                        updateImageView(cameraDisplay, imageToShow);
                        DragandDrop dragandDrop = new DragandDrop();
                       dragandDrop.local(cameraDisplay, EditingView.imageViewEditView);
                    }
                };

                timer = Executors.newSingleThreadScheduledExecutor();
                timer.scheduleAtFixedRate(framGrabber, 0, 33, TimeUnit.MILLISECONDS);

                btCamera.setText("Stop Camera");
            } else {

                System.err.println("Impossible to open the camera connection..");

            }
        }
        else {

            cameraActive = false;
            btCamera.setText("Start Camera");


            stopAcquisition();
        }
    }


    private Mat grabFrame()
    {
        Mat frame = new Mat();

        // check if the capture is opened.
        if (capture.isOpened())
        {
            try
            {
                // read the current frame
                capture.read(frame);

                // If the frame is not empty, process it.
                //if (!frame.empty())
                //{
                // Convert color to gray.
                //    Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2RGB);
                //}

            }
            catch (Exception e)
            {
                System.err.println("Exception during the image elaboration: " + e);
            }
        }
        return frame;
    }


    public BorderPane start()
    {


        btCamera.setOnAction(e->startCamera(e));

        btCameraBox.getChildren().add(btCamera);
        btCameraBox.setAlignment(Pos.CENTER);
        btCameraBox.setPadding(new Insets(50,0,50,0));
        borderPane.setBottom(btCameraBox);

        // Image Display
        borderPane.setCenter(cameraDisplay);
        cameraDisplay.setPreserveRatio(true);
        cameraDisplay.setFitHeight(500);
        cameraDisplay.setFitWidth(750);
        return borderPane;
        // Scene scene = new Scene(borderPane, 800,600);
        /*primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            private void handle(WindowEvent event) {
                setClosed();
            }
        });
        primaryStage.show();*/
    }



   /* private void main(String[] args){
        // load the OpenCV library
        nu.pattern.OpenCV.loadShared();

        launch(args);
    }*/
}