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
import com.de013.dto.ProductRequest;
import com.de013.dto.ProductVO;
import com.de013.exception.RestException;
import com.de013.model.Paging;
import com.de013.model.Product;
import com.de013.service.ProductService;
import com.de013.utils.URI;

@RestController
@RequestMapping(URI.V1 + URI.PRODUCT)
public class ProductController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @PostMapping(value = URI.LIST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listAll(@RequestBody FilterVO request) throws Exception {
        log.info("Search products");
        Pageable paging = new Paging().getPageRequest(request);
        Page<Product> result = productService.search(request, paging);
        log.info("Search products total elements [{}]", result.getTotalElements());
        List<ProductVO> responseList = result.stream().map(Product::getVO).toList();

        return responseList(responseList, result);
    }

    @GetMapping(value = URI.VIEW + URI.ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity viewDataById(@PathVariable("id") Long id) throws Exception {
        log.info("Get detail product by [{}]", id);

        Product product = productService.findById(id);
        if (product != null) {
            return response(product.getVO());
        } else {
            throw new RestException("Product Id [" + id + "] invalid");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody ProductRequest request) throws Exception {
        log.info("Create product");

        Product product = productService.create(request);
        return response(product.getVO());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@RequestBody ProductRequest request) throws Exception {
        Long id = request.getId();

        log.info("Update product id [{}]", id);
        Product existed = productService.findById(id);

        if (existed == null) {
            throw new RestException("Product id [" + id + "] invalid");
        }

        Product result = productService.update(request, existed);
        return response(result.getVO());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = URI.ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteById(@PathVariable("id") Long id) throws Exception {
        log.info("Delete product id: [{}]", id);

        Product existed = productService.findById(id);
        if (existed == null) {
            throw new RestException("Product id [" + id + "] invalid");
        }

        productService.deleteById(id);
        return responseMessage("Deleted product [" + id + "] successfully");
    }
}
