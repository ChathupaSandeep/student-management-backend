package com.Academa.student_management.guardian;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuardianRepository extends JpaRepository<Guardian, Long> {
    @Override
    Optional<Guardian> findById(Long id);
}