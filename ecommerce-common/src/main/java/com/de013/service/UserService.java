package com.de013.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.de013.dto.CategoryRequest;
import com.de013.dto.FilterVO;
import com.de013.dto.ProfileRequest;
import com.de013.dto.UserRequest;
import com.de013.model.Category;
import com.de013.model.User;
import com.de013.repository.UserRepository;
import com.de013.utils.Utils;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(CouponService.class);

    @Autowired
    private UserRepository userRepository;

    public Page<User> search(FilterVO request, Pageable paging) {
        return userRepository.search(request, paging);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User create(UserRequest request) {
        User user = new User(request);
        this.save(user);

        return user;
    }

    public User update(UserRequest request, User existed) {
        log.debug("Update " + request);
        Utils.copyNonNullProperties(request, existed);
        existed = this.save(existed);

        return existed;
    }

    public User updateProfile(ProfileRequest request, User existed) {
        log.debug("Update " + request);
        Utils.copyNonNullProperties(request, existed);
        existed = this.save(existed);

        return existed;
    }

    public User save(User user) {
        user = userRepository.save(user);
        log.debug("save: " + user);
        
        return user;
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
