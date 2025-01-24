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
import com.de013.dto.OrderItemRequest;
import com.de013.dto.OrderItemVO;
import com.de013.exception.RestException;
import com.de013.model.OrderItem;
import com.de013.model.Paging;
import com.de013.service.OrderItemService;
import com.de013.utils.URI;

@RestController
@RequestMapping(URI.V1 + URI.ORDER_ITEM)
public class OrderItemController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(OrderItemController.class);

    @Autowired
    private OrderItemService orderItemService;

    @PostMapping(value = URI.LIST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listAll(@RequestBody FilterVO request) throws Exception {
        log.info("Search order items");
        Pageable paging = new Paging().getPageRequest(request);
        Page<OrderItem> result = orderItemService.search(request, paging);
        log.info("Search order items total elements [{}]", result.getTotalElements());
        List<OrderItemVO> responseList = result.stream().map(OrderItem::getVO).toList();

        return responseList(responseList, result);
    }

    @GetMapping(value = URI.VIEW + URI.ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity viewDataById(@PathVariable("id") Long id) throws Exception {
        log.info("Get detail order item by [{}]", id);

        OrderItem orderItem = orderItemService.findById(id);
        if (orderItem != null) {
            return response(orderItem.getVO());
        } else {
            throw new RestException("Order Item Id [" + id + "] invalid");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody OrderItemRequest request) throws Exception {
        log.info("Create order item");

        OrderItem orderItem = orderItemService.create(request);
        return response(orderItem.getVO());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@RequestBody OrderItemRequest request) throws Exception {
        Long id = request.getId();

        log.info("Update order item id [{}]", id);
        OrderItem existed = orderItemService.findById(id);

        if (existed == null) {
            throw new RestException("Order Item id [" + id + "] invalid");
        }

        OrderItem result = orderItemService.update(request, existed);
        return response(result.getVO());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = URI.ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteById(@PathVariable("id") Long id) throws Exception {
        log.info("Delete order item id: [{}]", id);

        OrderItem existed = orderItemService.findById(id);
        if (existed == null) {
            throw new RestException("Order Item id [" + id + "] invalid");
        }

        orderItemService.deleteById(id);
        return responseMessage("Deleted order item [" + id + "] successfully");
    }
}
