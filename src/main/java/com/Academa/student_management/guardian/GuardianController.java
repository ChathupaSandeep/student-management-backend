package com.Academa.student_management.guardian;

import com.Academa.student_management.guardian.Guardian;
import com.Academa.student_management.guardian.GuardianService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/guardian")
public class GuardianController {
    private final GuardianService guardianService;

    public GuardianController(GuardianService guardianService) {
        this.guardianService = guardianService;
    }

    @GetMapping
    public List<Guardian> getGuardians(){
        return guardianService.getGuardians();
    }

    @PostMapping
    public void addNewGuardian(@RequestBody Guardian guardian){
        guardianService.addNewGuardian(guardian);
    }

    @DeleteMapping(path="{guardianId}")
    public void deleteGuardian(@PathVariable("guardianId") Long guardianId){
        guardianService.deleteGuardian(guardianId);
    }

    @PutMapping(path = "{guardianId}")
    public void updateGuardian(@PathVariable("guardianId") Long guardianId, @RequestBody Guardian guardian) {
        String name = guardian.getName();
        String contactNo = guardian.getContactNo();
        guardianService.updateGuardian(guardianId, name, contactNo);
    }
}
