package com.de013.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.de013.dto.FilterVO;
import com.de013.model.Coupon;

import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {
    
    @Query("SELECT c FROM Coupon c WHERE 1 = 1")
    public Page<Coupon> search(@Param("p") FilterVO request, Pageable paging);
}
