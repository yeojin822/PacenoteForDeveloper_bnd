package com.example.portfoliopagebuilder_bnd.oauth.dto;

public interface OAuth2UserInfo {
	String getProviderId();
	String getProvider();
	String getEmail();
	String getName();
}
