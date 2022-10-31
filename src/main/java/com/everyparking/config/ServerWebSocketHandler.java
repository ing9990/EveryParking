package com.everyparking.config;

import com.everyparking.api.dto.socket.SendMessageDto;
import com.everyparking.data.user.service.JwtTokenUtils;
import com.everyparking.data.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Taewoo
 */


@Slf4j
@RequiredArgsConstructor
public class ServerWebSocketHandler implements WebSocketHandler {

    private final ObjectMapper om;
    private final JwtTokenUtils jwtTokenUtils;
    private final UserService userService;

    Map<WebSocketSession, Integer> sessionMap = new ConcurrentHashMap<>();
    AtomicInteger userCount = new AtomicInteger(0);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionMap.put(session, userCount.incrementAndGet());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        var payload = message.getPayload();

        try {
            var dto = om.readValue(payload.toString(), SendMessageDto.class);

            var sendUser = jwtTokenUtils.getUserByToken(dto.getAuthorization());
            var user = userService.findUserById(dto.getUser());

            log.info(sendUser.getNickname() + " -> " + user.getNickname() + " [" + dto.getMessage() + "]");
            log.info(dto.getMessage());

        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.info("Error!!");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        sessionMap.remove(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
