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
import com.de013.dto.RoleRequest;
import com.de013.dto.RoleVO;
import com.de013.exception.RestException;
import com.de013.model.Coupon;
import com.de013.model.Paging;
import com.de013.model.Product;
import com.de013.model.Role;
import com.de013.service.CouponService;
import com.de013.service.RoleService;
import com.de013.utils.URI;

@RestController
@RequestMapping(URI.V1 + URI.ROLE)
public class RoleController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(CouponController.class);

    @Autowired
    private RoleService roleService;

    @PostMapping(value = URI.LIST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listAll(@RequestBody FilterVO request) throws Exception {
        log.info("Search role");
        Pageable paging = new Paging().getPageRequest(request);
        Page<Role> result = roleService.search(request, paging);
        log.info("Search role total elements [" + result.getTotalElements() + "]");
        List<RoleVO> reponseList = result.stream().map(Role::getVO).toList();
        
        return responseList(reponseList, result);
    }

    @GetMapping(value = URI.VIEW + URI.ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity viewDataById(@PathVariable("id") Integer id) throws Exception {
        log.info("Get detail role by [" + id + "]");
        
        Role role = roleService.findById(id);
        if (role != null) {
            return response(role.getVO());
        } else {
            throw new RestException("Role Id [" + id + "] invaild");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = URI.ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getById(@PathVariable("id") Integer id) throws Exception {
        log.info("Get detail role by [" + id + "]");
        Role role = roleService.findById(id);

        if (role != null) {
            return response(role.getVO());
        } else {
            throw new RestException("Role Id [" + id + "] invaild");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@ModelAttribute RoleRequest request) throws Exception {
        log.info("Create role");

        Role role = roleService.create(request);
        return response(role.getVO());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@ModelAttribute RoleRequest request) throws Exception {
        Integer id = request.getId();

        log.info("Update role id [{}]", id);
        Role existed = roleService.findById(id);

        if (existed == null) {
            throw new RestException("Role id [" + id + "] invalid");
        }

        Role result = roleService.update(request, existed);
        return response(result.getVO());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = URI.ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteById(@PathVariable("id") Integer id) throws Exception {
        log.info("Delete role id: [{}]", id);

        Role existed = roleService.findById(id);
        if (existed == null) {
            throw new RestException("Role id [" + id + "] invailid");
        }

        roleService.deleteById(id);
        return responseMessage("Deleted role [" + id + "] sucessfully");
    }
}
