package com.qa.TDL_Project.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.qa.TDL_Project.persistence.domain.TaskList;

@SpringBootTest
//@DataJpaTest
public class TaskListRepoTest {
	
	@Autowired
	private TaskListRepo repo;
	
	private final String Test_Name1 = "ABC";
	private final Integer Test_Priority1 = 2;
	private final TaskList Test_TaskList1= new TaskList(Test_Name1,Test_Priority1);
	
	private final String Test_Name2 = "XYZ";
	private final Integer Test_Priority2 = 1;
	private final TaskList Test_TaskList2= new TaskList(Test_Name2,Test_Priority2);
	
	private final String Test_Name3 = "MNO";
	private final Integer Test_Priority3 = 3; 
	private final TaskList Test_TaskList3= new TaskList(Test_Name3,Test_Priority3);
	
	private List<TaskList> results;
	
	@BeforeEach
    void init() {
        this.repo.deleteAll();
        this.results = new ArrayList<>();
        this.repo.save(this.Test_TaskList1);
        this.repo.save(this.Test_TaskList2);
        this.repo.save(this.Test_TaskList3);
    }
	
	@Test
	void LastIDTest() throws Exception{
		List<TaskList> TL = this.repo.findAll();
		Long IdMax =0l;
		for(TaskList findId:TL) {
			if(findId.getId()>IdMax) {
				IdMax = findId.getId();
			}
		}
		
		assertThat(IdMax).isEqualTo(this.repo.LastID());
	}
	
	@Test
	void orderByPriorityJPQLTest() {
		List<TaskList> repoL = this.repo.orderByPriorityJPQL();
		this.results.add(Test_TaskList3);
		this.results.add(Test_TaskList1);
		this.results.add(Test_TaskList2);
		assertThat(repoL.get(0).getName()).isEqualTo(this.results.get(0).getName());
		assertThat(repoL.get(1).getName()).isEqualTo(this.results.get(1).getName());
		assertThat(repoL.get(2).getName()).isEqualTo(this.results.get(2).getName());
	}
	
	@Test
	void orderByNameJPQLTest() {
		List<TaskList> repoL = this.repo.orderByNameJPQL();
		this.results.add(Test_TaskList1);
		this.results.add(Test_TaskList3);
		this.results.add(Test_TaskList2);
		assertThat(repoL.get(0).getName()).isEqualTo(this.results.get(0).getName());
		assertThat(repoL.get(1).getName()).isEqualTo(this.results.get(1).getName());
		assertThat(repoL.get(2).getName()).isEqualTo(this.results.get(2).getName());
	}
}
