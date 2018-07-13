package com.example.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author wangbin
 */
public interface UserRepository extends JpaRepository<User, String> {
  /**
   * 通过用户名查询用户
   *
   * @param username 用户名
   * @return 用户信息
   */
  @Query("select u from User u left join fetch u.roles r where u.username=:username")
  User loadUserByUsername(@Param("username") String username);
}
