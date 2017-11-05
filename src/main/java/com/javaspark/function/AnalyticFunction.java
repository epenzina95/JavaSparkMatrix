package com.javaspark.function;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.math3.special.Gamma;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

public class AnalyticFunction extends JavaSparkFunction implements Serializable {
	
	@Inject
	public AnalyticFunction(Double alpha, Double gamma) {
		super(alpha, gamma);
	}

	
	@Override
	public CalculationResponse calcFunction(JavaSparkContext sc, double k, double n) {
		CalculationResponse response = new CalculationResponse();
		
		try {
			response.getData().put("FirstFunctionRes", 
					((n > k) ? this.toArray(rowParallel(sc, k, n), false) : this.toArray(colParallel(sc, k, n), true))); // вообще тут должны бы быть точки для графика.
			
		} catch(Exception e) {
			response.getErrors().add(e.getMessage());
			e.printStackTrace();
		}
		
		return response;
	}

	@Override
	public Double calcFunctionElement(double k, double tau) throws Exception {
		double res = 0;
		for (int s = 0; s < k; s++) {
			res += countCombinations(k, s) * countCombinations(k + s + this.getAlpha(), s + this.getAlpha()) * Math.pow(-1, s) * Math.exp(-(2 * s + this.getAlpha() + 1) * this.C * this.getGamma() * tau / 2);
		}
		return res;
	}
	
	private static Double countCombinations(double n, double k) throws Exception {
		return Gamma.gamma(n + 1) / (Gamma.gamma(k + 1) + Gamma.gamma(n - k + 1));
	}
}
