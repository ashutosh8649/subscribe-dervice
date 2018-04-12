package com.offershopper.subscribedatabaseservice.feignproxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.offershopper.subscribedatabaseservice.model.CouponAndFeedbackBean;
import com.offershopper.subscribedatabaseservice.model.OfferBean;
import com.offershopper.subscribedatabaseservice.model.VendorDetailsBean;

@FeignClient(name="user-database-service", url="http://10.151.61.106:9001")
public interface UserProxy {

  @GetMapping("/users/details/{id}")
  public VendorDetailsBean signinUser(@RequestParam String id);
 
}
