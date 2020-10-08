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
import com.qa.TDL_Project.dto.TaskListDTO;
import com.qa.TDL_Project.persistence.domain.TaskList;
import com.qa.TDL_Project.persistence.repository.TaskListRepo;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskListControllerIntegrationTest {
	
	@Autowired
	private MockMvc mock;

	@Autowired
	private TaskListRepo repo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ObjectMapper objectMapper;

	private Long id;
	private TaskList testTaskList;
	private TaskList testTaskListWithId;
    
	private TaskListDTO mapToDTO(TaskList taskList) {
		return this.modelMapper.map(taskList, TaskListDTO.class);
	}

	@BeforeEach
	void init() {
		this.repo.deleteAll();

		this.testTaskList = new TaskList("Monday",1);
		this.testTaskListWithId = this.repo.save(this.testTaskList);
		this.id = this.testTaskListWithId.getId();

	}

	@Test
	void testCreate() throws Exception {
		this.mock
				.perform(request(HttpMethod.POST, "/taskList/create")
						.contentType(MediaType.APPLICATION_JSON)
						.content(this.objectMapper.writeValueAsString(testTaskList))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(content().json(this.objectMapper.writeValueAsString(testTaskListWithId)));
	}

	@Test
	void testRead() throws Exception {
		this.mock.perform(request(HttpMethod.GET, "/taskList/read/" + this.id)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json(this.objectMapper.writeValueAsString(this.testTaskList)));
	}

	@Test
	void testReadAll() throws Exception {
		List<TaskList> taskListList = new ArrayList<>();
		taskListList.add(this.testTaskListWithId);

		String content = this.mock
				.perform(request(HttpMethod.GET, "/taskList/read/default")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

		assertEquals(this.objectMapper.writeValueAsString(taskListList), content);
	}
	
	@Test
	void testReadByPriority() throws Exception {
		TaskList testTaskList2 = new TaskList("XYZ",2);
		TaskList testTaskListWithId2 = this.repo.save(testTaskList2);
		TaskList testTaskList3 = new TaskList("ABC",3);
		TaskList testTaskListWithId3 = this.repo.save(testTaskList3);
		List<TaskList> taskListList = new ArrayList<>();
		taskListList.add(testTaskListWithId3);
		taskListList.add(testTaskListWithId2);
		taskListList.add(this.testTaskListWithId);

		String content = this.mock
				.perform(request(HttpMethod.GET, "/taskList/read/priority")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

		assertEquals(this.objectMapper.writeValueAsString(taskListList), content);
	}
	
	@Test
	void testReadByName() throws Exception {
		TaskList testTaskList2 = new TaskList("XYZ");
		TaskList testTaskListWithId2 = this.repo.save(testTaskList2);
		TaskList testTaskList3 = new TaskList("ABC");
		TaskList testTaskListWithId3 = this.repo.save(testTaskList3);
		List<TaskList> taskListList = new ArrayList<>();
		taskListList.add(testTaskListWithId3);
		taskListList.add(this.testTaskListWithId);
		taskListList.add(testTaskListWithId2);

		String content = this.mock
				.perform(request(HttpMethod.GET, "/taskList/read/name")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

		assertEquals(this.objectMapper.writeValueAsString(taskListList), content);
	}
	@Test
	void testReadLastID() throws Exception {
		List<TaskList> taskListList = new ArrayList<>();
		taskListList.add(this.testTaskListWithId);

		String content = this.mock
				.perform(request(HttpMethod.GET, "/taskList/readLast")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

		assertEquals(this.objectMapper.writeValueAsString(taskListList.get(0).getId()), content);
	}

	@Test
    void testUpdate() throws Exception {

        TaskList newTaskList = new TaskList("Tuesday",2);
        TaskList updatedTaskList = new TaskList(newTaskList.getName(),newTaskList.getPriority());
        updatedTaskList.setId(this.id);

        String result = this.mock
                .perform(request(HttpMethod.PUT, "/taskList/update/" + this.id)
                		.accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(newTaskList)))
                .andExpect(status().isAccepted())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(this.objectMapper.writeValueAsString(this.mapToDTO(updatedTaskList)), result);
    }

	@Test
	void testDelete() throws Exception {
		this.mock.perform(request(HttpMethod.DELETE, "/taskList/delete/" + this.id)).andExpect(status().isNoContent());
	}

}
