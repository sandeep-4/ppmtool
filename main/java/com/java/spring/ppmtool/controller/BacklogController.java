package com.java.spring.ppmtool.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.java.spring.ppmtool.model.ProjectTask;
import com.java.spring.ppmtool.repository.BacklogRepository;
import com.java.spring.ppmtool.services.CommonService;
import com.java.spring.ppmtool.services.ProjectTaskService;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {
	
	@Autowired
	ProjectTaskService projectTaskService;
	@Autowired
	CommonService commonService;
	
	@Autowired
	BacklogRepository backlogRepo;

	@RequestMapping(value="/{backlog_id}",method=RequestMethod.POST)
	public ResponseEntity<?> saveProjectTask(@PathVariable(name="backlog_id") String projectIdentifier,
			@Valid @RequestBody ProjectTask projectTask,BindingResult result,Principal principal) {
		 ResponseEntity<?> errorMap=commonService.errorMapping(result);
		 if(errorMap!=null) {
			 return errorMap;
		 }
		
		ProjectTask projectTask1= projectTaskService.saveProjectTask(projectIdentifier, projectTask,principal.getName());
		
		return ResponseEntity.ok().body(projectTask1);
	}
	
	@RequestMapping(value="",method=RequestMethod.GET)
	public List<ProjectTask> getAllTask(){
		
		return projectTaskService.findAllTasks();
	}
	
	//since this this giving us the single project details only 
	
	
//	@RequestMapping(value="/{backlog_id}",method=RequestMethod.GET)
//	public List<ProjectTask> getTaskByIdk(@PathVariable(name="backlog_id")long backlog_id){
//		
//		return projectTaskService.findById(backlog_id);
//	}
	
	//my personal way of it which i did by database geertaed primary key
	
//	@RequestMapping(value="/{backlog_id}",method=RequestMethod.GET)
//	public List<Backlog> getTaskByIdk(@PathVariable(name="backlog_id")long backlog_id){
//		
//		return backlogRepo.findById(backlog_id);
//	}

	@RequestMapping(value="/{backlog_id}",method=RequestMethod.GET)
	public List<ProjectTask> getTaskByIdk(@PathVariable(name="backlog_id")String backlog_id,Principal principal){
		
		return projectTaskService.findBackLogById(backlog_id, principal.getName());
	}

	@RequestMapping(value="/{backlog_id}/{projectSequence}",method=RequestMethod.GET)
	public ResponseEntity<?> findBySequenceID(@PathVariable(name="backlog_id")String backlog_id,@PathVariable(name="projectSequence")String projectSequence,Principal principal) {
		ProjectTask pt=projectTaskService.findByProjectSequence(backlog_id,projectSequence,principal.getName());
		return ResponseEntity.ok().body(pt);
	}
	
	@RequestMapping(value="/{backlog_id}/{pt_id}",method=RequestMethod.PATCH)
	public ResponseEntity<?> updateProjectTask(@PathVariable(name="backlog_id")String backlog_id,
			@PathVariable(name="pt_id")String pt_id,@Valid @RequestBody ProjectTask projectTask,
			BindingResult result,Principal principal){
		 ResponseEntity<?> errorMap=commonService.errorMapping(result);
		 if(errorMap!=null) {
			 return errorMap;
		 }
		
		ProjectTask pt= projectTaskService.updateTheProjectTask(backlog_id,pt_id, projectTask,principal.getName());
		return ResponseEntity.ok().body(pt);
	}
	
	@RequestMapping(value="/{backlog_id}/{pt_id}",method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteProjectTask(@PathVariable(name="backlog_id")String backlog_id,
			@PathVariable(name="pt_id")String pt_id,Principal principal) {
		projectTaskService.deleteTasks(backlog_id, pt_id,principal.getName());
		return new ResponseEntity<String>("deleted sucessfully !!!",HttpStatus.OK);
	}

}

