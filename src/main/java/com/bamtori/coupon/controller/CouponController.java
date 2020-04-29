package com.bamtori.coupon.controller;

import com.bamtori.coupon.dto.CouponResponse;
import com.bamtori.coupon.dto.PostedResponse;
import com.bamtori.coupon.model.Coupon;
import com.bamtori.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    /**
     * [POST] Post New One Coupon
     * @return Code
     */
    @PostMapping()
    public ResponseEntity<CouponResponse> postNewCoupon() {
        return ResponseEntity.ok(Optional.of(couponService.postNewCoupon())
                .map(coupon -> CouponResponse.builder().code(coupon.getCode()).build())
                .orElseThrow(NoSuchElementException::new));
    }

    /**
     * [POST] Post New Several Coupons
     * @param size
     * @return Count of posted coupons
     */
    @PostMapping("/{size}")
    public ResponseEntity<PostedResponse> postNewCouponWithSize(@PathVariable int size) {
        int posted = 0;
        while (posted < size) {
            couponService.postNewCoupon();
            posted++;
        }
        return ResponseEntity.ok(PostedResponse.builder().posted(posted).build());
    }

    /**
     * [PUT] Publish One Standby Coupon
     * @return Code
     */
    @PutMapping()
    public ResponseEntity<CouponResponse> publishCoupon() {
        Coupon published = couponService.publishCoupon();
        return published != null
                ? ResponseEntity.ok(CouponResponse.builder().code(published.getCode()).build())
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * [GET] Get Published Coupons
     * @return Codes
     */
    @GetMapping("/published")
    public ResponseEntity<List<CouponResponse>> getPublishedCoupons() {
        return ResponseEntity.ok(couponService.getPublishedCoupons().stream()
                .map(code -> CouponResponse.builder().code(code).build())
                .collect(Collectors.toList())
        );
    }

    /**
     * [PUT] Use the Coupon
     * @param code
     * @return Used Code
     */
    @PutMapping("/{code}/use")
    public ResponseEntity<CouponResponse> useCoupon(@PathVariable String code) {
        String usedCode = couponService.useCoupon(code);
        return !StringUtils.isEmpty(usedCode)
                ? ResponseEntity.ok(CouponResponse.builder().code(usedCode).build())
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * [PUT] Cancel the Coupon
     * @param code
     * @return Canceled Code
     */
    @PutMapping("/{code}/cancel")
    public ResponseEntity<CouponResponse> cancelCoupon(@PathVariable String code) {
        String canceledCode = couponService.cancelCoupon(code);
        return !StringUtils.isEmpty(canceledCode)
                ? ResponseEntity.ok(CouponResponse.builder().code(canceledCode).build())
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * [GET] Get Coupons will be expired TODAY
     * @return Codes
     */
    @GetMapping("/expired")
    public ResponseEntity<List<CouponResponse>> getTodayExpiredCoupons() {
        return ResponseEntity.ok(couponService.getTodayExpiredCoupons().stream()
                .map(code -> CouponResponse.builder().code(code).build())
                .collect(Collectors.toList())
        );
    }

    /**
     * [POST] Post Messages for be expired coupons after 3 days
     * @return Message
     */
    @PostMapping("/expired/message")
    public ResponseEntity<PostedResponse> postMessagesForBeExpiredAfter3Days() {
        return ResponseEntity.ok(PostedResponse.builder().posted(couponService.postMessages()).build());
    }
}
