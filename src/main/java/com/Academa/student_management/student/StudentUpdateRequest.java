package com.Academa.student_management.student;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentUpdateRequest {
    private String name;
    private String email;
    private LocalDate dob;
}
