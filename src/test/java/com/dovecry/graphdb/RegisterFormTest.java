package com.dovecry.graphdb;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import com.dovecry.graphdb.data.IUserRepository;
import com.dovecry.graphdb.model.User;
import com.dovecry.web.UserController;

public class RegisterFormTest {
@Test
public void shouldRegister() throws Exception{
	IUserRepository mockRepository = mock(IUserRepository.class);
	User unsavedUser = new User();
	unsavedUser.setUsername("testuser");
	unsavedUser.setEmail("testuser@email.com");
	unsavedUser.setPassword("password");
	
	User savedUser = new User();
	savedUser.setUsername("testuser");
	savedUser.setEmail("testuser@email.com");
	savedUser.setPassword("password");
	
	when(mockRepository.save(unsavedUser)).thenReturn(savedUser);
	
	UserController userController = 
			new UserController(mockRepository);
	MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	
	mockMvc.perform(post("/user/register")
			.param("username", "testuser")
			.param("email", "testuser@email")
			.param("password", "password"))
			.andExpect(redirectedUrl("user/testuser"));
	
	verify(mockRepository,atLeastOnce()).save(unsavedUser);
}
}
