package com.cst438.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AssignmentRepository extends CrudRepository <Assignment, Integer> {

	@Query("select a from Assignment a where a.course.instructor= :email order by a.id")
	List<Assignment> findByEmail(@Param("email") String email);

	static Optional<Assignment> findById(List<Assignment> assignments, int assignmentId) {
		for(int i = 0; i < assignments.size(); i++) {
			if(assignments.get(i).getId() == assignmentId) {
				return Optional.ofNullable((assignments.get(i)));
			}
		}
		return null;
	}

	
}
