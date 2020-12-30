package com.musinsa.watcher.domain.price;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PriceRepository extends JpaRepository<Price, Long> {

  @Query("SELECT p FROM Price p WHERE p.product.productId  = ?1")
  Page<Price> findByProductId(int productId, Pageable pageable);

}
