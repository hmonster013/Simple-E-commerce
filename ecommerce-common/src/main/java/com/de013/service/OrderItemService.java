package com.de013.service;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.de013.dto.CouponRequest;
import com.de013.dto.FilterVO;
import com.de013.dto.OrderItemRequest;
import com.de013.model.Category;
import com.de013.model.Coupon;
import com.de013.model.Order;
import com.de013.model.OrderItem;
import com.de013.repository.OrderItemRepository;
import com.de013.repository.OrderRepository;
import com.de013.utils.Utils;
import com.de013.utils.JConstants.CrudMethod;

@Service
public class OrderItemService {
    private static final Logger log = LoggerFactory.getLogger(CouponService.class);

    @Autowired
    private OrderItemRepository orderItemRepository;

    public Page<OrderItem> search(FilterVO request, Pageable paging) {
        return orderItemRepository.search(request, paging);
    }

    public OrderItem findById(Long id) {
        return orderItemRepository.findById(id).orElse(null);
    }

    public OrderItem create(OrderItemRequest request) {
        OrderItem orderItem = new OrderItem(request);
        this.save(orderItem);
        
        return orderItem;
    }

    public OrderItem update(OrderItemRequest request, OrderItem existed) {
        log.debug("Update " + request);

        Utils.copyNonNullProperties(request, existed);
        existed = this.save(existed);

        return existed;
    }

    public OrderItem save(OrderItem orderItem) {
        orderItem = orderItemRepository.save(orderItem);
        log.debug("save: " + orderItem);
        
        return orderItem;
    }

    public void deleteById(Long id) {
        orderItemRepository.deleteById(id);
    }

    public List<OrderItem> findByOrder(Order order) {
        return orderItemRepository.findByOrder(order);
    }
}
