package com.javaspark.controller;

/*import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestParam;

import com.holdenkarau.spark.testing.SharedJavaSparkContext;*/
import com.javaspark.function.AnalyticFunction;
import com.javaspark.function.CalculationResponse;
import com.javaspark.function.JavaSparkFunction;
import com.javaspark.helper.ModelComparator;

/*import static org.junit.Assert.*;
import static org.mockito.Mockito.*;*/

import java.io.File;
import java.util.List;
import java.util.Map;

public class CalculationControllerTest { // extends SharedJavaSparkContext {

	private static final String HADOOP_PROPERTY = "hadoop.home.dir";
	private static final String HADOOP_DIR = "winutils";
	
	/*@BeforeClass
	public static void init() {
		String hadoopDirAbsPath = new File(HADOOP_DIR).getAbsolutePath();
		System.setProperty(HADOOP_PROPERTY, hadoopDirAbsPath);
	}
	
	@Test
	public void calculateFunctionTest() {
		int k = 3;
		int n = 3;
		double alpha = 1;
		double gamma = 1;
		JavaSparkFunction func = new AnalyticFunction(alpha, gamma);
		CalculationResponse response = generateResponse();
		when(func.calcFunction(jsc(), k, n)).thenReturn(response);
		assertTrue(ModelComparator.compareResponse(response, func.calcFunction(jsc(), k, n)));
	}*/
	
	private CalculationResponse generateResponse() {
		CalculationResponse response = new CalculationResponse();
		
		response.setStatus(true);
		response.getData().put("FirstFunctionRes", (new Double[3][3]).toString());
		
		return response;
	}
}
