package com.offershopper.subscribedatabaseservice.controller;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.offershopper.subscribedatabaseservice.feignproxy.CouponAndFeedbackProxy;
import com.offershopper.subscribedatabaseservice.feignproxy.OfferProxy;
import com.offershopper.subscribedatabaseservice.feignproxy.UserProxy;
import com.offershopper.subscribedatabaseservice.model.AddressBean;
import com.offershopper.subscribedatabaseservice.model.CouponAndFeedbackBean;
import com.offershopper.subscribedatabaseservice.model.OfferBean;
import com.offershopper.subscribedatabaseservice.model.OffersAndFeedbackBean;
import com.offershopper.subscribedatabaseservice.model.VendorDetailsBean;
import com.offershopper.subscribedatabaseservice.model.VendorProfileBean;

@RestController
@EnableFeignClients("com.offershopper.subscribedatabaseservice.controller")
public class VendorProfileController {

  //http://localhost:4501/vendor-profile/paper@gmail.com
  @Autowired
  private UserProxy userproxy;
  private OfferProxy offerProxy;
  private CouponAndFeedbackProxy couponAndFeedbackProxy;
  public VendorProfileBean vendorProfileBean;
  private OffersAndFeedbackBean offersAndFeedbackSingleBean;
  private List<OffersAndFeedbackBean> offersAndFeedbackBeans;
  
  //finds profile of vendor by id
  @GetMapping("/vendor-profile/{id}")
  public VendorProfileBean loginUser(@PathVariable String id) {
    VendorDetailsBean vendorDetailsBean = userproxy.signinUser(id); 
    List<OfferBean> offerBeans=offerProxy.getOffers();
    Iterator<OfferBean> it=offerBeans.iterator();
    
    while(it.hasNext()) {
      if(it.next().getUserId().equals(id)) {
        String offerId=it.next().getOfferId();
       
        CouponAndFeedbackBean couponAndFeedbackBean=couponAndFeedbackProxy.retrieveFeedback(offerId);
        
       offersAndFeedbackSingleBean = new OffersAndFeedbackBean(offerId, it.next().getOfferTitle(), it.next().getOfferValidity(),
          it.next().getDateOfAnnouncement(), it.next().getOfferDescription(), it.next().getOriginalPrice(),it.next().getDiscount(),
          it.next().getOfferRating(),it.next().getOfferCategory(), it.next().getOfferTerms(),couponAndFeedbackBean.getCouponId(),couponAndFeedbackBean.getFeedback());
      }
      offersAndFeedbackBeans.add(offersAndFeedbackSingleBean);
    }
    
    vendorProfileBean= new VendorProfileBean(vendorDetailsBean.getId(), vendorDetailsBean.getFirstName(),vendorDetailsBean.getLastName(), vendorDetailsBean.getEmail(),
        vendorDetailsBean.getShopAddress(), vendorDetailsBean.getVendorMobileNo(),offersAndFeedbackBeans);    
    
    return vendorProfileBean;
  
 }
}
