package com.de013.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.de013.dto.CategoryRequest;
import com.de013.dto.FilterVO;
import com.de013.dto.WishlistRequest;
import com.de013.model.Category;
import com.de013.model.Wishlist;
import com.de013.repository.WishlistRepository;
import com.de013.utils.Utils;

@Service
public class WishlistService {
    private static final Logger log = LoggerFactory.getLogger(CouponService.class);

    @Autowired
    private WishlistRepository wishlistRepository;

    public Page<Wishlist> search(FilterVO request, Pageable paging) {
        return wishlistRepository.search(request, paging);
    }

    public Wishlist findById(Long id) {
        return wishlistRepository.findById(id).orElse(null);
    }

    public Wishlist create(WishlistRequest request) {
        Wishlist wishlist = new Wishlist(request);
        this.save(wishlist);

        return wishlist;
    }

    public Wishlist update(WishlistRequest request, Wishlist existed) {
        log.debug("Update " + request);
        Utils.copyNonNullProperties(request, existed);
        existed = this.save(existed);

        return existed;
    }

    public Wishlist save(Wishlist wishlist) {
        wishlist = wishlistRepository.save(wishlist);
        log.debug("save: " + wishlist);
        
        return wishlist;
    }

    public void deleteById(Long id) {
        wishlistRepository.deleteById(id);
    }
}
