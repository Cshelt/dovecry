package com.dovecry.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dovecry.graphdb.data.IUserRepository;
import com.dovecry.graphdb.model.ModelUser;
import com.dovecry.security.RequestVerifier;

@RestController
@RequestMapping("/users")
public class UserController {
	  @Autowired 
	  ModelUser user;
	  
	  @Autowired
	  RequestVerifier requestVerifier;
	  
	  private IUserRepository userRepository;
	  private BCryptPasswordEncoder bCryptPasswordEncoder;

	    public UserController(IUserRepository applicationUserRepository,
	                          BCryptPasswordEncoder bCryptPasswordEncoder) {
	        this.userRepository = applicationUserRepository;
	        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	    }
	  @GetMapping
	  public @ResponseBody java.util.List<String> GetAllUsers() {
		  return userRepository.getAllUserNames();
	  }
	  @PostMapping("/register")
	  public void register(@RequestBody ModelUser user) {
		  user.setRole(user.getUsername());
		  user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		  userRepository.save(user);
	  }
	  
	  @GetMapping("/{username}")
	  public @ResponseBody String getUserInfo(@PathVariable String username,
			  HttpServletRequest httpServletRequest) {
		  if(requestVerifier.verifyUserForResource(httpServletRequest,username)) {
			  return "You're in";
		  }
		  return "You're out!";
	  }
  
}
