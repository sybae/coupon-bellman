package com.example.coupon.service;

import com.example.coupon.code.CouponStatus;
import com.example.coupon.model.Coupon;
import com.example.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    public boolean amIOk() {
        return true;
    }

    public String getNameById(Long id) {
        return "";
        //return couponRepository.findById(id).map(coupon -> coupon.getName()).orElse("N/A");
    }

    public String postNewCoupon() {
        Coupon coupon = Coupon.builder().status(CouponStatus.STANDBY)
                .expired_date(new Timestamp(new Date().getTime())).build();
        couponRepository.save(coupon);
        return coupon.getCode();
    }

    public int postNewCouponWithSize(int size) {
        int count = 0;
        String code;
        for (int idx = 0; idx < size; idx++) {
            code = postNewCoupon();
            if (!StringUtils.isEmpty(code)) {
                count++;
            }
        }
        return count;
    }

    public void publishCoupon() {

    }
}
