package com.de013.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.de013.dto.FilterVO;
import com.de013.dto.OrderRequest;
import com.de013.model.Order;
import com.de013.repository.OrderRepository;
import com.de013.utils.Utils;

@Service
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    public Page<Order> search(FilterVO request, Pageable paging) {
        return orderRepository.search(request, paging);
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order create(OrderRequest request) {
        Order order = new Order(request);
        this.save(order);

        return order;
    }

    public Order update(OrderRequest request, Order existed) {
        log.debug("Update " + request);
        Utils.copyNonNullProperties(request, existed);
        existed = this.save(existed);

        return existed;
    }

    public Order save(Order order) {
        order = orderRepository.save(order);
        log.debug("save: " + order);
        
        return order;
    }

    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }
}
