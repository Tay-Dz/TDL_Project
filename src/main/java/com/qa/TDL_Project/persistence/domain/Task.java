package com.qa.TDL_Project.persistence.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
//import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
//@ToString
//@EqualsAndHashCode
public class Task {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "task_name")
	@NotNull
	@Size(min = 1, max = 120)
	private String name;

	@Column(name = "priority")
	private Integer priority;
	
	@ManyToOne
	private TaskList taskList;

	public Task(@NotNull @Size(min = 1, max = 120) String name, Integer priority) {
		super();
		this.name = name;
		this.priority = priority;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
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
		if (taskList == null) {
			if (other.taskList != null)
				return false;
		} else if (!taskList.equals(other.taskList))
			return false;
		return true;
	}
	
	

}
