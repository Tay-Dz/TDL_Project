package com.qa.TDL_Project.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@ToString
@EqualsAndHashCode
public class TaskListDTO {
	
	private Long id;
	private String name;
	private Integer priority;
	private List<TaskDTO> tasks= new ArrayList<>();
	public TaskListDTO(Long id, String name,Integer priority) {
		super();
		this.id = id;
		this.name = name;
		this.priority=priority;
	}
	
	
	
}
