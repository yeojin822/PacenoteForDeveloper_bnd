package com.example.portfoliopagebuilder_bnd.login.dto;

public interface OAuth2UserInfo {
	String getProviderId();
	String getProvider();
	String getEmail();
	String getName();
}
