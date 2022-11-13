package com.example.portfoliopagebuilder_bnd.techblog.controller;

import com.example.portfoliopagebuilder_bnd.builder.dto.Builder;
import com.example.portfoliopagebuilder_bnd.builder.service.BuilderService;
import com.example.portfoliopagebuilder_bnd.techblog.model.dto.FavoriteDto;
import com.example.portfoliopagebuilder_bnd.techblog.service.TechBlogService;
import io.swagger.v3.core.util.Json;
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
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api")
@Slf4j
@Tag(name = "tech-blog", description = "tech-blog-controller")
@RequiredArgsConstructor
public class TechBlogController {
    private final TechBlogService techBlogService;

    @Operation(summary = "테크블로그 좋아요 저장", description = "테크블로그 좋아요 저장")
    @Parameter(in = ParameterIn.HEADER, name = "sessionkey", description = "session key", required = true, schema = @Schema(type = "string", defaultValue = "ppbTestdev"))
    @PostMapping("/tech-blog/favorite")
    public boolean saveFavorite(@Parameter(description = "좋아요 DTO") @RequestBody FavoriteDto favoriteDto) throws Exception {
        return techBlogService.save(favoriteDto);
    }

    @Operation(summary = "테크블로그 클릭 저장", description = "테크블로그 클릭 저장")
    @Parameter(in = ParameterIn.HEADER, name = "sessionkey", description = "session key", required = true, schema = @Schema(type = "string", defaultValue = "ppbTestdev"))
    @PostMapping("/tech-blog/click")
    public boolean saveClickCount(@Parameter(description = "블로그 아이디", required = true) @RequestBody Long blogId) throws Exception {
        return techBlogService.saveClickCount(blogId);
    }

    @Operation(summary = "테크블로그 목록 조회", description = "테크블로그 목록 조회")
    @Parameter(in = ParameterIn.HEADER, name = "sessionkey", description = "session key", required = true, schema = @Schema(type = "string", defaultValue = "ppbTestdev"))
    @GetMapping("/tech-blog/{id}")
    public ResponseEntity<?> getOfficialList(@Parameter(description = "사용자 아이디", required = true) @PathVariable String id) throws Exception {
        return techBlogService.list(id);
    }

    @Operation(summary = "테크블로그 목록 추가", description = "테크블로그 목록 추가")
    @Parameter(in = ParameterIn.HEADER, name = "admin code", description = "admin code", required = true, schema = @Schema(type = "string", defaultValue = "12345r"))
    @PutMapping("/tech-blog/admin")
    public ResponseEntity<?> getOfficialList(@Parameter(description = "어드민 코드", required = true) @RequestBody String id, @RequestBody Map<?,?> param) throws Exception {
        return techBlogService.saveOfficial(param);
    }

}
