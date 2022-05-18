package com.iquinto.workingstudent.security;

import com.iquinto.workingstudent.model.User;
import com.iquinto.workingstudent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class AuthUserDetailsService implements UserDetailsService {

	@Autowired
	UserService userService;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user;
		try {
			user = userService.findByUsername(username);
			return AuthUserDetails.build(user);
		}catch (UsernameNotFoundException e){
			throw new UsernameNotFoundException("User Not Found with username: " + username);
		}
	}

}
