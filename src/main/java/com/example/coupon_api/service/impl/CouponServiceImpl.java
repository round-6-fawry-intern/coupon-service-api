package com.example.coupon_api.service.impl;

import com.example.coupon_api.Error.exceptions.DuplicateException;
import com.example.coupon_api.Error.exceptions.NotFoundException;
import com.example.coupon_api.dto.CouponDto;
import com.example.coupon_api.dto.OrderDto;
import com.example.coupon_api.repository.CouponRepository;
import com.example.coupon_api.repository.entity.CouponEntity;
import com.example.coupon_api.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;


    @Override
    public void createNewCoupon(CouponDto coupondto) {
        if (coupondto.getCode()!= null) {
            if(couponRepository.findByCode(coupondto.getCode()).isPresent()) {
                throw new DuplicateException("Coupon with code " + coupondto.getCode() + " already exists");
            }
            CouponEntity couponEntity = new CouponEntity();
            couponDtoToEntityMapper(coupondto, couponEntity);
            couponRepository.save(couponEntity);

        }
    }

    @Override
    public List<CouponDto> getAllCoupons() {
        List<CouponEntity> couponEntities = couponRepository.findAll();
        List<CouponDto> couponDtos= couponEntities
                .stream()
                .map(couponEntity -> {
                    CouponDto couponDto = new CouponDto();
                    couponEntityToDtoMapper(couponEntity, couponDto);
                    return couponDto;
                }).toList();
        return couponDtos;
    }



    @Override
    public CouponDto getCouponByCode(String code) {
        Optional<CouponEntity> optionalCoupon = couponRepository.findByCode(code);
        if (!optionalCoupon.isPresent()){
            throw new NotFoundException("Coupon with code " + code + " does not exist");
        }
        CouponDto couponDto = new CouponDto();
        couponEntityToDtoMapper(optionalCoupon.get(), couponDto);
        return couponDto;
    }

    @Override
    public CouponDto getCouponById(Long id) {
        Optional<CouponEntity> optionalCoupon = couponRepository.findById(id);
        if (optionalCoupon.isPresent()) {
           throw new NotFoundException("Coupon with id " + id + " does not exist");
        }
        CouponDto couponDto = new CouponDto();
        couponEntityToDtoMapper(optionalCoupon.get(), couponDto);
        return couponDto;
    }

    @Override
    public void deleteCouponById(Long id) {
        Optional<CouponEntity> optionalCoupon= couponRepository.findById(id);
        if (!optionalCoupon.isPresent()){
            throw new NotFoundException("Coupon with id " + id + " does not exist");
        }
        couponRepository.deleteById(id);
    }

    @Override
    public void updateCoupon(CouponDto couponDto, Long id) {
        Optional<CouponEntity> optionalCoupon= couponRepository.findById(id);
        if (!optionalCoupon.isPresent()){
            throw new NotFoundException("Coupon with id " + id + " does not exist");
        }
        CouponEntity couponEntity = optionalCoupon.get();
        couponDtoToEntityMapper(couponDto, couponEntity);
        couponRepository.save(couponEntity);
    }


    private void couponDtoToEntityMapper(CouponDto coupondto, CouponEntity couponEntity) {
        couponEntity.setCode(coupondto.getCode());
        couponEntity.setQuantity(coupondto.getNumberOfUses());
        couponEntity.setExpirationDate(coupondto.getExpirationDate());
        couponEntity.setValue(coupondto.getValue());
        couponEntity.setFixed(coupondto.isFixed());
    }

    private void couponEntityToDtoMapper(CouponEntity couponEntity, CouponDto couponDto) {
        couponDto.setId(couponEntity.getId());
        couponDto.setCode(couponEntity.getCode());
        couponDto.setNumberOfUses(couponEntity.getQuantity());
        couponDto.setExpirationDate(couponEntity.getExpirationDate());
        couponDto.setValue(couponEntity.getValue());
        couponDto.setFixed(couponEntity.isFixed());
    }
}
