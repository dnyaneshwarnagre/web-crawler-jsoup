package com.rss.feed.service;

import com.rss.feed.entity.RSSFeedConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RSSFeedConfigService {
    RSSFeedConfig create(RSSFeedConfig rssFeedConfig);
    RSSFeedConfig getById(Long id);

    Page<RSSFeedConfig> get(Pageable pageable);

    RSSFeedConfig update(Long id, RSSFeedConfig updatedConfig);
}
