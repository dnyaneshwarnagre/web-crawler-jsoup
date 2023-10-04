package com.rss.feed.controller;

import com.rss.feed.dto.constants.FeedAPIEndpoints;
import com.rss.feed.dto.response.Response;
import com.rss.feed.service.RSSFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(FeedAPIEndpoints.FEED_PROCESS)
public class FeedDataController extends Controller{

    @Autowired
    private RSSFeedService rssFeedService;

    @PostMapping
    public ResponseEntity<Response> processRssFeed(@RequestParam("id") Long id) {
        logger.info("Processing RSS feed with ID: {}", id);
        return data(rssFeedService.processRssFeed(id));
    }


    @GetMapping
    public ResponseEntity<Response> getRSSFeedsByCategoriesAndDateRange(@RequestParam List<String> categories,
                                                            @RequestParam("startDate") String startDate,
                                                            @RequestParam("endDate") String endDate,
                                                            @RequestParam(name = "page", defaultValue = "0") int page,
                                                            @RequestParam(name = "size", defaultValue = "10") int size) {
        logger.info("Fetching RSS feeds by categories: {}, startDate: {}, endDate: {}, page: {}, size: {}",
                categories, startDate, endDate, page, size);
        return data(rssFeedService.findByCategoriesIn(categories, startDate, endDate, page, size));
    }


}
