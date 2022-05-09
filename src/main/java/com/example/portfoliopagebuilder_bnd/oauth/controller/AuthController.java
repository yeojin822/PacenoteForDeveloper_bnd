package com.example.portfoliopagebuilder_bnd.oauth.controller;

import com.example.portfoliopagebuilder_bnd.oauth.repository.UserRepository;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
@Api(value = "AuthController")
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;


	// 시큐리티 세션에 있는 Authentication 객체 안에 UserDetails, OAuth2User 객체를 저장할 수 있음
	// UserDetails: 기존 로그인 유저 객체
	// OAuth2User: OAuth 로그인 유저 객체
	// 그래서 PrincipalDetails에 UserDetails, OAuth2User 모두 구현해야 함

	// Authentication 객체 안에 인증된 유저 정보 가져오기
	@GetMapping("/test")
	public String token() {

		return "토큰 발급";

	}


//	@GetMapping("/test/login")
//	public @ResponseBody String testLogin(Authentication authentication, @AuthenticationPrincipal PrincipalDetails userDetails){
//		log.info("==== /test/login =====");
//		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
//		log.info("authentication : " + principalDetails.getUser());
//		log.info("userDetails : " + userDetails.getUser());
//
//		return "세션 정보 확인하기";
//	}
//

//	// Authentication 객체 안에 oauth 인증된 유저 정보 가져오기
//	@GetMapping("/oauth/login")
//	public String testOAuthLogin(Authentication authentication, @AuthenticationPrincipal OAuth2User oAuth2User){
//		log.info("==== /oauth/login =====");
//		OAuth2User oAuth2UserObj = (OAuth2User) authentication.getPrincipal();
//		log.info("authentication : " + oAuth2UserObj.getAttributes());
//		log.info("oAuth2User : " + oAuth2User.getAttributes());
//
//		return "OAuth 세션 정보 확인하기";
//	}
//
//	// loadUser, loadUserByUsername에서 각각 로그인 유저 객체를 Authentication에 저장해줌
//	// @AuthenticationPrincipal 어노테이션으로 Authentication에 저장되어 있는 일반, OAuth 로그인 유저 객체 모두 받아올 수 있음
//	@GetMapping("/user")
//	public String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
//		log.info("User : " + principalDetails.getUser());
//		return "user";
//	}
//
//	@GetMapping("/admin")
//	public String admin(){
//		return "admin";
//	}
//
//	@GetMapping("/manager")
//	public String manager(){
//		return "manager";
//	}
//
//	@GetMapping("/loginForm")
//	public String loginForm(){
//		return "loginForm";
//	}
//
//	@GetMapping("/joinForm")
//	public String joinForm(){
//		return "joinForm";
//	}
//
//	@PostMapping("/join")
//	public String join(User user) {
//		user.setRole("ROLE_USER");
//		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//		userRepository.save(user);
//
//		return "redirect:/loginForm";
//	}
//
//	@Secured("ROLE_ADMIN")
//	@GetMapping("/data")
//	public String data(){
//		return "data only admin";
//	}
//
//	@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
//	@GetMapping("/users")
//	public String userList(){
//		return "user list only manager, admin";
//	}
}
