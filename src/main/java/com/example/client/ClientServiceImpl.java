package com.example.client;

import com.example.config.EmbeddedRedisConfig;
import com.example.util.SerializeUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

import static com.example.consts.RedisConst.CLIENT_LIST_KEY;

/**
 * @author wangbin
 */
@Component
public class ClientServiceImpl implements ClientDetailsService {
    @Resource
    private ClientRepository clientRepository;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private EmbeddedRedisConfig.RedisProperties redisProperties;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        // 从redis读取
        if (redisProperties != null && ("true".equals(redisProperties.getEnable()) || "true".equals(redisProperties.getEmbeddedEnable()))) {
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            String json = valueOperations.get(CLIENT_LIST_KEY + clientId);
            if (!ObjectUtils.isEmpty(json)) {
                return SerializeUtil.deserialize(json, Client.class);
            }
        }
        ClientDetails clientDetails = clientRepository.loadClientByClientId(clientId);
        if (clientDetails == null) {
            throw new ClientNotExistException();
        }

        // 存放到redis中
        if (redisProperties != null && ("true".equals(redisProperties.getEnable()) || "true".equals(redisProperties.getEmbeddedEnable()))) {
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            String serialize = SerializeUtil.serialize(clientDetails);
            valueOperations.set(CLIENT_LIST_KEY + clientId, serialize, redisProperties.getCacheTimeout(), TimeUnit.MINUTES);
        }

        return clientDetails;
    }

    public boolean save(Client client) {
        Client customClient = clientRepository.loadClientByClientId(client.getClientId());
        if (customClient != null) {
            throw new ClientExistException();
        }
        clientRepository.save(client);
        return true;
    }
}
