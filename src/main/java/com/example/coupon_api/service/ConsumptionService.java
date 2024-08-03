package com.example.coupon_api.service;

import com.example.coupon_api.dto.ConsumptionDto;
import com.example.coupon_api.dto.OrderDto;

import java.util.List;

public interface ConsumptionService {
    public void createNewConsumption( OrderDto orderDto);

    public String validateCoupon(String couponCode, String userEmail);

    public List<ConsumptionDto> getAllConsumptions();

    public ConsumptionDto getConsumptionById(Long id);

    public double calculateOrderAmount(double amount, String couponCode);

    public void deleteConsumptionById(Long id);
}
