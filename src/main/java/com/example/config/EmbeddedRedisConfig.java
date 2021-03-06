package com.example.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author wangbin
 */
@Configuration
public class EmbeddedRedisConfig {
    @Resource
    private RedisProperties redisProperties;
    private RedisServer redisServer;

    @PostConstruct
    public void getRedisServer() throws IOException {
        if ("true".equals(redisProperties.getEmbeddedEnable())) {
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
    public static class RedisProperties {
        private String enable = "false";
        private Integer cacheTimeout = 120;
        private String embeddedEnable = "false";
    }
}
