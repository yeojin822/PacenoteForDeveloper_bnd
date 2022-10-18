package com.example.portfoliopagebuilder_bnd.common.configuration;

import com.example.portfoliopagebuilder_bnd.common.configuration.properties.AppProperties;
import com.example.portfoliopagebuilder_bnd.common.util.JwtTokenProvider;
import com.example.portfoliopagebuilder_bnd.login.dto.RoleType;
import com.example.portfoliopagebuilder_bnd.login.handler.OAuth2AuthenticationFailureHandler;
import com.example.portfoliopagebuilder_bnd.login.handler.OAuth2AuthenticationSuccessHandler;
import com.example.portfoliopagebuilder_bnd.login.handler.OAuth2SuccessHandler;
import com.example.portfoliopagebuilder_bnd.login.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.example.portfoliopagebuilder_bnd.login.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsUtils;

// oauth2 과정
// 1.코드받기(인증), 2.액세스토큰(권한),
// 3-1.사용자프로필 정보를 가지고 회원가입 진행
// 3-2. 기본 데이터 (이메일, 전화번호, 이름, 아이디)
// 쇼핑몰 -> (집주소) 정보 필요 / 백화점몰 -> (vip, 일반등급) 정보 필요

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final CustomOAuth2UserService oAuth2UserService;
	private final AppProperties appProperties;
	private final JwtTokenProvider jwtTokenProvider;

	@Bean
	public BCryptPasswordEncoder encodePassword(){
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.cors()
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.csrf().disable()
				.formLogin().disable()
				.httpBasic().disable()
				.exceptionHandling()
				.and()
				.authorizeRequests()
				.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
				.antMatchers("/api/**").hasAnyAuthority(RoleType.USER.getCode())
				.antMatchers("/api/**/admin/**").hasAnyAuthority(RoleType.ADMIN.getCode())
				.anyRequest().authenticated()
				.and()
				.oauth2Login()
				.authorizationEndpoint()
				.baseUri("/oauth2/authorization")
				.authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())
				.and()
				.redirectionEndpoint()
				.baseUri("/*/oauth2/code/*")
				.and()
				.userInfoEndpoint()
				.userService(oAuth2UserService)
				.and()
				.successHandler(oAuth2AuthenticationSuccessHandler())
				.failureHandler(oAuth2AuthenticationFailureHandler());
//		http	.csrf().disable()
//				.formLogin().disable()
//				.httpBasic().disable()
//				.oauth2Login()
//				.authorizationEndpoint()
//				.baseUri("/oauth2/authorization")
//				.authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())
//				.and()
//					.redirectionEndpoint()
//					.baseUri("/*/oauth2/code/*")
//				.and()
//					.userInfoEndpoint()
//					.userService(oAuth2UserService)
//				.and()
//					.successHandler(successHandler)
//					.failureUrl("/");

//		http.csrf().disable();
//		http.oauth2Login()
//			.successHandler(successHandler)
//			.userInfoEndpoint() // 로그인이 완료되면 코드가 아닌 (엑세스 토큰 + 사용자 프로필 정보)를 받음
//			.userService(oAuth2UserService);
	}

	/*
	 * 쿠키 기반 인가 Repository
	 * 인가 응답을 연계 하고 검증할 때 사용.
	 * */
	@Bean
	public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
		return new OAuth2AuthorizationRequestBasedOnCookieRepository();
	}

	/*
	 * Oauth 인증 성공 핸들러
	 * */
	@Bean
	public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
		return new OAuth2AuthenticationSuccessHandler(
				oAuth2AuthorizationRequestBasedOnCookieRepository(),
				appProperties,
				jwtTokenProvider
				);
	}

	/*
	 * Oauth 인증 실패 핸들러
	 * */
	@Bean
	public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
		return new OAuth2AuthenticationFailureHandler(oAuth2AuthorizationRequestBasedOnCookieRepository());
	}
}
