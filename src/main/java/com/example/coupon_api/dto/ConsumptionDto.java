package com.example.coupon_api.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ConsumptionDto {
    long id;
    long orderId;
    String userEmail;
    Date consumptionDate;


}
