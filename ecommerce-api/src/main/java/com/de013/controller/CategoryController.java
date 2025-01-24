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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.de013.dto.CategoryRequest;
import com.de013.dto.CategoryVO;
import com.de013.dto.FilterVO;
import com.de013.exception.RestException;
import com.de013.model.Category;
import com.de013.model.Paging;
import com.de013.service.CategoryService;
import com.de013.utils.URI;

@RestController
@RequestMapping(URI.V1 + URI.CATEGORY)
public class CategoryController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @PostMapping(value = URI.LIST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listAll(@RequestBody FilterVO request) throws Exception {
        log.info("Search category");
        Pageable paging = new Paging().getPageRequest(request);
        Page<Category> result = categoryService.search(request, paging);
        log.info("Search category total elements [" + result.getTotalElements() + "]");
        List<CategoryVO> reponseList = result.stream().map(Category::getVO).toList();
        
        return responseList(reponseList, result);
    }

    @GetMapping(value = URI.VIEW, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity viewDataById(@PathVariable("id") Integer id) throws Exception {
        log.info("Get detail category by [" + id + "]");
        
        Category category = categoryService.findById(id);
        if (category != null) {
            return response(category.getVO());
        } else {
            throw new RestException("Category Id [" + id + "] invaild");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = URI.ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getById(@PathVariable("id") Integer id) throws Exception {
        log.info("Get detail category by [" + id + "]");
        Category category = categoryService.findById(id);

        if (category != null) {
            return response(category.getVO());
        } else {
            throw new RestException("Category Id [" + id + "] invaild");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@ModelAttribute CategoryRequest request) throws Exception {
        log.info("Create category");

        Category category = categoryService.create(request);
        return response(category.getVO());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@ModelAttribute CategoryRequest request) throws Exception {
        Integer id = request.getId();

        log.info("Update category id [{}]", id);
        Category existed = categoryService.findById(id);

        if (existed == null) {
            throw new RestException("Category id [" + id + "] invalid");
        }

        Category result = categoryService.update(request, existed);
        return response(result);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = URI.ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteById(@PathVariable("id") Integer id) throws Exception {
        log.info("Delete category id: [{}]", id);

        Category existed = categoryService.findById(id);
        if (existed == null) {
            throw new RestException("Category id [" + id + "] invailid");
        }

        categoryService.deleteById(id);
        return responseMessage("Deleted category [" + id + "] sucessfully");
    }
}
