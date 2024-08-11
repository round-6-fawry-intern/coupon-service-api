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
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private CouponEntity coupon;

    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "user_email")
    private String email;
    @Column(name = "consumption_date")
    private Date consumptionDate;

}
