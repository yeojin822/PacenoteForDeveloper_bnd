package com.example.portfoliopagebuilder_bnd.techblog.scheduler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TechBlogSchedulerTest {

    @Mock
    TechBlogScheduler techBlogScheduler;

    @Test
    void updateNewPostTask() {
        techBlogScheduler.updateNewPostTask();
    }
}