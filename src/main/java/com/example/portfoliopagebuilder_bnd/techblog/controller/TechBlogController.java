package com.example.portfoliopagebuilder_bnd.techblog.controller;

import com.example.portfoliopagebuilder_bnd.builder.service.BuilderService;
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

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
@Tag(name="tech_blog", description = "tech_blog-controller")
@RequiredArgsConstructor
public class TechBlogController {
    private final TechBlogService techBlogService;

    @Operation(summary = "테크블로그 목록 조회", description = "테크블로그 목록 조회")
    @Parameter(in = ParameterIn.HEADER, name = "sessionkey", description = "session key", required = true, schema = @Schema(type = "string", defaultValue = "ppbTestdev"))
    @GetMapping("/tech-blog")
    public ResponseEntity<?> getOfficialList(@Parameter(description = "사용자 아이디", required = true) @PathVariable String id) throws Exception {
        return techBlogService.getTechBlogList(id);
    }

}
