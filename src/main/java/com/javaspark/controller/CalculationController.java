package com.javaspark.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.javaspark.function.*;



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
	public @ResponseBody Object calculateFunction(
			@RequestParam(value = "functionNum", required = true) Integer functionNum,
			@RequestParam(value = "k", required = true) Integer k,
			@RequestParam(value = "n", required = true) Integer n,
			@RequestParam(value = "alpha", required = false) Double alpha,
			@RequestParam(value = "gamma", required = false) Double gamma) {
		
		CalculationResponse result = new CalculationResponse();
		JavaSparkFunction func = null;
		
		try { 
			
			if (conf == null || sc == null) {
				conf = new SparkConf();
				conf.setAppName(APP_NAME);
				conf.setMaster(MASTER);
				sc = new JavaSparkContext(conf);
			}
			
			long currTime = System.currentTimeMillis();
			
			switch(functionNum) {
			case 1: 
				func = new AnalyticFunction(alpha, gamma);
				result = func.calcFunction(sc, k, n);
				break;
			case 2: 
				func = new IntegralFunction(alpha, gamma);
				result = func.calcFunction(sc, k, n);
				break;
			case 3: 
				func = new RecurrenceFunction(alpha, gamma);
				result = func.calcFunction(sc, k, n);
				break;
			default: 
				result.getErrors().add("Ошибка: неверный тип функции");
			break;
			}
			
			result.getData().put("Вычисление матрицы", LoadingTest.timeFormat(System.currentTimeMillis() - currTime));
			LoadingTest.doTests(result, (Double[][])result.getData().get("resMatr"));
			result.getData().remove("resMatr");
			
		} catch(Exception e) {
			result.getErrors().add(e.getMessage());
			e.printStackTrace();
		}
		
		return result;
	}
}
