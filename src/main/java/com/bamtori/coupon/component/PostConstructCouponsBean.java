package com.bamtori.coupon.component;

import com.bamtori.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class PostConstructCouponsBean {

    private final CouponService couponService;

    @PostConstruct
    public void init() {
        postSomeCoupons();
        publishSomeCoupons();
    }

    private void postSomeCoupons() {
        int posted = 0;
        while (posted < 100) {
            couponService.postNewCoupon();
            posted++;
        }
    }

    private void publishSomeCoupons() {
        int published = 0;
        while (published < 30) {
            couponService.publishCoupon();
            published++;
        }
    }
}
