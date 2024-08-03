package com.example.coupon_api.repository;

import com.example.coupon_api.repository.entity.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<CouponEntity, Long> {

    public Optional<CouponEntity> findByCode(String code);

}
