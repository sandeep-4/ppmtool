package com.java.spring.ppmtool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.spring.ppmtool.model.ProjectTask;

@Repository
public interface ProjectTaskRepository extends JpaRepository<ProjectTask,Long>{
public List<ProjectTask> findById(long id);

public List<ProjectTask> findByProjectIdentifierOrderByPriority(String id);

public ProjectTask findByProjectSequence(String projectSequence);

}
