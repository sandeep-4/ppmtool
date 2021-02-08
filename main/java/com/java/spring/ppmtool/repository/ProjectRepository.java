package com.java.spring.ppmtool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.spring.ppmtool.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long>{

	public Project findByProjectIdentifier(String projectIdentifier);
	
	List<Project> findAllByProjectLeader(String username);
}
