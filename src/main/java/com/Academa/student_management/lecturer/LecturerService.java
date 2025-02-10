package com.Academa.student_management.lecturer;

import com.Academa.student_management.enums.Gender;
import com.Academa.student_management.lecturer.Lecturer;
import com.Academa.student_management.student.Student;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LecturerService {
    private final LecturerRepository lecturerRepository;

    @Autowired
    public LecturerService(LecturerRepository lecturerRepository) {
        this.lecturerRepository = lecturerRepository;
    }

    public List<Lecturer> getLecturers() {return lecturerRepository.findAll();}

    public void addNewLecturer(Lecturer lecturer){
        lecturerRepository.save(lecturer);
    }

    public void deleteLecturer(Long lecturerId) {
        boolean exists = lecturerRepository.existsById(lecturerId);
        if (!exists){
            throw new IllegalStateException("lecturer with id "+lecturerId+" does not exists");
        }
        lecturerRepository.deleteById(lecturerId);
    }

    @Transactional
    public void updateLecturer(Long lecturerId, String name, String email, Gender gender) {
        Lecturer lecturer = lecturerRepository.findById(lecturerId)
                .orElseThrow(() -> new IllegalStateException("Lecturer with id " + lecturerId + " not found"));

        if (name != null && !name.isEmpty()) {
            lecturer.setName(name);
        } else {
            throw new IllegalStateException("Lecturer name can't be empty");
        }
        if (email != null && !email.isEmpty()) {
            Optional<Lecturer> lecturerOptional = lecturerRepository.findLecturerByEmail(email);
            lecturer.setEmail(email);
        } else {
            throw new IllegalStateException("Email can't be empty");
        }

        lecturer.setGender(gender);
    }
}