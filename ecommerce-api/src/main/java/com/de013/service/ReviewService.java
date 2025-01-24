package com.de013.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.de013.dto.FilterVO;
import com.de013.dto.ReviewRequest;
import com.de013.model.Category;
import com.de013.model.Review;
import com.de013.repository.ReviewRepository;
import com.de013.utils.Utils;

@Service
public class ReviewService {
    private static final Logger log = LoggerFactory.getLogger(CouponService.class);

    @Autowired
    private ReviewRepository reviewRepository;

    public Page<Review> search(FilterVO request, Pageable paging) {
        return reviewRepository.search(request, paging);
    }

    public Review findById(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }

    public Review create(ReviewRequest request) {
        Review review = new Review(request);
        this.save(review);

        return review;
    }

    public Review update(ReviewRequest request, Review existed) {
        log.debug("Update " + request);
        Utils.copyNonNullProperties(request, existed);
        existed = this.save(existed);

        return existed;
    }

    public Review save(Review review) {
        review = reviewRepository.save(review);
        log.debug("save: " + review);
        
        return review;
    }

    public void deleteById(Long id) {
        reviewRepository.deleteById(id);
    }
}
