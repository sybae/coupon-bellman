package com.bamtori.coupon.service;

import com.bamtori.coupon.code.CouponStatus;
import com.bamtori.coupon.model.Coupon;
import com.bamtori.coupon.repository.CouponRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponRepository couponRepository;

    @Test
    public void postNewCouponTest() {
        Coupon coupon = couponService.postNewCoupon();
        assertThat(coupon).isNotNull();
        assertThat(coupon.getCode()).isNotEmpty();
        assertThat(coupon.getCode().length()).isEqualTo(13);
        assertThat(coupon.getExpiredDate()).isNotNull();
    }

    @Test
    public void publishCouponTest() {
        Coupon coupon = couponService.publishCoupon();
        assertThat(coupon).isNotNull();
        assertThat(coupon.getStatus()).isEqualTo(CouponStatus.PUBLISHED);
    }

    @Test
    public void getPublishedCouponsTest() {
        List<String> codes = couponService.getPublishedCoupons();
        Coupon coupon = codes.stream().findAny().map(code -> couponRepository.findById(code)).get().get();
        assertThat(coupon.getStatus()).isEqualTo(CouponStatus.PUBLISHED);
    }

    @Test
    public void useCouponTest() {
        List<String> codes = couponService.getPublishedCoupons();
        String code = codes.stream().findAny().get();
        String usedCode = couponService.useCoupon(code);
        Coupon usedCoupon = couponRepository.findById(usedCode).get();
        assertThat(usedCoupon.getUsedDate()).isNotNull();
        assertThat(usedCoupon.getStatus()).isEqualTo(CouponStatus.USED);
    }

    @Test
    public void cancelCouponTest() {
        List<String> codes = couponService.getPublishedCoupons();
        String code = codes.stream().findAny().get();
        String usedCode = couponService.useCoupon(code);
        String canceledCode = couponService.cancelCoupon(usedCode);
        Coupon canceledCoupon = couponRepository.findById(canceledCode).get();
        assertThat(canceledCoupon.getUsedDate()).isNull();
        assertThat(canceledCoupon.getStatus()).isEqualTo(CouponStatus.PUBLISHED);
    }

    @Test
    public void getTodayExpiredCouponsTest() {
        List<String> codes = couponService.getTodayExpiredCoupons();
        Coupon willBeExpiredCoupon = couponRepository.findById(codes.stream().findAny().get()).get();
        Timestamp expiredDate = willBeExpiredCoupon.getExpiredDate();
        LocalDateTime triggerTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(expiredDate.getTime()),
                        TimeZone.getDefault().toZoneId());
        assertThat(triggerTime.getDayOfMonth()).isEqualTo(LocalDateTime.now().getDayOfMonth());
    }

    @Test
    public void postMessagesTest() {
        couponService.postMessages();
    }
}