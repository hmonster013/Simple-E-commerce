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
import com.de013.dto.CouponRequest;
import com.de013.dto.CouponVO;
import com.de013.dto.FilterVO;
import com.de013.exception.RestException;
import com.de013.model.Category;
import com.de013.model.Coupon;
import com.de013.model.Paging;
import com.de013.service.CategoryService;
import com.de013.service.CouponService;
import com.de013.utils.URI;

@RestController
@RequestMapping(URI.V1 + URI.COUPON)
public class CouponController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(CouponController.class);

    @Autowired
    private CouponService couponService;

    @PostMapping(value = URI.LIST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listAll(@RequestBody FilterVO request) throws Exception {
        log.info("Search coupon");
        Pageable paging = new Paging().getPageRequest(request);
        Page<Coupon> result = couponService.search(request, paging);
        log.info("Search coupon total elements [" + result.getTotalElements() + "]");
        List<CouponVO> reponseList = result.stream().map(Coupon::getVO).toList();
        
        return responseList(reponseList, result);
    }

    @GetMapping(value = URI.VIEW + URI.ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity viewDataById(@PathVariable("id") Integer id) throws Exception {
        log.info("Get detail coupon by [" + id + "]");
        
        Coupon coupon = couponService.findById(id);
        if (coupon != null) {
            return response(coupon.getVO());
        } else {
            throw new RestException("Coupon Id [" + id + "] invaild");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = URI.ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getById(@PathVariable("id") Integer id) throws Exception {
        log.info("Get detail coupon by [" + id + "]");
        Coupon coupon = couponService.findById(id);

        if (coupon != null) {
            return response(coupon.getVO());
        } else {
            throw new RestException("Coupon Id [" + id + "] invaild");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@ModelAttribute CouponRequest request) throws Exception {
        log.info("Create coupon");

        Coupon coupon = couponService.create(request);
        return response(coupon.getVO());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@ModelAttribute CouponRequest request) throws Exception {
        Integer id = request.getId();

        log.info("Update coupon id [{}]", id);
        Coupon existed = couponService.findById(id);

        if (existed == null) {
            throw new RestException("Coupon id [" + id + "] invalid");
        }

        Coupon result = couponService.update(request, existed);
        return response(result.getVO());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = URI.ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteById(@PathVariable("id") Integer id) throws Exception {
        log.info("Delete coupon id: [{}]", id);

        Coupon existed = couponService.findById(id);
        if (existed == null) {
            throw new RestException("Coupon id [" + id + "] invailid");
        }

        couponService.deleteById(id);
        return responseMessage("Deleted coupon [" + id + "] sucessfully");
    }
}
