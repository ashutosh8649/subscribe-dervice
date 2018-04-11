package com.offershopper.subscribedatabaseservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.offershopper.subscribedatabaseservice.feignproxy.VendorProfileProxy;
import com.offershopper.subscribedatabaseservice.model.VendorDetailsBean;

@RestController
@EnableFeignClients("com.offershopper.subscribedatabaseservice.controller")
public class VendorProfileController {

  @Autowired
  private VendorProfileProxy proxy;
  
  //finds profile of vendor by id
  @GetMapping("/vendor-profile/{id}")
  public VendorDetailsBean loginUser(@PathVariable String id) {
    VendorDetailsBean vendorProfileInfo = proxy.signinUser(id);
    return vendorProfileInfo;
 
  }
  

}
