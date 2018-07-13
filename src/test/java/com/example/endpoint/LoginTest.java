package com.example.endpoint;

import com.example.CodeforfunOauth2Application;
import com.example.CodeforfunOauth2ApplicationTests;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = {CodeforfunOauth2Application.class, CodeforfunOauth2ApplicationTests.class})
@RunWith(SpringRunner.class)
@ActiveProfiles("integration-test")
@AutoConfigureMockMvc
public class LoginTest {
  @Autowired
  private MockMvc mockMvc;

  /**
   * 登录测试
   */
  @Test
  public void testLogin() throws Exception {
    mockMvc.perform(formLogin())
            .andExpect(status().isFound())
            .andExpect(redirectedUrl("/"))
            .andDo(print())
    ;
  }

  /**
   * 注销测试
   */
  @Test
  public void testLogout() throws Exception {
    mockMvc.perform(logout("/oauth/logout"))
            .andExpect(status().isFound())
            .andExpect(redirectedUrl("/login?logout"))
            .andDo(print())
    ;
  }
}
