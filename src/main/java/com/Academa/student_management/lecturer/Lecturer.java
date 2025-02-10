package com.Academa.student_management.lecturer;

import com.Academa.student_management.enums.Gender;
import com.Academa.student_management.student.Student;
import jakarta.persistence.*;
import lombok.Data;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;
}