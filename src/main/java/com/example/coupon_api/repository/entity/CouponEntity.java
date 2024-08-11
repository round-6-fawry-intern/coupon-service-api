package com.example.coupon_api.repository.entity;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;


@Entity
@Table(name = "coupons")
@Data
public class CouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "code")
    private String code;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "expiration_date")
    @Timestamp()
    private LocalDate expirationDate;
    @Column(name = "value")
    private double value;
    @Column(name = "fixed")
    private boolean fixed;


    public void decrementQuantity() {
        quantity--;
    }
}
