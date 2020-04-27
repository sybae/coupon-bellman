package com.bamtori.coupon.service;

import com.bamtori.coupon.repository.CouponRepository;
import com.bamtori.coupon.model.Coupon;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
//@Rollback(false)
@Transactional
public class CouponServiceTest {

    @Autowired
    CouponService couponService;

    @Autowired
    CouponRepository couponRepository;

    @Test
    public void postNewCouponTest() {
        Coupon coupon = couponService.postNewCoupon();
        assertThat(coupon).isNotNull();
        assertThat(coupon.getCode().length()).isEqualTo(13);

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