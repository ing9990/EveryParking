package com.everyparking.data.borrow.service;

import com.everyparking.data.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 렌트를 해준 사람은 주차장 대여비 원가를 받고 빌린 사람은 할인정책 만큼 금액 감면을 받는다.
 * <p>
 * 송신자의 금액 차감과 수신자의 금액 추가가 동시에 일어나야한다.
 */

@Service
public class DefaultPayService implements PayService {

    @Override
    @Transactional
    public void payPoint(User renter, User borrower, long cost, PayPolicy payPolicy) {
        long price = getPayCost(cost, payPolicy);

        renter.upPoint(cost);
        renter.downPoint(price);
    }


    private long getPayCost(long cost, PayPolicy payPolicy) {
        if (payPolicy == PayPolicy.NONE) {
            return cost;
        }

        if (payPolicy == PayPolicy.UP_TEN_PERCENT) {
            return cost + cost / 10;
        }

        if (payPolicy == PayPolicy.DOWN_TEN_PERCENT) {
            return cost - cost / 10;
        }

        throw new UnsupportedOperationException("잘못된 결제방식입니다.");
    }
}
