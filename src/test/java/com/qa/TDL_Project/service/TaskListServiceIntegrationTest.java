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
    
    private List<Task> testTasks = new ArrayList<>();

    @Autowired
    private ModelMapper modelMapper;

    private TaskListDTO mapToDTO(TaskList taskList) {
        return this.modelMapper.map(taskList, TaskListDTO.class);
    }

    @BeforeEach
    void init() {
    	testTasks.add(new Task("Clean",1));
        this.repo.deleteAll();
        this.testTaskList = new TaskList("Monday",1,testTasks);
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
