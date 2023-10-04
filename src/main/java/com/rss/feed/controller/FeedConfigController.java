package com.rss.feed.controller;

import com.rss.feed.dto.constants.FeedConfigAPIEndpoints;
import com.rss.feed.dto.response.Response;
import com.rss.feed.entity.RSSFeedConfig;
import com.rss.feed.service.RSSFeedConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(FeedConfigAPIEndpoints.CONFIG)
public class FeedConfigController extends Controller{

    @Autowired
    private RSSFeedConfigService rssFeedConfigService;

    @PostMapping
    public ResponseEntity<Response> createFeedConfig(@RequestBody RSSFeedConfig rssFeedConfig) {
        logger.info("Creating RSS Feed Configuration: {}", rssFeedConfig.toString());

        return data(rssFeedConfigService.create(rssFeedConfig));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Response> getRSSFeedConfig(@PathVariable Long id) {
        logger.info("Fetching RSS Feed Configuration by ID: {}", id);
        return data(rssFeedConfigService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Response> getAllScrapWebsite( @RequestParam(name = "page", defaultValue = "0") int page,
                                                        @RequestParam(name = "size", defaultValue = "10") int size) {
        logger.info("Fetching all RSS Feed Configurations");

        Pageable pageable = PageRequest.of(page, size);
        return data(rssFeedConfigService.get(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateRSSFeedConfig(@PathVariable Long id, @RequestBody RSSFeedConfig updatedConfig) {
        logger.info("Updating RSS Feed Configuration with ID:- {} : {}",  id,  updatedConfig.toString());
        return data(rssFeedConfigService.update(id, updatedConfig));
    }

}
