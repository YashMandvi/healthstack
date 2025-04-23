package com.example.demo;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorDashboardService {

    @Autowired private UserRepository userRepo;
    @Autowired private RemarkRepository remarkRepo;
    @Autowired private DoctorRepository doctorRepo;

    public Optional<User> getUserByHealthId(String healthId) {
        return userRepo.findByHealthId(healthId);
    }

    public List<Remark> getRemarksForHealthId(String healthId) {
        return remarkRepo.findByHealthIdOrderByTimestampDesc(healthId);
    }

    public void saveRemark(String healthId, int doctorId, String doctorNmae, String doctorAdd, String desease, String remarkText) {
        Remark remark = new Remark();
        remark.setHealthId(healthId);
        remark.setDoctorId(doctorId);
        remark.setDoctorName(doctorNmae);
        remark.setDoctorAdd(doctorAdd);
        remark.setDesease(desease);
        remark.setRemarks(remarkText);
        remark.setTimestamp(LocalDateTime.now());
        remarkRepo.save(remark);
    }

    public String getDoctorOfTheWeek() {
        List<Object[]> result = remarkRepo.findTopDoctorByRemarks();
        if (result.isEmpty()) return "No data available";

        Object[] topDoctor = result.get(0);
        Number doctorIdNumber = (Number) topDoctor[0];
        int doctorId = doctorIdNumber.intValue();

        return doctorRepo.findById(doctorId)
                         .map(Doctor::getName)
                         .orElse("Unknown Doctor");
    }
    
    public Map<String, Long> getPatientsPerDoctor() {
        List<Object[]> data = remarkRepo.countPatientsPerDoctor();
        Map<String, Long> map = new LinkedHashMap<>();

        for (Object[] obj : data) {
            Number doctorIdNumber = (Number) obj[0];
            int doctorId = doctorIdNumber.intValue();

            String doctorName = doctorRepo.findById(doctorId)
                                          .map(Doctor::getName)
                                          .orElse("Unknown");

            Long patientCount = ((Number) obj[1]).longValue();
            map.put(doctorName, patientCount);
        }

        return map;
    }
    
    public Map<String, Long> getPatientsPerHospital() {
        List<Object[]> result = remarkRepo.countPatientsPerHospital();
        Map<String, Long> map = new LinkedHashMap<>();
        for (Object[] row : result) {
            String hospital = (String) row[0];
            Long count = (Long) row[1];
            map.put(hospital, count);
        }
        return map;
    }


}

