package com.example.portfoliopagebuilder_bnd;

import com.example.portfoliopagebuilder_bnd.common.configuration.properties.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties({
        AppProperties.class
})
public class PortfolioPageBuilderBndApplication {

    static {
        System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
    }

    public static void main(String[] args) {
        SpringApplication.run(PortfolioPageBuilderBndApplication.class, args);
    }

}
