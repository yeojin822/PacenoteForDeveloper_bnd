package com.example.portfoliopagebuilder_bnd.techblog.scheduler;

import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class TechBlogScheduler {

    @Scheduled(fixedRate = 60 * 60 * 1000) // 60 * 60 * 1000
    public void updateNewPostTask(){

    }
}
