package com.dovecry.security;


import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dovecry.graphdb.data.IUserRepository;
import com.dovecry.graphdb.model.ModelUser;

@Service
public class UserServiceImp implements UserDetailsService {
	private final IUserRepository userRepository;
	
	public UserServiceImp(IUserRepository userRepository) {
		this.userRepository=userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ModelUser user = userRepository.findByUsername(username);
		if(user!=null) {
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority(user.getRole()));
			return new org.springframework.security.core.userdetails.User(
					user.getUsername(),
					user.getPassword(),
					authorities
					);
		}
		throw new UsernameNotFoundException("user not found...");
	}
}
