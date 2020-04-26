package com.example.coupon.service;

import com.example.coupon.code.CouponStatus;
import com.example.coupon.model.Coupon;
import com.example.coupon.model.CouponArchive;
import com.example.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public Coupon postNewCoupon() {
        Coupon coupon = Coupon.builder().status(CouponStatus.STANDBY)
                .expired_date(new Timestamp(new Date().getTime())).build();
        couponRepository.save(coupon);
        return coupon;
    }

    @Transactional
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

    @Transactional
    public String useCoupon(String code) {
        return couponRepository.findById(code)
                .map(coupon -> {
                    Timestamp now = new Timestamp(new Date().getTime());
                    couponRepository.save(
                            coupon.toBuilder().status(CouponStatus.USED).used_date(now).build()
                    );
                    return coupon.getCode();
                }).orElse(null);
    }

    @Transactional
    public String cancelCoupon(String code) {
        return couponRepository.findById(code)
                .map(coupon -> {
                    couponRepository.save(
                            coupon.toBuilder().status(CouponStatus.PUBLISHED).used_date(null).build()
                    );
                    return coupon.getCode();
                }).orElse(null);
    }
}
