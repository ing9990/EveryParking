package com.everyparking.data.borrow.domain;

/**
 * @author Taewoo
 */

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

    @Column(name = "RENTER_NAME")
    private String renterName;

    @Column(name = "BORROWER_NAME")
    private String borrowerName;

    @Column(name = "RENTER_TEL")
    private String renterTel;

    @Column(name = "CAR_NUMBER")
    private String carNumber;

    @Column(name = "CAR_MODEL")
    private String carModel;

    @Column(name = "RENT_MESSAGE")
    private String message;

    @Column(name = "RENT_COST")
    private long cost;

    @Column(name = "PLACE_ADDR")
    private String addr;

    @Column(name = "PLACE_IMG_URL", length = 10000)
    private String placeImgUrl;

    @Column(name = "BORROW_START_AT")
    private LocalDateTime startAt;

    @Column(name = "BORROW_END_AT")
    private LocalDateTime endAt;

    @Column(name = "CREATE_AT")
    private LocalDateTime createAt = LocalDateTime.now();

}


