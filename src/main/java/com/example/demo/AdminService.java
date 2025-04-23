package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
	
	@Autowired
	private AdminRepository adminRepo;
	
	public void saveAdmin() {
		Admin a = new Admin();
		a.setAdmin_Id("admin1");
		a.setName("admin1");
		a.setPassword("admin123");
		adminRepo.save(a);
		
		Admin b = new Admin();
		a.setAdmin_Id("admin2");
		a.setName("admin2");
		a.setPassword("admin123");
		adminRepo.save(a);
	}
	
	 public Admin loginAdmin(String adminId, String Password) {
	        return adminRepo.findByAdminIdAndPassword(adminId, Password);
	    }

}
