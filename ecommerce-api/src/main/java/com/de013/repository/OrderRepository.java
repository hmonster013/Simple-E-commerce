package com.de013.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.de013.dto.FilterVO;
import com.de013.model.Order;

import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    @Query("SELECT c FROM Order c WHERE 1 = 1")
    public Page<Order> search(@Param("p") FilterVO request, Pageable paging);
}
