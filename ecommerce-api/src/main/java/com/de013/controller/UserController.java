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

import com.de013.dto.CouponRequest;
import com.de013.dto.CouponVO;
import com.de013.dto.FilterVO;
import com.de013.dto.UserRequest;
import com.de013.dto.UserVO;
import com.de013.exception.RestException;
import com.de013.model.Coupon;
import com.de013.model.Paging;
import com.de013.model.User;
import com.de013.service.CouponService;
import com.de013.service.UserService;
import com.de013.utils.URI;

@RestController
@RequestMapping(URI.V1 + URI.USER)
public class UserController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(CouponController.class);

    @Autowired
    private UserService userService;

    @PostMapping(value = URI.LIST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listAll(@RequestBody FilterVO request) throws Exception {
        log.info("Search user");
        Pageable paging = new Paging().getPageRequest(request);
        Page<User> result = userService.search(request, paging);
        log.info("Search user total elements [" + result.getTotalElements() + "]");
        List<UserVO> reponseList = result.stream().map(User::getVO).toList();
        
        return responseList(reponseList, result);
    }

    @GetMapping(value = URI.VIEW + URI.ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity viewDataById(@PathVariable("id") Long id) throws Exception {
        log.info("Get detail user by [" + id + "]");
        
        User user = userService.findById(id);
        if (user != null) {
            return response(user.getVO());
        } else {
            throw new RestException("User Id [" + id + "] invaild");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = URI.ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getById(@PathVariable("id") Long id) throws Exception {
        log.info("Get detail user by [" + id + "]");
        User user = userService.findById(id);

        if (user != null) {
            return response(user.getVO());
        } else {
            throw new RestException("User Id [" + id + "] invaild");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@ModelAttribute UserRequest request) throws Exception {
        log.info("Create user");

        User user = userService.create(request);
        return response(user.getVO());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@ModelAttribute UserRequest request) throws Exception {
        Long id = request.getId();

        log.info("Update user id [{}]", id);
        User existed = userService.findById(id);

        if (existed == null) {
            throw new RestException("User id [" + id + "] invalid");
        }

        User result = userService.update(request, existed);
        return response(result.getVO());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = URI.ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteById(@PathVariable("id") Long id) throws Exception {
        log.info("Delete coupon id: [{}]", id);

        User existed = userService.findById(id);
        if (existed == null) {
            throw new RestException("USer id [" + id + "] invailid");
        }

        userService.deleteById(id);
        return responseMessage("Deleted user [" + id + "] sucessfully");
    }   
}