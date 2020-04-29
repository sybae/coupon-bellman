package com.bamtori.coupon.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PostedResponse {

    private int posted;
}
