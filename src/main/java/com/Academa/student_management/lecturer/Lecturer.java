package com.Academa.student_management.lecturer;

import com.Academa.student_management.course.Course;
import com.Academa.student_management.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
public class Lecturer {
    @Id
    @SequenceGenerator(
            name="lecturer_sequence",
            sequenceName = "lecturer_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "lecturer_sequence"
    )
    private Long id;
    private String name;
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "lecturer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Course> courses = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    public void addCourse(Course course) {
        courses.add(course);
        course.setLecturer(this);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
        course.setLecturer(null);
    }
}