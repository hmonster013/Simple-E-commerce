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

    @Autowired
    private OrderService orderService;

    public Page<OrderItem> search(FilterVO request, Pageable paging) {
        return orderItemRepository.search(request, paging);
    }

    public OrderItem findById(Long id) {
        return orderItemRepository.findById(id).orElse(null);
    }

    public OrderItem create(OrderItemRequest request) {
        OrderItem orderItem = new OrderItem(request);
        this.save(orderItem);

        BigDecimal newItemTotalAmount = orderItem.getPrice().multiply(
                            BigDecimal.valueOf(orderItem.getQuantity()));

        this.updateTotalAmount(orderItem.getOrder(), newItemTotalAmount, BigDecimal.ZERO);

        return orderItem;
    }

    public OrderItem update(OrderItemRequest request, OrderItem existed) {
        log.debug("Update " + request);
        
        BigDecimal oldItemTotalAmount = existed.getPrice().multiply(
                                BigDecimal.valueOf(existed.getQuantity()));
        Utils.copyNonNullProperties(request, existed);
        existed = this.save(existed);

        BigDecimal newItemTotalAmount = existed.getPrice().multiply(
                                BigDecimal.valueOf(existed.getQuantity()));
        this.updateTotalAmount(existed.getOrder(), newItemTotalAmount, oldItemTotalAmount);

        return existed;
    }

    public void updateTotalAmount(Order order, BigDecimal newItemTotalAmount, BigDecimal oldItemTotalAmount) {
        BigDecimal orderTotalAmount = order.getTotalAmount();

        BigDecimal totalAmount = orderTotalAmount
                                    .subtract(oldItemTotalAmount)
                                    .add(newItemTotalAmount);

        order.setTotalAmount(totalAmount);
        this.orderService.save(order);
    }

    public OrderItem save(OrderItem orderItem) {
        orderItem = orderItemRepository.save(orderItem);
        log.debug("save: " + orderItem);
        
        return orderItem;
    }

    public void deleteById(Long id) {
        OrderItem orderItem = orderItemRepository.findById(id).orElse(null);
        BigDecimal oldItemTotalAmount = orderItem.getPrice().multiply(
                            BigDecimal.valueOf(orderItem.getQuantity()));

        orderItemRepository.deleteById(id);
        this.updateTotalAmount(orderItem.getOrder(), BigDecimal.ZERO, oldItemTotalAmount);
    }

    public List<OrderItem> findByOrder(Order order) {
        return orderItemRepository.findByOrder(order);
    }
}
