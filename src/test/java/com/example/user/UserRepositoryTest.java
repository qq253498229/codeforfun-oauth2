package com.example.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@DataJpaTest
@RunWith(SpringRunner.class)
@ActiveProfiles("unit-test")
public class UserRepositoryTest {


  private static final String USER = "user";
  @Autowired
  private UserRepository userRepository;

  @Before
  public void setUp() {
    userRepository.deleteAll();
    userRepository.flush();
    User user = new User(USER, "password", true, null);
    userRepository.save(user);
  }

  @Test
  public void loadUserByUsername() {
    UserDetails user = userRepository.loadUserByUsername(USER);
    assertNotNull(user);
    assertEquals(user.getUsername(), USER);
  }
}