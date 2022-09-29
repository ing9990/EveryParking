package com.everyparking.data.car.domain;

/**
 * @author Taewoo
 */


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
@Table(name = "TABLE_CAR")
public class Car {

    @Id
    private String carNumber;

    @ManyToOne
    private User user;

    @Column(name = "CAR_CARMODEL")
    private String carModel;

    @Column(name = "CAR_SIZE")
    @Enumerated(EnumType.STRING)
    private CarSize carSize;

    public static enum CarSize {
        소형, 중형, 대형
    }
}