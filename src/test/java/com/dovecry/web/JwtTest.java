package com.dovecry.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.dovecry.graphdb.data.IUserRepository;
import com.dovecry.graphdb.model.ModelUser;
import com.google.gson.Gson;

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
		userRepository.deleteAll();
		String jsonString=gson.toJson(user);
		MvcResult mvcResult = mockMvc.perform(post("users/register").contentType(MediaType.APPLICATION_JSON).
				content(jsonString)).andExpect(status().isOk()).andDo(print()).andReturn();
		System.out.println(mvcResult.getResponse().getContentAsString());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
}
