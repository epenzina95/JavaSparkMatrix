package com.javaspark.function;

import java.util.*;


public class Calculation {
	
	public static CalculationResponse calcSecondFunction(int k, double alpha, double fi) {
		CalculationResponse result = new CalculationResponse();
		
		try {
			// TODO
		} catch(Exception e) {
			result.getErrors().add(e.getMessage());
			e.printStackTrace();
		}
		
		return result;
	}

	public static CalculationResponse calcThirdFunction(Integer iterations, Double alpha, Double fi) {
		CalculationResponse result = new CalculationResponse();

		try {
			// TODO
		} catch(Exception e) {
			result.getErrors().add(e.getMessage());
			e.printStackTrace();
		}

		return result;
	}
}
