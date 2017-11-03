package com.dovecry.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dovecry.graphdb.data.IUserRepository;
import com.dovecry.graphdb.model.User;

@Controller
@RequestMapping("/user")
public class UserController {
	  @Autowired 
	  User user;
	  
	  private IUserRepository userRepository;
	  
	  @Autowired
	  public UserController(IUserRepository userRepository) {
	    this.userRepository = userRepository;
	  }
	  
	  @RequestMapping(value="/users",method=GET)
		  public @ResponseBody Iterable<String> getAllUsers() {
		  	return userRepository.getAllUserNames();
	  	}
	  
	  @RequestMapping(value="/register", method=GET)
	  public String addUser() {
		return "register";
	  }
	  
	  @RequestMapping(value="/register", method=POST)
	  public String addUser(User user) {
		userRepository.save(user);
		return "redirect:/user/"+user.getUsername();
	  }
	  
	  @RequestMapping(value="/{username}",method=GET)
	  public String showUserProfile(@PathVariable String username) {
		  //User user = userRepository.findByUsername(username);
		  return "userconfirmation";
	  }
}
