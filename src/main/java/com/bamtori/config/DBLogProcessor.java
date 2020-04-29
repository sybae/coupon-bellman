package com.bamtori.config;

import com.bamtori.coupon.model.Coupon;
import org.springframework.batch.item.ItemProcessor;

public class DBLogProcessor implements ItemProcessor<Coupon, Coupon> {

    @Override
    public Coupon process(Coupon coupon) throws Exception {
        System.out.println("Insert Coupon Data : " + coupon.getCode());
        return coupon;
    }
}
