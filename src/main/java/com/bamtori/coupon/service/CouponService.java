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
                .expiredDate(Timestamp.valueOf(LocalDateTime.now().plusDays(getRandomDayWithin10Days()))).build();
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
        return couponRepository.findCouponsByExpiredDateBetween(getStartOfDay(currentDate), getEndOfDay(currentDate))
                .map(coupons -> coupons.stream().map(coupon -> coupon.getCode()).collect(Collectors.toList()))
                .orElse(null);
    }

    public int postMessages() {
        LocalDate after3days = LocalDate.now().plusDays(3l);
        return couponRepository.findCouponsByExpiredDateBetween(getStartOfDay(after3days), getEndOfDay(after3days))
                .map(coupons -> {
                    coupons.forEach(coupon -> printMessage(coupon));
                    return coupons.size();
                }).orElse(0);
    }

    private void printMessage(Coupon coupon) {
        System.out.println(
                "The coupon expires in 3 days."
                .concat(System.lineSeparator())
                .concat(coupon.getCode()));
    }

    private int getRandomDayWithin10Days() {
        Random rand = new Random(System.currentTimeMillis());
        return rand.nextInt(10);
    }

    private Timestamp getStartOfDay(LocalDate date) {
        return Timestamp.valueOf(LocalDateTime.of(date, LocalTime.MIDNIGHT));
    }

    private Timestamp getEndOfDay(LocalDate date) {
        return Timestamp.valueOf(LocalDateTime.of(date, LocalTime.MAX));
    }
}
