package com.Academa.student_management.course;

import com.Academa.student_management.lecturer.Lecturer;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Course {
    @Id
    @SequenceGenerator(
            name="course_sequence",
            sequenceName = "course_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "course_sequence"
    )
    private Long id;
    private String name;
    private int duration;

    @ManyToOne
    @JoinColumn(name = "lecturer_id")
    private Lecturer lecturer;
}