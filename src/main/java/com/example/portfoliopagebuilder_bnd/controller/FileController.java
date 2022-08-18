package com.example.portfoliopagebuilder_bnd.controller;

import com.example.portfoliopagebuilder_bnd.service.file.AmazonS3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/file", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
@Tag(name="file", description = "file-controller")
public class FileController {
    private final AmazonS3Service amazonS3ResourceStorage;


    @PostMapping("/upload")
    @Operation(summary = "파일 저장", description = "aws 파일 저장")
    @Parameter(in = ParameterIn.HEADER, name = "sessionkey", description = "session key", required = true, schema = @Schema(type = "string", defaultValue = "ppbTestdev"))
    public ResponseEntity<?> post(MultipartHttpServletRequest request) throws Exception {
        log.info("file  ::: {}", request.getFile("file"));
        log.info("id  ::: {}", request.getParameter("id"));
        return amazonS3ResourceStorage.uploadMultiPartAWSs3Object(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "파일 조회", description = "aws 파일 조회")
    @Parameter(in = ParameterIn.HEADER, name = "sessionkey", description = "session key", required = true, schema = @Schema(type = "string", defaultValue = "ppbTestdev"))
    public String get(@PathVariable String id) throws Exception {
        return amazonS3ResourceStorage.getAWSs3Object(id);
    }

}
