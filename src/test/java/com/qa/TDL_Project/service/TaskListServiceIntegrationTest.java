package com.qa.TDL_Project.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.qa.TDL_Project.dto.TaskListDTO;
import com.qa.TDL_Project.persistence.domain.Task;
import com.qa.TDL_Project.persistence.domain.TaskList;
import com.qa.TDL_Project.persistence.repository.TaskListRepo;

@SpringBootTest
class TaskListServiceIntegrationTest {
	@Autowired
    private TaskListService service;

    @Autowired
    private TaskListRepo repo;

    private TaskList testTaskList;
    private TaskList testTaskListWithId;
    
    @Autowired
    private ModelMapper modelMapper;

    private TaskListDTO mapToDTO(TaskList taskList) {
        return this.modelMapper.map(taskList, TaskListDTO.class);
    }
    private List<TaskListDTO> mapToDTO(List<TaskList> taskList) {
    	List<TaskListDTO> TLDTOlist= new ArrayList<>();
        for(TaskList task:taskList) {
        	TLDTOlist.add(this.modelMapper.map(task, TaskListDTO.class));
        }
        return TLDTOlist;
    }
    
    

    @BeforeEach
    void init() {
        this.repo.deleteAll();
        this.testTaskList = new TaskList("Monday",1);
        this.testTaskListWithId = this.repo.save(this.testTaskList);
    }

    @Test
    void testCreate() {
        assertThat(this.mapToDTO(this.testTaskListWithId))
            .isEqualTo(this.service.create(testTaskList));
    }

    @Test
    void testRead() {
        assertThat(this.service.read(this.testTaskListWithId.getId()))
            .isEqualTo(this.mapToDTO(this.testTaskListWithId));
    }

    @Test
    void testReadAll() {
        assertThat(this.service.read())
            .isEqualTo(Stream.of(this.mapToDTO(testTaskListWithId)).collect(Collectors.toList()));
    }
    @Test
    void testReadByPriority() {
        TaskList testTaskList2 = new TaskList("Tuesday",3);
        TaskList testTaskListWithId2 = this.repo.save(testTaskList2);
        TaskList testTaskList3 = new TaskList("Wednesday",2);
        TaskList testTaskListWithId3 = this.repo.save(testTaskList3);
        List<TaskList> TLPriority = new ArrayList<>();
        TLPriority.add(testTaskListWithId2);
        TLPriority.add(testTaskListWithId3);
        TLPriority.add(this.testTaskListWithId);
        
        assertThat(this.service.readByPriority())
            .isEqualTo(this.mapToDTO(TLPriority));
    }
    @Test
    void testReadByName() {
        TaskList testTaskList2 = new TaskList("XYZ",3);
        TaskList testTaskListWithId2 = this.repo.save(testTaskList2);
        TaskList testTaskList3 = new TaskList("ABC",2);
        TaskList testTaskListWithId3 = this.repo.save(testTaskList3);
        List<TaskList> TLName = new ArrayList<>();
        TLName.add(testTaskListWithId3);
        TLName.add(this.testTaskListWithId);
        TLName.add(testTaskListWithId2);
        
        assertThat(this.service.readByName())
            .isEqualTo(this.mapToDTO(TLName));
    }
    

    
    @Test 
    void readLastID() {
    	assertThat(this.service.readLastID())
    	.isEqualTo(this.testTaskListWithId.getId());
    }
    
    @Test
    void testUpdate() {
        TaskListDTO newTaskList = new TaskListDTO(null, "Tuesday",2);
        TaskListDTO updatedTaskList = new TaskListDTO(this.testTaskListWithId.getId(), newTaskList.getName(), newTaskList.getPriority(),newTaskList.getTasks());

        assertThat(this.service.update(newTaskList, this.testTaskListWithId.getId()))
            .isEqualTo(updatedTaskList);
    }

    @Test
    void testDelete() {
        assertThat(this.service.delete(this.testTaskListWithId.getId()))
            .isTrue();
    }


}
