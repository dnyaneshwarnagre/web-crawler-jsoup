package com.rss.feed.controller;

import com.rss.feed.dto.response.Response;
import com.rss.feed.entity.RSSFeedConfig;
import com.rss.feed.service.RSSFeedConfigService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(MockitoJUnitRunner.class)
public class FeedConfigControllerTest {

    @InjectMocks
    private FeedConfigController rssFeedConfigController;

    @Mock
    private RSSFeedConfigService rssFeedConfigService;

    @Mock
    private HttpServletRequest request;

    private RSSFeedConfig rssFeedConfig;
    private Long  id;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    public void setData() {
        rssFeedConfig = RSSFeedConfig.builder().website("https://thedispatch.com/rssfeeds/").build();
        id = 1L;
    }

        @Test
    public void testCreateFeedConfig() {
        // Arrange
        when(rssFeedConfigService.create(any(RSSFeedConfig.class))).thenReturn(rssFeedConfig);

        // Act
        ResponseEntity<Response> response = rssFeedConfigController.createFeedConfig(rssFeedConfig);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetRSSFeedConfig() {
        // Arrange
        when(rssFeedConfigService.getById(eq(id))).thenReturn(rssFeedConfig);

        // Act
        ResponseEntity<Response> response = rssFeedConfigController.getRSSFeedConfig(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetAllScrapWebsite() {
        ResponseEntity<Response> response = rssFeedConfigController.getAllScrapWebsite(0, 10);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdateRSSFeedConfig() {
        Long id = 1L;
        when(rssFeedConfigService.update(eq(id), any(RSSFeedConfig.class))).thenReturn(rssFeedConfig);

        ResponseEntity<Response> response = rssFeedConfigController.updateRSSFeedConfig(id, rssFeedConfig);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}

