package com.de013.repository;

import org.springframework.stereotype.Repository;

import com.de013.dto.FilterVO;
import com.de013.model.Coupon;
import com.de013.model.User;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
    @Query("SELECT c FROM User c INNER JOIN c.roles r WHERE 1 = 1 "
        + " AND (:#{#p.role} IS NULL OR :#{#p.role} = r.name) ")
    public Page<User> search(@Param("p") FilterVO request, Pageable paging);

    Optional<User> findByUserName(String userName);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);
}
