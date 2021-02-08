package com.java.spring.ppmtool.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.java.spring.ppmtool.exception.ProjectIdException;
import com.java.spring.ppmtool.model.Project;
import com.java.spring.ppmtool.services.CommonService;
import com.java.spring.ppmtool.services.ProjectService;

@RestController
@RequestMapping("/api/")
@CrossOrigin
public class ProjectController {

	@Autowired
	ProjectService projectService;

	@Autowired
	CommonService commonService;

	@RequestMapping(value = "/project/all", method = RequestMethod.GET)
	public List<Project> getProjects(Principal principal) {
		return projectService.findAllProjects(principal.getName());
	}

	// @RequestMapping(value="/project",method=RequestMethod.POST)
	// public String saveProject(@Valid @RequestBody Project project) {
	//
	// projectService.saveProject(project);
	// return "added sucessfully !!!";
	// }

	@RequestMapping(value = "/project", method = RequestMethod.POST)
	public ResponseEntity<?> saveProject(@Valid @RequestBody Project project, BindingResult result,
			Principal principal) {

		ResponseEntity<?> errorMap = commonService.errorMapping(result);
		if (errorMap != null) {
			return errorMap;
		}

		Project pro = projectService.saveProject(project, principal.getName());
		return ResponseEntity.ok().body(pro);
	}

	@RequestMapping(value = "/projects/{id}", method = RequestMethod.GET)
	public String getProject(@PathVariable(name = "id") long id) {
		projectService.getOne(id);
		return "";
	}

	@RequestMapping(value = "/projects/{projectId}", method = RequestMethod.GET)
	public ResponseEntity<?> getProjectById(@PathVariable(name = "projectId") String projectId, Principal principal) {
		Project project = projectService.findProjectByIdentifier(projectId.toUpperCase(), principal.getName());

		return ResponseEntity.ok().body(project);

	}

	@RequestMapping(value = "/project/updates/{id}", method = RequestMethod.PUT)
	public String updateProjectById(@PathVariable(name = "id") long id, @Valid @RequestBody Project project,
			Principal principal) {
		Project oldProject = projectService.getOne(id);
		if (oldProject != null) {
			oldProject.setProjectName(project.getProjectName());
			// oldProject.setProjectIdentifier(project.getProjectIdentifier()); since its a
			// unique item
			oldProject.setDescription(project.getDescription());
			oldProject.setStart_date(project.getStart_date());
			oldProject.setEnd_date(project.getEnd_date());

			oldProject.setUpdateAt(project.getUpdateAt());

			projectService.saveProject(oldProject, principal.getName());
			return "updated sucessfully !!!";
		} else {
			return "error updating !!!";
		}
	}

	@RequestMapping(value = "/project/deletessss/{id}", method = RequestMethod.DELETE)
	public String deletesssss(@PathVariable(name = "id") long id) {
		projectService.deleteById(id);
		return "delete sucessfully !!!";
	}

	@RequestMapping(value = "/project/delete/{projectIdentifier}", method = RequestMethod.DELETE)
	public String delete(@PathVariable(name = "projectIdentifier") String projectIdentifier) {
		projectService.deleteByProjectIdentifier(projectIdentifier);
		return "delete sucessfully !!!";
	}

	@RequestMapping(value = "/project/{projectIdentifier}", method = RequestMethod.GET)
	public ResponseEntity<?> findProjectByPId(@PathVariable(name = "projectIdentifier") String projectIdentifier,
			Principal principal) {
		Project p = projectService.findProjectByIdentifier(projectIdentifier, principal.getName());

		return new ResponseEntity<Project>(p, HttpStatus.OK);
	}

	@RequestMapping(value = "/project/update/{projectIdentifier}", method = { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> updateProjectByIdentifier(
			@PathVariable(name = "projectIdentifier") String projectIdentifier, @Valid @RequestBody Project project) {
		return projectService.updateProject(projectIdentifier, project);
	}

	// @RequestMapping(value="/project/update/{projectIdentifier}",method=
	// {RequestMethod.GET,RequestMethod.POST})
	// public ResponseEntity<Project>
	// updateProject(@PathVariable(name="projectIdentifier") String
	// projectIdentifier,@Valid @RequestBody Project project){
	// Project
	// oldProject=projectService.findProjectByIdentifier(projectIdentifier.toUpperCase());
	// if(oldProject!=null) {
	// oldProject.setProjectName(project.getProjectName());
	//
	// // oldProject.setProjectIdentifier(project.getProjectIdentifier());
	//
	// oldProject.setDescription(project.getDescription());
	// oldProject.setStart_date(project.getStart_date());
	// oldProject.setEnd_date(project.getEnd_date());
	//
	// oldProject.setUpdateAt(project.getUpdateAt());
	//
	// projectService.saveProject(oldProject);
	// return ResponseEntity.ok().body(oldProject);
	// }else {
	// return ResponseEntity.notFound().build();
	// }
	// }

	// @GetMapping("/all")
	// List<Project> getAllProjects(Principal principal){
	// return projectService.findAllProjects(principal.getName());
	// }
}
