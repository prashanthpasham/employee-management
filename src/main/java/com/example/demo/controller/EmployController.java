package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.EmployDAO;
import com.example.demo.model.Employ;
import com.example.demo.service.EmployService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class EmployController {
	
	@Autowired
	private EmployService employService;
	
	@GetMapping("/employs")
	public List<Employ> getAllEmploys(){
		return employService.getAllEmploysService();
	}
	
	@PostMapping("/employs")
	public EmployDAO createEmploye(@RequestBody Employ employData) {
		return employService.createEmployeService(employData);
	}
	
	@GetMapping("/employs/{id}")
	public ResponseEntity<Employ> getEmployById(@PathVariable Long id){
		return employService.getEmployById(id);
	}
	
	@PutMapping("/employs/{id}")
	public ResponseEntity<String> updateEmploy(@PathVariable Long id,@RequestBody Employ employDetails){
		String result = employService.updateEmployService(id, employDetails);
			return  ResponseEntity.ok(result);
	}
	
	@DeleteMapping("/employs/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmploy(@PathVariable Long id){
		return employService.deleteEmployService(id);
	}
}