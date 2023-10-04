package com.rss.feed.service.impl;

import com.rss.feed.entity.RSSFeed;
import com.rss.feed.entity.RSSFeedConfig;
import com.rss.feed.repository.RSSFeedConfigRepository;
import com.rss.feed.repository.RSSFeedRepository;
import com.rss.feed.service.RSSFeedService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


@Service
public class RSSFeedServiceImpl implements RSSFeedService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RSSFeedRepository rssFeedRepository;

    @Autowired
    private RSSFeedConfigRepository feedConfigRepository;

    private static final String DATE_FORMAT="MM-dd-yyyy HH:mm";

    @Override
    public String processRssFeed(Long id) {
        Optional<RSSFeedConfig> rssFeedConfig = feedConfigRepository.findById(id);

        if (rssFeedConfig.isEmpty()) {
            throw new RuntimeException("Provided RSS feed ID is not configured in the DB.");
        }

        logger.info("Web scrapping process start");
        RSSFeedConfig config = rssFeedConfig.get();
        Document document = URLConnect(config.getWebsite());
        Elements links = document.select("div p a[href]");

        List<CompletableFuture<Void>> futures = links.stream()
                .map(link -> CompletableFuture.runAsync(() -> processLink(link)))
                .collect(Collectors.toList());

        CompletableFuture<Void>[] futuresArray = futures.toArray(new CompletableFuture[0]);

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futuresArray);
        allOf.join();
        logger.info("Web scrapping process ended.");
        return "Processing of RSS feeds is completed.";
    }

    @Override
    public Page<RSSFeed> findByCategoriesIn(List<String> categories, String startDate, String endDate, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return rssFeedRepository.findByCategoriesInAndPublicationDateBetween(categories, startDate, endDate, pageable);
    }


    private void processLink(Element link) {
        try {
            Document doc = URLConnect(link.attr("href"));
            Elements categoryElements = doc.select("category");

            String[] categories = categoryElements.stream()
                    .map(Element::text)
                    .toArray(String[]::new);

            logger.info("Link: {}", link.attr("href"));
            logger.info("Categories: {}", Arrays.toString(categories));

            Elements items = doc.select("item");

            items.forEach(item -> processItem(item, link.attr("href"), categories));
        } catch (Exception e) {
            logger.error("Error processing link:", e);
        }
    }
    private void processItem(Element item, String link, String[] categories) {
        try {
            ZonedDateTime zonedDateTime = ZonedDateTime.parse(item.select("pubDate").text(), DateTimeFormatter.RFC_1123_DATE_TIME);
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

            Optional<RSSFeed> existingEntry = rssFeedRepository.findByLink(link);
            RSSFeed rssFeed = existingEntry.orElseGet(() -> {
                // If it doesn't exist, create a new entry
                return RSSFeed.builder()
                        .link(link)
                        .categories(new ArrayList<>())
                        .build();
            });

            // Update the fields
            rssFeed.setTitle(item.select("title").text());
            rssFeed.setThumbnail(item.select("link").text());
            rssFeed.setDescription(item.select("description").text());
            rssFeed.setAuthor(item.select("author").text());
            rssFeed.setPublicationDate(zonedDateTime.format(outputFormatter));
            rssFeed.setLink(link);
            rssFeed.getCategories().addAll(Arrays.asList(categories));

            logger.info("RSS Feeds: {}", rssFeed);
            rssFeedRepository.save(rssFeed);
        } catch (Exception e) {
            logger.error("Error processing item:", e);
        }
    }
    private Document URLConnect(String url) {
        try {
            Document document = Jsoup.parse(Jsoup.connect(url).get().html());
            return document;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

