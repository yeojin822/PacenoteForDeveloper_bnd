package com.example.portfoliopagebuilder_bnd.oauth.dto.provider;

import com.example.portfoliopagebuilder_bnd.oauth.dto.OAuth2UserInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class GitHubUserInfo implements OAuth2UserInfo {
	private Map<String, Object> attributes; // oAuth2User.getAttributes()

	public GitHubUserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String getProviderId() {
		return String.valueOf(attributes.get("id"));
	}

	@Override
	public String getProvider() {
		return "github";
	}

	@Override
	public String getEmail() {
		return attributes.get("email") == null ? "미동의" : (String)attributes.get("email");
	}

	@Override
	public String getName() {
		return attributes.get("name") == null ? "미동의" : (String)attributes.get("name");
	}
}
