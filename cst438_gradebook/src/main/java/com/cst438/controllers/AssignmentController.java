package com.cst438.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Assignment;
import com.cst438.domain.AssignmentDTO;
import com.cst438.domain.AssignmentRepository;
import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.google.common.base.Optional;

@RestController
@CrossOrigin 
public class AssignmentController {
	
	@Autowired
	AssignmentRepository assignmentRepository;
	
	@Autowired
	CourseRepository courseRepository;
	
	@GetMapping("/assignment")
	public AssignmentDTO[] getAllAssignmentsForInstructor() {
		// get all assignments for this instructor
		String instructorEmail = "dwisneski@csumb.edu";  // user name (should be instructor's email) 
		List<Assignment> assignments = assignmentRepository.findByEmail(instructorEmail);
		AssignmentDTO[] result = new AssignmentDTO[assignments.size()];
		for (int i=0; i<assignments.size(); i++) {
			Assignment as = assignments.get(i);
			AssignmentDTO dto = new AssignmentDTO(
					as.getId(), 
					as.getName(), 
					as.getDueDate().toString(), 
					as.getCourse().getTitle(), 
					as.getCourse().getCourse_id());
			result[i]=dto;
		}
		return result;
	}
	
	// TODO create CRUD methods for Assignment
	@GetMapping("/assignment/{id}")
	public AssignmentDTO getAssignment(@PathVariable("id") int id)  {
		// check that assignment belongs to the instructor
		AssignmentDTO[] assignments = getAllAssignmentsForInstructor();
		AssignmentDTO assignment = null;
		for(int i = 0; i < assignments.length; i++) {
			if(assignments[i].id() == id) {
				assignment = assignments[i];
			}
		}
	    // return Assignment data for the given assignment 
	    // if assignment not found, return HTTP status code 404
		if(assignment == null) {
			return null;
		}else {
			return assignment;
		}
	}
	
	@PostMapping("/assignment")
	public int createAssignment(@RequestBody AssignmentDTO adto) {
		// check that course id in AssignmentDTO exists and belongs 
        //  to this instructor
		AssignmentDTO[] assignments = getAllAssignmentsForInstructor();
		for(int i = 0; i < assignments.length; i++) {
			if(assignments[i].id() == adto.id() ) {
				return 0;
			}
		}
		// then create a new Assignment Entity and save to database
		assignments[assignments.length] = adto;
		//setAssignments(assignments);
		// return the assignment id of the new assignment
		return(adto.id());
	}
	
	@PutMapping("/assignment/{id}")
	public void updateAssignment(
	      @PathVariable("id") int id, 
	      @RequestBody AssignmentDTO adto) {
		// check assignment belongs to a course for this instructor
		AssignmentDTO[] assignments = getAllAssignmentsForInstructor();
		for(int i = 0; i < assignments.length; i++){
			if(assignments[i].id() == id) {
				// update assignment with data in AssignmentDTO
				assignments[i] = adto;
			}
		}
		return;
	}
		
	@DeleteMapping("/assignment/{id}")
	public void deleteAssignment(
	       @PathVariable("id") int id, 
	       @RequestParam("force") Optional<String> force) {
		// check assignment belongs to this instructor
		AssignmentDTO[] assignments = getAllAssignmentsForInstructor();
		for( int i = 0; i < assignments.length; i++) {
			if(assignments[i].id() == id) {
				for(int k = i; k < assignments.length-1; k++) {
					assignments[k] = assignments[k+1];
				}
				assignments[assignments.length] = null;
				return;
			}
		}
		// delete assignment if there are no grades.
		// if there are grades for the assignment, delete the 
		// assignment only if "force" is specified
		return;
	}
	

}
