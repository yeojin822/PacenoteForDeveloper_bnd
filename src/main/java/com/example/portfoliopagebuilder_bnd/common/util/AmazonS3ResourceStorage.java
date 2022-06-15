package com.example.portfoliopagebuilder_bnd.common.util;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.portfoliopagebuilder_bnd.file.dto.FileDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmazonS3ResourceStorage {

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    private final AmazonS3Client amazonS3Client;

    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile).orElseThrow(() -> new IllegalArgumentException("파일 전환 실패"));

        return upload(uploadFile, dirName);
    }
    // S3로 파일 업로드하기
    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.getName();   // S3에 저장된 파일 이름
        String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    // S3로 업로드
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // 로컬에 저장된 이미지 지우기
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("File delete success");
            return;
        }
        log.info("File delete fail");
    }

    private Optional<File> convert(MultipartFile multipartFile) throws IOException{
        File convertFile = new File(System.getProperty("user.dir") + "/" + multipartFile.getOriginalFilename());
        // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                fos.write(multipartFile.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();

    }

//    public ResponseEntity<?> uploadMultiPartAWSs3Object(StandardMultipartHttpServletRequest req) {
//        String bucketName = bucket;
//
//        try {
//            String fileKey = req.getParameter("file_key");
//
//            StandardMultipartHttpServletRequest smhsReq = new StandardMultipartHttpServletRequest(req);
//
//            log.error("19191919 "+smhsReq);
//
//            Iterator<String> itr = smhsReq.getFileNames();
//            MultipartFile file = temp2.getFile(itr.next());
//            //MultipartFile file = smhsReq.getRequest()
//
//            ObjectMetadata objectMetadata = new ObjectMetadata();
//            objectMetadata.setContentType(file.getContentType());
//            objectMetadata.setContentLength(file.getSize());
//
//            String path = "public/" + fileKey;
//
//            PutObjectRequest request = new PutObjectRequest(bucketName, path, file.getInputStream(), objectMetadata);
//            amazonS3Client.putObject(request);
//        } catch (AmazonServiceException e) {
//            e.printStackTrace();
//        } catch (SdkClientException e){
//            e.printStackTrace();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        return new ResponseEntity(HttpStatus.OK);
//    }

//    public void store(String fullPath, MultipartFile multipartFile) {
//        File file = new File(MultipartUtil.getLocalHomeDirectory(), fullPath);
//
//        log.info("bucket ::: {}", bucket);
//        log.info("fullPath ::: {}", fullPath);
//        log.info("file ::: {}", file);
//        log.info("multipartFile ::: {}", multipartFile);
//
//        try {
//            multipartFile.transferTo(file);
//            amazonS3Client.putObject(new PutObjectRequest(bucket, fullPath, file)
//                    .withCannedAcl(CannedAccessControlList.PublicRead));
//        } catch (Exception e) {
//            throw new RuntimeException();
//        } finally {
//            if (file.exists()) {
//                file.delete();
//            }
//        }
//    }
}
