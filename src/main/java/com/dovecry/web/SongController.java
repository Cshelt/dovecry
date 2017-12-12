package com.dovecry.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dovecry.graphdb.data.ISongRepository;
import com.dovecry.graphdb.model.ModelSong;
import com.dovecry.security.RequestVerifier;

@RestController
@RequestMapping("/songs")
public class SongController {
	
	  @Autowired
	  RequestVerifier requestVerifier;
	  
	  @Autowired
	  ModelSong song;
	  
	  private ISongRepository songRepository;
	
	  public SongController(ISongRepository songRepository) {
		this.songRepository=songRepository;
	}
	  
	 @PostMapping("/{username}")
	 public @ResponseBody String addUserSong(@RequestBody ModelSong song, @PathVariable String username,
			  								  HttpServletRequest httpServletRequest) {
		  if(requestVerifier.verifyUserForResource(httpServletRequest,username)) {
			  return "You're in "+username+ " " +song.getSongname()+ " "+song.getNeedstracks().toString();
		  }
		  return "You're out!";
	  }
	 
	 @GetMapping("/{username}")
	 public @ResponseBody String getUserSongs(@PathVariable String username,
			  HttpServletRequest httpServletRequest) {
		  if(requestVerifier.verifyUserForResource(httpServletRequest,username)) {
			  return "You're in";
		  }
		  return "You're out!";
	  }
}
