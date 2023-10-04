package com.rss.feed.service;

import com.rss.feed.entity.RSSFeed;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RSSFeedService {

    String processRssFeed(Long id);
    Page<RSSFeed> findByCategoriesIn(List<String> categories, String startDate, String endDate, int page, int size);
}
