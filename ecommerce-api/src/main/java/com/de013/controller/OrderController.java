package com.de013.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.de013.dto.FilterVO;
import com.de013.dto.OrderRequest;
import com.de013.dto.OrderVO;
import com.de013.exception.RestException;
import com.de013.model.Order;
import com.de013.model.Paging;
import com.de013.service.OrderService;
import com.de013.utils.URI;

@RestController
@RequestMapping(URI.V1 + URI.ORDER)
public class OrderController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping(value = URI.LIST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listAll(@RequestBody FilterVO request) throws Exception {
        log.info("Search orders");
        Pageable paging = new Paging().getPageRequest(request);
        Page<Order> result = orderService.search(request, paging);
        log.info("Search orders total elements [{}]", result.getTotalElements());
        List<OrderVO> responseList = result.stream().map(Order::getVO).toList();

        return responseList(responseList, result);
    }

    @GetMapping(value = URI.VIEW + URI.ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity viewDataById(@PathVariable("id") Long id) throws Exception {
        log.info("Get detail order by [{}]", id);

        Order order = orderService.findById(id);
        if (order != null) {
            return response(order.getVO());
        } else {
            throw new RestException("Order Id [" + id + "] invalid");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody OrderRequest request) throws Exception {
        log.info("Create order");

        Order order = orderService.create(request);
        return response(order.getVO());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@RequestBody OrderRequest request) throws Exception {
        Long id = request.getId();

        log.info("Update order id [{}]", id);
        Order existed = orderService.findById(id);

        if (existed == null) {
            throw new RestException("Order id [" + id + "] invalid");
        }

        Order result = orderService.update(request, existed);
        return response(result.getVO());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = URI.ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteById(@PathVariable("id") Long id) throws Exception {
        log.info("Delete order id: [{}]", id);

        Order existed = orderService.findById(id);
        if (existed == null) {
            throw new RestException("Order id [" + id + "] invalid");
        }

        orderService.deleteById(id);
        return responseMessage("Deleted order [" + id + "] successfully");
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping(value = URI.HISTORY, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUserHistory(@RequestBody FilterVO request) throws Exception {
        log.info("View orders by user id [{}]", request.getUserId());

        request.setOrderHistory(true);
        Pageable paging = new Paging().getPageRequest(request);
        Page<Order> result = orderService.search(request, paging);
        log.info("View orders total elements [{}]", result.getTotalElements());
        List<OrderVO> responseList = result.stream().map(Order::getVO).toList();

        return responseList(responseList, result);
    }

    // Order status (PENDING -> PROCESSING)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PutMapping(value = URI.PROCESSING, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity processingOrder(@RequestBody OrderRequest request) {
        Long id = request.getId();

        log.info("Convert order to processing id [{}]", id);
        Order existed = orderService.findById(id);

        if (existed == null) {
            throw new RestException("Order id [" + id + "] invalid");
        }

        Order result = orderService.updateProcessing(request, existed);
        return response(result.getVO());
    }
}
