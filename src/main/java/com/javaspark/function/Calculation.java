package com.javaspark.function;

import java.util.*;

import org.apache.commons.math3.analysis.integration.TrapezoidIntegrator;
import org.apache.commons.math3.special.Gamma;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;


public class Calculation {
	
	
	public static CalculationResponse calcFirstFunction(JavaSparkContext sc, int k, double alpha, double gamma) {
		CalculationResponse response = new CalculationResponse();
		
		try {
			double c = 2;
			double tau = 1; // ????
			
			JavaRDD<Double> srcParallelList = sc.parallelize(generateArr(k));
			Double res = srcParallelList.map(new Function<Double, Double>() {
				@Override
				public Double call(Double s) throws Exception {
					return countCombinations(k, s) * countCombinations(k + s + alpha, s + alpha) * Math.pow(-1, s) * Math.exp(-(2 * s + alpha + 1) * c * gamma * tau / 2);
				}
			}).reduce((a, b) -> a + b);
			
			response.getData().put("FirstFunctionRes", res); // вообще тут должны бы быть точки для графика.
			
		} catch(Exception e) {
			response.getErrors().add(e.getMessage());
			e.printStackTrace();
		}
		
		return response;
	}
	
	private static Double countCombinations(double n, double k) throws Exception {
		return Gamma.gamma(n + 1) / (Gamma.gamma(k + 1) + Gamma.gamma(n - k + 1));
	}
	
	public static CalculationResponse calcSecondFunction(JavaSparkContext sc, int k, double alpha, double gamma) {
		CalculationResponse response = new CalculationResponse();
		
		try {
			Double res = (k == 0) ? calcSecondFunctionForZero(sc, alpha, gamma) : calcSecondFunctionNonZero(sc, k, alpha, gamma);
			response.getData().put("SecondFunctionRes", res); // вообще тут должны бы быть точки для графика.
		} catch(Exception e) {
			response.getErrors().add(e.getMessage());
			e.printStackTrace();
		}
		
		return response;
	}

	private static List<Double> generateArr (int k) {
		Double[] arr = new Double[k];
		for (int i = 0; i < k; i++) {
			arr[i] = (double) (i + 1);
		}
		return Arrays.asList(arr);
	}
	
	private static Double f(double x, JavaSparkContext sc, int k, double alpha, double gamma) throws Exception {
		
		JavaRDD<Double> srcParallelList = sc.parallelize(generateArr(k));
		Double midRes = srcParallelList.map(new Function<Double, Double>() {
			@Override
			public Double call(Double s) throws Exception {
				return Math.atan((2 * k + alpha + 1) * Math.tan(x) / (2 * s + alpha + 1));
			}
		}).reduce((a, b) -> a + b);
		
		int c = 2;
		int tau = 1; // ???? what is tau
		
		return Math.cos(x + 2 * midRes) * (Math.cos((2 * k + alpha + 1) * c * gamma * tau / (2 * Math.tan(x)))) / Math.cos(x);
	}
	
	private static Double calcSecondFunctionNonZero(JavaSparkContext sc, int k, double alpha, double gamma) throws Exception {
		
		// Simpson's method for integral approximation
		double a = 0;
		double b = Math.PI / 2;
		
		JavaRDD<Double> parallelList = sc.parallelize(Arrays.asList(a, (a + b) / 2, (a + b) / 2, (a + b) / 2, (a + b) / 2, b));
		Double res = (b - a) / 6 * parallelList.map(new Function<Double, Double>() {
			@Override
			public Double call(Double x) throws Exception {
				return f(x, sc, k, alpha, gamma);
			}
			
		}).reduce((x, y) -> x + y);
		
		return res;
		//return (b - a) / 6 * (f(a, sc, k, alpha, gamma) + 4 * f((a + b) / 2, sc, k, alpha, gamma) + f(b, sc, k, alpha, gamma));
	}

	private static Double calcSecondFunctionForZero(JavaSparkContext sc, double alpha, double gamma) {
		
		double a = 0;
		double b = Math.PI / 2;
		int c = 2;
		double tau = 1; // ???
		
		JavaRDD<Double> parallelList = sc.parallelize(Arrays.asList(a, (a + b) / 2, (a + b) / 2, (a + b) / 2, (a + b) / 2, b));
		Double res = (b - a) / 6 * parallelList.map(new Function<Double, Double>() {
			@Override
			public Double call(Double x) throws Exception {
				return Math.cos((alpha + 1) * c * gamma * tau / (2 * Math.tan(x)));
			}
			
		}).reduce((x, y) -> x + y);
		
		return res;
	}

	
	public static CalculationResponse calcThirdFunction(JavaSparkContext sc, Integer iterations, Double alpha, Double gamma) {
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
