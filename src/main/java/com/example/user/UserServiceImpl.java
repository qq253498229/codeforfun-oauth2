package com.example.user;

import com.example.config.EmbeddedRedisConfig;
import com.example.util.SerializeUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

import static com.example.consts.RedisConst.USER_LIST_KEY;

/**
 * @author wangbin
 */
@Component
public class UserServiceImpl implements UserDetailsService {
    @Resource
    private UserRepository userRepository;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private EmbeddedRedisConfig.RedisProperties redisProperties;

    /**
     * 通过用户名获取用户信息
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从redis读取
        if (redisProperties != null && ("true".equals(redisProperties.getEnable()) || "true".equals(redisProperties.getEmbeddedEnable()))) {
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            String json = valueOperations.get(USER_LIST_KEY + username);
            if (!ObjectUtils.isEmpty(json)) {
                return SerializeUtil.deserialize(json, User.class);
            }
        }


        UserDetails userDetails = userRepository.loadUserByUsername(username);
        if (userDetails == null) {
            throw new InvalidGrantException("用户名或密码错误");
        }

        // 存放到redis中
        if (redisProperties != null && ("true".equals(redisProperties.getEnable()) || "true".equals(redisProperties.getEmbeddedEnable()))) {
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            String serialize = SerializeUtil.serialize(userDetails);
            valueOperations.set(USER_LIST_KEY + username, serialize, redisProperties.getCacheTimeout(), TimeUnit.MINUTES);
        }

        return userDetails;
    }
}
