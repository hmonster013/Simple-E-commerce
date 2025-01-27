package com.de013.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.de013.dto.FilterVO;
import com.de013.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    @Query("SELECT c FROM Review c WHERE 1 = 1")
    public Page<Review> search(@Param("p") FilterVO request, Pageable paging);

    @Query("SELECT c FROM Review c WHERE 1 = 1 "
        + " AND :#{#p.productId} = c.orderItem.product.id "
    )
    public Page<Review> findByProductId(@Param("p") FilterVO request, Pageable paging);
}
