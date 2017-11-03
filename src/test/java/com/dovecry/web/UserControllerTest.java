package com.dovecry.web;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import com.dovecry.graphdb.data.IUserRepository;
import com.dovecry.web.UserController;

public class UserControllerTest {
@Autowired
IUserRepository userRepository;
@Test
public void testControllerPage() {
	UserController userController = new UserController(userRepository);
	MockMvc mockMvc = standaloneSetup(userController).build();
	try {
		mockMvc.perform(get("/user"));
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
