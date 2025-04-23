package com.example.demo;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Remark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String healthId;
    private int doctorId;
    private String doctorName;
    private String doctorAdd;
    
    private String desease;
    private String remarks;

    private LocalDateTime timestamp;
    
    public Remark() {
    	
    }

	public Remark(Long id, String healthId, int doctorId, String desease, String remarks, LocalDateTime timestamp) {
		super();
		this.id = id;
		this.desease = desease;
		this.healthId = healthId;
		this.doctorId = doctorId;
		this.remarks = remarks;
		this.timestamp = timestamp;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHealthId() {
		return healthId;
	}
	
	
	

	public String getDoctorAdd() {
		return doctorAdd;
	}

	public void setDoctorAdd(String doctorAdd) {
		this.doctorAdd = doctorAdd;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public void setHealthId(String healthId) {
		this.healthId = healthId;
	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getDesease() {
		return desease;
	}

	public void setDesease(String desease) {
		this.desease = desease;
	}

	@Override
	public String toString() {
		return "Remark [id=" + id + ", healthId=" + healthId + ", doctorId=" + doctorId + ", desease=" + desease
				+ ", remarks=" + remarks + ", timestamp=" + timestamp + "]";
	}
	
	

	
	
	
    
    

    // Getters and Setters
}
