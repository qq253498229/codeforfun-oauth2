package com.example.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    /**
     * 通过用户名获取用户信息
     */
    @Test
    public void loadUserByUsername() {
        given(userRepository.loadUserByUsername(anyString())).willReturn(new User());
        UserDetails userDetails = userService.loadUserByUsername(anyString());
        assertNotNull(userDetails);
    }

    /**
     * 用户名不存在的情况
     */
    @Test(expected = InvalidGrantException.class)
    public void loadUserByUsername_resultNull() {
        given(userRepository.loadUserByUsername(anyString())).willReturn(null);
        UserDetails userDetails = userService.loadUserByUsername(anyString());
        assertNotNull(userDetails);
    }
}