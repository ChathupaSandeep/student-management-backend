package com.Academa.student_management;

import com.Academa.student_management.course.Course;
import com.Academa.student_management.course.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

public class CourseControllerTest extends TestContainerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CourseService courseService;

    private ObjectMapper objectMapper;

    @Test
    void testGetCourses() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/api/v1/course", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        System.out.println("Hello, World!");
    }

    @Test
    void testAddNewCourse() {
        Course course = new Course();
        course.setName("Test Course");
        course.setDuration(12);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Course> request = new HttpEntity<>(course, headers);

        ResponseEntity<Void> response = restTemplate.postForEntity("http://localhost:8081/api/v1/course", request, Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify the course was added
        ResponseEntity<Course[]> getResponse = restTemplate.getForEntity("http://localhost:8081/api/v1/course", Course[].class);
        assertTrue(getResponse.getBody().length > 0);
        boolean courseFound = false;
        for (Course c : getResponse.getBody()) {
            if (c.getName().equals("Test Course") && c.getDuration() == 12) {
                courseFound = true;
                break;
            }
        }
        assertTrue(courseFound, "Added course not found in GET response");
    }

    @Test
    void testDeleteCourse() {
        // add a course to delete
        Course course = new Course();
        course.setName("Course to Delete");
        course.setDuration(10);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Course> request = new HttpEntity<>(course, headers);

        ResponseEntity<Course> postResponse = restTemplate.postForEntity("http://localhost:8081/api/v1/course", request, Course.class);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());

        // Get the courses to find the ID of our test course
        ResponseEntity<Course[]> getResponse = restTemplate.getForEntity("http://localhost:8081/api/v1/course", Course[].class);
        Long courseIdToDelete = null;
        for (Course c : getResponse.getBody()) {
            if (c.getName().equals("Course to Delete")) {
                courseIdToDelete = c.getId();
                break;
            }
        }
        assertNotNull(courseIdToDelete, "Test course not found for deletion");

        // Delete the course
        restTemplate.delete("http://localhost:8081/api/v1/course" + "/" + courseIdToDelete);

        // Verify the course was deleted
        ResponseEntity<Course[]> verifyResponse = restTemplate.getForEntity("http://localhost:8081/api/v1/course", Course[].class);
        boolean courseStillExists = false;
        for (Course c : verifyResponse.getBody()) {
            if (c.getId().equals(courseIdToDelete)) {
                courseStillExists = true;
                break;
            }
        }
        assertFalse(courseStillExists, "Course was not successfully deleted");
    }
}
