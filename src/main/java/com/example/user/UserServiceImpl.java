package com.example.user;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
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
    @Cacheable(value = "users", key = "#username")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = userRepository.loadUserByUsername(username);
        if (userDetails == null) {
            throw new InvalidGrantException("用户名或密码错误");
        }
        return userDetails;
    }
}
