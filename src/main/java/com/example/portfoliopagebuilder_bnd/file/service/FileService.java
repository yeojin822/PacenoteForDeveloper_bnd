package com.example.portfoliopagebuilder_bnd.file.service;

import com.example.portfoliopagebuilder_bnd.common.service.AmazonS3ResourceStorage;
import com.example.portfoliopagebuilder_bnd.file.dto.FileDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileService {
        private final AmazonS3ResourceStorage amazonS3ResourceStorage;

        public FileDetail save(MultipartFile multipartFile) {
            FileDetail fileDetail = FileDetail.multipartOf(multipartFile);
          //  amazonS3ResourceStorage.store(fileDetail.getPath(), multipartFile);
            return fileDetail;
        }
}

