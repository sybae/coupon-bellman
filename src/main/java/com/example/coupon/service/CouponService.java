package com.example.coupon.service;

import com.example.coupon.code.CouponStatus;
import com.example.coupon.model.Coupon;
import com.example.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    public Coupon postNewCoupon() {
        Coupon coupon = Coupon.builder().status(CouponStatus.STANDBY)
                .expired_date(new Timestamp(new Date().getTime())).build();
        couponRepository.save(coupon);
        return coupon;
    }

    public int postNewCouponWithSize(int size) {
        int count = 0;
        Coupon coupon;
        for (int idx = 0; idx < size; idx++) {
            coupon = postNewCoupon();
            if (coupon != null && !StringUtils.isEmpty(coupon.getCode())) {
                count++;
            }
        }
        return count;
    }

    public Coupon publishCoupon() {
        return couponRepository.findFirstByStatus(CouponStatus.STANDBY)
                .map(coupon -> {
                    coupon.setStatus(CouponStatus.PUBLISHED);
                    couponRepository.save(coupon);
                    return coupon;
                }).orElse(null);
    }

    public List<String> getPublishedCoupons() {
        //PageRequest pr = new PageRequest(0, 10, Sort.by(Sort.Order.desc("code")));
        return couponRepository.findAllByStatus(CouponStatus.PUBLISHED)
                .map(coupons -> coupons.stream().map(coupon -> coupon.getCode()).collect(Collectors.toList()))
                .orElseGet(null);
    }
}
