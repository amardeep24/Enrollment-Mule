package com.amardeep.constants;

public enum AppConstants {
	ID("id"),
	NAME("name"),
	GPA("gpa"),
	FLAG("flag"),
	EXCEPTION_MESSAGE("Invalid payload");
	
	private final String value;
	AppConstants(String value){
		this.value=value;
	}
	@Override
	public String toString(){
		return this.value;
	}
}
