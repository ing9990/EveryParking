package com.everyparking.data.borrow.domain;

/**
 * @author Taewoo
 */


import com.everyparking.data.place.domain.Place;
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
    private User borrowerId;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User userId;

    @OneToOne
    @JoinColumn(name = "PLACE_ID")
    private Place place;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}


