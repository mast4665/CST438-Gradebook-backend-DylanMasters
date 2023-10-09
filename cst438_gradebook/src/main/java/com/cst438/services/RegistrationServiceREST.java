package com.cst438.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cst438.domain.FinalGradeDTO;
import com.google.common.net.HttpHeaders;
import com.google.common.net.MediaType;
import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.EnrollmentDTO;
import com.cst438.domain.EnrollmentRepository;
import com.cst438.domain.Enrollment;

@Service
@ConditionalOnProperty(prefix = "registration", name = "service", havingValue = "rest")
@RestController
public class RegistrationServiceREST implements RegistrationService {

	
	RestTemplate restTemplate = new RestTemplate();
	
	@Value("${registration.url}") 
	String registration_url;
	
	public RegistrationServiceREST() {
		System.out.println("REST registration service ");
	}
	
	@Override
	public void sendFinalGrades(int course_id , FinalGradeDTO[] grades) { 
		
		//TODO use restTemplate to send final grades to registration service
		// Create a RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        
        // Set URL for PUT 
        String url = registration_url + "/course/" + course_id + "/finalgrades";
        

//        
        // Create an HttpEntity with the grades and headers
        HttpEntity<FinalGradeDTO[]> requestEntity = new HttpEntity<>(grades);
        
        try {
            // Send the PUT request to the registration service
        	ResponseEntity<String> response = restTemplate.exchange(
                    url, HttpMethod.PUT, requestEntity, String.class);            // Handle the response as needed
            if (response.getStatusCode().is2xxSuccessful()) {
                // Request was successful, you can handle the response body if needed
                String responseBody = response.getBody();
                // Process the response here
            } else {
                // Handle the error response (e.g., log or throw an exception)
                System.err.println("Error: " + response.getStatusCode());
            }
        } catch (Exception e) {
            // Handle any exceptions (e.g., network issues)
            e.printStackTrace();
        }
    }
	
	
	@Autowired
	CourseRepository courseRepository;

	@Autowired
	EnrollmentRepository enrollmentRepository;

	
	/*
	 * endpoint used by registration service to add an enrollment to an existing
	 * course.
	 */
	@PostMapping("/enrollment")
	@Transactional
	public EnrollmentDTO addEnrollment(@RequestBody EnrollmentDTO enrollmentDTO) {
		
		// Receive message from registration service to enroll a student into a course.
		
		System.out.println("GradeBook addEnrollment "+enrollmentDTO);
		
		if (enrollmentDTO.studentName() == null || enrollmentDTO.courseId() == 0) {
			//error handle
            throw new IllegalArgumentException("StudentId and CourseId are required.");
        }
		return enrollmentDTO;
		
	}

}
