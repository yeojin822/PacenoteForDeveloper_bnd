package com.example.portfoliopagebuilder_bnd.file.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmazonS3Service {

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    private final AmazonS3Client amazonS3Client;

    public ResponseEntity<?> uploadMultiPartAWSs3Object(MultipartHttpServletRequest req) {
        String bucketName = bucket;
        try {
            MultipartFile file = req.getFile("file");
            log.info("multpartFile ::: {}", file);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(file.getSize());


            String path = "public/" + UUID.randomUUID() + file.getOriginalFilename();

            log.info("path ::: {}",  path );

            PutObjectRequest request = new PutObjectRequest(bucketName, path, file.getInputStream(), objectMetadata);
            amazonS3Client.putObject(request);
        } catch (AmazonServiceException e) {
            e.printStackTrace();
        } catch (SdkClientException e){
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new ResponseEntity(HttpStatus.OK);
    }
}
