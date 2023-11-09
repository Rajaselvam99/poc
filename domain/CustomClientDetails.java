package com.obs.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="oauth_client_details")
public class CustomClientDetails {

	@Id
	@Column(name = "client_id", nullable = false, unique = true)
	private String clientId;

	
	@Column(name="client_secret")
	private String clientSecret;
	
	private String secret;
	
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}

	@Column(name="resource_ids")
	private String resourceIds;
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientSecret() {
		return clientSecret;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	public String getResourceIds() {
		return resourceIds;
	}
	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getAuthorizedGrantTypes() {
		return authorizedGrantTypes;
	}
	public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
		this.authorizedGrantTypes = authorizedGrantTypes;
	}
	public String getRegisteredRedirectUri() {
		return registeredRedirectUri;
	}
	public void setRegisteredRedirectUri(String registeredRedirectUri) {
		this.registeredRedirectUri = registeredRedirectUri;
	}
	public String getAuthorities() {
		return authorities;
	}
	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}
	public Integer getAccessTokenValiditySeconds() {
		return accessTokenValiditySeconds;
	}
	public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
		this.accessTokenValiditySeconds = accessTokenValiditySeconds;
	}
	public Integer getRefreshTokenValiditySeconds() {
		return refreshTokenValiditySeconds;
	}
	public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
		this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
	}
	public String getAutoApprove() {
		return autoApprove;
	}
	public void setAutoApprove(String autoApprove) {
		this.autoApprove = autoApprove;
	}

	private String scope;

	@Column(name="authorized_grant_types")
	private String authorizedGrantTypes;

	@Column(name="web_server_redirect_uri")
	private String registeredRedirectUri;
	private String authorities;
	@Column(name="access_token_validity")
	private Integer accessTokenValiditySeconds;
	@Column(name="refresh_token_validity")
	private  Integer refreshTokenValiditySeconds;
	
	public String getAdditionalInformation() {
		return additionalInformation;
	}
	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}
	@Column(name="autoapprove")
	private String autoApprove;
	
	@Column(name="additional_information")
	 private String additionalInformation;
}
