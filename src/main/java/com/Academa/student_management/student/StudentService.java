package com.Academa.student_management.student;

import com.Academa.student_management.course.Course;
import com.Academa.student_management.course.CourseRepository;
import com.Academa.student_management.guardian.Guardian;
import com.Academa.student_management.guardian.GuardianRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final GuardianRepository guardianRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, GuardianRepository guardianRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.guardianRepository = guardianRepository;
        this.courseRepository = courseRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentByEmail = studentRepository.findStudentByEmail(student.getEmail());
        if (studentByEmail.isPresent()){
            throw new IllegalStateException("email taken");
        }
        studentRepository.save(student);
   }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists){
            throw new IllegalStateException("student with id "+studentId+" does not exists");
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email, LocalDate dob) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("Student with id " + studentId + " not found"));

        if (name != null && !name.isEmpty() && !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }

        if (email != null && !email.isEmpty() && !Objects.equals(student.getEmail(), email)) {
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if (studentOptional.isPresent() && !studentOptional.get().getId().equals(studentId)) {
                throw new IllegalStateException("Email is already taken");
            }
            student.setEmail(email);
        }

        if (dob != null) {
            student.setDob(dob);
        }
    }


    @Transactional
    public void assignGuardianToStudent(Long studentId, Long guardianId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("Student with id " + studentId + " not found"));
        Guardian guardian = guardianRepository.findById(guardianId)
                .orElseThrow(() -> new IllegalStateException("Student with id " + studentId + " not found"));

        student.setGuardian(guardian);
        studentRepository.save(student);
    }

    @Transactional
    public void enrollStudent(Long courseId, Long studentId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalStateException(
                        "Course with id " + courseId + " does not exist"
                ));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        "Student with id " + studentId + " does not exist"
                ));
        student.getCourses().add(course);
        studentRepository.save(student);
    }

    @Transactional
    public void unenrollStudent(Long courseId, Long studentId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalStateException(
                        "Course with id " + courseId + " does not exist"
                ));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        "Student with id " + studentId + " does not exist"
                ));
        student.getCourses().remove(course);
        studentRepository.save(student);
    }

}
