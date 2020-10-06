package com.qa.TDL_Project.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.TDL_Project.dto.TaskDTO;
import com.qa.TDL_Project.persistence.domain.Task;
import com.qa.TDL_Project.persistence.repository.TaskRepo;

@SpringBootTest
class TaskServiceUnitTest {
	
	@Autowired
    private TaskService service;

    @MockBean
    private TaskRepo repo;

    @MockBean
    private ModelMapper modelMapper;

    private List<Task> taskList;
    private Task testTask;
    private Task testTaskWithId;
    private TaskDTO taskDTO;
    
    private final Long id = 1L;

    
    @BeforeEach
    void init() {
        this.taskList = new ArrayList<>();
        this.testTask = new Task("Clean", 1 );
        this.testTaskWithId = new Task(testTask.getName(), testTask.getPriority());
        this.testTaskWithId.setId(id);
        this.taskList.add(testTaskWithId);
        this.taskDTO = new ModelMapper().map(testTaskWithId,TaskDTO.class);
    }
    
    @Test
    void createTest() {
    	when(this.repo.save(this.testTask)).thenReturn(this.testTaskWithId);
    	
    	when(this.modelMapper.map(testTaskWithId, TaskDTO.class)).thenReturn(taskDTO);
    	
    	assertThat(this.taskDTO).isEqualTo(this.service.create(testTask));
    	verify(this.repo, times(1)).save(this.testTask);
    }
    
    @Test
    void readTest() {
    	when(this.repo.findById(this.id)).thenReturn(Optional.of(this.testTaskWithId));
    	when(this.modelMapper.map(testTaskWithId, TaskDTO.class)).thenReturn(taskDTO);
    	assertThat(this.taskDTO).isEqualTo(this.service.read(this.id));
    	verify(this.repo,times(1)).findById(this.id);
    }
    
    @Test
    void readAllTest() {
        when(repo.findAll()).thenReturn(this.taskList);

        when(this.modelMapper.map(testTaskWithId, TaskDTO.class)).thenReturn(taskDTO);

        assertThat(this.service.read().isEmpty()).isFalse();

        verify(repo, times(1)).findAll();
    }
    
    @Test
    void deleteTest() {
        when(this.repo.existsById(id)).thenReturn(true, false);

        assertThat(this.service.delete(id)).isTrue();

        verify(this.repo, times(1)).deleteById(id);

        verify(this.repo, times(2)).existsById(id);
    } 
 
}
