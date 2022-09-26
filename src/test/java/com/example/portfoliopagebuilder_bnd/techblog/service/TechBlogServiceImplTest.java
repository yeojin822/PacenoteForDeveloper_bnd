package com.example.portfoliopagebuilder_bnd.techblog.service;

import com.example.portfoliopagebuilder_bnd.techblog.repository.TechOfficialRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TechBlogServiceImplTest {

    @Mock
    TechOfficialRepository techOfficialRepository;

    @InjectMocks
    TechBlogServiceImpl techBlogService;

    @Test
    void save() {
    }

    @Test
    void list() {
    }

    @Test
    void newList() throws Exception {
        assertThat(techBlogService.newList()).isTrue();
    }
}