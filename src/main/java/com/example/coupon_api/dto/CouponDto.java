package com.example.coupon_api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jdk.jfr.Timestamp;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class CouponDto {

    private Long id;

    private String code;

    @Positive(message = "Number of uses must be positive")
    @NotEmpty(message = "Question Ids are mandatory")
    private int numberOfUses;

    @NotBlank(message = "Expiration Date is mandatory")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;

    @Positive(message = "Value must be positive")
    private double value;

    private boolean fixed=true;
}
