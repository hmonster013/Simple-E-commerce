package com.de013.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.de013.dto.FilterVO;
import com.de013.model.Order;

import java.util.List;
import com.de013.model.User;
import com.de013.utils.JConstants.OrderStatus;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    @Query("SELECT c FROM Order c WHERE 1 = 1 "
        + " AND ( :#{#p.userId} = 0 OR :#{#p.userId} = c.user.id ) "
        + " AND ( :#{#p.status} IS NULL OR :#{#p.orderStatus} = c.status ) "
        + " AND ( :#{#p.orderHistory} = false OR 'PENDING' != c.status ) "
    )
    public Page<Order> search(@Param("p") FilterVO request, Pageable paging);

    List<Order> findByUserAndStatus(User user, OrderStatus status);
}
