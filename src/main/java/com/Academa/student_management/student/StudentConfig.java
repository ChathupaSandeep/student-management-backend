package com.Academa.student_management.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student nimal = new Student("Nimal","nimal@gmail.com", LocalDate.of(2000, Month.AUGUST,11));
            Student kamal = new Student("Kamal","kamal@gmail.com", LocalDate.of(2002, Month.FEBRUARY,18));
            repository.saveAll(List.of(nimal,kamal));
        };
    }
}
