package com.example.client;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wangbin
 */
@Component
public class ClientServiceImpl implements ClientDetailsService {
    @Resource
    private ClientRepository clientRepository;

    @Override
    @Cacheable(value = "clients", key = "#clientId")
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        ClientDetails clientDetails = clientRepository.loadClientByClientId(clientId);
        if (clientDetails == null) {
            throw new ClientNotExistException();
        }
        return clientDetails;
    }

    @CachePut(value = "clients", key = "#client.id")
    public boolean save(Client client) {
        Client customClient = clientRepository.loadClientByClientId(client.getClientId());
        if (customClient != null) {
            throw new ClientExistException();
        }
        clientRepository.save(client);
        return true;
    }
}
