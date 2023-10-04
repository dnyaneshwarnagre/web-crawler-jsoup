package com.rss.feed.controller;


import com.rss.feed.dto.response.Response;
import com.rss.feed.entity.RSSFeed;
import com.rss.feed.entity.RSSFeedConfig;
import com.rss.feed.repository.RSSFeedRepository;
import com.rss.feed.service.RSSFeedService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(MockitoJUnitRunner.class)
public class FeedDataControllerTests {
    @InjectMocks
    private FeedDataController rssFeedController;

    @Mock
    private RSSFeedService rssFeedService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private RSSFeedRepository rssFeedRepository;

    private String ingestRssFeedResponse;
    private Response response;
    private List<String> categories;
    private String startDate;
    private String endDate;
    private int page;
    private int size;
    private List<RSSFeed> rssFeeds;

    Long rssFeedId;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    public void setData(){
        ingestRssFeedResponse = "Processing of RSS feeds is complete.";
        rssFeedId = 1L;

        categories = Arrays.asList("Category1", "Category2");
        startDate = "2023-09-26";
        endDate = "2023-09-27";
        page = 1;
        size = 10;
        rssFeeds = Arrays.asList(
                RSSFeed.builder().id(1L).link("https://example.com/link1").title("Title 1").description("Description 1").author("Author 1").publicationDate("2023-09-26 10:00").thumbnail("thumbnail1").build(),
                RSSFeed.builder().id(2L).link("https://example.com/link2").title("Title 2").description("Description 2").author("Author 2").publicationDate("2023-09-26 11:00").thumbnail("thumbnail2").build(),
                RSSFeed.builder().id(3L).link("https://example.com/link3").title("Title 3").description("Description 3").author("Author 3").publicationDate("2023-09-26 12:00").thumbnail("thumbnail3").build()
        );
    }

    @Test
    public void testIngestRssFeed() {
        // Define your test data and expected results

        // Mock the service method
        Mockito.when(rssFeedService.processRssFeed(rssFeedId)).thenReturn(ingestRssFeedResponse);
        // Call the controller method
        ResponseEntity<Response> responseEntity = rssFeedController.processRssFeed(rssFeedId);
        // Assert the response
        Assert.assertEquals(ingestRssFeedResponse, responseEntity.getBody().getMessage());
        Assert.assertEquals(200, responseEntity.getBody().getStatus().intValue());
    }


    @Test
    public void testGetRSSFeedsByCategories() {
        Page<RSSFeed> rssFeedPage = new PageImpl<>(rssFeeds, PageRequest.of(page, size), rssFeeds.size());

        when(rssFeedService.findByCategoriesIn(categories, startDate, endDate, page, size))
                .thenReturn(rssFeedPage);

        ResponseEntity<Response> response = rssFeedController.getRSSFeedsByCategoriesAndDateRange(categories, startDate, endDate, page, size);

        Assert.assertEquals(200, response.getBody().getStatus().intValue());
    }
}

