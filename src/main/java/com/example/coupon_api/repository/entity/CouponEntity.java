package com.example.coupon_api.repository.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Entity
@Table(name = "coupons")
@Data
public class CouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @Column(name = "code")
    String code;
    @Column(name = "quantity")
    int quantity;
    @Column(name = "expiration_date")
    @Temporal(TemporalType.DATE)
    Date expirationDate;
    @Column(name = "value")
    double value;
    @Column(name = "fixed")
    boolean fixed;


    public void decrementQuantity() {
        quantity--;
    }
}
