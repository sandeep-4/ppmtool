package com.java.spring.ppmtool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.spring.ppmtool.model.Backlog;
@Repository
public interface BacklogRepository extends JpaRepository<Backlog,Long>{

	public Backlog findByProjectIdentifier(String projectIdentifier);
	
	public List<Backlog> findById(long backlog_id);
}
