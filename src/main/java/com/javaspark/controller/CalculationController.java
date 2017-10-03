package com.javaspark.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.javaspark.function.Calculation;
import com.javaspark.function.CalculationResponse;

@RestController
public class CalculationController {

	private static final String CALCULATE_FUNCTION = "/calc";
	
	@RequestMapping(value = CALCULATE_FUNCTION, method = RequestMethod.GET)
	public @ResponseBody Object getODHEntitiesFilteredByIds(
			@RequestParam(value = "functionNum", required = true) Integer functionNum,
			@RequestParam(value = "iterations", required = true) Integer iterations,
			@RequestParam(value = "alpha", required = false) Double alpha,
			@RequestParam(value = "fi", required = false) Double fi) {
		
		CalculationResponse result = new CalculationResponse();
		
		try { 
			switch(functionNum) {
			case 1: 
				// Катина функция
				break;
			case 2: 
				result = Calculation.calcSecondFunction(iterations, alpha, fi);
				break;
			case 3: 
				// Настина функция
				result = Calculation.calcThirdFunction(iterations, alpha, fi);
				break;
			default: 
				result.getErrors().add("Ошибка: неверный тип функции");
			break;
			}
		} catch(Exception e) {
			result.getErrors().add(e.getMessage());
			e.printStackTrace();
		}
		
		return result;
	}
}
