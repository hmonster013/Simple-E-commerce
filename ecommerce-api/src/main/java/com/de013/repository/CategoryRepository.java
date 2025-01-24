package com.de013.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.de013.dto.FilterVO;
import com.de013.model.Category;

import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> { 

    @Query("SELECT c FROM Category c WHERE 1 = 1")
    public Page<Category> search(@Param("p") FilterVO request, Pageable paging);
}
