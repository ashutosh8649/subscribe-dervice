package com.offershopper.subscribedatabaseservice.feignproxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.offershopper.subscribedatabaseservice.model.VendorDetailsBean;

@FeignClient(name="zuul-gateway")
public interface VendorProfileProxy {

  @GetMapping("/user-database-service/user/{id}")
  public VendorDetailsBean signinUser(@RequestParam String id);
  

  
  
}
