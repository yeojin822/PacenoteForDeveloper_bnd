package com.example.portfoliopagebuilder_bnd.common.util.web;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WebRequestUtilTest {

    @Test
    void httpRequestTest() {
        String url = "http://www.google.com";
        String res = WebRequestUtil.httpRequest(url,"","GET");

        assertEquals(res.isEmpty(),false);
    }
}