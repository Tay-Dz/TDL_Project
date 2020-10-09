package com.qa.TDL_Project.persistence.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
//import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
//@ToString
//@EqualsAndHashCode
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
	private List<Task> tasks = new ArrayList<>();


	public TaskList( @NotNull @Size(min = 1, max = 60) String name, int priority, List<Task> tasks) {
		super();
		//this.id = id;
		this.name = name;
		this.priority = priority;
		this.tasks = tasks;
	}
	public TaskList( @NotNull @Size(min = 1, max = 60) String name, int priority) {
		super();
		//this.id = id;
		this.name = name;
		this.priority = priority;
	}
	public TaskList(@NotNull @Size(min = 1, max = 60) String name) {
		super();
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaskList other = (TaskList) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
			return false;
		if (tasks == null) {
			if (other.tasks != null)
				return false;
		} else if (!tasks.equals(other.tasks))
			return false;
		return true;
	}
	
		
}
