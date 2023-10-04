package com.rss.feed.service.impl;

import com.rss.feed.entity.RSSFeedConfig;
import com.rss.feed.repository.RSSFeedConfigRepository;
import com.rss.feed.service.RSSFeedConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RSSFeedConfigServiceImpl implements RSSFeedConfigService {

    @Autowired
    private RSSFeedConfigRepository rssFeedConfigRepository;

    public RSSFeedConfig create(RSSFeedConfig rssFeedConfig) {
        return rssFeedConfigRepository.save(rssFeedConfig);
    }

    public RSSFeedConfig getById(Long id) {
        try {
            return rssFeedConfigRepository.findById(id)
                    .orElseThrow(() -> new Exception("RSSFeedConfig not found with id: " + id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Page<RSSFeedConfig> get(Pageable pageable) {
        return rssFeedConfigRepository.findAll(pageable);
    }

    public RSSFeedConfig update(Long id, RSSFeedConfig updatedConfig) {
        RSSFeedConfig existingConfig = getById(id);
        existingConfig.setWebsite(updatedConfig.getWebsite());
        return rssFeedConfigRepository.save(existingConfig);
    }

}
