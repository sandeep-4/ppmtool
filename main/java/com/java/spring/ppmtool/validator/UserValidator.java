package com.java.spring.ppmtool.validator;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.java.spring.ppmtool.model.User;

@Component
public class UserValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		User user=(User) target;
		if(user.getPassword().length()<8) {
			errors.rejectValue("password", "length","password must be 8 charaters");
		}
		
		if(!user.getPassword().equals(user.getConfirmPassword())) {
			errors.rejectValue("password", "match","password doesnt match");

		}
		
	}

}
