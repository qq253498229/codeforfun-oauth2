package com.example.client;

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
  public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
    ClientDetails clientDetails = clientRepository.loadClientByClientId(clientId);
    if (clientDetails == null) {
      throw new ClientNotExistException();
    }
    return clientDetails;
  }

  boolean save(Client client) {
    Client customClient = clientRepository.loadClientByClientId(client.getClientId());
    if (customClient != null) {
      throw new ClientExistException();
    }
    clientRepository.save(client);
    return true;
  }
}
