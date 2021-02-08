package com.java.spring.ppmtool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.spring.ppmtool.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

	public User findByUsername(String username);
	
	public User findByUsernameAndPassword(String username,String password);
	
	public User findById(long id);
}
