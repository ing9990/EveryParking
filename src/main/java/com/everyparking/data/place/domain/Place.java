package com.everyparking.data.place.domain;

/**
 * @author Taewoo
 */


import java.awt.*;
import java.util.*;

import com.everyparking.data.user.domain.User;
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
@Table(name = "TABLE_PLACE")
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PLACE_OWNER")
    private User user;

    @Column(name = "PLACE_NAME")
    private String name;

    @Column(name = "PLACE_ADDR", unique = true)
    private String addr;

    private String message;

    private double mapX;
    private double mapY;


}























