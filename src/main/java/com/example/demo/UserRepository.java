package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User , Integer> {

	User getByEmail(String email);
	Optional<User> findByHealthId(String healthId);
	
	User findByemailAndHealthId(String email, String healthId);
	
	 List<User> findByState(String state);
	    List<User> findByStateAndDistrict(String state, String district);
	    List<User> findByStateAndDistrictAndTehsil(String state, String district, String tehsil);
	    List<User> findByStateAndDistrictAndTehsilAndVillage(String state, String district, String tehsil, String village);

}
