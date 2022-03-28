package com.example.demo.exception;

import javax.naming.AuthenticationException;


public class JWTTokenMalFormedException extends AuthenticationException{

	public JWTTokenMalFormedException(String message) {
		super(message);
	}

}
