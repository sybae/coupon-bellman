package com.bamtori.coupon.service;

import com.bamtori.coupon.model.Coupon;
import com.bamtori.coupon.repository.CouponRepository;
import com.bamtori.coupon.code.CouponStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public Coupon postNewCoupon() {
        Coupon coupon = Coupon.builder().status(CouponStatus.STANDBY)
                .expiredDate(Timestamp.valueOf(LocalDateTime.now())).build();
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
                    Timestamp now = Timestamp.valueOf(LocalDateTime.now());
                    couponRepository.save(
                            coupon.toBuilder().status(CouponStatus.USED).usedDate(now).build()
                    );
                    return coupon.getCode();
                }).orElse(null);
    }

    @Transactional
    public String cancelCoupon(String code) {
        return couponRepository.findById(code)
                .map(coupon -> {
                    couponRepository.save(
                            coupon.toBuilder().status(CouponStatus.PUBLISHED).usedDate(null).build()
                    );
                    return coupon.getCode();
                }).orElse(null);
    }

    public List<String> getTodayExpiredCoupons() {
        LocalDate currentDate = LocalDate.now();
        LocalDateTime startOfDay = LocalDateTime.of(currentDate, LocalTime.MIDNIGHT);
        LocalDateTime endOfDay = LocalDateTime.of(currentDate, LocalTime.MAX);
        return couponRepository.findCouponsByExpiredDateBetween(Timestamp.valueOf(startOfDay), Timestamp.valueOf(endOfDay))
                .map(coupons -> coupons.stream().map(coupon -> coupon.getCode()).collect(Collectors.toList()))
                .orElse(null);
    }
}
