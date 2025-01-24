package com.de013.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.de013.dto.CategoryRequest;
import com.de013.dto.CouponRequest;
import com.de013.dto.FilterVO;
import com.de013.model.Category;
import com.de013.model.Coupon;
import com.de013.repository.CouponRepository;
import com.de013.utils.Utils;

@Service
public class CouponService {
    private static final Logger log = LoggerFactory.getLogger(CouponService.class);

    @Autowired
    private CouponRepository couponRepository;

    public Page<Coupon> search(FilterVO request, Pageable paging) {
        return couponRepository.search(request, paging);
    }

    public Coupon findById(Integer id) {
        return couponRepository.findById(id).orElse(null);
    }

    public Coupon create(CouponRequest request) {
        Coupon coupon = new Coupon(request);
        this.save(coupon);

        return coupon;
    }

    public Coupon update(CouponRequest request, Coupon existed) {
        log.debug("Update " + request);
        Utils.copyNonNullProperties(request, existed);
        existed = this.save(existed);

        return existed;
    }

    public Coupon save(Coupon coupon) {
        coupon = couponRepository.save(coupon);
        log.debug("save: " + coupon);
        
        return coupon;
    }

    public void deleteById(Integer id) {
        couponRepository.deleteById(id);
    }
}
