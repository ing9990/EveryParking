package com.everyparking.data.rent.domain;

/**
 * @author Taewoo
 */


import java.time.LocalDateTime;
import java.util.*;

import com.everyparking.data.place.domain.Place;
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
@Table(name = "TABLE_RENT")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    private Place place;

    @Column(name = "RENT_COST")
    private long cost;

    @Column(name = "RENT_MESSAGE")
    private String message;

    @Column(name = "RENT_ISBORROWED")
    private boolean isBorrowed;

    @Column(name = "RENT_START_TIME")
    private LocalDateTime start;

    @Column(name = "RENT_END_TIME")
    private LocalDateTime end;

    public static Rent dtoToEntity(Place place, long cost, String message, boolean isBorrowed, LocalDateTime start, LocalDateTime end) {
        return Rent.builder()
                .place(place)
                .cost(cost)
                .message(message)
                .isBorrowed(isBorrowed)
                .start(start)
                .end(end)
                .build();
    }
}
