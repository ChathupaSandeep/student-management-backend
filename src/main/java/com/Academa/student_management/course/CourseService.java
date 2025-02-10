package com.Academa.student_management.course;

import com.Academa.student_management.student.Student;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getCourses() {return courseRepository.findAll();}

    public void addNewCourse(Course course){
        courseRepository.save(course);
    }

    public void deleteCourse(Long courseId) {
        boolean exists = courseRepository.existsById(courseId);
        if (!exists){
            throw new IllegalStateException("course with id "+courseId+" does not exists");
        }
        courseRepository.deleteById(courseId);
    }

    @Transactional
    public void updateCourse(Long courseId, String name, int duration) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalStateException("Course with id " + courseId + " not found"));

        if (name != null && !name.isEmpty()) {
            course.setName(name);
        } else{
            throw new IllegalStateException("Course name can't be empty");
        }
        if (duration > 0) {
            course.setDuration(duration);
        } else{
            throw new IllegalStateException("Course duration can't be less than 0");
        }
    }
}
