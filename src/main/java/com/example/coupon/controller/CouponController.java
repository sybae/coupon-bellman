package com.example.coupon.controller;

import com.example.coupon.model.Coupon;
import com.example.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping()
    public ResponseEntity<String> postNewCoupon() {
        return ResponseEntity.ok(Optional.of(couponService.postNewCoupon())
                .map(coupon -> coupon.getCode())
                .orElseThrow(NoSuchElementException::new));
    }

    @PostMapping("/{size}")
    public ResponseEntity<Integer> postNewCouponWithSize(@PathVariable int size) {
        return ResponseEntity.ok(couponService.postNewCouponWithSize(size));
    }

    @PutMapping("/publish")
    public ResponseEntity<String> publishCoupon() {
        Coupon published = couponService.publishCoupon();
        return published != null
                ? ResponseEntity.ok(published.getCode())
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/published")
    public ResponseEntity<List<String>> getPublishedCoupons() {
        return ResponseEntity.ok(couponService.getPublishedCoupons());
    }
}
