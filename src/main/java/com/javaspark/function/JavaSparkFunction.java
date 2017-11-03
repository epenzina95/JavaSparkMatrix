package com.javaspark.function;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;


public abstract class JavaSparkFunction implements Serializable { 

	public static final int C = 2;
	
	private Double alpha;
	private Double gamma;
	
	
	
	public JavaSparkFunction(Double alpha, Double gamma) {
		this.alpha = alpha;
		this.gamma = gamma;
	}

	
		
	public abstract CalculationResponse calcFunction(JavaSparkContext sc, double k, double n);
	
	public abstract Double calcFunctionElement(double k, double tau) throws Exception;
		
	
	
	public List<List<Double>> rowParallel(JavaSparkContext sc, double k, double n) throws Exception {
		JavaRDD<Double> parallelRows = sc.parallelize(generateArrRows(n));
		List<List<Double>> res = new ArrayList<>();
		for (int s = 0; s < k; s++) {
			res.add(calcFunctionCol(parallelRows, s));
			System.out.println();
		}
		return res;
	}

	public List<List<Double>> colParallel(JavaSparkContext sc, double k, double n) throws Exception {
		JavaRDD<Double> parallelCols = sc.parallelize(generateArrCols(k));
		List<List<Double>> res = new ArrayList<>();
		for (int tau = 1; tau <= n; tau++) {
			res.add(calcFunctionRow(parallelCols, tau));
			System.out.println();
		}
		return res;
	}

	public List<Double> calcFunctionRow(JavaRDD<Double> parallelCols, double tau) throws Exception {
		return parallelCols.map(new Function<Double, Double>() {
			@Override
			public Double call(Double s) throws Exception {
				Double res = calcFunctionElement(s, tau);
				System.out.print(res.doubleValue() + "\t");
				return res;
			}		
		}).top((int) parallelCols.count());
	}
	
	public List<Double> calcFunctionCol(JavaRDD<Double> parallelRows, double k) throws Exception {
		return parallelRows.map(new Function<Double, Double>() {
			@Override
			public Double call(Double s) throws Exception {
				Double res = calcFunctionElement(k, s);
				System.out.print(res.doubleValue() + "\t");
				return res;
			}		
		}).top((int) parallelRows.count());
	}
	
	public Double[][] toArray(List<List<Double>> list, boolean colParallel) {
		Double[][] res = null;
		if (list != null) {
			if (colParallel) {
				res = new Double[list.size()][];
				for (int i = 0; i < res.length; i++) {
					res[i] = new Double[list.get(i).size()];
					for (int j = 0; j < res[i].length; j++)
						res[i][j] = list.get(i).get(j);
				}
			} else {
				res = new Double[list.get(0).size()][];
				for (int i = 0; i < res.length; i++) {
					res[i] = new Double[list.size()];
					for (int j = 0; j < res[i].length; j++)
						res[i][j] = list.get(j).get(i);
				}
			}
		}
		return res;
	}
	
	public List<Double> generateArrCols (double k) {
		Double[] arr = new Double[(int) k];
		for (int i = 0; i < k; i++) {
			arr[i] = (double) (i);
		}
		return Arrays.asList(arr);
	}

	public List<Double> generateArrRows (double n) {
		Double[] arr = new Double[(int) n];
		for (int i = 0; i < n; i++) {
			arr[i] = (double) (i + 1);
		}
		return Arrays.asList(arr);
	}
	
	
	
	public Double getAlpha() {
		return alpha;
	}

	public void setAlpha(Double alpha) {
		this.alpha = alpha;
	}

	public Double getGamma() {
		return gamma;
	}

	public void setGamma(Double gamma) {
		this.gamma = gamma;
	}
}
