package com.example.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wangbin
 */
@Component
public class UserServiceImpl implements UserDetailsService {
  @Resource
  private UserRepository userRepository;

  /**
   * 通过用户名获取用户信息
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserDetails userDetails = userRepository.loadUserByUsername(username);
    if (userDetails == null) {
      throw new NotUserException();
    }
    return userDetails;
  }
}
