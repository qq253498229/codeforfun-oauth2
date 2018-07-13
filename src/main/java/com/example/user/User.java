package com.example.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 用户实体
 *
 * @author wangbin
 */
@Entity
@Data
@JsonIgnoreProperties(value = {"users", "user"})
@Table(name = "t_user")
public class User implements UserDetails {
  @Id
  @GenericGenerator(name = "uuid", strategy = "uuid")
  @GeneratedValue(generator = "uuid")
  @Column(length = 32)
  private String id;

  private Date createAt;

  private Date updateAt;

  @ManyToOne
  @JoinColumn(name = "user_id", insertable = false, updatable = false)
  private User createBy;

  @ManyToOne
  @JoinColumn(name = "user_id", insertable = false, updatable = false)
  private User updateBy;
  /**
   * 用户名
   */
  @Column(unique = true, nullable = false, length = 64)
  private String username;

  /**
   * 手机
   */
  @Column(unique = true, length = 20)
  private String phoneId;

  /**
   * 密码
   */
  @Column(nullable = false, length = 128)
  private String password;

  /**
   * 性别，0女，1男，2其它
   */
  private Integer sex;

  @Column
  private Boolean enabled = true;

  @ManyToMany
  @JoinTable(
          name = "t_user_role",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private List<Role> roles = new ArrayList<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    this.roles.forEach(role -> authorities.add(role::getName));
    return authorities;
  }

  public User(String username, String password, Boolean enabled, List<Role> roles) {
    this.username = username;
    this.password = password;
    this.enabled = enabled;
    this.roles = roles;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return this.enabled;
  }

}
