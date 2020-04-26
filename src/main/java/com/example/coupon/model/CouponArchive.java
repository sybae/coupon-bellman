package com.example.coupon.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "coupon_archive")
public class CouponArchive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;
}
