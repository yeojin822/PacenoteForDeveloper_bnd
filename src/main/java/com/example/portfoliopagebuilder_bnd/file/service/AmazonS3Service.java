package com.example.portfoliopagebuilder_bnd.file.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.example.portfoliopagebuilder_bnd.oauth.mapper.UserMapper;
import com.example.portfoliopagebuilder_bnd.oauth.model.User;
import com.example.portfoliopagebuilder_bnd.oauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmazonS3Service {

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    private final AmazonS3Client amazonS3Client;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

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

            User user = userRepository.findById(req.getParameter("id")).orElseThrow(() -> new IllegalArgumentException("no such data"));
            user.setProfile(path);
            userRepository.save(user);

        } catch (AmazonServiceException e) {
            e.printStackTrace();
        } catch (SdkClientException e){
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return new ResponseEntity(HttpStatus.OK);
    }

    public String getAWSs3Object(String id) throws Exception {
        String out = new String();
        String path = userMapper.getProfilePath(id);

        try {

            S3Object original_obj = amazonS3Client.getObject(new GetObjectRequest(bucket, path));

            File tempFile = File.createTempFile("p12", ".tmp");
            tempFile.deleteOnExit();
            S3ObjectInputStream is = original_obj.getObjectContent();
            OutputStream os = new FileOutputStream(tempFile);

            FileInputStream fis = new FileInputStream(tempFile);
            IOUtils.copy(is, os);

            int len = 0;
            byte[] buf = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {

                while ((len = fis.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }

                byte[] fileArray = baos.toByteArray();
                out = new String(Base64.encodeBase64(fileArray));

//                // Thumbnail 요청의 경우만 변환
//                if("Y".equals(thumbYn)) {
//                    BufferedImage thumnailImg = makeThumbnail(tempFile.getAbsolutePath(), false, thumbSize);
//                    ByteArrayOutputStream os2 = new ByteArrayOutputStream();
//                    ImageIO.write(thumnailImg, fileKey.substring(fileKey.lastIndexOf(".") + 1, fileKey.length()), os2);
//
//                    byte[] fileArray2 = os2.toByteArray();
//                    out = new String(Base64.encodeBase64(fileArray2));
//                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                fis.close();
                baos.close();
                is.close();
                os.close();
                original_obj.close();
                System.gc();
                tempFile.delete();
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return out;
        }


    }

}
