package com.qa.TDL_Project.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.TDL_Project.dto.TaskDTO;
import com.qa.TDL_Project.exception.TaskListNotFoundException;
import com.qa.TDL_Project.exception.TaskNotFoundException;
import com.qa.TDL_Project.persistence.domain.Task;
import com.qa.TDL_Project.persistence.repository.TaskRepo;
import com.qa.TDL_Project.utils.SAPIBeanUtils;

@Service
public class TaskService {
	
	private TaskRepo repo;
	private ModelMapper mapper;

	@Autowired
	public TaskService(TaskRepo repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}

	private TaskDTO mapToDTO(Task task) {
		return this.mapper.map(task, TaskDTO.class);
	}

//	private Task mapFromDTO(TaskDTO taskDTO) {
//		return this.mapper.map(taskDTO, Task.class);
//	}

	public TaskDTO create(Task task) {
//		Task toSave = this.mapFromDTO(taskDTO);
		Task saved = this.repo.save(task);
		return mapToDTO(saved);
	}

	public List<TaskDTO> read() {
		return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	public TaskDTO read(Long id) {
		Task found = this.repo.findById(id).orElseThrow(TaskNotFoundException::new);
		return this.mapToDTO(found);
	}

	public TaskDTO update(TaskDTO taskDTO,Long id) {
		Task toUpdate = this.repo.findById(id).orElseThrow(TaskNotFoundException::new);
		SAPIBeanUtils.mergeNotNull(taskDTO,toUpdate);
		return this.mapToDTO(this.repo.save(toUpdate));
	}

	public boolean delete(Long id) {
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}

}
