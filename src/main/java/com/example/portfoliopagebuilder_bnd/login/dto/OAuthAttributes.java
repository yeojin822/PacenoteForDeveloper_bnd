package com.example.portfoliopagebuilder_bnd.login.dto;

import java.util.Map;

import com.example.portfoliopagebuilder_bnd.login.dto.provider.GitHubUserInfo;
import com.example.portfoliopagebuilder_bnd.login.dto.provider.KakaoUserInfo;
import com.example.portfoliopagebuilder_bnd.login.model.User;
import lombok.extern.slf4j.Slf4j;
import lombok.Builder;
import lombok.Getter;

@Getter
@Slf4j
public class OAuthAttributes {
	private String provider;
	private String providerId;
	private String username;
	private String email;

	@Builder
	public OAuthAttributes(String provider, String providerId, String username, String password, String email) {
		this.provider = provider;
		this.providerId = providerId;
		this.username = username;
		this.email = email;
	}

	public static OAuthAttributes of(String provider, Map<String, Object> attributes){
		if("github".equals(provider)){
			log.info("깃허브 로그인 요청");
			return createUser(new GitHubUserInfo(attributes));
		} else if("kakao".equals(provider)){
			log.info("카카오 로그인 요청");
			return createUser(new KakaoUserInfo(attributes));
		}

		throw new NullPointerException("해당 Provider는 존재하지 않습니다.");
	}

	private static OAuthAttributes createUser(OAuth2UserInfo oAuth2UserInfo){
		StringBuilder sb = new StringBuilder();
		log.info("oAuth2UserInfo :: {}" , oAuth2UserInfo.getEmail());
		log.info("oAuth2UserInfo :: {}" , oAuth2UserInfo.getName());
		log.info("oAuth2UserInfo :: {}" , oAuth2UserInfo.getProvider());
		log.info("oAuth2UserInfo :: {}" , oAuth2UserInfo.getProviderId());
		return OAuthAttributes.builder()
			.provider(oAuth2UserInfo.getProvider())
			.providerId(sb.append(oAuth2UserInfo.getProvider())
				.append("_")
				.append(oAuth2UserInfo.getProviderId()).toString())
			.username(oAuth2UserInfo.getName())
			.email(oAuth2UserInfo.getEmail())
			.build();
	}

	public User toEntity(){
		return User.builder()
			.id(providerId)
			.username(username)
			.email(email)
			.role(RoleType.USER.getCode())
			.build();
	}
}
