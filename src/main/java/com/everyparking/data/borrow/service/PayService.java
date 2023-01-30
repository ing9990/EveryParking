package com.everyparking.data.borrow.service;

import com.everyparking.data.user.domain.User;

public interface PayService {
    void payPoint(User renter, User borrower, long cost, PayPolicy payPolicy);
}
