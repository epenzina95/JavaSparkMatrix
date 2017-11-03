package com.javaspark.controller;

/*import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestParam;

import com.holdenkarau.spark.testing.SharedJavaSparkContext;*/
import com.javaspark.function.AnalyticFunction;
import com.javaspark.function.CalculationResponse;
import com.javaspark.function.JavaSparkFunction;

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
	public void calculateFunction() {
		int k = 3;
		int n = 3;
		double alpha = 1;
		double gamma = 1;
		JavaSparkFunction func = new AnalyticFunction(alpha, gamma);
		CalculationResponse response = generateResponse();
		when(func.calcFunction(jsc(), k, n)).thenReturn(response);
		assertTrue(compareResponse(response, func.calcFunction(jsc(), k, n)));
	}*/
	
	private CalculationResponse generateResponse() {
		CalculationResponse response = new CalculationResponse();
		
		response.setStatus(true);
		response.getData().put("FirstFunctionRes", (new Double[3][3]).toString());
		
		return response;
	}
	
	private boolean compareResponse(CalculationResponse a, CalculationResponse b) {
		if (a == b)
			return true;
		else {
			if (a.getStatus() && b.getStatus()) {
				if (!compareLists(a.getErrors(), b.getErrors())) return false;
				if (!compareMaps(a.getData(), b.getData())) return false;
				return true;
			}
			return false;
		}
	}
	
	private boolean compareMaps(Map<?, ?> a, Map<?, ?> b) {
		if (a != null && b != null) {
			if (a.size() == b.size()) {
				for (Object key : a.keySet()) {
					if (a.get(key) instanceof Double[][] && b.get(key) instanceof Double[][]) {
						Double[][] tmpA = ((Double[][])a.get(key));
						Double[][] tmpB = ((Double[][])b.get(key));
						for (int i = 0; i < tmpA.length; i++) {
							for (int j = 0; j < tmpA[i].length; j++)
								if (tmpA[i][j] != tmpB[i][j]) return false;
						}
					} else {
						if (a.get(key) instanceof String && b.get(key) instanceof String)
							if (!a.get(key).equals(b.get(key))) return false;
					}
				}
				return true;
			}
		}
		return (a == null && b == null);
	}
	
	private boolean compareLists (List<?> a, List<?> b) {
		if (a != null && b != null) {
			if (a.size() == b.size()) {
				for (int i = 0; i < a.size(); i++) {
					if (a.get(i) instanceof String && a.get(i) instanceof String)
						if (!a.get(i).equals(b.get(i))) return false;
				}
				return true;
			}
		}
		return (a == null && b == null);
	}
}
