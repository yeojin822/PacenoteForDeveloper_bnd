package com.example.portfoliopagebuilder_bnd.login.handler;


import com.example.portfoliopagebuilder_bnd.common.configuration.properties.AppProperties;
import com.example.portfoliopagebuilder_bnd.common.util.CookieUtil;
import com.example.portfoliopagebuilder_bnd.common.util.JwtTokenProvider;
import com.example.portfoliopagebuilder_bnd.login.dto.PpbUser;
import com.example.portfoliopagebuilder_bnd.login.dto.PrincipalDetails;
import com.example.portfoliopagebuilder_bnd.login.dto.Token;
import com.example.portfoliopagebuilder_bnd.login.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import static com.example.portfoliopagebuilder_bnd.login.repository.OAuth2AuthorizationRequestBasedOnCookieRepository.REDIRECT_URI_PARAM_COOKIE_NAME;


@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository;
    private final AppProperties appProperties;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        log.info("redirectUri ::: {}", redirectUri);
        log.info("redirectUri.isPresent() ::: {}", redirectUri.isPresent());
        log.info("redirectUri ::: {}", !isAuthorizedRedirectUri(redirectUri.get()));

        if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new IllegalArgumentException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
        log.info("targetURL :: {} ", targetUrl);

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        log.info("from user :: " + principalDetails.getUser());

        PpbUser user = new PpbUser();
        user.setId(principalDetails.getUser().getId());
        user.setUsername(principalDetails.getUser().getUsername());
        // 세션키 생성
        Token token = null;
        try {
            token = jwtTokenProvider.createToken(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", token)
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        oAuth2AuthorizationRequestBasedOnCookieRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);
        log.info("clientRedirectUri ::: {}", clientRedirectUri);
        log.info("appProperties.getOauth2().getAuthorizedRedirectUris() ::: {}", appProperties.getOauth2().getAuthorizedRedirectUris());

        return appProperties.getOauth2().getAuthorizedRedirectUris()
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    // Only validate host and port. Let the clients use different paths if they want to
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    log.info("authorizedRedirectUri ::: {}", authorizedURI);

                    if(authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                            && authorizedURI.getPort() == clientRedirectUri.getPort()) {
                        return true;
                    }
                    return false;
                });
    }
}
