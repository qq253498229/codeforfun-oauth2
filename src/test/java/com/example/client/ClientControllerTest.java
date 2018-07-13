package com.example.client;

import com.example.CodeforfunOauth2Application;
import com.example.CodeforfunOauth2ApplicationTests;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = {CodeforfunOauth2Application.class, CodeforfunOauth2ApplicationTests.class})
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("integration-test")
public class ClientControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ClientServiceImpl clientService;

  /**
   * 没有权限时候无法访问
   */
  @Test
  public void save_isUnauthorized() throws Exception {
    given(clientService.save(any())).willReturn(true);

    getPerform()
            .andExpect(status().isUnauthorized())
            .andDo(print())
    ;
  }

  /**
   * 正常执行保存client操作
   */
  @Test
  @WithMockUser
  public void save_ok() throws Exception {
    given(clientService.save(any())).willReturn(true);

    getPerform()
            .andExpect(status().isOk())
            .andDo(print())
    ;
  }

  /**
   * client已经存在，报409
   */
  @Test
  @WithMockUser
  public void save_exist() throws Exception {
    given(clientService.save(any())).willThrow(ClientExistException.class);

    getPerform()
            .andExpect(status().isConflict())
            .andDo(print())
    ;
  }

  private ResultActions getPerform() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(
            new Client("client", "secret", "app", "authorization_code", "http://www.baidu.com", 7200, 604800)
    );
    return mockMvc.perform(post("/oauth/client").contentType(APPLICATION_JSON_UTF8).content(json));
  }


}