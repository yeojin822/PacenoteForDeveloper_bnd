package com.example.portfoliopagebuilder_bnd.controller;

import com.example.portfoliopagebuilder_bnd.service.builder.BuilderService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/api/builder")
@Slf4j
@OpenAPIDefinition(info = @Info(title = "builder", description = "테스트 builder"))
public class BuilderController {
    BuilderService builderService;

    @Autowired
    public BuilderController(BuilderService builderService) {
        this.builderService = builderService;
    }

    @PostMapping("/{userId}")
    @ResponseBody
    public ResponseEntity<?> updateBuilder(@RequestBody Map<String, Object> param, HttpSession session) throws Exception {
        return builderService.updateBuilder(param);
    }


    @PostMapping("/save")
    @ResponseBody
    public boolean saveBuilder(@RequestBody Map<String, Object> param, HttpSession session) throws Exception {
        return builderService.testSave(param);
    }

    @GetMapping("/{userId}")
    @ResponseBody
    public ResponseEntity<?> detail(@PathVariable String userId) throws Exception {
        return builderService.detail(userId);
    }

}
