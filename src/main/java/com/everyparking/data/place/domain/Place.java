package com.everyparking.data.place.domain;

/**
 * @author Taewoo
 */


import com.everyparking.data.car.domain.Car;
import com.everyparking.data.user.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private User user;

    @Column(name = "PLACE_NAME")
    private String name;

    @Column(name = "PLACE_ADDR")
    private String addr;

    private String mapX;
    private String mapY;

    @Column(name = "PLACE_SIZE")
    @Enumerated(EnumType.ORDINAL)
    private Car.CarSize placeSize;

    private boolean isBorrow = false;

    @Column(name = "PLACE_IMG", length = 10000)
    private String imgUrl;

    @Transient
    public static Place dtoToEntity(User user, String name, String addr, String mapX, String mapY, Car.CarSize size, String imgUrl) {
        return Place.builder().user(user).name(name).addr(addr).mapX(mapX).mapY(mapY).placeSize(size)
                .imgUrl(imgUrl).build();
    }

}























