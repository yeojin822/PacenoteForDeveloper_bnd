package com.example.portfoliopagebuilder_bnd.test;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@OpenAPIDefinition(info = @Info(title = "테스트", description = "테스트URL"))
@RequestMapping("/test/swagger")
public class SwaggerTestController {

    @GetMapping("/time")
    @ResponseBody
    public LocalDateTime Hello(){
        return LocalDateTime.now();
    }
}
