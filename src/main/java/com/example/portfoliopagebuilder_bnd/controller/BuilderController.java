package com.example.portfoliopagebuilder_bnd.controller;

import com.example.portfoliopagebuilder_bnd.service.builder.BuilderService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/builder")
@Slf4j
@OpenAPIDefinition(info = @Info(title = "builder", description = "테스트 builder"))
public class BuilderController {
    BuilderService builderService;

    @PostMapping("/{userId}")
    @ResponseBody
    public void builderUpdateInfo(@RequestAttribute(value = "params") String datas,
                                                    Map<String, Object> modelMap, HttpSession session) throws SQLException {


        try {
            modelMap  = new ObjectMapper().readValue(datas, Map.class);
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        log.info("test  ::: {}", modelMap);

    }
}
