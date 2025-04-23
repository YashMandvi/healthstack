package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    public Doctor loginDoctor(String email, String password) {
        return doctorRepository.findByEmailAndPassword(email, password);
    }
    
    public boolean checkDoctorExist(String email) {
    	return doctorRepository.existsByEmail(email);
    }
    
    void saveDoctor(Doctor d) {
		doctorRepository.save(d);
	}
    
    public Optional<Doctor> getDoctorById(int doctor_id) {
    	return doctorRepository.findById(doctor_id);
    }
    
    public List<Doctor> getAllPendingDoctors() {
        return doctorRepository.findByVerificationFalse();
    }

    public void verifyDoctor(int id) {
        Doctor doctor = doctorRepository.findById(id).orElse(null);
        if (doctor != null) {
            doctor.setVerification(true);
            doctorRepository.save(doctor);
        }
    }

    public void rejectDoctor(int id) {
        doctorRepository.deleteById(id); // or set a rejected flag
    }
    
    public long getTotalDoctorCount() {
        return doctorRepository.count();
    }
    
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
    
    public void removeDoctor(int id) {
        doctorRepository.deleteById(id);
    }
    
    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        return doctorRepository.findBySpecialization(specialization);
    }


    
}
