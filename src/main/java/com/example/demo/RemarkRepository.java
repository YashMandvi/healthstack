package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RemarkRepository extends JpaRepository<Remark, Long>  {
	 List<Remark> findByHealthIdOrderByTimestampDesc(String healthId);
	 
	 @Query("SELECT r.doctorId, COUNT(r.doctorId) as count FROM Remark r GROUP BY r.doctorId ORDER BY count DESC")
	    List<Object[]> findTopDoctorByRemarks();
	    
	    @Query("SELECT r.doctorId, COUNT(DISTINCT r.healthId) " +
	    	       "FROM Remark r GROUP BY r.doctorId")
	    	List<Object[]> countPatientsPerDoctor();
	    	
	    	@Query("SELECT d.hospital, COUNT(DISTINCT r.healthId) " +
	    		       "FROM Remark r JOIN Doctor d ON r.doctorId = d.doctor_id " +
	    		       "GROUP BY d.hospital")
	    		List<Object[]> countPatientsPerHospital();
	    		
	    		List<Remark> findByid(Long userId); 
	    		
	    		 List<Remark> findByDesease(String desease);
	    		 
	    		 @Query("SELECT r.desease, COUNT(r) FROM Remark r GROUP BY r.desease ORDER BY COUNT(r) DESC")
	    		    List<Object[]> countRemarksByDisease();
	    		    
	    		    @Query("SELECT r.doctorId, r.doctorName, COUNT(r) FROM Remark r GROUP BY r.doctorId, r.doctorName")
	    		    List<Object[]> getDoctorWisePatientCount();
	    		    
	    		    @Query("SELECT DATE(r.timestamp), COUNT(r) FROM Remark r GROUP BY DATE(r.timestamp) ORDER BY DATE(r.timestamp)")
	    		    List<Object[]> getPatientTrendByDate();
	    		    
	    		    @Query("SELECT r.doctorAdd, r.desease, COUNT(r) FROM Remark r GROUP BY r.doctorAdd, r.desease ORDER BY r.doctorAdd, COUNT(r) DESC")
	    		    List<Object[]> getAreaWiseDiseaseMapping();


}
