package com.de013.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.de013.dto.FilterVO;
import com.de013.model.ERole;
import com.de013.model.Role;

import io.lettuce.core.dynamic.annotation.Param;


@Repository
public interface RoleRepository  extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
    
    @Query("SELECT c FROM Role c WHERE 1 = 1")
    public Page<Role> search(@Param("p") FilterVO request, Pageable paging);
}
