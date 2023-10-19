package com.cst438.domain;

public record  EnrollmentDTO (int id, String studentEmail, String studentName, int courseId) {

	public Object getStudentId() {
		// TODO Auto-generated method stub
		return id;
	}
	
}
