package com.example.coupon.model;

import com.example.coupon.code.CouponStatus;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "coupon")
public class Coupon {

    @Id
    @GenericGenerator(name = "code", strategy = "com.example.coupon.util.CodeGenerator")
    @GeneratedValue(generator = "code")
    @Column(name = "code", length = 13)
    private String code;

    @Column(name = "status")
    private CouponStatus status;

    @Column(name = "expired_date")
    private Timestamp expired_date;
}
