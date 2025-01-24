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
import org.springframework.web.bind.annotation.*;

import com.de013.dto.FilterVO;
import com.de013.dto.ReviewRequest;
import com.de013.dto.ReviewVO;
import com.de013.exception.RestException;
import com.de013.model.Paging;
import com.de013.model.Review;
import com.de013.service.ReviewService;
import com.de013.utils.URI;

@RestController
@RequestMapping(URI.V1 + URI.REVIEW)
public class ReviewController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    private ReviewService reviewService;

    @PostMapping(value = URI.LIST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listAll(@RequestBody FilterVO request) throws Exception {
        log.info("Search reviews");
        Pageable paging = new Paging().getPageRequest(request);
        Page<Review> result = reviewService.search(request, paging);
        log.info("Search reviews total elements [{}]", result.getTotalElements());
        List<ReviewVO> responseList = result.stream().map(Review::getVO).toList();

        return responseList(responseList, result);
    }

    @GetMapping(value = URI.VIEW + URI.ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity viewDataById(@PathVariable("id") Long id) throws Exception {
        log.info("Get detail review by [{}]", id);

        Review review = reviewService.findById(id);
        if (review != null) {
            return response(review.getVO());
        } else {
            throw new RestException("Review Id [" + id + "] invalid");
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody ReviewRequest request) throws Exception {
        log.info("Create review");

        Review review = reviewService.create(request);
        return response(review.getVO());
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@RequestBody ReviewRequest request) throws Exception {
        Long id = request.getId();

        log.info("Update review id [{}]", id);
        Review existed = reviewService.findById(id);

        if (existed == null) {
            throw new RestException("Review id [" + id + "] invalid");
        }

        Review result = reviewService.update(request, existed);
        return response(result.getVO());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = URI.ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteById(@PathVariable("id") Long id) throws Exception {
        log.info("Delete review id: [{}]", id);

        Review existed = reviewService.findById(id);
        if (existed == null) {
            throw new RestException("Review id [" + id + "] invalid");
        }

        reviewService.deleteById(id);
        return responseMessage("Deleted review [" + id + "] successfully");
    }
}
