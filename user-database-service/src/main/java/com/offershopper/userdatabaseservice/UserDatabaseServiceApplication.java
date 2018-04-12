package com.offershopper.userdatabaseservice;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.xmlpull.v1.XmlPullParserException;

import brave.sampler.Sampler;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/*
 * File name:user-database-service
 * Author: Vishruty and Yashika
 * Date: 5-April-2018
 * Description: This service provides the functions to add user to database, retrieve user details from database or modify them.
*/

@EnableDiscoveryClient
@SpringBootApplication
@EnableSwagger2 
@EnableCircuitBreaker
@EnableHystrixDashboard
public class UserDatabaseServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserDatabaseServiceApplication.class, args);
	}
	
	@Bean 
	public Docket api() throws IOException, XmlPullParserException { 
	return new Docket(DocumentationType.SWAGGER_2); 
	} 
	   
	@Bean
       public Sampler defaultSampler()
       {
         return Sampler.ALWAYS_SAMPLE;
        }

}
