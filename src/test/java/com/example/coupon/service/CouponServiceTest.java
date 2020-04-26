package com.example.coupon.service;

import com.example.coupon.model.Coupon;
import com.example.coupon.repository.CouponRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback(false)
public class CouponServiceTest {

    @Autowired
    CouponRepository couponRepository;

    @Test
    public void amIOkTest() {
        /*
        // given
        Coupon c = Coupon.builder().name("kh").build();
        couponRepository.save(c);

        // when
        Coupon cc = couponRepository.findById(c.getId()).get();

        // then
        Assert.assertEquals(cc.getName(), "kh");
        */
    }

}