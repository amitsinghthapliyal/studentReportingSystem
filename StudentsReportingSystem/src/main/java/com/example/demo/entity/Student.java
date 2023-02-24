package com.example.demo.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Document(indexName = "students")
@Component
public class Student {

	@Id
	private Integer roll_no;
	private String name;
	private String email;
	private List<Semester> semesters;
	
}
