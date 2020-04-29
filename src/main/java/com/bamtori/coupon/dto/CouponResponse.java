package com.bamtori.coupon.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CouponResponse {

    private String code;
}
