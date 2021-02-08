package com.java.spring.ppmtool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{

	
	@ExceptionHandler
	public ResponseEntity<Object> handleProjectIDException(ProjectIdException ex,WebRequest request){
		ProjectIdException exceptionResponse=new ProjectIdException(ex.getMessage());
		return new ResponseEntity<Object>(exceptionResponse,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<Object> handleProjectException(ProjectNotFoundException ex,WebRequest request){
		ProjectNotFoundException exceptionRes=new ProjectNotFoundException(ex.getMessage());
		return new ResponseEntity<Object>(exceptionRes,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<Object> handleDublicateUsername(UsernameException ex,WebRequest request){
		UsernameException expectionUser=new UsernameException(ex.getMessage());
		return ResponseEntity.ok().body(expectionUser);
		
	}
}
