package com.shahian.facedetection.service;


import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.IOException;

@Service
public class FaceDetectionService {
    // Constants for brightness adjustment
    private static final double BRIGHTNESS_MULTIPLIER = 1.2;
    private static final Scalar BRIGHTNESS_ADDITION = new Scalar(10, 0, 0);

    // Constants for bilateral filter
    private static final int BILATERAL_DIAMETER = 9;
    private static final double BILATERAL_SIGMA_COLOR = 75.0;
    private static final double BILATERAL_SIGMA_SPACE = 75.0;

    // Constant for Gaussian blur
    private static final double GAUSSIAN_BLUR_SIZE = 0.05;


     Resource faceResource = new ClassPathResource("haarcascades/haarcascade_frontalface_alt.xml");
     Resource inputImagePath = new ClassPathResource("static/sample.jpg");
     String outputImagePath = "G:\\test_for_opencv\\output\\retouched_sample.jpg";

    public void skinRetouching() throws IOException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);


        // Load the input image
        Mat image = Imgcodecs.imread(inputImagePath.getFile().getPath());

        // Detect faces using Haar Cascade classifier
        CascadeClassifier faceDetector = new CascadeClassifier(faceResource.getFile().getAbsolutePath());

        MatOfRect faces = new MatOfRect();
        faceDetector.detectMultiScale(image, faces);


        // Apply skin retouching to each detected face
        for (Rect face : faces.toArray()) {
            Mat faceRegion = new Mat(image, new Rect(face.tl(), face.br())); // Ensure face region extraction

            // Apply skin retouching
            retouchSkin(faceRegion);
        }

        // Save the retouched image

        Imgcodecs.imwrite(outputImagePath, image);
    }

    private static void retouchSkin(Mat faceRegion) {
        // Convert to CV_8UC3 if needed
        if (faceRegion.type() != CvType.CV_8UC3) {
            Imgproc.cvtColor(faceRegion, faceRegion, Imgproc.COLOR_GRAY2BGR);
        }

        // Apply bilateral filtering for skin smoothing
        bilateralFiltermethod(faceRegion);

        // Adjust brightness and contrast
        brightness(faceRegion);

        // Apply Gaussian Blur for additional smoothing
        gaussianBlur(faceRegion);

        // Reduce redness
        Core.subtract(faceRegion, new Scalar(0, 0, 50), faceRegion);

        // Additional techniques like dodge and burn, frequency separation, etc. can be added here
    }


    private static void bilateralFiltermethod(Mat faceRegion) {
        Mat temp = new Mat();
        Imgproc.bilateralFilter(faceRegion, temp, BILATERAL_DIAMETER, BILATERAL_SIGMA_COLOR, BILATERAL_SIGMA_SPACE);
        temp.copyTo(faceRegion);
    }

    private static void gaussianBlur(Mat faceRegion) {
        Imgproc.GaussianBlur(faceRegion, faceRegion, new Size(0, 0), GAUSSIAN_BLUR_SIZE);
    }

    private static void brightness(Mat faceRegion) {
        Imgproc.cvtColor(faceRegion, faceRegion, Imgproc.COLOR_BGR2Lab);
        Core.multiply(faceRegion, new Scalar(BRIGHTNESS_MULTIPLIER, 1.0, 1.0), faceRegion);
        Core.add(faceRegion, BRIGHTNESS_ADDITION, faceRegion);
        Imgproc.cvtColor(faceRegion, faceRegion, Imgproc.COLOR_Lab2BGR);
    }


}

