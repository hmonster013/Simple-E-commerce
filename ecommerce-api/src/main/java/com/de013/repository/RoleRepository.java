package com.de013.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.de013.dto.FilterVO;
import com.de013.model.Role;
import com.de013.utils.JConstants.ERole;


@Repository
public interface RoleRepository  extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
    
    @Query("SELECT c FROM Role c WHERE 1 = 1")
    public Page<Role> search(@Param("p") FilterVO request, Pageable paging);
}
