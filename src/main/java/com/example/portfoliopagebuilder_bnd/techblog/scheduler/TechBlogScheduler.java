package com.example.portfoliopagebuilder_bnd.techblog.scheduler;

import org.springframework.scheduling.annotation.Scheduled;

public class TechBlogScheduler {

    @Scheduled(fixedDelay = 60 * 60 * 1000)
    public void updateNewPostTask(){

    }
}
