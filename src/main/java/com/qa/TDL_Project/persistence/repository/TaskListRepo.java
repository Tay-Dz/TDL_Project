package com.qa.TDL_Project.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.qa.TDL_Project.persistence.domain.TaskList;

@Repository
public interface TaskListRepo extends JpaRepository<TaskList,Long> {
	
	@Query(value ="SELECT * FROM TaskList ORDER BY priority", nativeQuery = true)
    Optional<TaskList> orderByPriorityJPQL();
	
	@Query(value ="SELECT * FROM TaskList ORDER BY task_list_name", nativeQuery = true)
    Optional<TaskList> orderByNameJPQL();


}
