package com.Academa.student_management.lecturer;

import com.Academa.student_management.enums.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/lecturer")
public class LecturerController {

    private final LecturerService lecturerService;

    @Autowired
    public LecturerController(LecturerService lecturerService) {
        this.lecturerService = lecturerService;
    }

    @GetMapping
    public List<Lecturer> getLecturers() {
        return lecturerService.getLecturers();
    }

    @PostMapping
    public void registerNewLecturer(@RequestBody Lecturer lecturer) {
        lecturerService.addNewLecturer(lecturer);
    }

    @DeleteMapping(path="{lecturerId}")
    public void deleteLecturer(@PathVariable("lecturerId") Long lecturerId){
        lecturerService.deleteLecturer(lecturerId);
    }

    @PutMapping(path = "{lecturerId}")
    public void updateLecturer(@PathVariable("lecturerId") Long lecturerId, @RequestBody Lecturer lecturer) {
        String name = lecturer.getName();
        String email = lecturer.getEmail();
        Gender gender = lecturer.getGender();
        lecturerService.updateLecturer(lecturerId, name, email, gender);
    }

    @PatchMapping("/enroll/{lecturerId}/{courseId}")
    public void enrollLecturer(@PathVariable Long lecturerId, @PathVariable Long courseId) {
        lecturerService.enrollLecturer(courseId, lecturerId);
    }
}
