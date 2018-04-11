package com.offershopper.subscribedatabaseservice;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.offershopper.subscribedatabaseservice.database.SubscribeRepository;
import com.offershopper.subscribedatabaseservice.model.SubscribeBean;
import com.offershopper.subscribedatabaseservice.service.MessageSender;

@RunWith(SpringRunner.class)
@WebMvcTest
public class SubscribeResourceTest {

	SubscribeBean getBean=new SubscribeBean("2","10","shirt");
	SubscribeBean addBean=new SubscribeBean("4","2","tshirt");
	List<SubscribeBean> subscribeBeans=new ArrayList<SubscribeBean>();

	  @Autowired
	  MockMvc mockMvc;

	  @MockBean
	  SubscribeBean subscribeBean;
	  
	  @MockBean
	  SubscribeRepository subscribeRepository;
	  
	  @MockBean
	  MessageSender messageSender;

	    
	  @Test
	  public void testGetAll() {
		  
		  subscribeBeans.add(getBean);
	     
	      Mockito.when(subscribeRepository.findAll()).thenReturn(subscribeBeans);

	      try {
	    	  
	    	  MvcResult result=mockMvc.perform(get("/subscribe/all")
	    			.accept(MediaType.APPLICATION_JSON))
	                	.andExpect(status().isOk())
	                	.andReturn();
	    	  System.out.println("\n" + result.getResponse().getContentAsString() + "\nHello\n");
	    	  
	    	  String expected = "[{\"userId\":\"2\",\"vendorId\":\"10\",\"category\":\"shirt\"}]";
	    	  String NegativeExpected = "[{\"userId\":\"3\",\"vendorId\":\"1\",\"category\":\"jeans\"}]";
	    	  
	    	  JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	    	  JSONAssert.assertNotEquals(NegativeExpected, result.getResponse().getContentAsString(), false);

	    	
	      	}catch(Exception e) {System.out.println(e.getMessage());}
	      
	      Mockito.verify(subscribeRepository).findAll();
	  }
	  
	  @Test
	  public void testAddSubscribeBean() throws Exception {
	     
		  String exampleBean="{\"userId\":\"4\",\"vendorId\":\"2\",\"category\":\"tshirts\"}";
      Mockito.when(subscribeRepository.findById(Mockito.any(String.class))).thenReturn(Optional.empty());
	    Mockito.when(subscribeRepository.insert(Mockito.any(SubscribeBean.class))).thenReturn(addBean);
			RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/subscribe/add")
												.accept(MediaType.APPLICATION_JSON)
												.content(exampleBean)
												.contentType(MediaType.APPLICATION_JSON);
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = result.getResponse();
		    //assertThat(result.getResponse().getStatus()).isEqualTo(200);


			assertEquals(HttpStatus.OK.value(), response.getStatus());
/*			assertNotEquals(HttpStatus.OK.value(), response.getStatus());
*/
	      }
	  @Test
	   public void testDelete() throws Exception {
      Mockito.when(subscribeRepository.deleteById(Mockito.anyString())).thenReturn(Optional.empty());
      MvcResult result=mockMvc.perform(delete("/subscribe/all")
          .accept(MediaType.APPLICATION_JSON))
                  .andExpect(status().isOk())
                  .andReturn();

	  }
	  
	  

	  		

}

