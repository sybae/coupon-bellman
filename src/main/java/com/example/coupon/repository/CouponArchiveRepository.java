package com.example.coupon.repository;

import com.example.coupon.model.CouponArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponArchiveRepository extends JpaRepository<CouponArchive, String> {

}
