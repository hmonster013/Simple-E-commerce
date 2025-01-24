package com.de013.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.de013.dto.CategoryRequest;
import com.de013.dto.FilterVO;
import com.de013.dto.ProductRequest;
import com.de013.model.Category;
import com.de013.model.Product;
import com.de013.repository.ProductRepository;
import com.de013.utils.Utils;

@Service
public class ProductService {
    private static final Logger log = LoggerFactory.getLogger(CouponService.class);

    @Autowired
    private ProductRepository productRepository;

    public Page<Product> search(FilterVO request, Pageable paging) {
        return productRepository.search(request, paging);
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product create(ProductRequest request) {
        Product product = new Product(request);
        this.save(product);

        return product;
    }

    public Product update(ProductRequest request, Product existed) {
        log.debug("Update " + request);
        Utils.copyNonNullProperties(request, existed);
        existed = this.save(existed);

        return existed;
    }

    public Product save(Product product) {
        product = productRepository.save(product);
        log.debug("save: " + product);
        
        return product;
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
