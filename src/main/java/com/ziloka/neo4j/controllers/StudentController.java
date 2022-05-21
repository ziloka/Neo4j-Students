package com.ziloka.neo4j.controllers;



import com.ziloka.neo4j.students.StudentDetailsDto;
import com.ziloka.neo4j.students.StudentService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class StudentController {
	
	@Autowired
	StudentService studentService;

	
    @GetMapping("/searchOneStudent")
    public List<StudentDetailsDto> searchOneStudent(@RequestParam("q") String name) {

    	studentService.setStudentService();
		return studentService.getStudentByName(name);
		
		
       
    }
    
	@GetMapping("/graph")
	public Map<String, List<Object>> getGraph(@RequestParam("q") String title) {
		return studentService.fetchStudentGraph(title);
		//return null;
	}
	


    @GetMapping("/student/create")
    public void createStudents() {
//     	GetPropertiesBean propertiesBean = new GetPropertiesBean ();
//     	StudentRepository studentRepository = null;
//		try {
//			studentRepository = StudentRepository.getInstance(
//					propertiesBean.getDatabaseURL(),
//					propertiesBean.getDatabaseUserName(), 
//					propertiesBean.getDatabasePassword()
//			);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    	studentService.populate();
    	
    }
    
    private static String stripWildcards(String title) {
		String result = title;
		if (result.startsWith("*")) {
			result = result.substring(1);
		}
		if (result.endsWith("*")) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}
}
