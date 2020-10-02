package com.qa.TDL_Project.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.TDL_Project.dto.TaskListDTO;
import com.qa.TDL_Project.exception.TaskListListNotFoundException;
import com.qa.TDL_Project.exception.TaskListNotFoundException;
import com.qa.TDL_Project.persistence.domain.TaskList;
import com.qa.TDL_Project.persistence.repository.TaskListRepo;
import com.qa.TDL_Project.utils.SAPIBeanUtils;

@Service
public class TaskListListService {
	
	private TaskListRepo repo;
	private ModelMapper mapper;

	@Autowired
	public TaskListListService(TaskListRepo repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}

	private TaskListDTO mapToDTO(TaskList taskList) {
		return this.mapper.map(taskList, TaskListDTO.class);
	}

//	private TaskList mapFromDTO(TaskListDTO taskListDTO) {
//		return this.mapper.map(taskListDTO, TaskList.class);
//	}

	public TaskListDTO create(TaskList taskList) {
//		TaskList toSave = this.mapFromDTO(taskListDTO);
		TaskList saved = this.repo.save(taskList);
		return mapToDTO(saved);
	}

	public List<TaskListDTO> read() {
		return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	public TaskListDTO read(Long id) {
		TaskList found = this.repo.findById(id).orElseThrow(TaskListNotFoundException::new);
		return this.mapToDTO(found);
	}

	public TaskListDTO update(TaskListDTO taskListDTO,Long id) {
		TaskList toUpdate = this.repo.findById(id).orElseThrow(TaskListNotFoundException::new);
		SAPIBeanUtils.mergeNotNull(taskListDTO,toUpdate);
		return this.mapToDTO(this.repo.save(toUpdate));
	}

	public boolean delete(Long id) {
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}

}
