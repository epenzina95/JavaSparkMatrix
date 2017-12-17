package com.javaspark.model;

import java.util.*;

public class CalculationResponse {

	private List<String> errors;
	private Map<Object, Object> data;
	private boolean isSuccessfull;
	
	
	public CalculationResponse() {
		this.errors = new ArrayList<>();
		this.data = new HashMap<>();
		isSuccessfull = false;
	}
	
	public CalculationResponse(boolean status) {
		this.isSuccessfull = status;
		this.errors = new ArrayList<>();
		this.data = new HashMap<>();
	}
	
	public List<String> getErrors() {
		return this.errors;
	}
	
	public Map<Object, Object> getData() {
		return this.data;
	}
	
	public boolean getStatus() {
		return this.isSuccessfull;
	}
	
	public void setStatus(boolean isSuccessfull) {
		this.isSuccessfull = isSuccessfull;
	}
}
