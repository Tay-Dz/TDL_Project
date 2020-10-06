package com.qa.TDL_Project.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.qa.TDL_Project.dto.TaskDTO;
import com.qa.TDL_Project.dto.TaskListDTO;
import com.qa.TDL_Project.persistence.domain.Task;
import com.qa.TDL_Project.persistence.domain.TaskList;
import com.qa.TDL_Project.service.TaskListService;

@SpringBootTest
public class TaskListControllerUnitTest {
	
	@Autowired
	private TaskListController controller;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@MockBean
	private TaskListService service;
	
	private List<TaskList> TLList= new ArrayList<>();;
    private TaskList testTL;
    private TaskList testTLWithId;
    private TaskListDTO TLDTO;
    private final Long id = 1L;
	private Task testTask;
	private List<Task> testTaskArray = new ArrayList<>();
    
    private TaskListDTO mapToDTO(TaskList taskList) {
        return this.modelMapper.map(taskList, TaskListDTO.class);
    }
    
    @BeforeEach
    void init() {
    	this.testTask = new Task("Cook", 2);
		this.testTaskArray.add(this.testTask);
		this.testTL = new TaskList("Monday", 1, testTaskArray);
        this.testTLWithId = new TaskList(testTL.getName(), testTL.getPriority(), testTL.getTasks());
        this.testTLWithId.setId(id);
        this.TLList.add(testTLWithId);
        this.TLDTO = this.mapToDTO(testTLWithId);
    }

    @Test
    void createTest() {
        when(this.service.create(testTL))
            .thenReturn(this.TLDTO);
        
        assertThat(new ResponseEntity<TaskListDTO>(this.TLDTO, HttpStatus.CREATED))
                .isEqualTo(this.controller.create(testTL));
        
        verify(this.service, times(1))
            .create(this.testTL);
    }

    @Test
    void readTest() {
        when(this.service.read(this.id))
            .thenReturn(this.TLDTO);
        
        assertThat(new ResponseEntity<TaskListDTO>(this.TLDTO, HttpStatus.OK))
                .isEqualTo(this.controller.read(this.id));
        
        verify(this.service, times(1))
            .read(this.id);
    }

    @Test
    void readAllTest() {
        when(service.read())
            .thenReturn(this.TLList
                    .stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList()));
        
        assertThat(this.controller.read().getBody()
                .isEmpty()).isFalse();
        
        verify(service, times(1))
            .read();
    }
    @Test
    void readAllByPriority() {
        when(service.readByPriority())
            .thenReturn(this.TLList
                    .stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList()));
        
        assertThat(this.controller.readByPriority().getBody()
                .isEmpty()).isFalse();
        
        verify(service, times(1))
            .readByPriority();
    }
    
    @Test
    void readAllByName() {
        when(service.readByName())
            .thenReturn(this.TLList
                    .stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList()));
        
        assertThat(this.controller.readByName().getBody()
                .isEmpty()).isFalse();
        
        verify(service, times(1))
            .readByName();
    }

    @Test
    void updateTest() {
    	TaskDTO newTask = new TaskDTO(null, "Cook",2);
    	List<TaskDTO> newTaskArray = new ArrayList<>();
    	newTaskArray.add(newTask);
    	TaskListDTO newTL = new TaskListDTO(null, "ITV", 3,newTaskArray);
    	TaskListDTO updatedTL= new TaskListDTO(this.id,newTL.getName(), newTL.getPriority(),newTL.getTasks());

        when(this.service.update(newTL, this.id))
            .thenReturn(updatedTL);
        
        assertThat(new ResponseEntity<TaskListDTO>(updatedTL, HttpStatus.ACCEPTED))
                .isEqualTo(this.controller.update(this.id, newTL));
        
        verify(this.service, times(1))
            .update(newTL, this.id);
    }
    
    @Test
    void deleteTest() {
        this.controller.delete(id);

        verify(this.service, times(1))
            .delete(id);
    }

}
