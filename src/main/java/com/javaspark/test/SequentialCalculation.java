package com.javaspark.test;

import java.util.*;

import com.javaspark.function.JavaSparkFunction;
import com.javaspark.model.CalculationResponse;
import com.javaspark.util.JavaSparkHelper;

public class SequentialCalculation {

	
	public static Map<Object, Object> calc(JavaSparkFunction function, int k, int n) throws Exception {
		Map<Object, Object> res = new HashMap<Object, Object>();
		Double[][] matr = new Double[n][k];
		long currTime = System.currentTimeMillis();
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < k; j++) {
				matr[i][j] = function.calcFunctionElement(k, i);
			}
		}
		
		res.put(JavaSparkHelper.SEQUENTIAL_CALC_MSG, JavaSparkHelper.timeFormat(System.currentTimeMillis() - currTime));
		
		return res;
	}
}
