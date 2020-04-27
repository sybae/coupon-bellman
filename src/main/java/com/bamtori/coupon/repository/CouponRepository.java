package com.bamtori.coupon.repository;

import com.bamtori.coupon.code.CouponStatus;
import com.bamtori.coupon.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, String> {

    Optional<Coupon> findFirstByStatus(CouponStatus status);
    Optional<List<Coupon>> findAllByStatus(CouponStatus status);
    Optional<List<Coupon>> findCouponsByExpiredDateBetween(Timestamp start, Timestamp end);
}
