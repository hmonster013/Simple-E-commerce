package com.de013.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.de013.dto.CategoryRequest;
import com.de013.dto.FilterVO;
import com.de013.dto.RoleRequest;
import com.de013.model.Category;
import com.de013.model.Role;
import com.de013.repository.RoleRepository;
import com.de013.utils.Utils;

@Service
public class RoleService {
    private static final Logger log = LoggerFactory.getLogger(CouponService.class);

    @Autowired
    private RoleRepository roleRepository;

    public Page<Role> search(FilterVO request, Pageable paging) {
        return roleRepository.search(request, paging);
    }

    public Role findById(Integer id) {
        return roleRepository.findById(id).orElse(null);
    }

    public Role create(RoleRequest request) {
        Role role = new Role(request);
        this.save(role);

        return role;
    }

    public Role update(RoleRequest request, Role existed) {
        log.debug("Update " + request);
        Utils.copyNonNullProperties(request, existed);
        existed = this.save(existed);

        return existed;
    }

    public Role save(Role role) {
        role = roleRepository.save(role);
        log.debug("save: " + role);
        
        return role;
    }

    public void deleteById(Integer id) {
        roleRepository.deleteById(id);
    }
}
