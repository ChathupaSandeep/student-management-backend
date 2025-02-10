package com.Academa.student_management.guardian;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuardianService {
    private final GuardianRepository guardianRepository;

    @Autowired
    public GuardianService(GuardianRepository guardianRepository) {
        this.guardianRepository = guardianRepository;
    }

    public List<Guardian> getGuardians() {return guardianRepository.findAll();}

    public void addNewGuardian(Guardian guardian){
        guardianRepository.save(guardian);
    }

    public void deleteGuardian(Long guardianId) {
        boolean exists = guardianRepository.existsById(guardianId);
        if (!exists){
            throw new IllegalStateException("guardian with id "+guardianId+" does not exists");
        }
        guardianRepository.deleteById(guardianId);
    }

    @Transactional
    public void updateGuardian(Long guardianId, String name, String contactNo) {
        Guardian guardian = guardianRepository.findById(guardianId)
                .orElseThrow(() -> new IllegalStateException("Guardian with id " + guardianId + " not found"));

        if (name != null && !name.isEmpty()) {
            guardian.setName(name);
        } else{
            throw new IllegalStateException("Guardian name can't be empty");
        }
        if (contactNo.length() >= 10) {
            guardian.setContactNo(contactNo);
        } else{
            throw new IllegalStateException("Please input a valid contact Number");
        }
    }
}