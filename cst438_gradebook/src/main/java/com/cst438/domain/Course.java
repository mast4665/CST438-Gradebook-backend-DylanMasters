package com.cst438.domain;

import java.util.List;
import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.security.auth.login.AccountNotFoundException;

@Entity
public class Course {
	
	@Id
	private int course_id;
	private String title;
	private String instructor;
	private int year;
	private String semester;
	
	@OneToMany(mappedBy="course")
	@OrderBy("studentName ASC")
	List<Enrollment> enrollments = new ArrayList<>();
	
	@OneToMany(mappedBy="course")
	List<Assignment> assignments = new ArrayList<>();
	
	public int getCourse_id() {
		return course_id;
	}
	public void setCourse_id(int course_id) {
		this.course_id = course_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getInstructor() {
		return instructor;
	}
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	
	
	public List<Enrollment> getEnrollments() {
		return enrollments;
	}
	public void setEnrollments(List<Enrollment> enrollments) {
		this.enrollments = enrollments;
	}
	
	public List<Assignment> getAssignments() {
		return assignments;
	}
	public void setAssignments(List<Assignment> assignments) {
		this.assignments = assignments;
	}
	@Override
	public String toString() {
		return "Course [course_id=" + course_id + ", title=" + title + ", instructor=" + instructor + ", year=" + year
				+ ", semester=" + semester + "]";
	}
	
//	public Assignment addAssignment(Course course, String name, Date dueDate) {
//        Assignment assignment = new Assignment();
//        assignment.setCourse(course);
//        assignment.setName(name);
//        assignment.setDueDate(dueDate);
//
//        // Save the assignment to the database
//        getAssignments().set(getAssignments().size(), assignment);
//        return(assignment);
//    }
	
	public Assignment addAssignment(Course course, String name, Date dueDate, Principal principal) {
	    // Check if the user is an instructor for the course
	    if (!isInstructor(course, principal.getName())) {
	        // Throw an exception or return an error response if unauthorized access
	        throw new SecurityException("You don't have permission to add assignments to this course.");
	    }

	    Assignment assignment = new Assignment();
	    assignment.setCourse(course);
	    assignment.setName(name);
	    assignment.setDueDate(dueDate);

	    // Save the assignment to the database
	    getAssignments().set(getAssignments().size(), assignment);
	    return assignment;
	}
	
	private boolean isInstructor(Course course, String name) {
		if(course.getInstructor() == name) {
			return true;
		}else {
			return false;
		}
}
//	public Assignment updateAssignment(int assignmentId, String name, Date dueDate) throws AccountNotFoundException {
//        Assignment assignment = AssignmentRepository.findById(getAssignments(), assignmentId)
//            .orElseThrow(() -> new AccountNotFoundException("Assignment not found"));
//
//        // Update assignment details
//        assignment.setName(name);
//        assignment.setDueDate(dueDate);
//
//        // Save the updated assignment to the database
//        getAssignments().set(getAssignments().size(), assignment);
//        return assignment;
//    }
	public Assignment updateAssignment(int assignmentId, String name, Date dueDate, Principal principal) {
	    Assignment assignment = AssignmentRepository.findById(getAssignments(), assignmentId)
	            .orElse(null);

	    if (assignment == null) {
	        // Handle case when assignment is not found
	        // You can return an error response or throw an exception
	        throw new SecurityException("Assignment not found");
	    }

	    // Check if the user is an instructor for the course to which the assignment belongs
	    if (!isInstructor(assignment.getCourse(), principal.getName())) {
	        // Throw an exception or return an error response indicating unauthorized access
	        throw new SecurityException("You don't have permission to update assignments for this course.");
	    }

	    // Update assignment details
	    assignment.setName(name);
	    assignment.setDueDate(dueDate);

	    // Save the updated assignment to the database
	    getAssignments().set(assignmentId, assignment);
	    return assignment;
	}

	
//	public void deleteAssignment(int assignmentId) {
//        Assignment assignment = AssignmentRepository.findById(getAssignments(),assignmentId)
//            .orElseThrow();
//
//        // Delete the assignment from the database
//        getAssignments().set(assignmentId, null);
//    }
	public void deleteAssignment(int assignmentId, Principal principal) {
	    Assignment assignment = AssignmentRepository.findById(getAssignments(), assignmentId)
	            .orElse(null);

	    if (assignment == null) {
	        // assignment is not found
	        throw new SecurityException("Assignment not found");
	    }

	    // Check if the user is an instructor for the course
	    if (!isInstructor(assignment.getCourse(), principal.getName())) {
	        // Throw an exception or return an error
	        throw new SecurityException("You don't have permission to delete assignments for this course.");
	    }

	    // Delete the assignment from the database
	    getAssignments().set(assignmentId, null);
	}

}
