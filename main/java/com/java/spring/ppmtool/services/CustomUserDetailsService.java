package com.java.spring.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.java.spring.ppmtool.model.User;
import com.java.spring.ppmtool.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user=userRepo.findByUsername(username);
		if(user==null) {
			throw new UsernameNotFoundException("not valid username");
		}
		return user;
	}
	
	@Transactional
	public User loadUserById(long id) {
		User user=userRepo.findById(id);
		if(user==null) {
			throw new UsernameNotFoundException("not valid username");
		}
		return user;
	}

}
