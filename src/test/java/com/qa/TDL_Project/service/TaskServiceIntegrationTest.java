package com.qa.TDL_Project.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.qa.TDL_Project.dto.TaskDTO;
import com.qa.TDL_Project.persistence.domain.Task;
import com.qa.TDL_Project.persistence.repository.TaskRepo;

@SpringBootTest
class TaskServiceIntegrationTest {
	
	@Autowired
    private TaskService service;

    @Autowired
    private TaskRepo repo;

    private Task testTask;
    private Task testTaskWithId;

    @Autowired
    private ModelMapper modelMapper;

    private TaskDTO mapToDTO(Task task) {
        return this.modelMapper.map(task, TaskDTO.class);
    }

    @BeforeEach
    void init() {
        this.repo.deleteAll();
        this.testTask = new Task("Clean",1);
        this.testTaskWithId = this.repo.save(this.testTask);
    }

    @Test
    void testCreate() {
        assertThat(this.mapToDTO(this.testTaskWithId))
            .isEqualTo(this.service.create(testTask));
    }

    @Test
    void testRead() {
        assertThat(this.service.read(this.testTaskWithId.getId()))
            .isEqualTo(this.mapToDTO(this.testTaskWithId));
    }

    @Test
    void testReadAll() {
        assertThat(this.service.read())
            .isEqualTo(Stream.of(this.mapToDTO(testTaskWithId)).collect(Collectors.toList()));
    }

    @Test
    void testUpdate() {
        TaskDTO newTask = new TaskDTO(null, "Cook",2);
        TaskDTO updatedTask = new TaskDTO(this.testTaskWithId.getId(), newTask.getName(), newTask.getPriority());

        assertThat(this.service.update(newTask, this.testTaskWithId.getId()))
            .isEqualTo(updatedTask);
    }

    @Test
    void testDelete() {
        assertThat(this.service.delete(this.testTaskWithId.getId()))
            .isTrue();
    }

}
