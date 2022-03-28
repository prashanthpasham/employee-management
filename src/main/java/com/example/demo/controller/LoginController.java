package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.LoginDAO;
import com.example.demo.model.ContactUs;
import com.example.demo.model.Login;
import com.example.demo.service.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/login")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@PostMapping("/getLoginDetails")
	public LoginDAO getLoginDetails(@RequestBody Login loginData) {
		return loginService.getLoginDetailsService(loginData.getEmailId(), loginData.getPassword());
	}

	@PostMapping("/signup")
	public LoginDAO signup(@RequestBody Login loginData) {
		return loginService.signupService(loginData);
	}

	@PostMapping("/logoutById")
	public LoginDAO logoutById(@RequestBody Login loginData) {
		return loginService.logout(loginData.getId());
	}

	@GetMapping("/users")
	public List<Login> usersList() {
		return loginService.getUsersList();
	}

	@GetMapping("/validateEmail/{emailId}")
	public String validateEmail(@PathVariable("emailId") String emailId) {
		return loginService.validateEmail(emailId);
	}

	@PutMapping(value = "/reset-password", produces = "text/plain")
	public String resetPassword(@RequestBody String json) {
		try {
			System.out.println(json);
			ObjectMapper mapper = new ObjectMapper();
			Map map = mapper.readValue(json, Map.class);
			return this.loginService.resetPassword(Long.parseLong(map.get("id").toString()),
					map.get("password").toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "fail";
	}

	@PutMapping(value = "/role-update", produces = "text/plain")
	public String updateRole(@RequestBody String json) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map map = mapper.readValue(json, Map.class);
			return this.loginService.updateRole(Long.parseLong(map.get("id").toString()), map.get("role").toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "fail";
	}
	@PostMapping(value="/submit-query", produces = "text/plain")
	public String submitQuery(@RequestBody ContactUs contact) {
		try {
		
			return this.loginService.submitQuery(contact);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Bad Request";
	}
}
