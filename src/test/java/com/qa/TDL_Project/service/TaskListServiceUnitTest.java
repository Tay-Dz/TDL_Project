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

import com.qa.TDL_Project.dto.TaskListDTO;
import com.qa.TDL_Project.persistence.domain.TaskList;
import com.qa.TDL_Project.persistence.repository.TaskListRepo;

@SpringBootTest
class TaskListServiceUnitTest {
	
	@Autowired
    private TaskListService service;

    @MockBean
    private TaskListRepo repo;

    @MockBean
    private ModelMapper modelMapper;

    private List<TaskList> taskListList;
    private TaskList testTaskList;
    private TaskList testTaskListWithId;
    private TaskListDTO taskListDTO;
    
    private final Long id = 1L;
    
    
    @BeforeEach
    void init() {
        this.taskListList = new ArrayList<>();
        this.testTaskList = new TaskList("Monday",1 );
        this.testTaskListWithId = new TaskList(testTaskList.getName(), testTaskList.getPriority(),testTaskList.getTasks());
        this.testTaskListWithId.setId(id);
        this.taskListList.add(testTaskListWithId);
        this.taskListDTO = new ModelMapper().map(testTaskListWithId,TaskListDTO.class);
    }
    
    @Test
    void createTest() {
    	when(this.repo.save(this.testTaskList)).thenReturn(this.testTaskListWithId);
    	
    	when(this.modelMapper.map(testTaskListWithId, TaskListDTO.class)).thenReturn(taskListDTO);
    	
    	assertThat(this.taskListDTO).isEqualTo(this.service.create(testTaskList));
    	verify(this.repo, times(1)).save(this.testTaskList);
    }
    
    @Test
    void readTest() {
    	when(this.repo.findById(this.id)).thenReturn(Optional.of(this.testTaskListWithId));
    	when(this.modelMapper.map(testTaskListWithId, TaskListDTO.class)).thenReturn(taskListDTO);
    	assertThat(this.taskListDTO).isEqualTo(this.service.read(this.id));
    	verify(this.repo,times(1)).findById(this.id);
    }
    
    @Test
    void readAllTest() {
        when(repo.findAll()).thenReturn(this.taskListList);

        when(this.modelMapper.map(testTaskListWithId, TaskListDTO.class)).thenReturn(taskListDTO);

        assertThat(this.service.read().isEmpty()).isFalse();

        verify(repo, times(1)).findAll();
    }
    @Test
    void readByPriorityTest() {
        when(repo.orderByPriorityJPQL()).thenReturn(this.taskListList);

        when(this.modelMapper.map(testTaskListWithId, TaskListDTO.class)).thenReturn(taskListDTO);

        assertThat(this.service.readByPriority().isEmpty()).isFalse();

        verify(repo, times(1)).orderByPriorityJPQL();
    }
    @Test
    void readByNameTest() {
        when(repo.orderByNameJPQL()).thenReturn(this.taskListList);

        when(this.modelMapper.map(testTaskListWithId, TaskListDTO.class)).thenReturn(taskListDTO);

        assertThat(this.service.readByName().isEmpty()).isFalse();

        verify(repo, times(1)).orderByNameJPQL();
    }
    
    @Test
    void deleteTest() {
        when(this.repo.existsById(id)).thenReturn(true, false);

        assertThat(this.service.delete(id)).isTrue();

        verify(this.repo, times(1)).deleteById(id);

        verify(this.repo, times(2)).existsById(id);
    }

}
