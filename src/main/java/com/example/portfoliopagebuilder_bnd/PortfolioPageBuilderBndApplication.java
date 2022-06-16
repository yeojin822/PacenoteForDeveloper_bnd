package com.example.portfoliopagebuilder_bnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class PortfolioPageBuilderBndApplication {

    static {
        System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
    }

    public static void main(String[] args) {
        SpringApplication.run(PortfolioPageBuilderBndApplication.class, args);
    }

}
