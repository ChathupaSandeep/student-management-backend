package com.Academa.student_management.guardian;

import com.Academa.student_management.student.Student;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Guardian {
    @Id
    @SequenceGenerator(
            name="guardian_sequence",
            sequenceName = "guardian_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "guardian_sequence"
    )
    private Long id;
    private String name;
    private String contactNo;
}
