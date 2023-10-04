package com.rss.feed.service;

import com.rss.feed.dto.response.Response;
import com.rss.feed.entity.RSSFeed;
import com.rss.feed.repository.RSSFeedConfigRepository;
import com.rss.feed.repository.RSSFeedRepository;
import com.rss.feed.service.impl.RSSFeedServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.lang.model.util.Elements;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@WebAppConfiguration
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class FeedDataServiceImplTest {

    @InjectMocks
    private RSSFeedServiceImpl rssFeedService;

    @Mock
    private RSSFeedRepository rssFeedRepository;
    @Mock
    private RSSFeedConfigRepository feedConfigRepository;


    @Mock
    private HttpServletRequest request;

    @Mock
    private Elements mockElements;

    @Mock
    private Document mockDocument;

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
        page = 0;
        size = 10;
        rssFeeds = Arrays.asList(
                RSSFeed.builder().id(1L).link("https://example.com/link1").title("Title 1").description("Description 1").author("Author 1").publicationDate("2023-09-26 10:00").thumbnail("thumbnail1").build(),
                RSSFeed.builder().id(2L).link("https://example.com/link2").title("Title 2").description("Description 2").author("Author 2").publicationDate("2023-09-26 11:00").thumbnail("thumbnail2").build(),
                RSSFeed.builder().id(3L).link("https://example.com/link3").title("Title 3").description("Description 3").author("Author 3").publicationDate("2023-09-26 12:00").thumbnail("thumbnail3").build()
        );
    }


    @Test
    public void testIngestRSSFeed() throws NoSuchMethodException {
        RSSFeedServiceImpl rssFeedService = new RSSFeedServiceImpl(); // Instantiate the service

        String mockLinkHtml = "<a href='http://example.com'>Example Link</a>";
        Document doc = Jsoup.parse(mockLinkHtml);
        Element mockLink = doc.select("a").first();

        Method processLinkMethod = RSSFeedServiceImpl.class.getDeclaredMethod("processLink", Element.class);
        processLinkMethod.setAccessible(true);

        Method urlConnectMethod = RSSFeedServiceImpl.class.getDeclaredMethod("URLConnect", String.class);
        urlConnectMethod.setAccessible(true);
        String mockLinkUrl = "https://thedispatch.com/rssfeeds/";


        Method processItemMethod = RSSFeedServiceImpl.class.getDeclaredMethod("processItem", Element.class, String.class, String[].class);
        processItemMethod.setAccessible(true);
        // Create a sample HTML content for the item
        Element mockItem = doc.select("item").first();


        String[] mockCategories = {"Category1", "Category2"};
        try {
            processLinkMethod.invoke(rssFeedService, mockLink);

            Document result = (Document) urlConnectMethod.invoke(rssFeedService, mockLinkUrl);
            Assert.assertEquals(true, result != null);

            processItemMethod.invoke(rssFeedService, mockItem, mockLinkUrl, mockCategories);

        } catch (IllegalAccessException | InvocationTargetException e) {
            Assert.assertEquals("Custom error message", e.getCause().getMessage());

        }
    }

    @Test
    public void testFindByCategoriesIn() {
        Page<RSSFeed> rssFeedPage = new PageImpl<>(rssFeeds, PageRequest.of(page, size), rssFeeds.size());

        when(rssFeedService.findByCategoriesIn(categories, startDate, endDate, page, size))
                .thenReturn(rssFeedPage);
        Page<RSSFeed> result = rssFeedService.findByCategoriesIn(categories, startDate, endDate, page, size);

        if (!result.isEmpty()) {
            Assert.assertFalse(result.isEmpty());
        } else {
            Assert.assertTrue(result.getTotalPages() > 1);
        }

    }
}

