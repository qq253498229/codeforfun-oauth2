package com.example.client;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@DataJpaTest
@RunWith(SpringRunner.class)
public class ClientRepositoryTest {
  private static final String CLIENT = "client";
  @Autowired
  private ClientRepository clientRepository;

  @Before
  public void setUp() {
    clientRepository.deleteAll();
    clientRepository.flush();
    Client client = new Client(CLIENT, "secret", "app",
            "password,authorization_code,refresh_token", "http://localhost:4200",
            7200, 604800);
    clientRepository.save(client);
  }

  /**
   * 通过client_id获取client信息
   */
  @Test
  public void loadClientByClientId() {
    ClientDetails client = clientRepository.loadClientByClientId("client");
    assertNotNull(client);
    assertEquals(client.getClientId(), CLIENT);
  }
}