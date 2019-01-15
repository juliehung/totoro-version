package io.dentall.totoro.config;

import io.dentall.totoro.TotoroApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
@TestPropertySource("/config/cache.properties")
public class CacheConfigurationTest {

    @Autowired
    private CacheManager cacheManager;

    @Test
    public void jCacheCacheWithCachesAndCustomizer() {
        assertThat(cacheManager.getCacheNames()).isNotEmpty();
    }
}
