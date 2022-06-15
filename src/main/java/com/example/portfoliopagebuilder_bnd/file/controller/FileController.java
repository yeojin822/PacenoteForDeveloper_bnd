package com.example.portfoliopagebuilder_bnd.file.controller;

import com.example.portfoliopagebuilder_bnd.common.util.AmazonS3ResourceStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/upload", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class FileController {
    private final AmazonS3ResourceStorage amazonS3ResourceStorage;


    @PostMapping
        public String post(
                @RequestPart("file") MultipartFile multipartFile) throws IOException {
                amazonS3ResourceStorage.upload(multipartFile, "static");
            return "Test";
        }

//    @PostMapping
//    public ResponseEntity<?> post(MultipartHttpServletRequest request) throws IOException {
//        log.info("file  ::: {}", request);
//        StandardMultipartHttpServletRequest req = new StandardMultipartHttpServletRequest(request);
//
//        return amazonS3ResourceStorage.uploadMultiPartAWSs3Object(req);
//    }

//        @PostMapping
//        public ResponseEntity<FileDetail> post(
//                @RequestPart("file") MultipartFile multipartFile) throws IOException {
//            return ResponseEntity.ok(fileService.save(multipartFile));
//        }

    }
