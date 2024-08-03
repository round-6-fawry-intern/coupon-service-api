package com.example.coupon_api.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name ="consumption_history")
@Setter
@Getter
public class ConsumptionHistoryEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    CouponEntity coupon;

    @Column(name = "order_id")
    Long orderId;
    @Column(name = "user_email")
    String email;
    @Column(name = "consumption_date")
    Date consumptionDate;

}
