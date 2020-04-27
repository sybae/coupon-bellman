package com.bamtori.coupon.repository;

import com.bamtori.coupon.model.CouponArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponArchiveRepository extends JpaRepository<CouponArchive, String> {

}
