package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.EmployDAO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Employ;
import com.example.demo.repo.EmployRepository;

@Service
@Transactional
public class EmployService {
	
	@Autowired
	private EmployRepository employRepository;

	public List<Employ> getAllEmploysService() {
		return employRepository.findAll();
	}

	public EmployDAO createEmployeService(Employ employData) {
		Employ employ = employRepository.findByEmailId(employData.getEmailId());
		
		EmployDAO employDAO = new EmployDAO();
		
		if(employ == null) {
			Employ saveEmploy = employRepository.save(employData);
			
			if(saveEmploy != null) {
				employDAO.setId(saveEmploy.getId());
				employDAO.setFirstName(saveEmploy.getFirstName());
				employDAO.setLastName(saveEmploy.getLastName());
				employDAO.setEmailId(saveEmploy.getEmailId());
				employDAO.setMessage("Employee created successfully");
			}
			else {
				employDAO.setMessage("Error");
			}
		}
		else  {
			employDAO.setMessage("Employee already exist");
		}
		return employDAO;
	}

	public ResponseEntity<Employ> getEmployById(Long id) {
		Employ employ = employRepository.findById(id).orElseThrow(()
				-> new ResourceNotFoundException("Employee not exist with id : "+id));
		return ResponseEntity.ok(employ);
	}

	public String updateEmployService(Long id, Employ employDetails) {
		Employ employ = employRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id : " + id));
		if (employ != null) {
			Employ employ2 = employRepository.findByEmailId(employDetails.getEmailId());
			if (employ2==null || employ2.getId() == employ.getId()) {
				employ.setFirstName(employDetails.getFirstName());
				employ.setLastName(employDetails.getLastName());
				employ.setEmailId(employDetails.getEmailId());
				// employRepository.save(employ);
				return "success";
			} else {
				return "EmailId already exist";
			}
		} else {
			return "Employee Not Found";
		}
	}

	public ResponseEntity<Map<String, Boolean>> deleteEmployService(Long id) {
		Employ employ = employRepository.findById(id).orElseThrow(()
			-> new ResourceNotFoundException("Employee not exist with id : "+id));
			employRepository.delete(employ);
			Map<String, Boolean> response = new HashMap<>();
			response.put("deleted", Boolean.TRUE);
			return ResponseEntity.ok(response);
	}

}
