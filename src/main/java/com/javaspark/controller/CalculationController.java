package com.javaspark.controller;

import java.io.File;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
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
	private static final String APP_NAME = "JavaSparkCalc";
	private static final String MASTER = "local";
	private static final String HADOOP_PROPERTY = "hadoop.home.dir";
	private static final String HADOOP_DIR = "winutils";
	
	SparkConf conf = null;
	JavaSparkContext sc = null;
	{
		String hadoopDirAbsPath = new File(HADOOP_DIR).getAbsolutePath();
		System.setProperty(HADOOP_PROPERTY, hadoopDirAbsPath);
		conf = new SparkConf();
		conf.setAppName(APP_NAME);
		conf.setMaster(MASTER);
		sc = new JavaSparkContext(conf);
	}
	
	@RequestMapping(value = CALCULATE_FUNCTION, method = RequestMethod.GET)
	public @ResponseBody Object getODHEntitiesgammalteredByIds(
			@RequestParam(value = "functionNum", required = true) Integer functionNum,
			@RequestParam(value = "iterations", required = true) Integer iterations,
			@RequestParam(value = "alpha", required = false) Double alpha,
			@RequestParam(value = "gamma", required = false) Double gamma) {
		
		CalculationResponse result = new CalculationResponse();
		
		try { 
			
			if (conf == null || sc == null) {
				conf = new SparkConf();
				conf.setAppName(APP_NAME);
				conf.setMaster(MASTER);
				sc = new JavaSparkContext(conf);
			}
			
			switch(functionNum) {
			case 1: 
				result = Calculation.calcFirstFunction(sc, iterations, alpha, gamma);
				break;
			case 2: 
				result = Calculation.calcSecondFunction(sc, iterations, alpha, gamma);
				break;
			case 3: 
				result = Calculation.calcThirdFunction(sc, iterations, alpha, gamma);
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
