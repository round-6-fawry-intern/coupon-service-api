package com.example.coupon_api.rest;

import com.example.coupon_api.dto.CouponDto;
import com.example.coupon_api.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("coupons")
public class CouponResource {

    private final CouponService couponService;

    @PostMapping("create")
    public ResponseEntity<Void> createNewCoupon(@RequestBody CouponDto couponDto) {
        couponService.createNewCoupon(couponDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("all")
    public ResponseEntity<List<CouponDto>> getAllCoupons() {
        List<CouponDto> couponDtos = couponService.getAllCoupons();
        return ResponseEntity.ok(couponDtos);
    }

    @GetMapping("/by-code/{code}")
    public ResponseEntity<CouponDto> getCouponByCode(@PathVariable String code) {
        CouponDto couponDto = couponService.getCouponByCode(code);
        return ResponseEntity.ok(couponDto);
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<CouponDto> getCouponById(@PathVariable Long id) {
        CouponDto couponDto = couponService.getCouponById(id);
        return ResponseEntity.ok(couponDto);
    }


}
