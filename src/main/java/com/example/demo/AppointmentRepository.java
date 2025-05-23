package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment , Integer> {
	
	List<Appointment> findByPatientId(String patientId);
	List<Appointment> findByDoctorId(int DoctorId);


}
