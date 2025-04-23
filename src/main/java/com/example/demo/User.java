package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;

private String email; // ✅ Primary Key

private String healthId; // ✅ Unique Health ID

private String firstName;
private String lastName;
private int age;
private String gender;
private String fhname;
private String address;
private String state;
private String district;
private String village;
private String tehsil;

public User() {
	super();
	// TODO Auto-generated constructor stub
}



public User(int id, String email, String healthId, String firstName, String lastName, int age, String gender,
		String fhname, String address, String state, String district, String village, String tehsil) {
	super();
	this.id = id;
	this.email = email;
	this.healthId = healthId;
	this.firstName = firstName;
	this.lastName = lastName;
	this.age = age;
	this.gender = gender;
	this.fhname = fhname;
	this.address = address;
	this.state = state;
	this.district = district;
	this.village = village;
	this.tehsil = tehsil;
}



public int getId() {
	return id;
}



public void setId(int id) {
	this.id = id;
}



public String getEmail() {
	return email;
}



public void setEmail(String email) {
	this.email = email;
}



public String getHealthId() {
	return healthId;
}



public void setHealthId(String healthId) {
	this.healthId = healthId;
}



public String getFirstName() {
	return firstName;
}



public void setFirstName(String firstName) {
	this.firstName = firstName;
}



public String getLastName() {
	return lastName;
}



public void setLastName(String lastName) {
	this.lastName = lastName;
}



public int getAge() {
	return age;
}



public void setAge(int age) {
	this.age = age;
}



public String getGender() {
	return gender;
}



public void setGender(String gender) {
	this.gender = gender;
}



public String getFhname() {
	return fhname;
}



public void setFhname(String fhname) {
	this.fhname = fhname;
}



public String getAddress() {
	return address;
}



public void setAddress(String address) {
	this.address = address;
}



public String getState() {
	return state;
}



public void setState(String state) {
	this.state = state;
}



public String getDistrict() {
	return district;
}



public void setDistrict(String district) {
	this.district = district;
}



public String getVillage() {
	return village;
}



public void setVillage(String village) {
	this.village = village;
}



public String getTehsil() {
	return tehsil;
}



public void setTehsil(String tehsil) {
	this.tehsil = tehsil;
}



@Override
public String toString() {
	return "User [id=" + id + ", email=" + email + ", healthId=" + healthId + ", firstName=" + firstName + ", lastName="
			+ lastName + ", age=" + age + ", gender=" + gender + ", fhname=" + fhname + ", address=" + address
			+ ", state=" + state + ", district=" + district + ", village=" + village + ", tehsil=" + tehsil + "]";
}


}
