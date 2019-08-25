package com.example.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataRedisTest
@ActiveProfiles("test")
public class RedisTest {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void test1() {
        ValueOperations<String, String> value = redisTemplate.opsForValue();
        value.set("amount", "5");
        Object amount = value.get("amount");
        assertEquals(amount, "5");
    }
}
