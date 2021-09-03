package com.ensemble.entreprendre.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ensemble.entreprendre.service.IUserService;

@Service
@Transactional
public class UserServiceImpl implements IUserService, UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
		UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(username).password(username)
				.authorities(grantedAuthorities).build();
		return userDetails;
	}



}
