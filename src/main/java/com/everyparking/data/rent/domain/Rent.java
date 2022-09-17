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

    private long cost;

    private String message;

    private boolean isBorrowed;

    private LocalDateTime start;
    private LocalDateTime end;

}
