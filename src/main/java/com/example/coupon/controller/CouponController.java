package com.example.coupon.controller;

import com.example.coupon.model.Coupon;
import com.example.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    /**
     * Post New One Coupon
     * @return Code
     */
    @PostMapping()
    public ResponseEntity<String> postNewCoupon() {
        return ResponseEntity.ok(Optional.of(couponService.postNewCoupon())
                .map(coupon -> coupon.getCode())
                .orElseThrow(NoSuchElementException::new));
    }

    /**
     * Post New Several Coupons
     * @param size
     * @return Count of posted coupons
     */
    @PostMapping("/{size}")
    public ResponseEntity<Integer> postNewCouponWithSize(@PathVariable int size) {
        int posted = 0;
        while (posted < size) {
            couponService.postNewCoupon();
            posted++;
        }
        return ResponseEntity.ok(posted);
    }

    /**
     * Publish One Standby Coupon
     * @return Code
     */
    @PutMapping()
    public ResponseEntity<String> publishCoupon() {
        Coupon published = couponService.publishCoupon();
        return published != null
                ? ResponseEntity.ok(published.getCode())
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * Get Published Coupons
     * @return Codes
     */
    @GetMapping("/published")
    public ResponseEntity<List<String>> getPublishedCoupons() {
        return ResponseEntity.ok(couponService.getPublishedCoupons());
    }

    /**
     * Use the Coupon
     * @param code
     * @return Used Code
     */
    @PutMapping("/{code}/use")
    public ResponseEntity<String> useCoupon(@PathVariable String code) {
        String usedCode = couponService.useCoupon(code);
        return !StringUtils.isEmpty(usedCode)
                ? ResponseEntity.ok(usedCode)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * Cancel the Coupon
     * @param code
     * @return Canceled Code
     */
    @PutMapping("/{code}/cancel")
    public ResponseEntity<String> cancelCoupon(@PathVariable String code) {
        String canceledCode = couponService.cancelCoupon(code);
        return !StringUtils.isEmpty(canceledCode)
                ? ResponseEntity.ok(canceledCode)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * Get Coupons will be expired TODAY
     * @return Codes
     */
    @GetMapping("/expired")
    public ResponseEntity<List<String>> getTodayExpiredCoupons() {
        return ResponseEntity.ok(couponService.getTodayExpiredCoupons());
    }
}
