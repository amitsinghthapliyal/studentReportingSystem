package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Semester;
import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepo;

@Service
public class StudentService{
	
    @Autowired
	private StudentRepo studentRepo;
    
    private final String indexName = "students";

	
	public String addStudent(Student student) {
		studentRepo.save(student);
		return "Student added Successfully..!!";
		
	}
	public List<Student> getAllStudents() {
        try {
            Iterable<Student> students = studentRepo.findAll();
            List<Student> studentList = new ArrayList<>();
            for (Student s : students) {
                studentList.add(s);
            }
            return studentList;
        }catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return null;
    }
	
    public String addMarks(Integer studentRoll, Integer semNo, String subject, Integer marks) {

        try {
            Optional<Student> students = studentRepo.findById(studentRoll);

            if(students.isEmpty()){
                 return "No Student with Roll number : " + studentRoll;
            }else{

            Student student = students.get();
            List<Semester> semesterList = student.getSemesters();

            for(Semester semester : semesterList){

                if (semester.getSemesterNo() == semNo) {
                    if (subject.equals("English")) {
                    	semester.setEnglish(marks);
                    } else if (subject.equals("Maths")) {
                    	semester.setMaths(marks);
                    } else if (subject.equals("Science")) {
                    	semester.setScience(marks);
                    } else {
                        return "There is no Subject of "+subject+" in this semester";
                    }
                    break;
                }
            }
            student.setSemesters(semesterList);
            studentRepo.save(student);
            }
        }catch (Exception ex){
            System.out.println(ex.toString());
        }

        return "Marks added successfully..!";
    }

    public Student getStudentByRoll(Integer studentRoll) {

        try {
            Optional<Student> students = studentRepo.findById(studentRoll);

            if(students.isEmpty()) {
                throw new Exception("There is no student with this roll number : "+studentRoll);
            }

            Student student = students.get();
            return student;

        } catch(Exception ex) {
            System.out.println(ex.toString());
        }
        return null;
    }

    

    public String deleteStudent(Integer roll){
        try {
            studentRepo.deleteById(roll);
            return "Student Deleted with roll : "+roll;
        }catch (Exception ex){
            return ex.toString();
        }
    }


    public Double averagePercentageInSemester(int sem){
        Double averageMarks = null;

        try {
            List<Double> percentageList = new ArrayList<>();
            Iterable<Student> studentList = studentRepo.findAll();
            for(Student stu : studentList){
                Semester semester = stu.getSemesters().get(sem-1);
                Double sum = (double)semester.getEnglish() + semester.getMaths() + semester.getScience();
                Double percentage = sum/3;
                percentageList.add(percentage);
            }

            for(Double avg : percentageList){
                averageMarks += avg;
            }
            averageMarks = averageMarks/percentageList.size();
            return averageMarks;

        }catch (Exception e){
            System.out.println(e.toString());
        }

        return averageMarks;
    }


    public Map<Integer, Double> topTwoConsistentStudent(){

        Map<Integer, Double> map = new HashMap<>();
        try {
            Iterable<Student> studentList = studentRepo.findAll();
            for(Student stu : studentList){
                Semester sem1 = stu.getSemesters().get(0);
                Semester sem2 = stu.getSemesters().get(1);
                Double sum = Double.valueOf((sem1.getEnglish() + sem1.getMaths() + sem1.getScience()+
                        sem2.getEnglish() + sem2.getMaths() + sem2.getScience()));
                Double max = sum/2;
                map.put(stu.getRoll_no(), max);
            }
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
        return map;
    }

}
