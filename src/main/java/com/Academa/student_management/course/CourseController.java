package com.Academa.student_management.course;

import com.Academa.student_management.student.Student;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/course")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> getCourses(){
        return courseService.getCourses();
    }

    @PostMapping
    public void addNewCourse(@RequestBody Course course){
        courseService.addNewCourse(course);
    }
}
