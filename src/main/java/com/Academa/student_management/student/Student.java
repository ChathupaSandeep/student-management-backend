package com.Academa.student_management.student;

import com.Academa.student_management.course.Course;
import com.Academa.student_management.guardian.Guardian;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Student {
    @Id
    @SequenceGenerator(
            name="student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Long id;
    private String name;
    private String email;
    private LocalDate dob;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "guardian_id", referencedColumnName = "id")
    private Guardian guardian;

    @OneToMany
    private List<Course> courses = new ArrayList<>();

    public Integer getAge() {
        return Period.between(this.dob, LocalDateTime.now().toLocalDate()).getYears();
    }
}
