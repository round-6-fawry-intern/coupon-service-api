package com.example.coupon_api.service;

import com.example.coupon_api.dto.CouponDto;
import com.example.coupon_api.dto.OrderDto;
import com.example.coupon_api.repository.entity.CouponEntity;

import java.util.List;

public interface CouponService {
    public void createNewCoupon(CouponDto couponEntity);

    public List<CouponDto> getAllCoupons();

    public CouponDto getCouponByCode(String code);

    public CouponDto getCouponById(Long id);

    //public OrderDto consumeCoupon(long userId,OrderDto orderDto,String code);
}
