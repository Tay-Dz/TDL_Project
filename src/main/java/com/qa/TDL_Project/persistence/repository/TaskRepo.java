package com.qa.TDL_Project.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.TDL_Project.persistence.domain.Task;

@Repository
public interface TaskRepo extends JpaRepository<Task,Long> {

}