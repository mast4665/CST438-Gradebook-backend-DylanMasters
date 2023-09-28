package com.cst438.services;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.base.Optional;

public class StudentDTO {
	@GetMapping("/student")
	public StudentDTO[] getStudents() {
		return null;
		// return data for all students 
	}
	
	
	@GetMapping("/student/{id}")
	public StudentDTO getStudent(@PathVariable("id") int id) {
		return null;
		// return student data given a student id 
	// return HTTP status 404 if not found 
	}
	
	
	@PutMapping("/student/{id}") 
	public void updateStudent(@PathVariable("id")int id, 
	                          @RequestBody StudentDTO sdto) {
		// update/replace student with data in StudentDTO
	}
	
	
	@PostMapping("/student")
	public int createStudent(@RequestBody StudentDTO sdto) {
		return 0;
		//  create new student with data in StudentDTO. 
	//  return the new student id 
	}
	
	
	@DeleteMapping("/student/{id}")
	public void deleteStudent(@PathVariable("id") int id, 
	                       @RequestParam("force") Optional<String> force) {
		// delete a student 
	// if student has enrollments, 
	// then delete the student only if "force" parameter is specified
	}
	

}
