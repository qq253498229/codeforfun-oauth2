package com.example.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.io.IOException;

@Configuration
@EnableCaching
@Profile("!docker")
public class EmbeddedRedisConfig {
    @Resource
    private RedisProperties redisProperties;
    private RedisServer redisServer;

    @PostConstruct
    public void getRedisServer() throws IOException {

        if (!redisProperties.getEnable()) {
            redisServer = new RedisServer(6379);
            try {
                redisServer.start();
            } catch (Exception ignored) {

            }
        }

    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }

    @Configuration
    @ConfigurationProperties("spring.redis")
    @Getter
    @Setter
    static class RedisProperties {
        private Boolean enable = false;
    }
}
