package com.Academa.student_management.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @PostMapping
    public void registerNewStudent(@RequestBody Student student) {
        studentService.addNewStudent(student);
    }

    @DeleteMapping(path="{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId){
        studentService.deleteStudent(studentId);
    }

    @PutMapping(path = "{studentId}")
    public void updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email) {
        studentService.updateStudent(studentId, name, email);
    }

    @PatchMapping("/enroll/{studentId}/{courseId}")
    public void enrollStudent(@PathVariable Long studentId, @PathVariable Long courseId) {
        studentService.enrollStudent(courseId, studentId);
    }

    @PostMapping("/assign-guardian/{studentId}/{guardianId}")
    public void assignGuardian(
            @PathVariable Long studentId,
            @PathVariable Long guardianId) {
        studentService.assignGuardianToStudent(studentId, guardianId);
    }

}
