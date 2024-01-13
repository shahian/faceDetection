package com.shahian.facedetection.controller;


import com.shahian.facedetection.service.FaceDetectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class MainController {
    private final FaceDetectionService faceDetectionService;

    public MainController(FaceDetectionService faceDetectionService) {
        this.faceDetectionService = faceDetectionService;
    }

    @ResponseBody
    @RequestMapping(value = "/skin-retouching", method = RequestMethod.GET)
    public ResponseEntity detectFaceJson1() throws IOException {
        faceDetectionService.skinRetouching();
            return new ResponseEntity(HttpStatus.OK);
    }
}
