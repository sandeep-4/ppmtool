package com.java.spring.ppmtool.exception;

public class ExceptionHandelers {

	String projectIdentifier;

	public ExceptionHandelers(String projectIdentifier) {
		super();
		this.projectIdentifier = projectIdentifier;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}
	
	
}
