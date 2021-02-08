package com.java.spring.ppmtool.services;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.java.spring.ppmtool.exception.ProjectIdException;
import com.java.spring.ppmtool.model.Backlog;
import com.java.spring.ppmtool.model.Project;
import com.java.spring.ppmtool.model.User;
import com.java.spring.ppmtool.repository.BacklogRepository;
import com.java.spring.ppmtool.repository.ProjectRepository;
import com.java.spring.ppmtool.repository.UserRepository;

@Service
public class ProjectService {

	@Autowired
	ProjectRepository projectRepo;
	
	@Autowired
	BacklogRepository backlogRepo;
	
	@Autowired
	UserRepository userRepo;
	
	public List<Project> findAllProjects(String username){
		return projectRepo.findAllByProjectLeader(username);
	}
	
	public Project getOne(long id) {
		return projectRepo.getOne(id);
	}
	
	public Project saveProject(Project project,String username) {
		
		if(project.getId()!=null) {
			Project existingProject=projectRepo.findByProjectIdentifier(project.getProjectIdentifier());
			
			if(existingProject!=null &&(!existingProject.getProjectLeader().equals(username))) {
				throw new ProjectIdException("Project not found exception");
			}else if(existingProject==null){
				throw new ProjectIdException("idendifier doesnt exist ");
			}
		}
		
		
		User  user=userRepo.findByUsername(username);
		project.setUser(user);
		project.setProjectLeader(user.getUsername());
		project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
		
	String toUpperID=	project.getProjectIdentifier().toUpperCase();
	project.setProjectIdentifier(toUpperID);
	try {	
		
		if(project.getId()==null) {
			Backlog backlog=new Backlog();
			project.setBacklog(backlog);
			backlog.setProject(project);
			backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
		}
		if(project.getId()!=null) {
			Backlog backlog1=backlogRepo.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			project.setBacklog(backlog1);
		}
		
		return projectRepo.save(project);
	}catch(Exception ex){
		throw new ProjectIdException("dublicate project identifier !!!  " +project.getProjectIdentifier());
	}
	
	
	}
	
	public void deleteById(long id) {
		projectRepo.deleteById(id);
	}
	
	//to find by user and find by the right authority
	
	public Project findProjectByIdentifier(String projectIdentifier,String username) {
	
		
		Project project=projectRepo.findByProjectIdentifier(projectIdentifier.toUpperCase());
		if(project==null) {
			throw new ProjectIdException("No such project id found "+projectIdentifier);
		}
	
	if(!project.getProjectLeader().equals(username)) {
		throw new ProjectIdException(username+" opps!!! you are trying to acess others project");

	}
	return project;
	}
	
	
	public void deleteByProjectIdentifier(String projectIdentifier) {
		Project project=projectRepo.findByProjectIdentifier(projectIdentifier.toUpperCase());
		if(project==null) {
			throw new ProjectIdException("not aviablable" +projectIdentifier);
		}
		 projectRepo.delete(project);
	}
	
	public ResponseEntity<Project> updateProject(String projectIdentifier,Project project){
		Project oldProject=projectRepo.findByProjectIdentifier(projectIdentifier.toUpperCase());
		if(oldProject!=null) {
			oldProject.setProjectName(project.getProjectName());
			
			oldProject.setProjectIdentifier(projectIdentifier.toUpperCase());
			
			oldProject.setDescription(project.getDescription());
			oldProject.setStart_date(project.getStart_date());
			oldProject.setEnd_date(project.getEnd_date());
			
			oldProject.setUpdateAt(project.getUpdateAt());
			
			projectRepo.save(oldProject);
			return ResponseEntity.ok().body(oldProject);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
}
