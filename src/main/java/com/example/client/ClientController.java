package com.example.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @author wangbin
 */
@RestController
public class ClientController {
  @Resource
  private ClientServiceImpl clientService;

  /**
   * 保存client信息
   */
  @PostMapping("/oauth/client")
  public ResponseEntity save(@RequestBody Client client) {
    return ok(clientService.save(client));
  }
}
