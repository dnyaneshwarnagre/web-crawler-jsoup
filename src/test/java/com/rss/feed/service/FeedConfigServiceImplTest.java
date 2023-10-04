package com.rss.feed.service;

import com.rss.feed.entity.RSSFeedConfig;
import com.rss.feed.repository.RSSFeedConfigRepository;
import com.rss.feed.service.impl.RSSFeedConfigServiceImpl;
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
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(MockitoJUnitRunner.class)
public class FeedConfigServiceImplTest {

    private RSSFeedConfig rssFeedConfig;
    private Long  id;

    @InjectMocks
    private RSSFeedConfigServiceImpl rssFeedConfigService;

    @Mock
    private RSSFeedConfigRepository rssFeedConfigRepository;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    public void setData() {
        rssFeedConfig = RSSFeedConfig.builder().id(1L).website("https://thedispatch.com/rssfeeds/").build();
        id = 1L;
    }

    @Test
    public void testCreate() {
        when(rssFeedConfigRepository.save(any(RSSFeedConfig.class))).thenReturn(rssFeedConfig);
        RSSFeedConfig savedConfig = rssFeedConfigService.create(rssFeedConfig);
        Assert.assertNotNull(savedConfig.getId());
    }

    @Test
    public void testGetById() {
        when(rssFeedConfigRepository.findById(anyLong())).thenReturn(Optional.of(rssFeedConfig));
        RSSFeedConfig retrievedConfig = rssFeedConfigService.getById(id);

        Assert.assertNotNull(retrievedConfig);
        Assert.assertEquals(rssFeedConfig.getId(), retrievedConfig.getId());
        Assert.assertEquals(rssFeedConfig.getWebsite(), retrievedConfig.getWebsite());
    }

    @Test
    public void testUpdate() {

        rssFeedConfig.setWebsite("https://www.theepochtimes.com/rssfeeds");
        when(rssFeedConfigRepository.findById(anyLong())).thenReturn(Optional.of(rssFeedConfig));

        when(rssFeedConfigRepository.save(any(RSSFeedConfig.class))).thenReturn(rssFeedConfig);
        Long id = 1L;
        RSSFeedConfig updated = rssFeedConfigService.update(id, rssFeedConfig);

        Assert.assertEquals(id, updated.getId());
        Assert.assertEquals("https://www.theepochtimes.com/rssfeeds", updated.getWebsite());
    }

    @Test
    public void testGet() {
        List<RSSFeedConfig> mockConfigs = new ArrayList<>();
        mockConfigs.add(rssFeedConfig);

        Pageable pageable = PageRequest.of(0, 10);

        when(rssFeedConfigRepository.findAll(pageable)).thenReturn(new PageImpl<>(mockConfigs));

        Page<RSSFeedConfig> result = rssFeedConfigService.get(pageable);

        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.getTotalElements());
        Assert.assertEquals(1, result.getTotalPages());
        Assert.assertEquals(1, result.getNumberOfElements());
        Assert.assertEquals(0, result.getNumber());

        verify(rssFeedConfigRepository, times(1)).findAll(pageable);
    }
}
