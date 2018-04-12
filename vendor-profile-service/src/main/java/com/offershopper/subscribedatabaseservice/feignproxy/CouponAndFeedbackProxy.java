package com.offershopper.subscribedatabaseservice.feignproxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.offershopper.subscribedatabaseservice.model.CouponAndFeedbackBean;

@FeignClient(name="feedback-database-service", url="https://http://10.151.61.152:9006")
public interface CouponAndFeedbackProxy {

  @GetMapping("/os/getfeedback/{offerId}")
  public CouponAndFeedbackBean retrieveFeedback(@PathVariable String offerId);
}
