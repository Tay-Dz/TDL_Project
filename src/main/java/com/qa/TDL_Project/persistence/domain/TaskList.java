package com.qa.TDL_Project.persistence.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class TaskList {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "task_list_name", unique = true)
	@NotNull
	@Size(min = 1, max = 60)
	private String name;

	@Column(name = "priority")
	private Integer priority;

	
	@OneToMany(mappedBy="taskList", cascade=CascadeType.ALL)
//	@OneToMany(mappedBy = "channel")
	private List<Task> tasks = new ArrayList<>();


	public TaskList( @NotNull @Size(min = 1, max = 60) String name, int priority, List<Task> tasks) {
		super();
		//this.id = id;
		this.name = name;
		this.priority = priority;
		this.tasks = tasks;
	}
	public TaskList(@NotNull @Size(min = 1, max = 60) String name) {
		super();
		this.name = name;
	}

}