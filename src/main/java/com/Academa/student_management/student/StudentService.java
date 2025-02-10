package com.Academa.student_management.student;

import com.Academa.student_management.course.Course;
import com.Academa.student_management.course.CourseRepository;
import com.Academa.student_management.guardian.Guardian;
import com.Academa.student_management.guardian.GuardianRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final GuardianRepository guardianRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, GuardianRepository guardianRepository) {
        this.studentRepository = studentRepository;
        this.guardianRepository = guardianRepository;
    }

    @Autowired
    private CourseRepository courseRepository;

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
    public void updateStudent(Long studentId, String name, String email) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("Student with id " + studentId + " not found"));

        if (name != null && !name.isEmpty() && !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }

        if (email != null && !email.isEmpty() && !Objects.equals(student.getEmail(), email)) {
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if (studentOptional.isPresent()) {
                throw new IllegalStateException("Email is already taken");
            }
            student.setEmail(email);
        }
    }

    public void enrollStudent(Long courseId, Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("Student with id " + studentId + " not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        student.setCourse(course);
        studentRepository.save(student);
    }

    @Transactional
    public void assignGuardianToStudent(Long studentId, Long guardianId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("Student with id " + studentId + " not found"));
        Guardian guardian = guardianRepository.findById(guardianId)
                .orElseThrow(() -> new IllegalStateException("Student with id " + studentId + " not found"));

        if (student.getGuardian() != null) {
            throw new IllegalStateException("Student already has a guardian assigned");
        }

        student.setGuardian(guardian);
        studentRepository.save(student);
    }
}
