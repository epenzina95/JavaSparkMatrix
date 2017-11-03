package com.javaspark.function;

import java.util.*;

import org.apache.commons.math3.analysis.integration.TrapezoidIntegrator;
import org.apache.commons.math3.special.Gamma;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;


public class Calculation {
	
	public static final int C = 2;
	public static final double A = 0;
	public static final double B = Math.PI/2;
	
	
	/*		FIRST FUNCTION CALCULATION		*/
	public static CalculationResponse calcFirstFunction(JavaSparkContext sc, int k, int n, double alpha, double gamma) {
		CalculationResponse response = new CalculationResponse();
		
		try {
			/*JavaRDD<Double> parallelRows = sc.parallelize(generateArrRows(n));
			JavaRDD<Double> parallelCols = sc.parallelize(generateArrCols(k));
			JavaRDD<JavaRDD<Double>> res = parallelRows.map(new Function<Double, JavaRDD<Double>>() { // ???
				@Override
				public JavaRDD<Double> call(Double tau) throws Exception {
					
					return parallelCols.map(new Function<Double, Double>() {
						@Override
						public Double call(Double s) throws Exception {
							return countFirstFunctionElement(sc, s, alpha, gamma, tau);
						}
						
					});
				}
			});*/
			
			JavaRDD<Double> parallelRows = sc.parallelize(generateArrRows(n));
			JavaRDD<Double> parallelCols = sc.parallelize(generateArrCols(k));
			List<List<Double>> res = new ArrayList<>();
			for (int tau = 1; tau <= n; tau++) {
				res.add(calcFirstFunctionRow(parallelCols, sc, tau, alpha, gamma));
				System.out.println();
			}
			
			response.getData().put("FirstFunctionRes", res.toString()); // вообще тут должны бы быть точки для графика.
			
		} catch(Exception e) {
			response.getErrors().add(e.getMessage());
			e.printStackTrace();
		}
		
		return response;
	}
	
	private static List<Double> calcFirstFunctionRow(JavaRDD<Double> parallelCols, JavaSparkContext sc, int tau, double alpha, double gamma) {
		return parallelCols.map(new Function<Double, Double>() {
			@Override
			public Double call(Double s) throws Exception {
				Double res = countFirstFunctionElement(sc, s, alpha, gamma, tau);
				System.out.print(res.doubleValue() + "\t");
				return res;
			}		
		}).top((int) parallelCols.count());
	}

	private static Double countFirstFunctionElement(JavaSparkContext sc, double k, double alpha, double gamma, double tau) throws Exception {
		JavaRDD<Double> srcParallelList = sc.parallelize(generateArrCols(k));
		Double res = srcParallelList.map(new Function<Double, Double>() {
			@Override
			public Double call(Double s) throws Exception {
				return countCombinations(k, s) * countCombinations(k + s + alpha, s + alpha) * Math.pow(-1, s) * Math.exp(-(2 * s + alpha + 1) * C * gamma * tau / 2);
			}
		}).reduce((a, b) -> a + b);
		
		return res;
	}
	
	private static Double countCombinations(double n, double k) throws Exception {
		return Gamma.gamma(n + 1) / (Gamma.gamma(k + 1) + Gamma.gamma(n - k + 1));
	}
	
	
	
	/* 		SECOND FUNCTION CALCULATION		*/
	public static CalculationResponse calcSecondFunction(JavaSparkContext sc, double k, double n, double alpha, double gamma) {
		CalculationResponse response = new CalculationResponse();
		
		try {
			/*JavaRDD<Double> parallelRows = sc.parallelize(generateArrRows(n));
			JavaRDD<Double> parallelCols = sc.parallelize(generateArrCols(k));
			JavaRDD<JavaRDD<Double>> res = parallelRows.map(new Function<Double, JavaRDD<Double>>() {
				@Override
				public JavaRDD<Double> call(Double tau) throws Exception {
				
					return parallelCols.map(new Function<Double, Double>() {
						@Override
						public Double call(Double s) throws Exception {
							return (k == 0) ? calcSecondFunctionForZeroElement(sc, tau, alpha, gamma) : calcSecondFunctionNonZeroElement(sc, s, tau, alpha, gamma);
						}
						
					});
				}
			});*/
			JavaRDD<Double> parallelRows = sc.parallelize(generateArrRows(n));
			JavaRDD<Double> parallelCols = sc.parallelize(generateArrCols(k));
			List<List<Double>> res = new ArrayList<>();
			for (int tau = 1; tau <= n; tau++) {
				res.add(calcSecondFunctionRow(parallelCols, sc, tau, alpha, gamma));
				System.out.println();
			}
			response.getData().put("SecondFunctionRes", res.toString()); // вообще тут должны бы быть точки для графика.
		} catch(Exception e) {
			response.getErrors().add(e.getMessage());
			e.printStackTrace();
		}
		
		return response;
	}
	
	private static List<Double> calcSecondFunctionRow(JavaRDD<Double> parallelCols, JavaSparkContext sc, int tau, double alpha, double gamma) {
		return parallelCols.map(new Function<Double, Double>() {
			@Override
			public Double call(Double s) throws Exception {
				Double res = (s == 0) ? calcSecondFunctionForZeroElement(sc, tau, alpha, gamma) : calcSecondFunctionNonZeroElement(sc, s, tau, alpha, gamma);
				System.out.print(res.doubleValue() + "\t");
				return res;
			}		
		}).top((int) parallelCols.count());
	}

	private static List<Double> generateArrCols (double k) {
		Double[] arr = new Double[(int) k];
		for (int i = 0; i < k; i++) {
			arr[i] = (double) (i);
		}
		return Arrays.asList(arr);
	}

	private static List<Double> generateArrRows (double n) {
		Double[] arr = new Double[(int) n];
		for (int i = 0; i < n; i++) {
			arr[i] = (double) (i + 1);
		}
		return Arrays.asList(arr);
	}
	
	private  static Double midRes(double x, double alpha) {
		return Math.atan((2 + alpha + 1) * Math.tan(x) / (alpha + 1)) + Math.atan((2 + alpha + 1) * Math.tan(x) / (2 + alpha + 1));
	}
	
	private static Double calcSecondFunctionNonZeroElement(JavaSparkContext sc, double k, double tau, double alpha, double gamma) throws Exception {
		
		// Simpson's method for integral approximation
		JavaRDD<Double> parallelList = sc.parallelize(Arrays.asList(A, (A + B) / 2, (A + B) / 2, (A + B) / 2, (A + B) / 2, B));
		return (B - A) / 6 * parallelList.map(new Function<Double, Double>() {
			@Override
			public Double call(Double x) throws Exception {
				return Math.cos(x + 2 * midRes(x, alpha)) * (Math.cos((2 * k + alpha + 1) * C * gamma * tau / (2 * Math.tan(x)))) / Math.cos(x);
			}
			
		}).reduce((x, y) -> x + y);
	}

	private static Double calcSecondFunctionForZeroElement(JavaSparkContext sc, double tau, double alpha, double gamma) {
		JavaRDD<Double> parallelList = sc.parallelize(Arrays.asList(A, (A + B) / 2, (A + B) / 2, (A + B) / 2, (A + B) / 2, B));
		return (B - A) / 6 * parallelList.map(new Function<Double, Double>() {
			@Override
			public Double call(Double x) throws Exception {
				return Math.cos((alpha + 1) * C * gamma * tau / (2 * Math.tan(x)));
			}
		}).reduce((x, y) -> x + y);
	}

	
	
	/*		THIRD FUNCTION CALCULATION		*/
	public static CalculationResponse calcThirdFunction(JavaSparkContext sc, double k, int n, double alpha, double gamma) {
		CalculationResponse response = new CalculationResponse();

		try {
			JavaRDD<Double> parallelRows = sc.parallelize(generateArrRows(n));
			JavaRDD<Double> parallelCols = sc.parallelize(generateArrCols(k));
			List<List<Double>> res = new ArrayList<>();
			for (int tau = 1; tau <= n; tau++) {
				res.add(calcThirdFunctionRow(parallelCols, tau, alpha, gamma));
				System.out.println();
			}
			response.getData().put("ThirdFunctionRes", res.toString()); // вообще тут должны бы быть точки для графика.
		} catch(Exception e) {
			response.getErrors().add(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}
	
	private static List<Double> calcThirdFunctionRow(JavaRDD<Double> parallelCols, int tau, double alpha, double gamma) {
		return parallelCols.map(new Function<Double, Double>() {
			@Override
			public Double call(Double s) throws Exception {
				Double res = calcThirdFunctionElement(s, tau, alpha, gamma);
				System.out.print(res.doubleValue() + "\t");
				return res;
			}		
		}).top((int) parallelCols.count());
	}
	
	private static double calcThirdFunctionElement(double k, double tau, double alpha, double gamma) throws Exception {
		if (k > 2) {
			return firstConst(k, tau, alpha, gamma) * calcThirdFunctionElement(k - 1, tau, alpha, gamma) - 
					secondConst(k, tau, alpha, gamma) * calcThirdFunctionElement(k - 2, tau, alpha, gamma);
		} else return 0;
	}
	
	private static double firstConst(double k, double tau, double alpha, double gamma) {
		return ((alpha + 2 * k) * (alpha + 2 * k - 2) * (1 - 2 * Math.exp(-1 * C * gamma * tau)) + alpha * alpha) / 
				(2 * k * (alpha + 2 * k - 2) * (alpha + k)) * (alpha + 2 * k - 1);
	}
	
	private static double secondConst(double k, double tau, double alpha, double gamma) {
		return ((alpha + k - 1) * (k - 1) * (alpha + 2 * k)) / (k * (alpha + 2 * k - 2) * (alpha + k));
	}
}
