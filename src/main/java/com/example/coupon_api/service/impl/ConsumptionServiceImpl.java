package com.example.coupon_api.service.impl;

import com.example.coupon_api.Error.exceptions.NotFoundException;
import com.example.coupon_api.Error.exceptions.NotValidException;
import com.example.coupon_api.dto.ConsumptionDto;
import com.example.coupon_api.dto.OrderDto;
import com.example.coupon_api.repository.ConsumptionHistoryRepository;
import com.example.coupon_api.repository.CouponRepository;
import com.example.coupon_api.repository.entity.ConsumptionHistoryEntity;
import com.example.coupon_api.repository.entity.CouponEntity;
import com.example.coupon_api.service.ConsumptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ConsumptionServiceImpl implements ConsumptionService {

    private final CouponRepository couponRepository;
    private final ConsumptionHistoryRepository consumptionRepository;



    @Override
    public String validateCoupon(String couponCode, String userEmail) {
        applyValidations(couponCode,userEmail);
        return "valid";
    }
    @Override
    @Transactional
    public void createNewConsumption(OrderDto orderDto) {
        Optional<CouponEntity> optionalCoupon= couponRepository.findByCode(orderDto.getCouponCode());
        applyValidations(orderDto.getCouponCode(), orderDto.getUserEmail());
        CouponEntity couponEntity = optionalCoupon.get();
        updateCouponQuantity(couponEntity);
        setConsumptionHistoryValuesAndSaveRecord(orderDto,  couponEntity);
    }


    @Override
    public List<ConsumptionDto> getAllConsumptions() {
        List<ConsumptionHistoryEntity> consumptionHistoryEntities = consumptionRepository.findAll();
        return consumptionHistoryEntities
                .stream()
                .map(consumptionHistoryEntity -> {
                    ConsumptionDto consumptionDto = new ConsumptionDto();
                    consumptionEntityToDtoMapper(consumptionHistoryEntity, consumptionDto);
                    return consumptionDto;
                }).toList();
    }





    @Override
    public ConsumptionDto getConsumptionById(Long id) {
        Optional<ConsumptionHistoryEntity> optionalConsumptionHistoryEntity = consumptionRepository.findById(id);
        if (!optionalConsumptionHistoryEntity.isPresent()) {
            throw new NotFoundException("Coupon with id: " + id + " is not found");
        }
        ConsumptionDto consumptionDto = new ConsumptionDto();
        consumptionEntityToDtoMapper(optionalConsumptionHistoryEntity.get(), consumptionDto);
        return consumptionDto;
    }

    @Override
    public double calculateOrderAmount(double amount, String couponCode) {
        Optional<CouponEntity> optionalCoupon= couponRepository.findByCode(couponCode);
        if (optionalCoupon.isEmpty()){
            throw new NotFoundException("Coupon with code " + couponCode + " does not exist");
        }
        return applyCoupon(amount,optionalCoupon.get());
    }

    @Override
    public void deleteConsumptionById(Long id) {
        Optional<ConsumptionHistoryEntity> optionalConsumptionHistoryEntity = consumptionRepository.findById(id);
        if (!optionalConsumptionHistoryEntity.isPresent()) {
            throw new NotFoundException("Coupon with id: " + id + " is not found");
        }
        consumptionRepository.deleteById(id);
    }

    private void applyValidations(String couponCode, String userMail) {
        Optional<CouponEntity> optionalCoupon = couponRepository.findByCode(couponCode);

        if (!optionalCoupon.isPresent()) {
            throw new NotValidException("Coupon code not valid");
        }
        CouponEntity couponEntity = optionalCoupon.get();
        if (couponEntity.getQuantity() <= 0) {
            throw new NotValidException("Coupon is not available");
        }
        if (couponEntity.getExpirationDate().before(new Date())) {
            throw new NotValidException("Coupon is expired");
        }
        Optional<ConsumptionHistoryEntity> optionalConsumption = consumptionRepository
                .findByMailAndCouponId(userMail,couponEntity.getId());
        if(optionalConsumption.isPresent()) {
            throw new NotValidException("Coupon is already used");
        }
    }

    private void updateCouponQuantity(CouponEntity couponEntity) {
        couponEntity.decrementQuantity();
        couponRepository.save(couponEntity);
    }

    private void setConsumptionHistoryValuesAndSaveRecord(OrderDto orderDto, CouponEntity couponEntity) {
        Optional<CouponEntity> optionalCoupon= couponRepository.findByCode(orderDto.getCouponCode());

        ConsumptionHistoryEntity consumptionHistoryEntity = new ConsumptionHistoryEntity();
        consumptionRepository.save(consumptionHistoryEntity);
        consumptionHistoryEntity.setCoupon(couponEntity);
        consumptionHistoryEntity.setEmail(orderDto.getUserEmail());
        consumptionHistoryEntity.setOrderId(orderDto.getOrderId());
        consumptionHistoryEntity.setConsumptionDate(new Date());
    }

    public double applyCoupon(double amount, CouponEntity couponEntity) {
        if (couponEntity.isFixed()) {
            return calculateOrderPriceForFixedCoupon(couponEntity, amount);
        }else {
            return calculateOrderPriceForPercentageCoupon(couponEntity, amount);
        }
    }

    private double calculateOrderPriceForPercentageCoupon(CouponEntity couponEntity, double amount) {
        double percentage = couponEntity.getValue();
        return(amount - (amount * percentage / 100));
    }

    private double calculateOrderPriceForFixedCoupon(CouponEntity couponEntity, double amount) {
        double fixedPrice = couponEntity.getValue();
        if (amount > fixedPrice) {
            return(amount - fixedPrice);
        }
        else {
            throw new NotValidException("Coupon value is greater than the price");
        }
    }

    private void consumptionEntityToDtoMapper(ConsumptionHistoryEntity  entity, ConsumptionDto dto) {
        dto.setConsumptionDate(entity.getConsumptionDate());
        dto.setId(entity.getId());
        dto.setUserEmail(entity.getEmail());
        dto.setOrderId(entity.getOrderId());
    }
}
