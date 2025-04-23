package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepo;
	
	void saveUser(User u) {
		userRepo.save(u);
	}
	
	User getUser(int id) {
		return userRepo.getById(id);
	}
	
	User getUserByEmail(String email) {
		return userRepo.getByEmail(email);
	}
	
	 public long getTotalUserCount() {
	        return userRepo.count();
	    }
	 
	 public List<User> getAllUsers() {
	        return userRepo.findAll();
	    }
	 
	 public void removeUser(int id) {
	        userRepo.deleteById(id);
	    }
	 

	    public User loginUser(String email, String healthId) {
	        return userRepo.findByemailAndHealthId(email, healthId);
	    }
	    

}
