package com.example.coupon_api.rest;

import com.example.coupon_api.dto.ConsumptionDto;
import com.example.coupon_api.dto.OrderDto;
import com.example.coupon_api.service.ConsumptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("consumptions")
public class ConsumptionResource {

    private final ConsumptionService consumptionService;


    @PostMapping("create")
    public ResponseEntity<Void> createNewConsumption(@RequestBody OrderDto orderDto) {
        consumptionService.createNewConsumption(orderDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "validate",params = {"couponCode","userEmail"})
    public ResponseEntity<String> validateCoupon(@RequestParam("couponCode") String couponCode, @RequestParam("userEmail") String userEmail) {
        return ResponseEntity.ok(consumptionService.validateCoupon(couponCode, userEmail));
    }

    @GetMapping(value = "calculate-amount",params = {"amount","couponCode"})
    public ResponseEntity<Double> calculateOrderAmount(@RequestParam("amount") double amount, @RequestParam("couponCode") String couponCode) {
        return ResponseEntity.ok(consumptionService.calculateOrderAmount(amount, couponCode));
    }

    @GetMapping("all")
    public ResponseEntity<List<ConsumptionDto>> getAllConsumptions() {
        return ResponseEntity.ok(consumptionService.getAllConsumptions());
    }



    @GetMapping("by-id/{id}")
    public ResponseEntity<ConsumptionDto> getConsumptionById(@PathVariable Long id) {
        ConsumptionDto consumptionDto = consumptionService.getConsumptionById(id);
        return ResponseEntity.ok(consumptionDto);
    }

    @DeleteMapping
    public void deleteConsumptionById(@PathVariable Long id) {
        consumptionService.deleteConsumptionById(id);
    }


}
