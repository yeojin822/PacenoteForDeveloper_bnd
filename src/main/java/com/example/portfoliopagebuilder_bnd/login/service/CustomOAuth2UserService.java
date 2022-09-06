package com.example.portfoliopagebuilder_bnd.login.service;

import com.example.portfoliopagebuilder_bnd.login.dto.PrincipalDetails;
import com.example.portfoliopagebuilder_bnd.login.dto.OAuthAttributes;
import com.example.portfoliopagebuilder_bnd.login.model.User;
import com.example.portfoliopagebuilder_bnd.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	private final UserRepository userRepository;

	// 구글로 부터 받은 userRequest 데이터에 대한 후처리되는 함수
	// 함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		log.info("userRequest: " + userRequest.getClientRegistration());
		log.info("getAccessToken: " + userRequest.getAccessToken());

		// 구글로그인 버튼 클릭 -> 구글 로그인 창 -> 로그인 완료 -> code를 리턴(OAuth-Client 라이브러리) -> AccessToken 요청
		// userRequest 정보(AccessToken 있음, 유저정보 필요함) -> loadUser 함수 호출 -> 구글로부터 회원 프로필 받아줌
		OAuth2User oAuth2User = super.loadUser(userRequest);
		log.info("getAttributes: " + oAuth2User.getAttributes());

		OAuthAttributes attributes = OAuthAttributes.of(userRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
		User user = saveOrUpdate(attributes);
		return new PrincipalDetails(user, oAuth2User.getAttributes());
	}

	private User saveOrUpdate(OAuthAttributes attributes) {
		User user = userRepository.findById(attributes.getProviderId())
			.map(entity -> entity.update(attributes.getUsername(), attributes.getEmail()))
			.orElse(attributes.toEntity());

		log.info("user create" + user);
		return userRepository.save(user);
	}

}
