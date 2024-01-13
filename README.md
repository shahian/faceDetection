# Face Detection and Skin Retouching Service

This service uses OpenCV and Spring Framework to perform face detection and skin retouching on input images.

## Overview

The `FaceDetectionService` class is a Spring service responsible for detecting faces in an input image and applying skin retouching effects to enhance the appearance of detected faces.

## Features

- **Face Detection**: Utilizes the Haar Cascade classifier to detect faces in the input image.

- **Skin Retouching Techniques**:
  - Bilateral filtering for skin smoothing.
  - Adjustment of brightness and contrast.
  - Gaussian Blur for additional smoothing.
  - Reduction of redness.
  - Other techniques like dodge and burn, frequency separation, etc., can be added.

## Usage

1. Ensure you have OpenCV installed and properly configured in your project.
2. Load the input image using `Imgcodecs.imread`.
3. Create an instance of `CascadeClassifier` to detect faces.
4. Apply skin retouching to each detected face using the `retouchSkin` method.

```java
// Example usage
FaceDetectionService faceDetectionService = new FaceDetectionService();
faceDetectionService.skinRetouching();
