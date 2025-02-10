package com.Academa.student_management.lecturer;

import com.Academa.student_management.guardian.Guardian;
import com.Academa.student_management.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
    @Override
    Optional<Lecturer> findById(Long id);
    Optional<Lecturer> findLecturerByEmail(String email);
}
