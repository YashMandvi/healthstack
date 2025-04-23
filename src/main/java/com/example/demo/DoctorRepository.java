package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    Doctor findByEmailAndPassword(String email, String password);
    boolean existsByEmail(String email);
    List<Doctor> findByVerificationFalse();
    
    
        List<Doctor> findBySpecialization(String specialization);
    


}