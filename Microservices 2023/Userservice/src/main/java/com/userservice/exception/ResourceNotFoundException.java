package com.userservice.exception;

public class ResourceNotFoundException extends RuntimeException {
	
	public ResourceNotFoundException() {
		super("Resource Not found on the server");
	}
	public ResourceNotFoundException(String message) {
		super(message);
	}
}
