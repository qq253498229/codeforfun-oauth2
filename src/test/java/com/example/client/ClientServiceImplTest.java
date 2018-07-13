package com.example.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.oauth2.provider.ClientDetails;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceImplTest {

  private static final String CLIENT = "client";
  @InjectMocks
  private ClientServiceImpl clientService;

  @Mock
  private ClientRepository clientRepository;


  /**
   * 正常获取client信息
   */
  @Test
  public void loadClientByClientId() {
    given(clientRepository.loadClientByClientId(anyString())).willReturn(new Client(CLIENT));

    ClientDetails clientDetails = clientService.loadClientByClientId(anyString());
    assertNotNull(clientDetails);
  }

  /**
   * client_id不存在的情况
   */
  @Test(expected = ClientNotExistException.class)
  public void loadClientByClientId_ResultNotFound() {
    given(clientRepository.loadClientByClientId(anyString())).willReturn(null);

    clientService.loadClientByClientId(anyString());
  }

  /**
   * 正常保存client信息
   */
  @Test
  public void save() {
    given(clientRepository.loadClientByClientId(anyString())).willReturn(null);

    boolean result = clientService.save(new Client(CLIENT));
    assertTrue(result);
  }

  /**
   * client已经存在的情况
   */
  @Test(expected = ClientExistException.class)
  public void save_exist() {
    given(clientRepository.loadClientByClientId(anyString())).willReturn(new Client(CLIENT));

    boolean result = clientService.save(new Client(CLIENT));
    assertTrue(result);
  }


}