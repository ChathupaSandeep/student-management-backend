package com.Academa.student_management.student;

import com.Academa.student_management.course.CourseService;
import com.Academa.student_management.student.StudentUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "api/v1/student")
public class StudentController {

    private final StudentService studentService;
    private final CourseService courseService;

    @Autowired
    public StudentController(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
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

    @PatchMapping(path = "{studentId}")
    public void updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestBody StudentUpdateRequest request) {
        studentService.updateStudent(studentId, request.getName(), request.getEmail(), request.getDob());
    }

    @PatchMapping("/assign-guardian/{studentId}/{guardianId}")
    public void assignGuardian(
            @PathVariable Long studentId,
            @PathVariable Long guardianId) {
        studentService.assignGuardianToStudent(studentId, guardianId);
    }

    @PostMapping("/{studentId}/courses/{courseId}")
    public void enrollStudent(
            @PathVariable Long studentId,
            @PathVariable Long courseId ){
        studentService.enrollStudent(courseId, studentId);
    }

    @DeleteMapping("/{studentId}/courses/{courseId}")
    public void unenrollStudent(
            @PathVariable Long studentId,
            @PathVariable Long courseId ){
        studentService.unenrollStudent(courseId, studentId);
    }

}
