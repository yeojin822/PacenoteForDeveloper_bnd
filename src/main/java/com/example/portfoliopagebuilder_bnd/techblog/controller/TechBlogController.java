package com.example.portfoliopagebuilder_bnd.techblog.controller;

import com.example.portfoliopagebuilder_bnd.techblog.service.TechBlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
@Tag(name = "tech-blog", description = "tech-blog-controller")
@RequiredArgsConstructor
public class TechBlogController {
    private final TechBlogService techBlogService;

    @Operation(summary = "테크블로그 좋아요 저장", description = "테크블로그 좋아요 저장")
    @Parameter(in = ParameterIn.HEADER, name = "sessionkey", description = "session key", required = true, schema = @Schema(type = "string", defaultValue = "ppbTestdev"))
    @GetMapping("/tech-blog/favorite")
    public boolean saveFavorite(@Parameter(description = "사용자 아이디", required = true) @PathVariable String userId, @Parameter(description = "테크블로그 아이디", required = true) @PathVariable String officialId) throws Exception {
        return techBlogService.save(userId, officialId);
    }

    @Operation(summary = "테크블로그 목록 조회", description = "테크블로그 목록 조회")
    @Parameter(in = ParameterIn.HEADER, name = "sessionkey", description = "session key", required = true, schema = @Schema(type = "string", defaultValue = "ppbTestdev"))
    @GetMapping("/tech-blog/{id}")
    public ResponseEntity<?> getOfficialList(@Parameter(description = "사용자 아이디", required = true) @PathVariable String id) throws Exception {
        return techBlogService.list(id);
    }

    @Operation(summary = "테크블로그 목록 조회", description = "테크블로그 목록 조회")
    @Parameter(in = ParameterIn.HEADER, name = "admin code", description = "admin code", required = true, schema = @Schema(type = "string", defaultValue = "12345r"))
    @PutMapping("/tech-blog")
    public ResponseEntity<?> getOfficialList(@Parameter(description = "어드민 코드", required = true) @PathVariable String id, @RequestBody Map<?,?> param) throws Exception {
        return techBlogService.saveOfficial(param);
    }

}
