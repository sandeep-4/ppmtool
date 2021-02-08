package com.java.spring.ppmtool.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.spring.ppmtool.model.User;
import com.java.spring.ppmtool.payload.JWTLoginResponse;
import com.java.spring.ppmtool.payload.LoginRequest;
import com.java.spring.ppmtool.security.JwtTokenProvider;
import com.java.spring.ppmtool.services.CommonService;
import com.java.spring.ppmtool.services.UserService;
import com.java.spring.ppmtool.validator.UserValidator;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	CommonService errorService;
	

	@Autowired
	UserValidator userValidator;
	
//	@Autowired
//	JWTLoginResponse jwtLoginResponse;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	AuthenticationManager authManager;
	
	@PostMapping("/register")
	public ResponseEntity<?> saveUser(@Valid @RequestBody
			User user,BindingResult result){
		userValidator.validate(user, result);

		
		ResponseEntity<?> mapError=errorService.errorMapping(result);
		if(mapError!=null) {
			return mapError;
		}
		
		User users=userService.saveUser(user);
		return ResponseEntity.ok().body(users);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest,BindingResult result){
		ResponseEntity<?> mapError=errorService.errorMapping(result);
		if(mapError!=null) {
			return mapError;
		}
	Authentication authentication=authManager.authenticate(
			new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
			);
	SecurityContextHolder.getContext().setAuthentication(authentication);
	String jwt="Bearer "+jwtTokenProvider.generateToken(authentication);
		
		
		return ResponseEntity.ok(new JWTLoginResponse(true, jwt));
	}

}
