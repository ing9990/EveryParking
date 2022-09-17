package com.everyparking.data.borrow.domain;

/**
 * @author Taewoo
 */


import com.everyparking.data.rent.domain.Rent;
import com.everyparking.data.user.domain.User;
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
@Table(name = "TABLE_BORROW")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "BORROWER_ID")
    private User borrower;

    @OneToOne(fetch = FetchType.EAGER)
    private Rent rent;

}
