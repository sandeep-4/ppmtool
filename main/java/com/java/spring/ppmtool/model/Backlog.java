package com.java.spring.ppmtool.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Backlog {

	public Backlog() {
		super();
	}

	public Backlog(long id, int pTSequence, String projectIdentifier) {
		super();
		this.id = id;
		PTSequence = pTSequence;
		this.projectIdentifier = projectIdentifier;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	long id;
	int PTSequence=0;
	String projectIdentifier;
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="project_id",nullable=false)
	@JsonIgnore   //this is ti discontinue the infinate loop from project to backlog to projevt to backlohg and vice versa
	Project project;
	@OneToMany(cascade=CascadeType.REFRESH,fetch=FetchType.EAGER,mappedBy="backlog",orphanRemoval = true)
	List<ProjectTask> projectTasks=new ArrayList<>();

	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getPTSequence() {
		return PTSequence;
	}

	public void setPTSequence(int pTSequence) {
		PTSequence = pTSequence;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}

	public List<ProjectTask> getProjectTasks() {
		return projectTasks;
	}

	public void setProjectTasks(List<ProjectTask> projectTasks) {
		this.projectTasks = projectTasks;
	}
	
	
}
