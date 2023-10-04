package com.rss.feed.repository;

import com.rss.feed.entity.RSSFeedConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RSSFeedConfigRepository extends JpaRepository<RSSFeedConfig, Long> {
}
