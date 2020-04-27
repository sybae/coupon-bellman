package com.bamtori.coupon.model;

import com.bamtori.coupon.code.CouponStatus;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "coupon_archive")
public class CouponArchive {

    @Id
    @Column(name = "code", length = 13)
    private String code;

    @Column(name = "status")
    private CouponStatus status;

    @Column(name = "used_date")
    private Timestamp used_date;

    @Column(name = "expired_date")
    private Timestamp expired_date;
}
