package com.qa.TDL_Project.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@ToString
//@EqualsAndHashCode
public class TaskListDTO {
	
	private Long id;
	private String name;
	private Integer priority;
	private List<TaskDTO> taskDTO = new ArrayList<>();

}
