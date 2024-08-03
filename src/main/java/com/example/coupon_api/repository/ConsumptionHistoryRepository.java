package com.example.coupon_api.repository;

import com.example.coupon_api.repository.entity.ConsumptionHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsumptionHistoryRepository extends JpaRepository<ConsumptionHistoryEntity, Long> {
    @Query("SELECT c FROM ConsumptionHistoryEntity c WHERE c.email = :email AND c.coupon.id = :couponId")
    public Optional<ConsumptionHistoryEntity> findByMailAndCouponId(@Param("email") String email, @Param("couponId") Long couponId);
}
