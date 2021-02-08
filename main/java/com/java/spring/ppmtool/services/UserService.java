package com.java.spring.ppmtool.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.java.spring.ppmtool.exception.UsernameException;
import com.java.spring.ppmtool.model.User;
import com.java.spring.ppmtool.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	BCryptPasswordEncoder bcrypt;
	
	
	public User saveUser(User user) {

		try {	
			user.setPassword(bcrypt.encode(user.getPassword()));
			user.setUsername(user.getUsername());
		user.setConfirmPassword(bcrypt.encode(user.getConfirmPassword()));
			return userRepo.save(user);	
		
		} catch (Exception e) {
			throw new UsernameException("Dublicate username  "+user.getUsername());
		}
	
			
		
		}
		
	
	
	public List<User> getAllUsers(){	
		return userRepo.findAll();
	}
	
	public User findByUsername(String username) {
		return userRepo.findByUsername(username);
	}
	
	public User login(String username,String password) {
		return userRepo.findByUsernameAndPassword(username, password);
	}
	
	public void delete(long id) {
		userRepo.deleteById(id);
	}
	
	
}
