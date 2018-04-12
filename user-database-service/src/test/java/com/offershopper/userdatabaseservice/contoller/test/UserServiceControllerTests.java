package com.offershopper.userdatabaseservice.contoller.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.offershopper.userdatabaseservice.model.AddressBean;
import com.offershopper.userdatabaseservice.controller.UserServiceController;
import com.offershopper.userdatabaseservice.model.UserBean;
import com.offershopper.userdatabaseservice.repository.UserRepository;

@RunWith(SpringRunner.class)
@WebMvcTest
public class UserServiceControllerTests {
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	UserRepository userRepository;
	
	List<UserBean> userList = new ArrayList<UserBean>();
	//List<AddressBean> homeAddressList = new ArrayList<AddressBean>();
	//List<AddressBean> shopAddressList = new ArrayList<AddressBean>();
	  UserBean userBean;
	  AddressBean homeAddress;
	  AddressBean shopAddress;
	  
	  @Before
	  public void setup() 
	  {
		  homeAddress=new AddressBean("d","e","f",155501);
		 // homeAddressList.add(homeAddress);
		  shopAddress=new AddressBean("d","e","f",155501);
		  //shopAddressList.add(shopAddress);
		  userBean=new UserBean("Riya","a","pwd1","9336048823","vish@gmail.com","1/4/77",homeAddress,"f",0,0,shopAddress,"56456","gjkshdbh");
		  userList.add(userBean);
	  }
	
	  @After
	  public void cleanUp() {
	    userBean = null;
	    homeAddress=null;
	    shopAddress=null;
	    userList = null;
	  }

	@Test	
	 public void getSingleUserById() throws Exception {
		    //mocking the findById method
		    Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(userBean));
		    // mock request
		    RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/details/game@gmail.com").accept(MediaType.APPLICATION_JSON);
		 // sending mock request at the url
		    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		  //json format that we expect
		    String exampleString="{\"id\":\"5ac77f17efce7b2cc78428ed\","
		    		+ "\"firstName\":\"mom\","
		    		+ "\"lastName\":\"a\","
		    		+ "\"password\":\"pwd1\","
		    		+ "\"mobileNo\":\"93360567y548823\","
		    		+ "\"email\":\"game@gmail.com\","
		    		+ "\"dob\":\"1/4/77\","
		    		+ "\"address\":{\"street\":\"d\","
		    		+ "\"city\":\"e\","
		    		+ "\"state\":\"f\","
		    		+ "\"zipCode\":155501},"
		    		+ "\"gender\":\"f\","
		    		+ "\"spinCount\":0,"
		    		+ "\"creditPoint\":0,"
		    		+ "\"shopAddress\":{\"street\":\"dsdf\","
		    		+ "\"city\":\"esgf\","
		    		+ "\"state\":\"fgfa\","
		    		+ "\"zipCode\":155501},"
		    		+ "\"vendorMobileNo\":\"564556746\","
		    		+ "\"timestamp\":\"dgjkshdbh\"}";
		    assertEquals(exampleString, result.getResponse().getContentAsString());
		  }
		  
	//negative test cases
	
	@Test
	 public void retrieveUserByIdNeg() throws Exception {
	   //mocking the findById method
		
		Optional<UserBean> temp_user = userRepository.findById(userBean.getId());
	   Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(temp_user);
	   // mock request
	   RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/details/game@gmail.com").accept(MediaType.APPLICATION_JSON);
	// sending mock request at the url
	   MvcResult result = mockMvc.perform(requestBuilder).andReturn();
	 //json format that we expect
	   String expected="{\"id\":\"5ac77f17efce7b2cc78428ed\","
	   		+ "\"firstName\":\"mom\","
	   		+ "\"lastName\":\"a\","
	   		+ "\"password\":\"pwd1\",\"mobileNo\":\"93360567y548823\",\"email\":\"game@gmail.com\",\"dob\":\"1/4/77\",\"address\":{\"street\":\"d\",\"city\":\"e\",\"state\":\"f\",\"zipCode\":155501},\"gender\":\"f\",\"spinCount\":0,\"creditPoint\":0,\"shopAddress\":{\"street\":\"dsdf\",\"city\":\"esgf\",\"state\":\"fgfa\",\"zipCode\":155501},\"vendorMobileNo\":\"564556746\",\"timestamp\":\"dgjkshdbh\"}";
	  // assertEquals(expected, result.getResponse().getContentAsString());
	   assertNotEquals(expected, result.getResponse().getContentAsString());
	 }
	
	@Test
	public void addUserTest() {
		
		String exampleString="{\"id\":\"5ac77f17efce7b2cc78428ed\",\"firstName\":\"mom\",\"lastName\":\"a\",\"password\":\"pwd1\",\"mobileNo\":\"93360567y548823\",\"email\":\"game@gmail.com\",\"dob\":\"1/4/77\",\"address\":{\"street\":\"d\",\"city\":\"e\",\"state\":\"f\",\"zipCode\":155501},\"gender\":\"f\",\"spinCount\":0,\"creditPoint\":0,\"shopAddress\":{\"street\":\"dsdf\",\"city\":\"esgf\",\"state\":\"fgfa\",\"zipCode\":155501},\"vendorMobileNo\":\"564556746\",\"timestamp\":\"dgjkshdbh\"}";
		try
		{
			 Mockito.when(userRepository.save(Mockito.any(UserBean.class))).thenReturn(userBean);
			 RequestBuilder requestBuilder = MockMvcRequestBuilders
						.post("/rest/users/add")
						.accept(MediaType.APPLICATION_JSON).content(exampleString)
						.contentType(MediaType.APPLICATION_JSON);
			  MvcResult result;
	          result = mockMvc.perform(requestBuilder).andReturn();
	          MockHttpServletResponse response = result.getResponse();
	          assertEquals(HttpStatus.OK.value(), response.getStatus());
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
	}
	
}
