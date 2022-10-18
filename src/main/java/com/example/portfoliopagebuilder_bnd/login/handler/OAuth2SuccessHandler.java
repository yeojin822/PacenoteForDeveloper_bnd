package com.example.portfoliopagebuilder_bnd.login.handler;

import com.example.portfoliopagebuilder_bnd.common.util.JwtTokenProvider;
import com.example.portfoliopagebuilder_bnd.login.dto.PpbUser;
import com.example.portfoliopagebuilder_bnd.login.dto.PrincipalDetails;
import com.example.portfoliopagebuilder_bnd.login.dto.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info("Start oauth  ");
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        log.info("from user :: " + principalDetails.getUser());

        String targetUrl = request.getHeader("referer");
        log.info("targetURL ::: {}", targetUrl);

        PpbUser user = new PpbUser();
        user.setId(principalDetails.getUser().getId());
        user.setUsername(principalDetails.getUser().getUsername());
        // 세션키 생성
        try {
            Token token = jwtTokenProvider.createToken(user);
            String url = makeRedirectUrl(token.getSessionKey(), targetUrl);

            if (response.isCommitted()) {
                log.debug("응답이 이미 커밋된 상태입니다. " + url + "로 리다이렉트하도록 바꿀 수 없습니다.");
                return;
            }
            getRedirectStrategy().sendRedirect(request, response, url);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        private String makeRedirectUrl(String token, String targetUrl) {
            return UriComponentsBuilder.fromUriString(targetUrl)
                    .queryParam("token", token)
                    .build().toUriString();
        }

}
