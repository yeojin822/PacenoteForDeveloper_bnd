package com.example.portfoliopagebuilder_bnd.controller;

import com.example.portfoliopagebuilder_bnd.service.builder.BuilderService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
@Tag(name="builder", description = "builder-controller")
public class BuilderController {
    BuilderService builderService;

    @Autowired
    public BuilderController(BuilderService builderService) {
        this.builderService = builderService;
    }

//    @PutMapping("/{userId}")
//    @ResponseBody
//    public ResponseEntity<?> updateBuilder(@RequestBody Map<String, Object> param, HttpSession session) throws Exception {
//        return builderService.updateBuilder(param);
//    }



    @Operation(summary = "빌더 정보 저장", description = "사용자 builder 정보 저장")
    @Parameter(in = ParameterIn.HEADER, name = "sessionkey", description = "session key", required = true, schema = @Schema(type = "string", defaultValue = "ppbTestdev"))
    @PostMapping("/builder")
    @ResponseBody
    public boolean save(@RequestBody Map<String, Object> param, HttpSession session) throws Exception {
        return builderService.save(param);
    }


    @Operation(summary = "빌더 정보 조회", description = "사용자 id 로 등록된 모든 builder 정보 조회")
    @Parameter(in = ParameterIn.HEADER, name = "sessionkey", description = "session key", required = true, schema = @Schema(type = "string", defaultValue = "ppbTestdev"))
    @GetMapping("/builder/{id}")
    public ResponseEntity<?> detail(@Parameter(description = "사용자 아이디", required = true) @PathVariable String id) throws Exception {
        return builderService.detail(id);
    }

}
