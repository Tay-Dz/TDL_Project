package com.qa.TDL_Project.rest;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.TDL_Project.dto.TaskDTO;
import com.qa.TDL_Project.persistence.domain.Task;
import com.qa.TDL_Project.persistence.repository.TaskRepo;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerIntegrationTest {
	
	@Autowired
	private MockMvc mock;
	
	@Autowired
	private TaskRepo repo;
	
	@Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;
    private Task testTask;
    private Task testTaskWithId;
    private TaskDTO taskDTO;
    
    private Long id;
//    private String testName;
//    private Integer testRunTime;
//    private String testGenre;
//    
    private TaskDTO mapToDTO(Task task) {
		return this.modelMapper.map(task, TaskDTO.class);
	}
    @BeforeEach
    void init() {
        this.repo.deleteAll();

        this.testTask = new Task("Clean", 1);
        this.testTaskWithId = this.repo.save(this.testTask);
        this.taskDTO = this.mapToDTO(testTaskWithId);

        this.id = this.testTaskWithId.getId();
//        this.testName = this.testTaskWithId.getName();
//        this.testRunTime = this.testTaskWithId.getRunTimeMins();
//        this.testGenre = this.testTaskWithId.getGenre();
    }
    @Test
    void testCreate() throws Exception {
        this.mock
                .perform(request(HttpMethod.POST, "/task/create").contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(testTask))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(this.objectMapper.writeValueAsString(taskDTO)));
    }

    @Test
    void testRead() throws Exception {
        this.mock.perform(request(HttpMethod.GET, "/task/read/" + this.id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(this.objectMapper.writeValueAsString(this.taskDTO)));
    }

    @Test
    void testReadAll() throws Exception {
        List<TaskDTO> taskList = new ArrayList<>();
        taskList.add(this.taskDTO);

        String content = this.mock
                .perform(request(HttpMethod.GET, "/task/read")
                		.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(this.objectMapper.writeValueAsString(taskList), content);
    }

    @Test
    void testUpdate() throws Exception {
        TaskDTO newTask = new TaskDTO(null, "Cook", 2);
        Task updatedTask = new Task(newTask.getName(), newTask.getPriority());
        updatedTask.setId(this.id);

        String result = this.mock
                .perform(request(HttpMethod.PUT, "/task/update/" + this.id)
                		.accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(newTask)))
                .andExpect(status().isAccepted())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(this.objectMapper.writeValueAsString(this.mapToDTO(updatedTask)), result);
    }

    @Test
    void testDelete() throws Exception {
        this.mock.perform(request(HttpMethod.DELETE, "/task/delete/" + this.id)).andExpect(status().isNoContent());
    }

}
