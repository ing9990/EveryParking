package com.everyparking.data.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Taewoo
 */

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("ROLE_USER","사용자"),
    ADMIN("ROLE_ADMIN","관리자");

    private final String key;
    private final String title;
}
