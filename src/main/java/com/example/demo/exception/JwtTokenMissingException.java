package com.example.demo.exception;

import javax.naming.AuthenticationException;

public class JwtTokenMissingException extends AuthenticationException {
 public JwtTokenMissingException(String msg) {
	 super(msg);
 }
}
