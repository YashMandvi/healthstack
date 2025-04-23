package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "admin")
public class Admin {
	
	@Id
	private String admin_Id;
	private String name;
	private String Password;
	
	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Admin(String admin_Id, String name, String password) {
		super();
		this.admin_Id = admin_Id;
		this.name = name;
		Password = password;
	}

	public String getAdmin_Id() {
		return admin_Id;
	}

	public void setAdmin_Id(String admin_Id) {
		this.admin_Id = admin_Id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	@Override
	public String toString() {
		return "Admin [admin_Id=" + admin_Id + ", name=" + name + ", Password=" + Password + "]";
	}

	
	
	
	

}
