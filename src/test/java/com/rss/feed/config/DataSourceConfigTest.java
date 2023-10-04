package com.rss.feed.config;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(MockitoJUnitRunner.class)
public class DataSourceConfigTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

    @Test
    public void testDataSourceConfiguration() {
        contextRunner
                .withPropertyValues(
                        "spring.datasource.driver-class-name=org.h2.Driver",
                        "spring.datasource.url=jdbc:h2:mem:testdb",
                        "spring.datasource.username=sa",
                        "spring.datasource.password=password"
                )
                .withUserConfiguration(DataSourceConfig.class)
                .run(context -> {
                    Assert.assertNotNull(context.getBean("dataSource"));
                });
    }
}
