package com.everyparking.data.user.service;

import com.everyparking.data.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Taewoo
 */


@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender javaMailSender;
    private final JwtTokenUtils jwtTokenUtils;

    private final InternetAddress getFrom;

    public void sendMailToUser(User user, String comment) {
        MimeMailMessage config = new MimeMailMessage();

        config.setTo(user.getEmail());
        config.setFrom(String.valueOf(getFrom));

    }

    public void sendMailToUsers(List<User> users) {
        SimpleMailMessage config = new SimpleMailMessage();

        config.setTo(users.stream().map(User::getEmail).toArray(String[]::new));
        config.setFrom(String.valueOf(getFrom));
        config.setText("Hello");

        javaMailSender.send(config);
    }


}
