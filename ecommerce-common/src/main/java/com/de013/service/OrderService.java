package com.de013.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.de013.dto.FilterVO;
import com.de013.dto.OrderRequest;
import com.de013.dto.ProductRequest;
import com.de013.dto.UserRequest;
import com.de013.model.Order;
import com.de013.model.OrderItem;
import com.de013.model.Product;
import com.de013.model.User;
import com.de013.repository.OrderRepository;
import com.de013.utils.Utils;
import com.de013.utils.JConstants.OrderStatus;
import com.de013.utils.JConstants.OrderType;

@Service
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

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

    public List<Order> findByUserAndStatus(User user, OrderStatus status) {
        return orderRepository.findByUserAndStatus(user, status);
    }


    public Order findByUserPending(User user) {
        List<Order> lsOrder = orderRepository.findByUserAndStatus(user, OrderStatus.PENDING);

        if (lsOrder.size() == 0) {
            return null;
        } 

        return lsOrder.get(0);
    }

    // Crate order status (PENDING)
    public void createPending(User user) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setUser(user);
        orderRequest.setType(OrderType.SALES);
        orderRequest.setTotalAmount(BigDecimal.ZERO);
        orderRequest.setStatus(OrderStatus.PENDING);

        this.create(orderRequest);
    } 

    // Order status (PENDING -> PROCESSING)
    public Order updateProcessing(OrderRequest request, Order existed) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        if (existed.getOrderItems() != null && !existed.getOrderItems().isEmpty()) {
            for (OrderItem orderItem : existed.getOrderItems()) {
                // totalAmount += x_price * quantity
                Product product = orderItem.getProduct();
                switch (existed.getType()) {
                    case SALES:
                        totalAmount = totalAmount.add(product.getSalePrice().multiply(new BigDecimal(orderItem.getQuantity())));
                        break;
                    case PURCHASE:
                        totalAmount = totalAmount.add(product.getPurchasePrice().multiply(new BigDecimal(orderItem.getQuantity())));
                        break;
                    default:
                        break;
                }
            }

            if (request.getCoupon() != null) {
                // totalAmount = totalAmount - discount * totalAmount
                BigDecimal discount = totalAmount.multiply(request.getCoupon().getDiscountPercentage().divide(BigDecimal.valueOf(100)));
                totalAmount = totalAmount.subtract(discount);
            }

            request.setTotalAmount(totalAmount);;
        }

        request.setStatus(OrderStatus.PROCESSING);

        Utils.copyNonNullProperties(request, existed);
        existed = this.save(existed);

        // Adjust quantity product
        this.adjustQuantityProduct(existed.getOrderItems(), existed.getType());

        // New order status pending
        if (existed.getType() == OrderType.SALES) {
            this.createPending(existed.getUser());
        }

        return existed;
    }

    public void adjustQuantityProduct(Set<OrderItem> orderItems, OrderType type) {
        for (OrderItem orderItem : orderItems) {
            Product product = orderItem.getProduct();
            switch (type) {
                case SALES:
                    product.setStockQuantity(product.getStockQuantity() - orderItem.getQuantity());
                    break;
                case PURCHASE:
                    product.setStockQuantity(product.getStockQuantity() + orderItem.getQuantity());
                    break;

                default:
                    break;
            }
            productService.save(product);
        }
    }
}
