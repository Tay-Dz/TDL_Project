package com.qa.TDL_Project.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.qa.TDL_Project.persistence.domain.TaskList;

@Repository
public interface TaskListRepo extends JpaRepository<TaskList,Long> {
	
	@Query("SELECT * FROM TaskList order by priority")
    Optional<TaskList> orderByPriorityJPQL();
	
	@Query("SELECT * FROM TaskList order by name")
    Optional<TaskList> orderByNameJPQL();


}
