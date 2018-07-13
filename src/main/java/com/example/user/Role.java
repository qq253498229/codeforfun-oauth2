package com.example.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色实体
 *
 * @author wangbin
 */
@Entity
@Data
@JsonIgnoreProperties(value = {"roles", "role"})
@Table(name = "t_role")
public class Role implements Serializable {

  @Id
  @GenericGenerator(name = "uuid", strategy = "uuid")
  @GeneratedValue(generator = "uuid")
  @Column(length = 32)
  private String id;

  @Column(nullable = false, unique = true)
  private String name;

  @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
  private List<User> users = new ArrayList<>();

  public Role(String name) {
    this.name = name;
  }
}
