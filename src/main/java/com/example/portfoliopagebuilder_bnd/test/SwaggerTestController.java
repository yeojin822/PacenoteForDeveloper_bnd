package com.example.portfoliopagebuilder_bnd.test;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/test/swagger")
public class SwaggerTestController {

    @GetMapping("/time")
    @ApiOperation(value = "test")
    @ResponseBody
    public LocalDateTime Hello(){
        return LocalDateTime.now();
    }
}
