package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "doctor")
public class Doctor {
    @Id
    private int doctor_id;
    private String name;
    private String email;
    private String password;
    
    private String gender;
    private String specialization;
    private String experience;
    private String phone;
    private String address;
    private String hospital;
    private boolean verification; 
    
    
    public Doctor() {
    }


	


	public Doctor(int doctor_id, String name, String email, String password, String gender, String specialization,
			String experience, String phone, String address, String hospital, boolean verification) {
		super();
		this.doctor_id = doctor_id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.gender = gender;
		this.specialization = specialization;
		this.experience = experience;
		this.phone = phone;
		this.address = address;
		this.hospital = hospital;
		this.verification = verification;
	}





	public int getDoctor_id() {
		return doctor_id;
	}


	public void setDoctor_id(int doctor_id) {
		this.doctor_id = doctor_id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getSpecialization() {
		return specialization;
	}


	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}


	public String getExperience() {
		return experience;
	}


	public void setExperience(String experience) {
		this.experience = experience;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getHospital() {
		return hospital;
	}


	public void setHospital(String hospital) {
		this.hospital = hospital;
	}





	public boolean isVerification() {
		return verification;
	}





	public void setVerification(boolean verification) {
		this.verification = verification;
	}





	@Override
	public String toString() {
		return "Doctor [doctor_id=" + doctor_id + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", gender=" + gender + ", specialization=" + specialization + ", experience=" + experience
				+ ", phone=" + phone + ", address=" + address + ", hospital=" + hospital + ", verification="
				+ verification + "]";
	}


	
   
    
    
}