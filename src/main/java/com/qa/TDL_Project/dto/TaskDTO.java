package com.qa.TDL_Project.dto;

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
public class TaskDTO {

	private Long id;
	private String name;
	private Integer priority;
}
