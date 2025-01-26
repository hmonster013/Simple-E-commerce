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
import com.de013.dto.WishlistRequest;
import com.de013.dto.WishlistVO;
import com.de013.exception.RestException;
import com.de013.model.Paging;
import com.de013.model.Product;
import com.de013.model.Wishlist;
import com.de013.service.WishlistService;
import com.de013.utils.URI;

@RestController
@RequestMapping(URI.V1 + URI.WISHLIST)
public class WishlistController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WishlistController.class);

    @Autowired
    private WishlistService wishlistService;

    @PostMapping(value = URI.LIST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listAll(@RequestBody FilterVO request) throws Exception {
        log.info("Search wishlist");
        Pageable paging = new Paging().getPageRequest(request);
        Page<Wishlist> result = wishlistService.search(request, paging);
        log.info("Search wishlist total elements [{}]", result.getTotalElements());
        List<WishlistVO> responseList = result.stream().map(Wishlist::getVO).toList();

        return responseList(responseList, result);
    }

    @GetMapping(value = URI.VIEW + URI.ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity viewDataById(@PathVariable("id") Long id) throws Exception {
        log.info("Get detail wishlist by [{}]", id);

        Wishlist wishlist = wishlistService.findById(id);
        if (wishlist != null) {
            return response(wishlist.getVO());
        } else {
            throw new RestException("Wishlist Id [" + id + "] invalid");
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody WishlistRequest request) throws Exception {
        log.info("Add item to wishlist");

        Wishlist wishlist = wishlistService.create(request);
        return response(wishlist.getVO());
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@RequestBody WishlistRequest request) throws Exception {
        Long id = request.getId();

        log.info("Update wishlist id [{}]", id);
        Wishlist existed = wishlistService.findById(id);

        if (existed == null) {
            throw new RestException("Wishlist id [" + id + "] invalid");
        }

        Wishlist result = wishlistService.update(request, existed);
        return response(result.getVO());
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping(value = URI.ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteById(@PathVariable("id") Long id) throws Exception {
        log.info("Delete wishlist item id: [{}]", id);

        Wishlist existed = wishlistService.findById(id);
        if (existed == null) {
            throw new RestException("Wishlist id [" + id + "] invalid");
        }

        wishlistService.deleteById(id);
        return responseMessage("Deleted wishlist item [" + id + "] successfully");
    }
}
