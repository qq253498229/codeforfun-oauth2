package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Configuration
@Profile("!docker")
public class EmbeddedRedisConfig {
    private RedisServer redisServer;

    @PostConstruct
    public void getRedisServer() throws IOException {
        redisServer = new RedisServer(6379);
        try {
            redisServer.start();
        } catch (Exception ignored) {

        }
    }

    @PreDestroy
    public void stopRedis() {
        redisServer.stop();
    }
}
