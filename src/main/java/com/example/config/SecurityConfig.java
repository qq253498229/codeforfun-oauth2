package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 安全配置
 *
 * @author wangbin
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private static final String REFERER = "referer";

  /**
   * 由于2.0不再提供自动化配置AuthenticationManager，于是需要手动配置Bean
   */
  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  /**
   * 设置security拦截路径
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().httpBasic();
    http.headers().frameOptions().disable();
    // 忽略登录操作认证
    http.formLogin().permitAll();
    // 设置注销成功后跳转到注销之前的页面，防止继续登录之后跳转到错误页面
    http.logout().logoutUrl("/oauth/logout").logoutSuccessHandler((request, response, authentication) -> {
      if (request.getHeader(REFERER) == null) {
        response.sendRedirect("/login?logout");
      } else {
        response.sendRedirect(request.getHeader(REFERER));
      }
    });
    // 忽略H2数据库的路径(测试用，之后需要删除)
    http.authorizeRequests().antMatchers("/console/**").permitAll();
    // 拦截所有请求
    http.authorizeRequests().anyRequest().authenticated();
  }
}
