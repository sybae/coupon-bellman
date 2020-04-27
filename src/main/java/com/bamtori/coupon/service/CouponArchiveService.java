package com.bamtori.coupon.service;

import com.bamtori.coupon.repository.CouponArchiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponArchiveService {

    private final CouponArchiveRepository couponArchiveRepository;
}
