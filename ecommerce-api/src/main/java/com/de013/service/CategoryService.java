package com.de013.service;

import org.apache.catalina.startup.Catalina;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.de013.dto.CategoryRequest;
import com.de013.dto.FilterVO;
import com.de013.model.Category;
import com.de013.repository.CategoryRepository;
import com.de013.utils.Utils;

@Service
public class CategoryService {
    private static final Logger log = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private CategoryRepository categoryRepository;

    public Page<Category> search(FilterVO request, Pageable paging) {
        return categoryRepository.search(request, paging);
    }

    public Category findById(Integer id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public Category create(CategoryRequest request) {
        Category category = new Category(request);
        this.save(category);

        return category;
    }

    public Category update(CategoryRequest request, Category existed) {
        log.debug("Update " + request);
        Utils.copyNonNullProperties(request, existed);
        existed = this.save(existed);

        return existed;
    }

    public Category save(Category category) {
        category = categoryRepository.save(category);
        log.debug("save: " + category);
        
        return category;
    }

    public void deleteById(Integer id) {
        categoryRepository.deleteById(id);
    }
}
