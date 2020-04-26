package com.example.coupon.controller;

import com.example.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dummy")
@RequiredArgsConstructor
public class DummyController {

    private final CouponService couponService;

    @PostMapping()
    public ResponseEntity<?> buildDummy() {
        int posted = 0;
        while (posted < 20) {
            couponService.postNewCoupon();
            posted++;
        }

        int published = 0;
        while (published < 5) {
            couponService.publishCoupon();
            published++;
        }

        return ResponseEntity.ok().build();
    }
}
