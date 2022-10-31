package com.everyparking.config;

import com.everyparking.data.user.service.JwtTokenUtils;
import com.everyparking.data.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

import java.awt.desktop.UserSessionEvent;

/**
 * @author Taewoo
 */


@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class ServerWebSocketConfig implements WebSocketConfigurer {

    private final JwtTokenUtils jwtTokenUtils;
    private final UserService userService;


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(), "/websocket");
    }

    @Bean
    public WebSocketHandler webSocketHandler() {
        return new ServerWebSocketHandler(new ObjectMapper(), jwtTokenUtils, userService);
    }
}
