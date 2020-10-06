package com.qa.TDL_Project.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.TDL_Project.dto.TaskListDTO;
import com.qa.TDL_Project.persistence.domain.TaskList;
import com.qa.TDL_Project.service.TaskListService;

@RestController
@CrossOrigin
@RequestMapping("/taskList")
public class TaskListController {
	
	TaskListService service;

	@Autowired
	public TaskListController(TaskListService service) {
		super();
		this.service = service;
	}
	
	@PostMapping("/create")
	public ResponseEntity<TaskListDTO> create(@RequestBody TaskList dto){
//		TaskListDTO created =this.service.create(taskListDTO);
		return new ResponseEntity<>(this.service.create(dto),HttpStatus.CREATED);
	}
	@GetMapping("/read/default")
	public ResponseEntity<List<TaskListDTO>> read(){
		return ResponseEntity.ok(this.service.read());
	}
	@GetMapping("/read/priority")
	public ResponseEntity<List<TaskListDTO>> readByPriority(){
		return ResponseEntity.ok(this.service.readByPriority());
	}
	@GetMapping("/read/name")
	public ResponseEntity<List<TaskListDTO>> readByName(){
		return ResponseEntity.ok(this.service.readByName());
	}
	
	@GetMapping("/read/{id}")
	public ResponseEntity<TaskListDTO> read(@PathVariable Long id){
		return ResponseEntity.ok(this.service.read(id));
	}
	@GetMapping("/readLast")
	public ResponseEntity<Long> readLastID(){
		return ResponseEntity.ok(this.service.readLastID());
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<TaskListDTO> update(@PathVariable Long id,
			@RequestBody TaskListDTO taskListDTO){
//		TaskListDTO updated = this.service.update(taskListDTO, id);
		return new ResponseEntity<>(this.service.update(taskListDTO, id),HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<TaskListDTO> delete(@PathVariable Long id){
		return this.service.delete(id)
				? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}


