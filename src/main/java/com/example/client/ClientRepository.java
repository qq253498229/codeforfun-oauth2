package com.example.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author wangbin
 */
public interface ClientRepository extends JpaRepository<Client, String> {
  /**
   * 通过client_id查询client信息
   *
   * @param clientId clientId
   * @return client信息
   */
  @Query("select c from Client c where c.clientId=:clientId")
  Client loadClientByClientId(@Param("clientId") String clientId);
}
