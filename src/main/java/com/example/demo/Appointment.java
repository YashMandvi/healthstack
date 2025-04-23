package com.example.demo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Appointment {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;

	    private int doctorId;        // Doctor ka ID
	    private String patientId;       // User/Patient ka ID

	    private LocalDate appointmentDate; // Future improvement: date select karna

	    private String status;        // Pending, Accepted, Rejected, Visited

	    private LocalDateTime requestTime; // Kab request ki gayi

	    // 🔥 Optional: Doctor or patient name cache me store karne ke liye
	    private String doctorName;
	    private String patientName;
	    private String doctorSpecialization;
	    
	    
	    
		public Appointment() {
			super();
			// TODO Auto-generated constructor stub
		}



		public Appointment(int id, int doctorId, String patientId, LocalDate appointmentDate, String status,
				LocalDateTime requestTime, String doctorName, String patientName) {
			super();
			this.id = id;
			this.doctorId = doctorId;
			this.patientId = patientId;
			this.appointmentDate = appointmentDate;
			this.status = status;
			this.requestTime = requestTime;
			this.doctorName = doctorName;
			this.patientName = patientName;
			
			
		}



		
		
		public String getDoctorSpecialization() {
			return doctorSpecialization;
		}



		public void setDoctorSpecialization(String doctorSpecialization) {
			this.doctorSpecialization = doctorSpecialization;
		}



		public int getId() {
			return id;
		}



		public void setId(int id) {
			this.id = id;
		}



		public int getDoctorId() {
			return doctorId;
		}



		public void setDoctorId(int doctorId) {
			this.doctorId = doctorId;
		}



		public String getPatientId() {
			return patientId;
		}



		public void setPatientId(String patientId) {
			this.patientId = patientId;
		}



		public LocalDate getAppointmentDate() {
			return appointmentDate;
		}



		public void setAppointmentDate(LocalDate appointmentDate) {
			this.appointmentDate = appointmentDate;
		}



		public String getStatus() {
			return status;
		}



		public void setStatus(String status) {
			this.status = status;
		}



		public LocalDateTime getRequestTime() {
			return requestTime;
		}



		public void setRequestTime(LocalDateTime requestTime) {
			this.requestTime = requestTime;
		}



		public String getDoctorName() {
			return doctorName;
		}



		public void setDoctorName(String doctorName) {
			this.doctorName = doctorName;
		}



		public String getPatientName() {
			return patientName;
		}



		public void setPatientName(String patientName) {
			this.patientName = patientName;
		}



		@Override
		public String toString() {
			return "Appointment [id=" + id + ", doctorId=" + doctorId + ", patientId=" + patientId
					+ ", appointmentDate=" + appointmentDate + ", status=" + status + ", requestTime=" + requestTime
					+ ", doctorName=" + doctorName + ", patientName=" + patientName + "]";
		}
		
		

	   
	}
	








