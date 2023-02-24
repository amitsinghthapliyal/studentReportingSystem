package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepo;
import com.example.demo.service.StudentService;




@Controller
public class StudentController {
	
	   @Autowired
	   private StudentService studentService;

	    @Autowired
	    private StudentRepo studentRepo;
	   
	   @PostMapping("/addStudent")
	    public String addStudentByName(@RequestBody() Student student){

	        String res = studentService.addStudent(student);
	        return "redirect:/";
	    }



	    @GetMapping("/")
	    public ModelAndView indexFile(){
	        ModelAndView model = new ModelAndView("index");
	        List<Student> studentList = studentService.getAllStudents();
	        model.addObject("students", studentList);

	        Double averageClass1 = studentService.averagePercentageInSemester(1);
	        Double averageClass2 = studentService.averagePercentageInSemester(2);

	        Map<Integer, Double> map = studentService.topTwoConsistentStudent();
	        Integer studentRoll = null;
	        Integer studentRoll2 = null;
	        Double score1 = 0.0;
	        Double score2 = 0.0;
	        for(Entry<Integer, Double> entry : map.entrySet()){
	            if(entry.getValue() > score1){
	            	score2 = score1;
	            	score1 = entry.getValue();
	                studentRoll2 = studentRoll;
	                studentRoll = entry.getKey();
	            }else if(entry.getValue() > score2){
	            	score2 = entry.getValue();
	                studentRoll2 = entry.getKey();
	            }
	        }

	         Optional<Student> student1 = studentRepo.findById(studentRoll);
	         String firstStudentName = student1.get().getName();

	         Optional<Student> student2 = studentRepo.findById(studentRoll2);
	         String secondStudentName = student2.get().getName();

	        model.addObject("averageClass1", averageClass1);
	        model.addObject("averageClass2", averageClass2);

	        model.addObject("top1", studentRoll);
	        model.addObject("top2", studentRoll2);
	        model.addObject("top1Score", score1);
	        model.addObject("top2Score", score2);
	        model.addObject("top1Name", firstStudentName);
	        model.addObject("top2Name", secondStudentName);

	        return model;
	    }


	    @PostMapping("/addMarks")
	    public String addMarksToStudent(@RequestParam("rollNo") Integer rollNo,
	    		                        @RequestParam("semNo") Integer semNo, 
	    		                        @RequestParam("Maths") Integer Maths,  
	    		                        @RequestParam("English") Integer English, 
	    		                        @RequestParam("Science") Integer Science){

	        try {
	            if(English>0){
	                studentService.addMarks(rollNo, semNo, "English", English);
	            }else if(Science>0){
	                studentService.addMarks(rollNo, semNo, "Science", Science);
	            }else{
	                studentService.addMarks(rollNo, semNo, "Maths", Maths);
	            }

	        }catch (Exception ex){
	            return ex.toString();
	        }

	        return "redirect:/";
	    }



	    @DeleteMapping("/deleteStudent")
	    public String deleteStudentByID(@RequestParam("roll") Integer roll){
	        try {
	            String res = studentService.deleteStudent(roll);
	        }
	        catch (Exception ex){
	            System.out.println(ex.toString());
	            return (ex.toString());
	        }
	        return "redirect:/";
	    }

	    

	    @RequestMapping("/home")
	    public ModelAndView home(){
	        ModelAndView model = new ModelAndView("home");
	        model.addObject("message", "this.message");
	        System.out.println("Home Section");
	        return model;
	    }


	    @RequestMapping("/save")
	    public String saveStudent() throws IOException {
	        return "save";
	    }

	    @RequestMapping("/find")
	    public String find(Model model){
	        return "find";
	    }

}
