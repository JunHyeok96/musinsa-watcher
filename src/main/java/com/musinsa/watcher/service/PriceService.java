package com.musinsa.watcher.service;

import com.musinsa.watcher.domain.price.Price;
import com.musinsa.watcher.domain.price.slave.PriceSlaveQueryRepository;
import com.musinsa.watcher.domain.price.slave.PriceSlaveRepository;
import com.musinsa.watcher.domain.product.Category;
import com.musinsa.watcher.web.dto.DiscountedProductDto;
import com.musinsa.watcher.web.dto.MinimumPriceProductDto;
import com.musinsa.watcher.web.dto.PriceResponseDto;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PriceService {

  private final PriceSlaveRepository priceSlaveRepository;
  private final PriceSlaveQueryRepository priceSlaveQueryRepository;
  private final CacheService cacheService;

  public Page<PriceResponseDto> findByProductId(int productId, Pageable pageable) {
    Page<Price> page = priceSlaveRepository.findByProductId(productId, pageable);
    return new PageImpl<>(page.getContent()
        .stream()
        .map(PriceResponseDto::new)
        .collect(Collectors.toList()), pageable, page.getTotalElements());
  }

  @Cacheable(value = "productCache", key = "'distcount'+#category+#pageable.pageNumber+#pageable.pageSize+#sort")
  public Page<DiscountedProductDto> findDiscountedProduct(Category category, Pageable pageable,
      String sort) {
    return priceSlaveQueryRepository
        .findTodayDiscountProducts(category, cacheService.getLastUpdatedDate(), pageable, sort);
  }

  @Cacheable(value = "productCache", key = "'distcount list'")
  public Map<String, Integer> countDiscountProductEachCategory() {
    return priceSlaveQueryRepository
        .countDiscountProductEachCategory(cacheService.getLastUpdatedDate());
  }

  @Cacheable(value = "productCache", key = "'minimum'+#category+#pageable.pageNumber+#pageable.pageSize+#sort")
  public Page<MinimumPriceProductDto> findMinimumPriceProduct(Category category, Pageable pageable,
      String sort) {
    return priceSlaveQueryRepository
        .findTodayMinimumPriceProducts(category, cacheService.getLastUpdatedDate(), pageable, sort);
  }

  @Cacheable(value = "productCache", key = "'minimum price list'")
  public Map<String, Integer> countMinimumPriceProductEachCategory() {
    return priceSlaveQueryRepository
        .countMinimumPriceProductEachCategory(cacheService.getLastUpdatedDate());
  }
}
