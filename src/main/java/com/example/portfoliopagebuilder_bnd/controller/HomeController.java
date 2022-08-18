package com.example.portfoliopagebuilder_bnd.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Slf4j
@Hidden
public class HomeController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String welcome() {
        return "hello";
    }
}
