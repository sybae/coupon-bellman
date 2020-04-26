package com.example.coupon.service;

import com.example.coupon.repository.CouponArchiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponArchiveService {

    private final CouponArchiveRepository couponArchiveRepository;
}
