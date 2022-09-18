package com.example.portfoliopagebuilder_bnd.techblog.scheduler;

import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

@Component
public class TechBlogScheduler {

    //추후 다른곳에 선언할 곳 만들기
    public final static long ONE_SECOND = 1000;
    public final static long ONE_MINUTE = 60 * ONE_SECOND;
    public final static long ONE_HOUR = 60 * ONE_MINUTE;

    @Scheduled(fixedRate = ONE_HOUR)
    public void updateNewPostTask(){
        //System.out.println(DateTime.now() + " : scheduler test"); //test용
    }
}
