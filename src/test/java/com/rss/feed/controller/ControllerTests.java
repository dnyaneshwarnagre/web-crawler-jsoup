package com.rss.feed.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(MockitoJUnitRunner.class)
public class ControllerTests {

    @Mock
    private HttpServletRequest request;

    @Mock
    private Controller controller;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        controller = new Controller();

    }





}
