package com.everyparking.data.user.domain;

/**
 * @author Taewoo
 */

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "TABLE_USER")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_EMAIL", unique = true)
    private String email;

    @Column(name = "USER_PASSWORD")
    private String password;

    @Column(unique = true, name = "USER_NICKNAME", columnDefinition = "VARCHAR(11)", nullable = false)
    private String nickname;

    @Column(name = "USER_TEL")
    private String tel;

    @Column(name = "USER_INTORDUCE", columnDefinition = "VARCHAR(30)")
    private String introduce;

    @Column(name = "USER_POINT")
    private long point;

    @Column(name = "USER_CITY")
    private City city;

    @Column(updatable = false)
    private LocalDateTime created;

    private LocalDateTime updated;

    public static enum City {
        서울특별시, 부산광역시, 대구광역시, 인천광역시, 광주광역시, 대전광역시,
        울산광역시, 세종특별자치시, 경기도, 강원도, 충청북도, 충청남도, 전라북도, 전라남도, 경상북도,
        경상남도, 제주특별자치도, 미정
    }
}