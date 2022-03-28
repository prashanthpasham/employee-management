package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.LoginDAO;
import com.example.demo.model.ContactUs;
import com.example.demo.model.Login;
import com.example.demo.model.LoginActivities;
import com.example.demo.repo.ContactUsRepository;
import com.example.demo.repo.LoginActivitiesRepository;
import com.example.demo.repo.LoginRepository;
import com.example.demo.util.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
@Transactional
public class LoginService {
	
	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private LoginActivitiesRepository loginActivitiesRepository;
	@Autowired
	private ContactUsRepository contactUsRepo;
	
	public LoginDAO getLoginDetailsService(String emailId, String password) { 
		LoginDAO logindao = new LoginDAO();
		
		Login fetchUserDetails = loginRepository.findByEmailId(emailId);
		
		if(fetchUserDetails != null) {
			
			
			
			LoginActivities fetchLoginStatus = loginActivitiesRepository.findByUserId(fetchUserDetails.getId());
			
			if(fetchLoginStatus == null) {
				
				try {
					Authentication authentication = authenticationManager
							.authenticate(new UsernamePasswordAuthenticationToken(emailId, password));

					SecurityContextHolder.getContext().setAuthentication(authentication);
					logindao = setLoginResponse(fetchLoginStatus, fetchUserDetails, logindao);
				} catch (Exception e) {
					e.printStackTrace();
					logindao.setMessage("Password incorrect");
				}
					//System.out.println("credentials>>"+authentication.getPrincipal());
					
				
			}
			else {
				if(fetchLoginStatus.getStatus().equalsIgnoreCase("Login")) {
					logindao.setMessage("You can't login again. Your session is active");
				}
				else if(fetchLoginStatus.getStatus() == "Logout" || fetchLoginStatus.getStatus().equalsIgnoreCase("Logout")){
					try {
					Authentication authentication = authenticationManager
							.authenticate(new UsernamePasswordAuthenticationToken(emailId, password));

					SecurityContextHolder.getContext().setAuthentication(authentication);
					 setLoginResponse(fetchLoginStatus, fetchUserDetails, logindao);
					} catch (Exception e) {
						//e.printStackTrace();
						logindao.setMessage("Password incorrect");
					}
				}
			}

		}
		else {
			logindao.setMessage("User not exist. Please register");
		}
			
		return logindao;
	}

	private LoginDAO setLoginResponse(LoginActivities fetchLoginStatus, Login fetchLoginDetails, LoginDAO logindao) {
		logindao.setId(fetchLoginDetails.getId());
		logindao.setUsername(fetchLoginDetails.getUsername());
		logindao.setRole(fetchLoginDetails.getRole());
		logindao.setMessage("Login success");
		
		String token = generateToken(fetchLoginDetails);
		

		logindao.setToken(token);
		
		fetchLoginStatus = loginActivitiesSave(logindao, fetchLoginStatus);
		
		return logindao;
	}
	
	private LoginActivities loginActivitiesSave(LoginDAO logindao, LoginActivities loginActivitiesData) {
		if(loginActivitiesData == null){
			loginActivitiesData = new LoginActivities();
		}
		
		loginActivitiesData.setStatus("Login");
		loginActivitiesData.setToken(logindao.getToken());
		loginActivitiesData.setUserId(logindao.getId());
		
		return loginActivitiesRepository.save(loginActivitiesData);
	}

	private String generateToken(Login fetchLoginDetails) {
		return jwtUtils.generateJwt(fetchLoginDetails);
	}

	public LoginDAO signupService(Login loginData) {
		Login fetchUserDetails = loginRepository.findByEmailId(loginData.getEmailId());
		
		LoginDAO login = new LoginDAO();
		
		if(fetchUserDetails == null) {
			Login saveLoginData = new Login();
			
			saveLoginData.setUsername(loginData.getUsername());
			saveLoginData.setPassword(encoder.encode(loginData.getPassword()));
			saveLoginData.setRole("user");
			saveLoginData.setEmailId(loginData.getEmailId());
			
			saveLoginData = loginRepository.save(saveLoginData);
			
			if(saveLoginData != null) {
				login.setMessage("Signup successfully");
			}
			else {
				login.setMessage("Signup error");
			}
		}
		else {
			login.setMessage("User already exist");
		}
		
		return login;
	}
	
	public LoginDAO logout(Long userId) {
		LoginActivities loginActivities = loginActivitiesRepository.findByUserId(userId);
		LoginDAO loginDAO = new LoginDAO();
		if (loginActivities != null) {
			loginActivities.setStatus("Logout");
			loginActivities.setToken("");

			loginActivities = loginActivitiesRepository.save(loginActivities);

			loginDAO.setToken(loginActivities.getToken());
		}
		loginDAO.setMessage("Logout success");
		return loginDAO;
	}

	public List<Login> getUsersList() {
		// TODO Auto-generated method stub
		return loginRepository.findAll();
	}

	public String validateEmail(String emailId) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode obj = mapper.createObjectNode();
		try {
			Login login = loginRepository.findByEmailId(emailId);
			if (login != null) {
				obj.put("message", "success");
				obj.put("id", login.getId());
			} else {
				obj.put("message", "fail");
				obj.put("id", 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj.toPrettyString();
	}

	public String resetPassword(long id, String newPwd) {
		try {
			Login login = loginRepository.getById(id);
			login.setPassword(encoder.encode(newPwd));
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "fail";
	}

	public String updateRole(long id, String newRole) {
		try {
			Login login = loginRepository.getById(id);
			login.setRole(newRole);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "fail";
	}


	public String submitQuery(ContactUs contact) {
		try {
			contactUsRepo.save(contact);
			return "Response Submitted Successfully!";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "fail";
	}
}
