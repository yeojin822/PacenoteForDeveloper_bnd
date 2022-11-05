package com.example.portfoliopagebuilder_bnd.builder.controller;

import com.example.portfoliopagebuilder_bnd.builder.dto.Builder;
import com.example.portfoliopagebuilder_bnd.builder.dto.BuilderType;
import com.example.portfoliopagebuilder_bnd.builder.dto.DeleteInfo;
import com.example.portfoliopagebuilder_bnd.builder.service.BuilderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@Tag(name="builder", description = "builder-controller")
@RequiredArgsConstructor
public class BuilderController {
    private final BuilderService builderService;

    @Operation(summary = "빌더 정보 저장", description = "사용자 builder 정보 저장")
    @Parameter(in = ParameterIn.HEADER, name = "sessionkey", description = "session key", required = true, schema = @Schema(type = "string", defaultValue = "ppbTestdev"))
    @PostMapping("/builder")
    @ResponseBody
    public ResponseEntity<?> save(@RequestBody @Validated Builder param, HttpSession session) throws Exception {
        return builderService.save(param);
    }


    @Operation(summary = "빌더 정보 조회", description = "사용자 id 로 등록된 모든 builder 정보 조회")
    @Parameter(in = ParameterIn.HEADER, name = "sessionkey", description = "session key", required = true, schema = @Schema(type = "string", defaultValue = "ppbTestdev"))
    @GetMapping("/builder/{id}")
    public ResponseEntity<?> detail(@Parameter(description = "사용자 아이디", required = true) @PathVariable String id) throws Exception {
        return builderService.detail(id);
    }

    @Operation(summary = "빌더 블럭 삭제", description = "type,id로 빌더 블럭 삭제")
    @Parameter(in = ParameterIn.HEADER, name = "sessionkey", description = "session key", required = true, schema = @Schema(type = "string", defaultValue = "ppbTestdev"))
    @PutMapping("/builder")
    public ResponseEntity<?> delete(@RequestBody @Validated DeleteInfo param) throws Exception {
        return builderService.delete(param);
    }

}
