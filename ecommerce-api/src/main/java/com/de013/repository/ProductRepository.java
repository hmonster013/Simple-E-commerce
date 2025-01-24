package com.de013.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.de013.dto.FilterVO;
import com.de013.model.Coupon;
import com.de013.model.Product;

import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    @Query("SELECT c FROM Product c WHERE 1 = 1")
    public Page<Product> search(@Param("p") FilterVO request, Pageable paging);
}
