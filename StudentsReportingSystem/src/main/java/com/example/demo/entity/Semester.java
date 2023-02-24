package com.example.demo.entity;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Semester {
		
		//Only I & II semesters are there
	    private Integer semesterNo;
	    private Integer English;
	    private Integer Maths;
	    private Integer Science;
	    	    
}