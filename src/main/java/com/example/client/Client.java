package com.example.client;

import com.example.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author wangbin
 */
@Entity
@Data
@Table(name = "t_client")
public class Client implements ClientDetails {
  @Id
  @GenericGenerator(name = "uuid", strategy = "uuid")
  @GeneratedValue(generator = "uuid")
  @Column(length = 32)
  private String id;

  @Column(nullable = false, unique = true)
  private String clientId;

  @Column
  private String resourceIds;

  @Column(nullable = false)
  private String clientSecret;

  @Column
  private String scope;

  @Column(nullable = false)
  private String authorizedGrantTypes;

  @Column
  private String registeredRedirectUri;

  @Column
  private String authorities;

  @Column(nullable = false)
  private Integer accessTokenValiditySeconds;

  @Column(nullable = false)
  private Integer refreshTokenValiditySeconds;

  @Column
  private String autoApproveScope;

  @Column
  private String additionalInformation;

  Client(String clientId) {
    this.clientId = clientId;
  }

  public Client(String clientId, String clientSecret, String scope, String authorizedGrantTypes, String registeredRedirectUri, Integer accessTokenValiditySeconds, Integer refreshTokenValiditySeconds) {
    this.clientId = clientId;
    this.clientSecret = clientSecret;
    this.scope = scope;
    this.authorizedGrantTypes = authorizedGrantTypes;
    this.registeredRedirectUri = registeredRedirectUri;
    this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
  }

  @Override
  public String getClientId() {
    return this.clientId;
  }

  @Override
  public Set<String> getResourceIds() {
    if (StringUtils.isEmpty(this.resourceIds)) {
      return new HashSet<>();
    } else {
      return StringUtils.commaDelimitedListToSet(this.resourceIds);
    }
  }

  @Override
  public boolean isSecretRequired() {
    return !StringUtils.isEmpty(this.clientSecret);
  }

  @Override
  public String getClientSecret() {
    return this.clientSecret;
  }

  @Override
  public boolean isScoped() {
    return this.getScope().size() > 0;
  }

  @Override
  public Set<String> getScope() {
    return StringUtils.commaDelimitedListToSet(this.scope);
  }

  @Override
  public Set<String> getAuthorizedGrantTypes() {
    return StringUtils.commaDelimitedListToSet(this.authorizedGrantTypes);
  }

  @Override
  public Set<String> getRegisteredRedirectUri() {
    return StringUtils.commaDelimitedListToSet(this.registeredRedirectUri);
  }

  @Override
  public Collection<GrantedAuthority> getAuthorities() {
    Set<String> set = StringUtils.commaDelimitedListToSet(this.authorities);
    Set<GrantedAuthority> result = new HashSet<>();
    set.forEach(authority -> result.add((GrantedAuthority) () -> authority));
    return result;
  }

  @Override
  public Integer getAccessTokenValiditySeconds() {
    return this.accessTokenValiditySeconds;
  }

  @Override
  public Integer getRefreshTokenValiditySeconds() {
    return this.refreshTokenValiditySeconds;
  }

  @Override
  public boolean isAutoApprove(String s) {
    return this.getAutoApproveScope().contains(scope);
  }

  @Override
  public Map<String, Object> getAdditionalInformation() {
    if (this.additionalInformation == null) {
      return null;
    }
    return JsonUtil.parse(this.additionalInformation);
  }

  private Set<String> getAutoApproveScope() {
    return StringUtils.commaDelimitedListToSet(this.autoApproveScope);
  }

  public void setResourceIds(Set<String> resourceIds) {
    this.resourceIds = StringUtils.collectionToCommaDelimitedString(resourceIds);
  }

  public void setScope(Set<String> scope) {
    this.scope = StringUtils.collectionToCommaDelimitedString(scope);
  }

  public void setAuthorizedGrantTypes(Set<String> authorizedGrantType) {
    this.authorizedGrantTypes = StringUtils.collectionToCommaDelimitedString(authorizedGrantType);
  }

  public void setRegisteredRedirectUri(Set<String> registeredRedirectUriList) {
    this.registeredRedirectUri = StringUtils.collectionToCommaDelimitedString(registeredRedirectUriList);
  }

  public void setAuthorities(Set<GrantedAuthority> authorities) {
    this.authorities = StringUtils.collectionToCommaDelimitedString(authorities);
  }

  public void setAutoApproveScope(Set<String> autoApproveScope) {
    this.autoApproveScope = StringUtils.collectionToCommaDelimitedString(autoApproveScope);
  }

  private static final ObjectMapper MAPPER = new ObjectMapper();

  public void setAdditionalInformation(Map<String, Object> additionalInformation) {
    try {
      this.additionalInformation = MAPPER.writeValueAsString(additionalInformation);
    } catch (IOException e) {
      this.additionalInformation = "";
    }
  }
}
