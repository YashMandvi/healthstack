package com.example.demo;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RemarkServics {
	
	@Autowired
	private RemarkRepository remarkRepo;

	public List<Remark> getRemarksByUserId(long userId) {
        return remarkRepo.findByid(userId);
    }
	
	public Map<String, Object> getHighestDiseaseArea(List<Object[]> rawList) {
	    Object[] maxEntry = rawList.stream()
	        .max(Comparator.comparingLong(e -> (Long) e[2]))
	        .orElse(null);

	    if (maxEntry == null) return null;

	    Map<String, Object> result = new HashMap<>();
	    result.put("area", (String) maxEntry[0]);
	    result.put("disease", (String) maxEntry[1]);
	    result.put("count", maxEntry[2]);
	    return result;
	}

}
