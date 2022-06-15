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

//    @Bean
//    MultipartConfigElement multipartConfigElement() {
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//        factory.setMaxFileSize(DataSize.ofBytes(-1));
//        factory.setMaxRequestSize(DataSize.ofBytes(-1));
//        return factory.createMultipartConfig();
//    }

}
