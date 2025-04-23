package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin , String> {
	
	@Query("SELECT a FROM Admin a WHERE a.admin_Id = :adminId AND a.Password = :password")
	Admin findByAdminIdAndPassword(@Param("adminId") String adminId, @Param("password") String password);
}
