package com.de013.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.de013.dto.FilterVO;
import com.de013.model.Category;
import com.de013.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    @Query("SELECT pd FROM Product pd WHERE 1 = 1 "
        + " AND ( :#{#p.name} = '' OR pd.name LIKE CONCAT('%', :#{#p.name}, '%') ) "
        + " AND ( :#{#p.fromPrice} <= pd.salePrice ) "
        + " AND ( :#{#p.toPrice} >= pd.salePrice ) "
        )
    public Page<Product> search(@Param("p") FilterVO request, Pageable paging);
    
    @Query("SELECT pd FROM Product pd "
        + " JOIN pd.categories c "
        + " WHERE 1 = 1 " 
        + " AND ( :#{#p.name} = '' OR pd.name LIKE CONCAT('%', :#{#p.name}, '%') ) "
        + " AND ( c IN :categories ) "
        + " AND ( :#{#p.fromPrice} <= pd.salePrice ) "
        + " AND ( :#{#p.toPrice} >= pd.salePrice ) "
        + " GROUP BY pd.id "
        + " HAVING COUNT(DISTINCT c) >= :categoriesSize ")
    public Page<Product> search(@Param("p") FilterVO request, Pageable paging, 
                                @Param("categories") List<Category> categories, 
                                @Param("categoriesSize") int categoriesSize);
}
