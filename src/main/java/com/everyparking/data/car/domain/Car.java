package com.everyparking.data.car.domain;


import com.everyparking.data.user.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "TABLE_CAR")
public class Car {

    @Id
    private String carNumber;

    @ManyToOne
    @JsonIgnore
    private User user;

    @Column(name = "CAR_CARMODEL")
    private String carModel;

    @Column(name = "CAR_SIZE")
    @Enumerated(EnumType.STRING)
    private CarSize carSize;

    @AllArgsConstructor
    @Getter
    public static enum CarSize {
        소형(1), 중형(2), 대형(3);
        private final int value;
    }
}