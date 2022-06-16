package com.example.portfoliopagebuilder_bnd.file.controller;

import com.example.portfoliopagebuilder_bnd.file.service.AmazonS3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/file", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class FileController {
    private final AmazonS3Service amazonS3ResourceStorage;


    @PostMapping("/upload")
    public ResponseEntity<?> post(MultipartHttpServletRequest request) throws Exception {
        log.info("file  ::: {}", request.getFile("file"));
        return amazonS3ResourceStorage.uploadMultiPartAWSs3Object(request);
    }


}
