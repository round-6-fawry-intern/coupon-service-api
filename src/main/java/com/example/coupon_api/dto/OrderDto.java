package com.example.coupon_api.dto;

import lombok.Data;
import lombok.Setter;

@Data
public class OrderDto {

    long orderId;
    String couponCode;
    String userEmail;
}
