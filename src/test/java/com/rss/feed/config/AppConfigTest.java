package com.rss.feed.config;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(MockitoJUnitRunner.class)
public class AppConfigTest {

    private String appVersion;

    @BeforeEach
    public void setData(){
        appVersion = "1.0";
    }


    @Test
    public void testAppVersionProperty() {
        Assert.assertNotNull(appVersion);
        Assert.assertEquals("1.0", appVersion);

    }

}
