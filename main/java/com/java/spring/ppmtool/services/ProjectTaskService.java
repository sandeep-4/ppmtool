package com.java.spring.ppmtool.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.spring.ppmtool.exception.ProjectNotFoundException;
import com.java.spring.ppmtool.model.Backlog;
import com.java.spring.ppmtool.model.ProjectTask;
import com.java.spring.ppmtool.repository.BacklogRepository;
import com.java.spring.ppmtool.repository.ProjectRepository;
import com.java.spring.ppmtool.repository.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	ProjectTaskRepository ptRepo;
	BacklogRepository backlogRepo;
	
	@Autowired
	ProjectRepository projectRepo;
	
	@Autowired
	ProjectService projectService;

	@Autowired
	public ProjectTaskService(ProjectTaskRepository ptRepo, BacklogRepository backlogRepo) {
		super();
		this.ptRepo = ptRepo;
		this.backlogRepo = backlogRepo;
	}

	public List<ProjectTask> findAllTasks() {
		return ptRepo.findAll();
	}

	public ProjectTask saveProjectTask(String projectIdentifier, ProjectTask projectTask,String username) {

	
			
		Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog();         //backlogRepo.findByProjectIdentifier(projectIdentifier);
			projectTask.setBacklog(backlog);
			int backlogSequence = backlog.getPTSequence();
			backlogSequence++;
			backlog.setPTSequence(backlogSequence);
			projectTask.setProjectSequence(projectIdentifier + "-1610" + backlogSequence);
			projectTask.setProjectIdentifier(projectIdentifier);
			if (Integer.toString(projectTask.getPriority())==null || projectTask.getPriority() == 0) {
				projectTask.setPriority(3);
			}
			if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
				projectTask.setStatus("TO_DO");
			}
			return ptRepo.save(projectTask);
		} 
		

	

	public ProjectTask getOne(long id) {
		return ptRepo.getOne(id);
	}

	public List<ProjectTask> findById(long backlog_id) {
		return ptRepo.findById(backlog_id);
	}

	public void delete(long id) {
		ptRepo.deleteById(id);
	}
	
	//this is done in online class

	public List<ProjectTask> findBackLogById(String id,String username) {
		
		//previous ones
		
//		Project project=projectRepo.findByProjectIdentifier(id);
//		if(project==null) {
//			throw new ProjectNotFoundException("no project found " +id);
//		}
//		
		projectService.findProjectByIdentifier(id, username);
		return ptRepo.findByProjectIdentifierOrderByPriority(id);
	}
	
	public ProjectTask findByProjectSequence(String backlog_id,String projectSequence,String username) {
		
		Backlog backlogTest=backlogRepo.findByProjectIdentifier(backlog_id);
		if(backlogTest==null) {
			throw new ProjectNotFoundException("No project id found : "+backlog_id);
		}
		
		ProjectTask projectSequenceTest=ptRepo.findByProjectSequence(projectSequence);
		if(projectSequenceTest==null){
			throw new ProjectNotFoundException("No project sequence found : "+projectSequence);

		}
		
		if(projectSequenceTest.getProjectIdentifier().equals(backlog_id)) {
			return ptRepo.findByProjectSequence(projectSequence);

		}
		throw new ProjectNotFoundException("your project identidier dosent match");
		
	}
	
	public ProjectTask updateTheProjectTask(String backlog_id,String pt_id,ProjectTask projectTask,String username) {
		ProjectTask projectTask1=findByProjectSequence(backlog_id,pt_id,username);  //from the just above method		
		if(projectTask1!=null) {
			projectTask1=projectTask;
//			projectTask1.setSummary(projectTask.getSummary());	
			ptRepo.save(projectTask1);
		}
		return null;
	}
	
	public void deleteTasks(String backlog_id,String pt_id,String username) {
		ProjectTask projectTask=findByProjectSequence(backlog_id,pt_id,username);
		
		
		//manullay removing from the list and again saving the list kind a trash code
		
//		Backlog backlog=projectTask.getBacklog();
//		List<ProjectTask> pt=backlog.getProjectTasks();
//		pt.remove(projectTask);
//		backlogRepo.save(backlog);
		
		
		ptRepo.delete(projectTask);
	}
}
