package com.musinsa.watcher.web.dto;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class MinimumPriceProductDtoTest {

  @Test
  @DisplayName("역대 최저가 품목 object mapper 테스트")
  public void test() {
    //given
    Object productId = 1;
    Object productName = "name";
    Object brand = "brand";
    Object img = "url";
    Object maxPrice = BigInteger.valueOf(10000L);
    Object today_price = 7000;
    Object modifiedDate = Timestamp.from(Instant.now());
    MinimumPriceProductDto testDto = MinimumPriceProductDto
        .builder()
        .productId((int) productId)
        .productName((String) productName)
        .brand((String) brand)
        .maxPrice(((BigInteger) maxPrice).longValue())
        .today_price((Integer)today_price)
        .img((String) img)
        .modifiedDate((Timestamp) modifiedDate)
        .build();

    Object[] objects = new Object[]{productId, productName, brand, img, modifiedDate, today_price,
        maxPrice};
    List<Object[]> list = new ArrayList<>();
    list.add(objects);
    //when
    List<MinimumPriceProductDto> minimumPriceProductDtoList = MinimumPriceProductDto
        .objectsToDtoList(list);
    //then
    MinimumPriceProductDto minimumPriceProductDto = minimumPriceProductDtoList.get(0);
    assertEquals(testDto.getMaxPrice(), minimumPriceProductDto.getMaxPrice());
    assertEquals(testDto.getToday_price(), minimumPriceProductDto.getToday_price());
    assertEquals(testDto.getBrand(), minimumPriceProductDto.getBrand());
    assertEquals(testDto.getImg(), minimumPriceProductDto.getImg());
    assertEquals(testDto.getProductId(), minimumPriceProductDto.getProductId());
    assertEquals(testDto.getModifiedDate(), minimumPriceProductDto.getModifiedDate());
    assertEquals(testDto.getProductName(), minimumPriceProductDto.getProductName());
  }
}