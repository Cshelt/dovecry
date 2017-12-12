package com.dovecry.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.dovecry.graphdb.data.IUserRepository;
import com.dovecry.graphdb.model.ModelUser;
import com.dovecry.spring.DovecryWebInitializer;
import com.dovecry.spring.GraphDbConfiguration;
import com.dovecry.spring.RootConfiguration;
import com.dovecry.spring.SecurityConfiguration;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {GraphDbConfiguration.class})
public class JwtTest {
	@Autowired
	IUserRepository userRepository;
	
	BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
	
	@Test
	public void testRegisterAndLogin() {
	ModelUser user = new ModelUser();
	user.setUsername("cshelton");
	user.setPassword("password");
	Gson gson = new Gson();
	UserController userController = new UserController(userRepository, bCryptPasswordEncoder);
	MockMvc mockMvc = standaloneSetup(userController).build();
	try {
		String jsonString=gson.toJson(user);
		MvcResult mvcResult = mockMvc.perform(post("/users/register").contentType(MediaType.APPLICATION_JSON).
				content(jsonString)).andExpect(status().isOk()).andDo(print()).andReturn();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	@Test 
	public void testLogin() {
		ModelUser user1 = new ModelUser();
		user1.setUsername("cshelton");
		user1.setPassword("password");
		user1.setRole("cshelton");
		Gson gson1 = new Gson();
		UserController userController = new UserController(userRepository, bCryptPasswordEncoder);
		MockMvc mockMvc = standaloneSetup(userController).build();
		String userString = gson1.toJson(user1);
		try {
			MvcResult mvcResult2 = mockMvc.perform(post("/users/login").contentType(MediaType.APPLICATION_JSON).
			content(userString)).andExpect(status().isOk()).andDo(print()).andReturn();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
