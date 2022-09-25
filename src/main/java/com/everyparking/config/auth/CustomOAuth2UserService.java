package com.everyparking.config.auth;

/**
 * @author Taewoo
 */


import com.everyparking.data.user.domain.User;
import com.everyparking.data.user.repository.UserRepository;
import com.everyparking.data.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {

        var oauth2User = super.loadUser(oAuth2UserRequest);
        var userInfo = oauth2User.getAttributes();

        var snsName = oAuth2UserRequest.getClientRegistration().getRegistrationId();

        var email = snsName + "_" + userInfo.get("email").toString();
        var uid = userInfo.get("sub").toString();
        var name = userInfo.get("name").toString();

        if (userRepository.existsUserByEmail(email)) {
            log.info("로그인 성공 " + email);
            return userRepository.findUserByEmail(email).get();
        }

        log.info("회원가입 성공 " + email);

        return userRepository.save(User.makeUser(email, "", uid + "_" + name, "", "", User.City.미정));
    }
}
