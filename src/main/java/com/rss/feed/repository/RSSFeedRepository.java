package com.rss.feed.repository;

import com.rss.feed.entity.RSSFeed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RSSFeedRepository extends JpaRepository<RSSFeed, Long> {
    Page<RSSFeed> findByCategoriesInAndPublicationDateBetween(
            List<String> categories,
            String startDate,
            String endDate,
            Pageable pageable
    );

    Optional<RSSFeed> findByLink(String link);

}
