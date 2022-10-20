package com.everyparking.data.borrow.domain;

/**
 * @author Taewoo
 */


import com.everyparking.data.car.domain.Car;
import com.everyparking.data.place.domain.Place;
import com.everyparking.data.rent.domain.Rent;
import com.everyparking.data.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class BorrowHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "BORROWER_ID")
    private User borrower;

    @OneToOne(fetch = FetchType.EAGER)
    private Rent rent;

    @Transient
    private String renterName;

    @Column(name = "BORROW_START_AT")
    private LocalDateTime startAt;

    @Column(name = "BORROW_END_AT")
    private LocalDateTime endAt;

    @OneToOne
    @JoinColumn(name = "BORROWER_CAR_ID")
    private Car car;
}


