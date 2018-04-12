package com.offershopper.subscribedatabaseservice.feignproxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.offershopper.subscribedatabaseservice.model.OfferBean;
@FeignClient(name="offer-database-service", url="http://10.151.61.121:9002")
public interface OfferProxy {
  
  @GetMapping("/offers")
  public List<OfferBean> getOffers(); 
}
