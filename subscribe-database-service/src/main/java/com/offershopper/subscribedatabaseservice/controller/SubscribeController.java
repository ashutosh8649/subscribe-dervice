/*
Name: Subscription Module
Author:Ashutosh Kumar Mishra, Dinesh Verma
Date: 6th Apr,2018
Discription: This class performs CRUD operations on the repository
Req. Files/Databases: SubscribeBean.class, MessageSender.class
*/



package com.offershopper.subscribedatabaseservice.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.offershopper.subscribedatabaseservice.database.SubscribeRepository;
import com.offershopper.subscribedatabaseservice.model.SubscribeBean;
import com.offershopper.subscribedatabaseservice.service.MessageSender;

@RestController
@RequestMapping("/subscribe")
public class SubscribeController {

	
	@Autowired
	private MessageSender sendMessageToRabbit;

	private SubscribeRepository subscribeRepository;
	
    public SubscribeController(SubscribeRepository subscribeRepository) {
        this.subscribeRepository = subscribeRepository;
    }

    
	@HystrixCommand(fallbackMethod="fallback")
    @GetMapping(value="/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SubscribeBean> getAll() {
		String msg="getting subscription data";
		sendMessageToRabbit.produceMsg(msg);
        return subscribeRepository.findAll();
    }

	public List<SubscribeBean> fallback() {

		return Collections.emptyList();
	}
    
	@PostMapping(value="/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatus> addSubscribeBean(@RequestBody SubscribeBean subscribeBean) {
		Optional<SubscribeBean> option = subscribeRepository.findById(subscribeBean.getUserId());

		if(option.isPresent()) {
		      System.out.println("this product is already existing");
		      return new ResponseEntity<>(HttpStatus.CONFLICT);

		}
		subscribeRepository.insert(subscribeBean);
	  return new ResponseEntity<>(HttpStatus.OK);


	}
	
	@DeleteMapping("/del/{userId}")
	public ResponseEntity<HttpStatus> delete(@PathVariable("userId") String userId) {
		subscribeRepository.deleteById(userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	

}
