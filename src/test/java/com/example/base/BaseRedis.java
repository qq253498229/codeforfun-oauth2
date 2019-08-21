package com.example.base;

import org.junit.After;
import org.junit.Before;
import redis.embedded.RedisServer;

public class BaseRedis {

    private RedisServer redisServer;

    @Before
    public void setUp() throws Exception {
        redisServer = new RedisServer(6379);
        redisServer.start();
    }

    @After
    public void tearDown() {
        redisServer.stop();
    }
}
