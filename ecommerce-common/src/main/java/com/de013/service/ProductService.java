package com.de013.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.de013.dto.CategoryRequest;
import com.de013.dto.FilterVO;
import com.de013.dto.ProductRequest;
import com.de013.model.Category;
import com.de013.model.Product;
import com.de013.repository.ProductRepository;
import com.de013.utils.URI;
import com.de013.utils.Utils;

@Service
public class ProductService {
    private static final Logger log = LoggerFactory.getLogger(CouponService.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageStorageService imageStorageService;

    @Autowired
    private CategoryService categoryService;

    public Page<Product> search(FilterVO request, Pageable paging) {
        if (request.getCategories() != null) {
            List<Integer> categoryIds = request.getCategories();
            List<Category> categories = null;
            categories = categoryService.findByListId(categoryIds);
    
            return productRepository.search(request, paging, categories, categories.size());
        }
        
        return productRepository.search(request, paging);
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product create(ProductRequest request, MultipartFile[] files) {
        Product product = new Product(request);

        // Save image
        List<String> imageUrl = new ArrayList<>();
        if (files != null) {
            for (MultipartFile file : files) {
                try {
                    imageUrl.add(URI.BASE
                                + URI.V1
                                + URI.IMAGE
                                + URI.VIEW
                                + URI.SLASH
                                + imageStorageService.saveFile(file)); 
                } catch (IOException e) {
                    log.error("Save image error: ", e.getMessage());
                } 
            }
        }
        product.setImageUrl(imageUrl);

        // Get List<Category> by List<Id>
        if (request.getCategories() != null && !request.getCategories().isEmpty()) {
            List<Category> categories = categoryService.findByListId(request.getCategories());
            product.setCategories(categories);
        }

        this.save(product);

        return product;
    }

    public Product update(ProductRequest request, Product existed, MultipartFile[] files) {
        log.debug("Update " + request);
    
        List<String> newImgUrl = request.getImageUrl();
        List<String> oldImgUrl = existed.getImageUrl();
    
        // Find images to remove
        List<String> removeImgUrl = getImagesToRemove(oldImgUrl, newImgUrl);
        removeImages(removeImgUrl);
    
        Utils.copyNonNullProperties(request, existed);
    
        List<String> imageUrl = saveImages(files, existed.getImageUrl());
        existed.setImageUrl(imageUrl);
    
        return this.save(existed);
    }
    
    private List<String> getImagesToRemove(List<String> oldImgUrl, List<String> newImgUrl) {
        List<String> removeImgUrl = new ArrayList<>(oldImgUrl);
        removeImgUrl.removeAll(newImgUrl);
        return removeImgUrl;
    }
    
    private void removeImages(List<String> removeImgUrl) {
        for (String imgUrl : removeImgUrl) {
            String fileName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
            try {
                imageStorageService.deleteFile(fileName);
            } catch (IOException e) {
                log.error("Delete image error: ", e.getMessage());
            }
        }
    }
    
    private List<String> saveImages(MultipartFile[] files, List<String> existingImageUrls) {
        List<String> imageUrl = new ArrayList<>(existingImageUrls);
    
        if (files != null) {
            for (MultipartFile file : files) {
                try {
                    String savedImageUrl = URI.BASE 
                                        + URI.V1 
                                        + URI.IMAGE 
                                        + URI.VIEW 
                                        + URI.SLASH 
                                        + imageStorageService.saveFile(file);
                    imageUrl.add(savedImageUrl);
                } catch (IOException e) {
                    log.error("Save image error: ", e.getMessage());
                }
            }
        }
        return imageUrl;
    }
    

    public Product save(Product product) {
        product = productRepository.save(product);
        log.debug("save: " + product);
        
        return product;
    }

    public void deleteById(Long id) {
        Product product = this.findById(id);

        this.removeImages(product.getImageUrl());
        
        productRepository.deleteById(id);
    }
}
