package com.shahian.facedetection;

import nu.pattern.OpenCV;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FaceDetectionApplication {

    public static void main(String[] args) {
        SpringApplication.run(FaceDetectionApplication.class, args);
        OpenCV.loadShared();
    }

}
