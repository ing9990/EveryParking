package com.everyparking.data.place.domain;

/**
 * @author Taewoo
 */


import com.everyparking.data.car.domain.Car;
import com.everyparking.data.user.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

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

    @Column(name = "PLACE_MAP_X")
    private String mapX;
    @Column(name = "PLACE_MAP_Y")
    private String mapY;

    @Column(name = "PLACE_SIZE")
    @Enumerated(EnumType.ORDINAL)
    private Car.CarSize placeSize;

    @Column(name = "PLACE_STATUS")
    @Enumerated(EnumType.STRING)
    private PlaceStatus placeStatus = PlaceStatus.waiting;

    @AllArgsConstructor
    @Getter
    public static enum PlaceStatus {
        waiting(1), pending(2), inUse(3);

        private final int value;
    }

    @Column(name = "PLACE_IMG", length = 10000)
    private String imgUrl;

    @Transient
    public static Place dtoToEntity(User user, String name, String addr, String mapX, String mapY, Car.CarSize size, String imgUrl) {
        return Place.builder().user(user)
                    .placeStatus(PlaceStatus.waiting)
                    .name(name).addr(addr).mapX(mapX).mapY(mapY).placeSize(size).imgUrl(imgUrl).build();
    }

}























