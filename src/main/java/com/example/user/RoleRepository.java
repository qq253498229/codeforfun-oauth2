package com.example.user;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wangbin
 */
public interface RoleRepository extends JpaRepository<Role, String> {
  /**
   * 通过名称查询角色
   *
   * @param name 名称
   * @return 角色
   */
  Role findByName(String name);
}
