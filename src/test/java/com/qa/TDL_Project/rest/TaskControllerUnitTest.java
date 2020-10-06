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
import com.qa.TDL_Project.persistence.domain.Task;
import com.qa.TDL_Project.service.TaskService;

@SpringBootTest
public class TaskControllerUnitTest {
	
	@Autowired
	private TaskController controller;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@MockBean
	private TaskService service;
	
	private List<Task> taskList;
    private Task testTask;
    private Task testTaskWithId;
    private TaskDTO taskDTO;
    private final Long id = 1L;
    
    private TaskDTO mapToDTO(Task task) {
        return this.modelMapper.map(task, TaskDTO.class);
    }
    
    @BeforeEach
    void init() {
        this.taskList = new ArrayList<>();
        this.testTask = new Task("Clean", 1);
        this.testTaskWithId = new Task(testTask.getName(), testTask.getPriority());
        this.testTaskWithId.setId(id);
        this.taskList.add(testTaskWithId);
        this.taskDTO = this.mapToDTO(testTaskWithId);
    }

    @Test
    void createTest() {
        when(this.service.create(testTask))
            .thenReturn(this.taskDTO);
        
        assertThat(new ResponseEntity<TaskDTO>(this.taskDTO, HttpStatus.CREATED))
                .isEqualTo(this.controller.create(testTask));
        
        verify(this.service, times(1))
            .create(this.testTask);
    }

    @Test
    void readTest() {
        when(this.service.read(this.id))
            .thenReturn(this.taskDTO);
        
        assertThat(new ResponseEntity<TaskDTO>(this.taskDTO, HttpStatus.OK))
                .isEqualTo(this.controller.read(this.id));
        
        verify(this.service, times(1))
            .read(this.id);
    }

    @Test
    void readAllTest() {
        when(service.read())
            .thenReturn(this.taskList
                    .stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList()));
        
        assertThat(this.controller.read().getBody()
                .isEmpty()).isFalse();
        
        verify(service, times(1))
            .read();
    }

    @Test
    void updateTest() {
        // given
        TaskDTO newTask = new TaskDTO(null, "Cook",2);
        TaskDTO updatedTask = new TaskDTO(this.id, newTask.getName(), newTask.getPriority());

        when(this.service.update(newTask, this.id))
            .thenReturn(updatedTask);
        
        assertThat(new ResponseEntity<TaskDTO>(updatedTask, HttpStatus.ACCEPTED))
                .isEqualTo(this.controller.update(this.id, newTask));
        
        verify(this.service, times(1))
            .update(newTask, this.id);
    }
    
    @Test
    void deleteTest() {
        this.controller.delete(id);

        verify(this.service, times(1))
            .delete(id);
    }

}
