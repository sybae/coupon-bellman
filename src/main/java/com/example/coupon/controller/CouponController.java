package com.example.coupon.controller;

import com.example.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @GetMapping("/test")
    public ResponseEntity<String> getRandom() {
        return ResponseEntity.ok("12938718931");
    }

    @GetMapping("/{id}/name")
    public ResponseEntity<String> getName(@PathVariable Long id) {
        return ResponseEntity.ok(couponService.getNameById(id));
    }

    @PostMapping()
    public ResponseEntity<String> postNewCoupon() {
        return ResponseEntity.ok(Optional.of(couponService.postNewCoupon())
                .orElseThrow(NoSuchElementException::new));
    }

    @PostMapping("/{size}")
    public ResponseEntity<Integer> postNewCouponWithSize(@PathVariable int size) {
        return ResponseEntity.ok(couponService.postNewCouponWithSize(size));
    }
}
